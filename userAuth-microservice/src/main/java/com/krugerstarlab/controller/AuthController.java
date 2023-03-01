package com.krugerstarlab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krugerstarlab.dto.LoginRequest;
import com.krugerstarlab.dto.LoginResponse;
import com.krugerstarlab.entity.security_model.SecurityUser;
import com.krugerstarlab.entity.security_model.UserDetailsRole;
import com.krugerstarlab.exception.InvalidUsernameOrPasswordException;
import com.krugerstarlab.service.authentication.AuthenticationService;
import com.krugerstarlab.service.member.MemberService;
import com.krugerstarlab.service.tutor.TutorService;

@RestController
@RequestMapping("api/v1/users/auth")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private TutorService tutorService;

	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping("/validate")
	public ResponseEntity validate() {
		/**
		 * this end point is empty, it will only be called by another microservices to
		 * validate the token, this is possible thank to the implementation of
		 * OncePerRequestFilter in the JwtAuthenticationFilter that will be called
		 * automatically each time a request come to any protected endpoint, so the
		 * jwtAuthenticationFilter will take the request and check if there is a
		 * token,if the token is valid, and if the token is for the correct user
		 */
		try {
			return new ResponseEntity(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

		try {
			// Authenticate user and get token
			String token = authenticationService.authenticateUser(request);
			logger.info("[+] Request has been validated and token is generated");

			// if it is member log the user as a member if tutor log the user as tutor
			SecurityUser secUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			LoginResponse response = null;
			if (secUser.getRole().equals(UserDetailsRole.TUTOR)) {
				// Tutor login
				response = tutorService.getTutorProfile(request.getEmail());
			} else {
				// Get user profile within a login response
				response = memberService.getMemberProfile(request.getEmail());

			}
			response.setUserRole(secUser.getRole());
			// Set the token in response
			response.setToken(token);
			logger.info("[+] User profile is set and login response is ready ");

			// Set the headers
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(response.getToken());
			logger.debug("[+] User has been authenticated and everything is OK");

			return ResponseEntity.ok().headers(headers).body(response);

		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			logger.error("[-] There was an error and the user could not be authenticated");
			return ResponseEntity.badRequest().body(LoginResponse.builder().status(HttpStatus.BAD_REQUEST)
					.message("Invalid email or password").build());
		} catch (InvalidUsernameOrPasswordException e) {
			logger.error("[-] There was an error and the user could not be authenticated");
			return ResponseEntity.badRequest().body(LoginResponse.builder().status(HttpStatus.BAD_REQUEST)
					.message("Invalid email or password").build());
		}

	}

}
