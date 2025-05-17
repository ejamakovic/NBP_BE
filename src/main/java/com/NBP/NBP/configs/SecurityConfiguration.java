package com.NBP.NBP.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.security.config.Customizer.withDefaults;

import com.NBP.NBP.security.CustomAccessDeniedHandler;
import com.NBP.NBP.security.CustomAuthenticationEntryPoint;
import com.NBP.NBP.services.CustomUserDetailsService;
import com.NBP.NBP.services.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfiguration(CustomAccessDeniedHandler customAccessDeniedHandler,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsService(userService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider) throws Exception {
        http.cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/reset-password").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        
                        .requestMatchers("/auth/send").hasAuthority("NBP08_ADMIN")
                        .requestMatchers("/admin/**").hasAuthority("NBP08_ADMIN")
                        .requestMatchers("/apps/**").hasAuthority("NBP08_ADMIN")
                        .requestMatchers("/logs/**").hasAuthority("NBP08_ADMIN")
                        .requestMatchers("/reports/**").hasAuthority("NBP08_ADMIN")
                        .requestMatchers("/roles/**").hasAuthority("NBP08_ADMIN")
                        .requestMatchers("/services/**").hasAuthority("NBP08_ADMIN")
                        
                        .requestMatchers("/user/**").hasAnyAuthority("NBP08_ADMIN", "NBP08_USER")
                        .requestMatchers("/categories/**").hasAnyAuthority("NBP08_ADMIN", "NBP08_USER")
                        .requestMatchers("/customUsers/**").hasAnyAuthority("NBP08_ADMIN", "NBP08_USER")
                        .requestMatchers("/departments/**").hasAnyAuthority("NBP08_ADMIN", "NBP08_USER")
                        .requestMatchers("/equipment/**").hasAnyAuthority("NBP08_ADMIN", "NBP08_USER")
                        .requestMatchers("/laboratories/**").hasAnyAuthority("NBP08_ADMIN", "NBP08_USER")
                        .requestMatchers("/orders/**").hasAnyAuthority("NBP08_ADMIN", "NBP08_USER")
                        .requestMatchers("/rentals/**").hasAnyAuthority("NBP08_ADMIN", "NBP08_USER")
                        .requestMatchers("/rentalRequests/**").hasAnyAuthority("NBP08_ADMIN", "NBP08_USER")
                        .requestMatchers("/suppliers/**").hasAnyAuthority("NBP08_ADMIN", "NBP08_USER")

                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}