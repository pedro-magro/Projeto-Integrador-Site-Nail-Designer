package br.com.nailDesigner.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.nailDesigner.services.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	CustomSuccessHandler successHandler;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/cadastro", "/login", "/css/**", "/js/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/funcionario/**").hasRole("FUNCIONARIO")
                .requestMatchers("/cliente/**").hasRole("CLIENTE")
                .anyRequest().permitAll())
            .formLogin(form -> form
            	.successHandler(successHandler)
                .loginPage("/login").permitAll())
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(usuarioService)
            .passwordEncoder(new BCryptPasswordEncoder())
            .and().build();
    }    

}
