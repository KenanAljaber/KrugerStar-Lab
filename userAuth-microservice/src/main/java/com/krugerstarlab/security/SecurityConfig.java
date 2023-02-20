package com.krugerstarlab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {
	
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuerer) throws Exception {
		return configuerer.getAuthenticationManager();
	}

	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		String[] authorizedPaths =new String[]
				{
						"/api/v1/members/login",
						"/api/v1/members/signup"
				};
		
		
		return http.csrf().disable()
				.authorizeHttpRequests(auth-> 
				auth.requestMatchers(null,authorizedPaths).permitAll())
				.build();
		

//http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	
	}
	
	
	


}
