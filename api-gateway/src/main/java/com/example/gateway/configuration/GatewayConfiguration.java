/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ianur
 */
@Configuration
public class GatewayConfiguration {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // user-management
                .route("user-management", r -> r.path("/api/v1/user/auth/**")
                .filters(f -> f.filter(filter))
                .uri("lb://user-management")) // dynamic routing
                
                // order-service
                .route("orderId", r -> r.path("/order/**")
                .uri("lb://ORDER-SERVICE"))

                // payment-service
                .route("paymentId", r -> r.path("/payment/**")
                .uri("lb://PAYMENT-SERVICE"))
                
                // book-service
                .route("bookId", r -> r.path("/book/**")
                .uri("lb://BOOK-SERVICE"))
                
                // student-service
                .route("studentId", r -> r.path("/student/**")
                .uri("lb://STUDENT-SERVICE"))
                
                // product-service
                .route("productId", r -> r.path("/product/**")
                .uri("lb://PRODUCT-SERVICE"))
                .build();
    }
}
