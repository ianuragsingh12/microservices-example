package com.example.gateway.configuration;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author ianur
 */
@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/user/auth/register",
            "/api/v1/user/auth/login",
            "/api/v1/user/auth/validate"
    );

    public Predicate<ServerHttpRequest> isSecured
            = request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
