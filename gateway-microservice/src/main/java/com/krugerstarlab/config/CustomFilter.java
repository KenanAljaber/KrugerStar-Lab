package com.krugerstarlab.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class CustomFilter {
	/*private static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

	@Autowired
	private WebClient webClient;
	
	

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	    logger.warn("Filtering the request in the gateway");

	    // The request path is /api/v1/users/tutors
	    String path = exchange.getRequest().getPath().toString();

	    if (rquierAuth(path)) {
	        logger.debug("Request requires authentication");

	        if (getToken(exchange.getRequest()) != null) {
	            // Validate token
	            return validateToken(exchange)
	                    .onErrorMap(e -> new RuntimeException("Token validation failed: " + e.getMessage()))
	                    .flatMap(resp -> {
	                        if (resp.getStatusCode().equals(HttpStatus.OK)) {
	                            logger.debug("[+] Response entity is all good");
	                            return chain.filter(exchange);
	                        } else {
	                            logger.error("[-]Response entity failed, status code: {}", resp.getStatusCode());
	                            return Mono.error(new RuntimeException("Response entity failed"));
	                        }
	                    });
	        } else {
	            return chain.filter(exchange);
	        }
	        

	        
	    } else {
	        return chain.filter(exchange);
	    }
	}


	private Mono<ResponseEntity<Object>> validateToken(ServerWebExchange exchange) {
		logger.debug("request has a token");
		  // Call the userAuth-microservice using WebClient
		String token=exchange.getRequest().getHeaders().get("Authorization").get(0);
	    return webClient.get()
	        .uri("http://userAuth-microservice/api/v1/users/members/blank")
	        .header(HttpHeaders.AUTHORIZATION, token)
	        .exchange()
	        .flatMap(response -> {
	            if (response.statusCode().is2xxSuccessful()) {
	                return Mono.just(ResponseEntity.ok().build());
	            } else {
	                return Mono.just(ResponseEntity.status(response.statusCode()).build());
	            }
	        })
	        .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
		
		
	}

	private boolean rquierAuth(String path) {
		List<String> authorizedPaths = List.of(

				"/api/v1/users/members/login", "/api/v1/users/members/signup", "/api/v1/users/tutors/login",
				"/api/v1/users/tutors/signup");

		return !authorizedPaths.contains(path);
	}

	private String getToken(ServerHttpRequest req) {

		HttpHeaders headerAuth = req.getHeaders();
		if (headerAuth.containsKey("Authorization")) {
			String authStr = headerAuth.get("Authorization").get(0);
			if (StringUtils.hasText(authStr) && authStr.startsWith("Bearer ")) {
				return authStr.substring(7, authStr.length());
			}
		}

		return null;
	}*/

}
