package com.book.store.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.book.store.security.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${book.store.JwtSecret}")
	private String jwtSecret;
	
	@Value("${book.store.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public String generateToken(Authentication auth) {
		
		UserDetailsImpl principal =
				(UserDetailsImpl) auth.getPrincipal();
		
		return Jwts.builder()
					.setSubject(principal.getUsername())
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();
		
	}
	
	public String getUsernameFromToken(String token) {
		
		return Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
	}
	
	/**
	 * 
	 * @param token JWT authorization token 
	 * @return A string containing the expiration date of the provided JWT token.
	 */
	public String getExpirationAt(String token) {
		return getClaims(token)
				.getExpiration()
				.toString();
	}
	
	public Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(authToken);
			
			return true;
			
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		
		return false;
	}

}
