/*
 * ===================================================================================
 * File:        SecurityConfig.java
 * Description: Configuration class for Spring Security, providing essential beans
 *              required for application security, such as password encoding.
 *
 * Author:      Kenneth (KvasirSG)
 * Created:     2024-11-27
 * Updated:     2024-11-28
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - This configuration defines a `PasswordEncoder` bean using
 *                `BCryptPasswordEncoder` for secure password hashing.
 *              - The `BCryptPasswordEncoder` is a recommended implementation
 *                for securely encoding passwords in modern applications.
 * ===================================================================================
 */

package f24c2c1.projektkalkulering.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up security-related beans in the application.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures a PasswordEncoder bean.
     *
     * @return a BCryptPasswordEncoder instance for password hashing.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures Spring Security to allow public access to all URLs.
     *
     * @param http the HttpSecurity object
     * @return a SecurityFilterChain for customizing security behavior
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
