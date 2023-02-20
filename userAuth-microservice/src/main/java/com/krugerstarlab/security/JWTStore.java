package com.krugerstarlab.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.krugerstarlab.entity.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTStore {
	
	//@Value("${jwt.secret}")
	private static  String JWT_SECRET="secret";
	
	@Autowired
	private static PasswordEncoder passwordEncoder;
	
	
	public static String generateToken(User user) {
		return "NEW_TOKEN";
	}
	
	public static boolean validateToken (String token) {
		  try {
	            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
	            return true;
	        } catch (SignatureException e) {
	            // signature verification failed
	            System.out.println("Invalid JWT signature: " + e.getMessage());
	        } catch (MalformedJwtException e) {
	            // invalid JWT format
	            System.out.println("Invalid JWT token: " + e.getMessage());
	        } catch (ExpiredJwtException e) {
	            // JWT token has expired
	            System.out.println("JWT token has expired: " + e.getMessage());
	        } catch (UnsupportedJwtException e) {
	            // unsupported JWT token
	            System.out.println("JWT token is unsupported: " + e.getMessage());
	        } catch (IllegalArgumentException e) {
	            // empty JWT token
	            System.out.println("JWT claims string is empty: " + e.getMessage());
	        }

	        return false;
	    }
	
	public static String getEmailFromToken(String token) {
		try {
		return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
		}catch (Exception e) {
			return null;
		}
	}
	

}
