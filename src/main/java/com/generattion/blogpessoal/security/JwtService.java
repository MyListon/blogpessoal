package com.generattion.blogpessoal.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET = "f1c0e61b41e3caf5e34bf04d1b6ddb9b485a8f20f9a83b3e1be7ef44a64aac08";
	private static final Duration EXPIRATION_DURATION = Duration.ofMinutes(60);
	
	private final SecretKey signingKey;
	
	public JwtService() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		this.signingKey = Keys.hmacShaKeyFor(keyBytes);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject().equals(userDetails.getUsername()) &&
				claims.getExpiration().after(new Date());
	}
	
	public String generateToken(String username) {
		Instant now = Instant.now();
		return Jwts.builder()
				.subject(username)
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(EXPIRATION_DURATION)))
				.signWith(signingKey)
				.compact();
	}
}
