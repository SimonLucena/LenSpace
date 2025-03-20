package br.edu.ifpb.pweb2.simon.projeto.LenSpace.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("lenspace/login")
                        .defaultSuccessUrl("/index")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // ✅ Rota correta para logout
                        .logoutSuccessUrl("/login?logout") // ✅ Redireciona para login após logout
                        .invalidateHttpSession(true) // ✅ Invalida a sessão
                        .deleteCookies("JSESSIONID") // ✅ Remove cookies de autenticação
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
