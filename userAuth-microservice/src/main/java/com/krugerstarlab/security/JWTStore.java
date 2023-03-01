package com.krugerstarlab.security;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.krugerstarlab.entity.security_model.SecurityUser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTStore {
	
	private static final long TOKEN_DURATION = 86400000; //24 hours in miliseconds

	@Value("${jwt.secret}")
	private String JWT_SECRET;
	
	private static final Logger logger= LoggerFactory.getLogger(JWTStore.class);
	
	@Autowired
	private static PasswordEncoder passwordEncoder;
	
	
	public  String generateToken(Authentication auth) {
		logger.debug("[+] Generating a token");
		SecurityUser user= (SecurityUser)auth.getPrincipal();
		HashMap<String,Object> claims=new HashMap<>();
		claims.put("id", user.getId());
		claims.put("role", user.getRole());
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getEmail())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + TOKEN_DURATION))
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact()
				;
	}
	
	public  boolean validateToken (String token) {
		  try {
			  logger.debug("[+] Validating token");
	            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
	            return true;
	        } catch (SignatureException e) {
	            // signature verification failed
	            logger.error("[-] Invalid JWT signature: " + e.getMessage());
	        } catch (MalformedJwtException e) {
	            // invalid JWT format
	            logger.error("[-] Invalid JWT token: " + e.getMessage());
	        } catch (ExpiredJwtException e) {
	            // JWT token has expired
	            logger.error("[-] JWT token has expired: " + e.getMessage());
	        } catch (UnsupportedJwtException e) {
	            // unsupported JWT token
	            logger.error("[-] JWT token is unsupported: " + e.getMessage());
	        } catch (IllegalArgumentException e) {
	            // empty JWT token
	            logger.error("[-] JWT claims string is empty: " + e.getMessage());
	        }

	        return false;
	    }
	
	public  String getEmailFromToken(String token) {
		try {
			logger.debug("[+] Extracting email from token");
		return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
		}catch (Exception e) {
			logger.error("[-] somtheing happend "+e);
			e.printStackTrace();
			return null;
		}
	}
	

}
