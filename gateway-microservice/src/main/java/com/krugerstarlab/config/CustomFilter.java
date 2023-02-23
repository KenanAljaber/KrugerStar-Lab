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

public class CustomFilter implements GlobalFilter {
	private static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

	@Autowired
	private WebClient.Builder webClientBuilder;

	//this filter will be called each time a request come to the gateway
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.warn("Filtering the request in the gateway");
		//get the path of the current request to check whether it is an open or protected path
		String path = exchange.getRequest().getPath().toString();

		if (rquierAuth(path)) {
			logger.debug("Request requires authentication");

			if (getToken(exchange.getRequest()) != null) {
				// Validate token
				return validateToken(exchange)
						.onErrorMap(e -> new RuntimeException("Token validation failed: " + e.getMessage()))
						.flatMap(resp -> {
							logger.debug(resp.getStatusCode().toString());
							if (resp.getStatusCode().equals(HttpStatus.OK)) {
								logger.debug("[+] Response entity is all good");
								return chain.filter(exchange);
							} else {
								logger.error("[-]Response entity failed, status code: {}", resp.getStatusCode());
								return Mono.error(new RuntimeException("Response entity failed"));
							}
						});
			} else {
				logger.error("[-] Trying to access a protected path without token");
				return Mono.error(new RuntimeException("[-] Sorry this path is protected"));
			}
		} else {
			return chain.filter(exchange);
		}
	}

	private Mono<ResponseEntity<Void>> validateToken(ServerWebExchange exchange) {
		logger.debug("request has a token");
		// Call the userAuth-microservice using WebClient
		String token = exchange.getRequest().getHeaders().get("Authorization").get(0);
		return webClientBuilder.build().get().uri("lb://USERAUTH-MICROSERVICE/api/v1/users/auth/validate")
				.header(HttpHeaders.AUTHORIZATION, token).retrieve().toBodilessEntity();
	}

	private boolean rquierAuth(String path) {
		List<String> authorizedPaths = List.of(

				"/api/v1/users/auth/login",
				"/api/v1/users/auth/signup");

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
	}

}
