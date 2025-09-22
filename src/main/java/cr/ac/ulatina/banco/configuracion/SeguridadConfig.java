package cr.ac.ulatina.banco.configuracion;

import cr.ac.ulatina.banco.repositorio.UsuarioRepositorio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {

    @Bean
    public SecurityFilterChain filtro(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/auth/**").permitAll()
                        .requestMatchers("/trabajador/**").hasRole("TRABAJADOR")
                        .requestMatchers("/cliente/**").hasRole("CLIENTE")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/iniciar-sesion")
                        .defaultSuccessUrl("/despues-login", true)
                        .permitAll()
                )
                .logout(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()); // demo
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
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}









