// Utilitades globales para la aplicación bancaria
class BancoApp {
    constructor() {
        this.init();
    }

    init() {
        this.setupFormValidation();
        this.setupTableSorting();
        this.setupFilterToggle();
        this.setupConfirmations();
        this.setupTooltips();
        this.setupLoadingStates();
        this.setupAnimations();
    }

    // Validación de formularios
    setupFormValidation() {
        const forms = document.querySelectorAll('form');
        forms.forEach(form => {
            form.addEventListener('submit', (e) => {
                if (!this.validateForm(form)) {
                    e.preventDefault();
                }
            });

            // Validación en tiempo real
            const inputs = form.querySelectorAll('input, select, textarea');
            inputs.forEach(input => {
                input.addEventListener('blur', () => this.validateField(input));
                input.addEventListener('input', () => this.clearFieldError(input));
            });
        });
    }

    validateForm(form) {
        let isValid = true;
        const inputs = form.querySelectorAll('input[required], select[required], textarea[required]');

        inputs.forEach(input => {
            if (!this.validateField(input)) {
                isValid = false;
            }
        });

        return isValid;
    }

    validateField(field) {
        const value = field.value.trim();
        let isValid = true;
        let errorMessage = '';

        // Limpiar errores previos
        this.clearFieldError(field);

        // Validar campo requerido
        if (field.hasAttribute('required') && !value) {
            errorMessage = 'Este campo es requerido';
            isValid = false;
        }

        // Validaciones específicas por tipo
        if (value && field.type === 'email') {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(value)) {
                errorMessage = 'Ingrese un email válido';
                isValid = false;
            }
        }

        if (value && field.name === 'cedula') {
            // Validación básica de cédula costarricense
            const cedulaRegex = /^\d{1}-\d{4}-\d{4}$|^\d{9}$/;
            if (!cedulaRegex.test(value)) {
                errorMessage = 'Formato de cédula inválido (ej: 1-1234-5678)';
                isValid = false;
            }
        }

        if (value && field.type === 'password' && value.length < 6) {
            errorMessage = 'La contraseña debe tener al menos 6 caracteres';
            isValid = false;
        }

        if (!isValid) {
            this.showFieldError(field, errorMessage);
        }

        return isValid;
    }

    showFieldError(field, message) {
        field.classList.add('error');
        let errorDiv = field.parentNode.querySelector('.error-message');
        if (!errorDiv) {
            errorDiv = document.createElement('div');
            errorDiv.className = 'error-message';
            field.parentNode.appendChild(errorDiv);
        }
        errorDiv.textContent = message;
    }

    clearFieldError(field) {
        field.classList.remove('error');
        const errorDiv = field.parentNode.querySelector('.error-message');
        if (errorDiv) {
            errorDiv.remove();
        }
    }

    // Ordenamiento de tablas
    setupTableSorting() {
        const tables = document.querySelectorAll('table');
        tables.forEach(table => {
            const headers = table.querySelectorAll('th');
            headers.forEach((header, index) => {
                if (!header.classList.contains('no-sort')) {
                    header.style.cursor = 'pointer';
                    header.addEventListener('click', () => this.sortTable(table, index));
                }
            });
        });
    }

    sortTable(table, column) {
        const tbody = table.querySelector('tbody');
        const rows = Array.from(tbody.querySelectorAll('tr'));
        const header = table.querySelectorAll('th')[column];
        const isAscending = !header.classList.contains('sort-desc');

        rows.sort((a, b) => {
            const aVal = a.cells[column].textContent.trim();
            const bVal = b.cells[column].textContent.trim();

            // Intentar comparar como números
            const aNum = parseFloat(aVal.replace(/[^\d.-]/g, ''));
            const bNum = parseFloat(bVal.replace(/[^\d.-]/g, ''));

            if (!isNaN(aNum) && !isNaN(bNum)) {
                return isAscending ? aNum - bNum : bNum - aNum;
            }

            // Comparar como texto
            return isAscending ? aVal.localeCompare(bVal) : bVal.localeCompare(aVal);
        });

        // Limpiar clases de ordenamiento
        table.querySelectorAll('th').forEach(th => {
            th.classList.remove('sort-asc', 'sort-desc');
        });

        // Agregar clase de ordenamiento
        header.classList.add(isAscending ? 'sort-asc' : 'sort-desc');

        // Reordenar filas
        rows.forEach(row => tbody.appendChild(row));
    }

    // Toggle para mostrar/ocultar filtros
    setupFilterToggle() {
        const filterToggle = document.querySelector('#toggle-filters');
        const filtersContainer = document.querySelector('.filters');

        if (filterToggle && filtersContainer) {
            filterToggle.addEventListener('click', () => {
                filtersContainer.classList.toggle('hidden');
                filterToggle.textContent = filtersContainer.classList.contains('hidden')
                    ? 'Mostrar Filtros' : 'Ocultar Filtros';
            });
        }
    }

    // Confirmaciones para acciones importantes
    setupConfirmations() {
        const dangerButtons = document.querySelectorAll('.btn-danger, button[value="RECHAZADA"], button[value="INCUMPLIDO"]');
        dangerButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                const action = button.textContent.trim();
                if (!confirm(`¿Está seguro que desea ${action.toLowerCase()}?`)) {
                    e.preventDefault();
                }
            });
        });
    }

    // Tooltips simples
    setupTooltips() {
        const elementsWithTooltip = document.querySelectorAll('[data-tooltip]');
        elementsWithTooltip.forEach(element => {
            element.addEventListener('mouseenter', (e) => {
                this.showTooltip(e.target, e.target.dataset.tooltip);
            });
            element.addEventListener('mouseleave', () => {
                this.hideTooltip();
            });
        });
    }

    showTooltip(element, text) {
        const tooltip = document.createElement('div');
        tooltip.className = 'tooltip';
        tooltip.textContent = text;
        document.body.appendChild(tooltip);

        const rect = element.getBoundingClientRect();
        tooltip.style.left = rect.left + (rect.width / 2) - (tooltip.offsetWidth / 2) + 'px';
        tooltip.style.top = rect.top - tooltip.offsetHeight - 5 + 'px';
    }

    hideTooltip() {
        const tooltip = document.querySelector('.tooltip');
        if (tooltip) {
            tooltip.remove();
        }
    }

    // Estados de carga para formularios
    setupLoadingStates() {
        const forms = document.querySelectorAll('form');
        forms.forEach(form => {
            form.addEventListener('submit', (e) => {
                const submitButton = form.querySelector('button[type="submit"]');
                if (submitButton) {
                    submitButton.disabled = true;
                    submitButton.innerHTML = '<span class="spinner"></span> Procesando...';
                }
            });
        });
    }

    // Animaciones de entrada
    setupAnimations() {
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('fade-in');
                    observer.unobserve(entry.target);
                }
            });
        }, observerOptions);

        // Observar cards y secciones
        const animatedElements = document.querySelectorAll('.card, .main-content, table');
        animatedElements.forEach(el => observer.observe(el));
    }

    // Utilidades de formateo
    static formatCurrency(amount) {
        return new Intl.NumberFormat('es-CR', {
            style: 'currency',
            currency: 'CRC'
        }).format(amount);
    }

    static formatPercentage(value) {
        return new Intl.NumberFormat('es-CR', {
            style: 'percent',
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        }).format(value);
    }

    static formatDate(date) {
        return new Intl.DateTimeFormat('es-CR').format(new Date(date));
    }

    // Mostrar notificaciones
    static showNotification(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `alert alert-${type} notification`;
        notification.textContent = message;

        document.body.appendChild(notification);

        setTimeout(() => {
            notification.classList.add('fade-out');
            setTimeout(() => notification.remove(), 300);
        }, 3000);
    }

    // Manejo de errores AJAX
    static handleAjaxError(error) {
        console.error('Error AJAX:', error);
        this.showNotification('Ocurrió un error. Por favor, intente nuevamente.', 'error');
    }
}

// Funciones específicas para páginas

// Calculadora de préstamos
class LoanCalculator {
    constructor() {
        this.setupCalculator();
    }

    setupCalculator() {
        const form = document.querySelector('#loan-calculator-form');
        if (!form) return;

        const inputs = form.querySelectorAll('input[type="number"]');
        inputs.forEach(input => {
            input.addEventListener('input', () => this.calculateInRealTime());
        });
    }

    calculateInRealTime() {
        const amount = parseFloat(document.querySelector('input[name="monto"]')?.value) || 0;
        const rate = parseFloat(document.querySelector('input[name="tasaAnual"]')?.value) || 0;
        const months = parseInt(document.querySelector('input[name="plazoMeses"]')?.value) || 0;

        if (amount > 0 && rate > 0 && months > 0) {
            const monthlyRate = rate / 12;
            const monthlyPayment = amount * (monthlyRate * Math.pow(1 + monthlyRate, months)) /
                                  (Math.pow(1 + monthlyRate, months) - 1);

            const totalPayment = monthlyPayment * months;
            const totalInterest = totalPayment - amount;

            this.displayPreview(monthlyPayment, totalPayment, totalInterest);
        }
    }

    displayPreview(monthlyPayment, totalPayment, totalInterest) {
        let preview = document.querySelector('#loan-preview');
        if (!preview) {
            preview = document.createElement('div');
            preview.id = 'loan-preview';
            preview.className = 'card mt-2';
            document.querySelector('#loan-calculator-form').parentNode.appendChild(preview);
        }

        preview.innerHTML = `
            <h4>Vista Previa</h4>
            <p><strong>Cuota mensual:</strong> ${BancoApp.formatCurrency(monthlyPayment)}</p>
            <p><strong>Pago total:</strong> ${BancoApp.formatCurrency(totalPayment)}</p>
            <p><strong>Interés total:</strong> ${BancoApp.formatCurrency(totalInterest)}</p>
        `;
    }
}

// Filtros avanzados para tablas
class TableFilters {
    constructor(tableSelector) {
        this.table = document.querySelector(tableSelector);
        this.setupFilters();
    }

    setupFilters() {
        if (!this.table) return;

        const filterInputs = document.querySelectorAll('.table-filter');
        filterInputs.forEach(input => {
            input.addEventListener('input', () => this.filterTable());
        });
    }

    filterTable() {
        const filters = {};
        document.querySelectorAll('.table-filter').forEach(input => {
            if (input.value.trim()) {
                filters[input.dataset.column] = input.value.trim().toLowerCase();
            }
        });

        const rows = this.table.querySelectorAll('tbody tr');
        rows.forEach(row => {
            let showRow = true;
            Object.keys(filters).forEach(column => {
                const cellIndex = parseInt(column);
                const cellText = row.cells[cellIndex]?.textContent.toLowerCase() || '';
                if (!cellText.includes(filters[column])) {
                    showRow = false;
                }
            });
            row.style.display = showRow ? '' : 'none';
        });
    }
}

// Inicializar la aplicación cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', () => {
    new BancoApp();

    // Inicializar componentes específicos según la página
    if (document.querySelector('#loan-calculator-form')) {
        new LoanCalculator();
    }

    if (document.querySelector('table')) {
        new TableFilters('table');
    }
});

// Exportar para uso global
window.BancoApp = BancoApp;
window.LoanCalculator = LoanCalculator;
window.TableFilters = TableFilters;
