package com.marketmaven.MarketMaven.Ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //Configuration class to allow resource sharing
public class CorsConfig
{
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        //springboot components that lets you customize how my web app handles thing like CORS,URL Mappings and more
        //Set rules for which websites can access your backend and which HTTP Methods are allowed.
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {//CorsRegistry assists with the registration of global, URL pattern based CorsConfiguration mappings.
            registry.addMapping("/**")//configures CORS to apply to all URL paths in your Spring Boot application.
                    .allowedMethods("GET", "POST", "PUT", "DELETE") //Specifies which HTTP methods are allowed when other domains make requests to your API
                    .allowedOrigins("*");//Defines which origins (domains) can access your API/* Means all origins are allowed to make requests.
            //username@yourdomain.com
        }
    };
    }
}



//CORS=Cross-Origin Resource Sharing
//This class sets up roles for CORS in my application.
//Allows Backend to accept requests from any websites or domain,using HTTP Methods like GET,POST,PUT and DELETE.
//Useful when frontend and backend are hosted on different servers.
