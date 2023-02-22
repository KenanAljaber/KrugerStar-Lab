package com.krugerstarlab.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {


   
  /* @Bean
    GlobalFilter globalFilter() {
	   return new CustomFilter();
   }
   */
   @LoadBalanced
   @Bean
   public WebClient builder(){
       return WebClient.builder().build();
   }

}
