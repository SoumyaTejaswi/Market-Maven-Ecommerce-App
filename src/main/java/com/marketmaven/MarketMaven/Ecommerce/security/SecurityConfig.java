package com.marketmaven.MarketMaven.Ecommerce.security;
/*
Sets up security rules for my appl.
Protects API Endpoints
JWT Based authentication
pw encryption
stateless authentication flow
defines public and protected routes.
*/
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Main Security Configuration class
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    //private final HttpSecurity httpSecurity;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()) //enables cors as default
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**","/categories/**","/products/**","/product/**","/orders/**").permitAll()//url access rules
                        //the public endpoints are :auth,categories,products,orders.other require authentication.
                        .anyRequest().authenticated())//manage categories
                .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//set statless session management
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);//adds jwt auth filter before default filter.
        return http.build();
    }
    //Bean for password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {//to securily has pw before storing them in a db and verify during authentication.
        return new BCryptPasswordEncoder();//To hash the pw
    }

    //Authetincation manager
    @Bean
    //used for validating credintials during login.
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();//CustomUserDetailsService is called here for matching the user with the userdetails in the db.
    }
    // When a user submits their login credentials (username and password), the AuthenticationManager is used to validate these credentials against the stored data (e.g., in a database).
    //user login request POST Req: auth/login authentication manager comes into action.checks the credintails using provides from the databse.
    //if valid,generates a JWT Token for the user.otherwise throws exception.

}
//What happens in AuthenticationManagerBean?
/*
1. Receives credentials
{
    "username": "user@email.com",
    "password": "rawPassword"
}

2. Creates authentication token
UsernamePasswordAuthenticationToken token =
    new UsernamePasswordAuthenticationToken(username, password);

3. AuthenticationManager processes this token by:
    Loading user details via CustomUserDetailsService
    Verifying password using passwordEncoder
    Checking if account is enabled/not expired

    try {
    // Validates credentials
    Authentication auth = authenticationManager.authenticate(token);
    // If successful:
    // - Creates JWT token
    // - Sets security context
} catch (BadCredentialsException e) {
    // Handles invalid credentials
}

So takes care of creating a JWT Token and sets secuirty standards.
 */

