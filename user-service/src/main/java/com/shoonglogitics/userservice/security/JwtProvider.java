package com.shoonglogitics.userservice.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shoonglogitics.userservice.domain.entity.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Component
@Getter
public class JwtProvider {

	private final SecretKey secretKey;

	@Value("${jwt.expiration}")
	private Long accessTokenExpiration;

	public JwtProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createAccessToken(Long id, UserRole role) {
		return Jwts.builder()
			.claim("userId", id)
			.claim("userRole", role)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
			.signWith(secretKey)
			.compact();
	}

	public Boolean validateToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
		return claims.get("userId", Long.class);
	}

	public UserRole getUserRoleFromToken(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
		return claims.get("userRole", UserRole.class);
	}

}
