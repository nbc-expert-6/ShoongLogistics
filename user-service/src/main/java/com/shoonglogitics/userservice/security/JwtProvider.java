package com.shoonglogitics.userservice.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shoonglogitics.userservice.domain.entity.UserRole;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Getter
public class JwtProvider {

	private final SecretKey secretKey;
	public static final String REFRESH_TOKEN_COOKIE = "refreshToken";

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

}
