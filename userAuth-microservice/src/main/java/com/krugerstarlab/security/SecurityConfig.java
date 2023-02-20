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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {
	
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuerer) throws Exception {
		return configuerer.getAuthenticationManager();
	}

	//the bean that spring security will use once the application is running
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//paths taht will be allowed to access without authenticatoin
		String[] authorizedPaths =new String[]
				{
						"/api/v1/members/login",
						"/api/v1/members/signup",
						"/api/v1/tutors/login",
						"/api/v1/tutors/signup"
				};
		
		
		return http.csrf().disable()
				.authorizeHttpRequests(auth-> 
				//allowing the paths to be accessed without tokens
				auth.requestMatchers(null,authorizedPaths).permitAll()
				//if we want to specify paths only accessed by tutors here we can do it 
				//.requestMatchers("/api/v1/tutors/**").hasAnyAuthority("TUTOR")
				
				//the rest of the paths will requier authentication
				.requestMatchers("/api/v1/**").authenticated()
				)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				// for paths that need auth the jwtAuthenticationFilter will be called as an implementation 
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	
	


}
