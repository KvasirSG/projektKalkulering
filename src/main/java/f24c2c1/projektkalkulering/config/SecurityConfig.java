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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}
