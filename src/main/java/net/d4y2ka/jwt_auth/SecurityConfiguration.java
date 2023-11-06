package net.d4y2ka.jwt_auth;

import net.d4y2ka.jwt_auth.auth.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
  @Autowired
  private JwtAuthorizationFilter jwtAuthorizationFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorize ->
            authorize
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().permitAll()
        )
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}
