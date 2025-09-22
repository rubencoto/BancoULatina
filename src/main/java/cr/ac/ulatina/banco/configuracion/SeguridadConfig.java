package cr.ac.ulatina.banco.configuracion;

import cr.ac.ulatina.banco.repositorio.UsuarioRepositorio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    @Bean
    public SecurityFilterChain filtro(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Recursos públicos
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()

                        // Páginas de autenticación
                        .requestMatchers("/auth/**").permitAll()

                        // Páginas específicas para trabajadores
                        .requestMatchers("/trabajador/**").hasRole("TRABAJADOR")

                        // Páginas específicas para clientes
                        .requestMatchers("/cliente/**").hasRole("CLIENTE")

                        // Endpoint de redirección después del login
                        .requestMatchers("/despues-login").authenticated()

                        // Cualquier otra solicitud debe estar autenticada
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/iniciar-sesion")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("correo")
                        .passwordParameter("contrasena")
                        .successHandler(authenticationSuccessHandler())
                        .failureUrl("/auth/iniciar-sesion?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/iniciar-sesion?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                .csrf(csrf -> csrf.disable()); // Solo para desarrollo

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService detallesUsuario(UsuarioRepositorio usuarioRepositorio) {
        return correo -> usuarioRepositorio.findByCorreo(correo)
                .map(u -> User.withUsername(u.getCorreo())
                        .password(u.getHashContrasena())
                        .roles(u.getRol().name())
                        .disabled(!u.isHabilitado())
                        .accountLocked(!u.isHabilitado())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String targetUrl = determineTargetUrl(authentication);
            response.sendRedirect(targetUrl);
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/auth/iniciar-sesion?error=access_denied");
        };
    }

    private String determineTargetUrl(org.springframework.security.core.Authentication authentication) {
        boolean esTrabajador = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TRABAJADOR"));

        boolean esCliente = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));

        if (esTrabajador) {
            return "/trabajador/inicio";
        } else if (esCliente) {
            return "/cliente/inicio";
        } else {
            return "/auth/iniciar-sesion?error=invalid_role";
        }
    }
}
