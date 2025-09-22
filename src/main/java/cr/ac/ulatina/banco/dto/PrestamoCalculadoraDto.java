package cr.ac.ulatina.banco.dto;

import java.math.BigDecimal;

public class PrestamoCalculadoraDto {
    private BigDecimal monto = new BigDecimal("1000000"); // CRC
    private BigDecimal tasaAnual = new BigDecimal("0.12"); // 12%
    private Integer plazoMeses = 24;

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public BigDecimal getTasaAnual() { return tasaAnual; }
    public void setTasaAnual(BigDecimal tasaAnual) { this.tasaAnual = tasaAnual; }
    public Integer getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(Integer plazoMeses) { this.plazoMeses = plazoMeses; }
}
