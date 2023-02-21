package com.krugerstarlab.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("registry",r-> r.path("/registry")
						.uri("http://localhost:9091"))
				.build();
	}
	
}
