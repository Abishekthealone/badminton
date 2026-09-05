package com.badminton.winzz.security;


import com.badminton.winzz.filter.JwtFilterChain;
import com.badminton.winzz.service.CustomUserDetailsService;
import com.badminton.winzz.util.JWTutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private JwtFilterChain jwtFilterChain;


    @Bean
    public SecurityFilterChain  filterChain(HttpSecurity http) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable)

                // JWT is self-contained, so never create an HTTP session.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((authorize) -> authorize

                        // --- API endpoints reachable without a token ---
                        // /login/register must be public, otherwise you would
                        // need an account in order to create an account.
                        .requestMatchers("/auth/token", "/login/register").permitAll()

                        // --- Temporary Thymeleaf UI + its static assets ---
                        // A browser navigation cannot send an Authorization
                        // header, so an HTML PAGE can never be token-protected.
                        // The token protects the JSON these pages fetch. React
                        // will work exactly the same way, so these rules stay.
                        .requestMatchers("/ui/**", "/css/**", "/js/**",
                                         "/images/**", "/favicon.ico").permitAll()

                        // --- Swagger ---
                        .requestMatchers("/v3/api-docs", "/v3/api-docs/**",
                                         "/swagger-ui.html", "/swagger-ui/**").permitAll()

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){

        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(daoAuthenticationProvider);

    }
}
