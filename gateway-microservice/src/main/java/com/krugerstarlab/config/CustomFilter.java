package com.krugerstarlab.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

public class CustomFilter implements GlobalFilter {
	private static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

	@Autowired
	private RestTemplate rest;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	    logger.warn("Filtering the request in the gateway");

	    // The request path is /api/v1/users/tutors
	    String path = exchange.getRequest().getPath().toString();

	    if (rquierAuth(path)) {
	        logger.debug("Request requires authentication");

	        if (getToken(exchange.getRequest()) != null) {
	            // Validate token
	            try {
	                ResponseEntity resp = validateToken(exchange);

	                if (resp != null && resp.getStatusCode().equals(HttpStatus.OK)) {
	                    logger.debug("[+] Response entity is all good ");
	                    return chain.filter(exchange);
	                }

	                logger.error("[-]Response entity failed it may be null or the token is not valid");

	                return Mono.error(new RuntimeException("Response entity failed or the token is not valid"));
	            } catch (Exception e) {
	                logger.error("[-]The validation has failed");

	                return Mono.error(new RuntimeException("Token validation failed: " + e.getMessage()));
	            }
	        }

	        throw new RuntimeException("You are not authorized");
	    } else {
	        return chain.filter(exchange);
	    }
	}


	private ResponseEntity validateToken(ServerWebExchange exchange) {
		logger.debug("request has a token");
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", exchange.getRequest().getHeaders().get("Authorization").get(0));
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("field", "value");
		logger.debug("[+] this is the headers map " + exchange.getRequest().getHeaders().toSingleValueMap().toString());
		HttpEntity entity = new HttpEntity(body, headers);
		ResponseEntity resp = rest.exchange("http://localhost:8081/api/v1/users/members/blank", HttpMethod.GET, entity,
				ResponseEntity.class);
		return resp;
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
	}

}
