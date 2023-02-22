package com.krugerstarlab.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {
	private static final Logger logger=LoggerFactory.getLogger(GatewayConfig.class);
   @Value("${spring.application.name}")
   private String appName;

   @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
      return builder.routes()
         .route("registry-microservice", r -> r.path("/")
            .uri("http://localhost:9091"))
         .build();
   }
   
   @Bean
    GlobalFilter globalFilter() {
	   return new CustomFilter();
   }
   
   @LoadBalanced
   @Bean
   public WebClient builder(){
       return WebClient.builder().build();
   }

}
