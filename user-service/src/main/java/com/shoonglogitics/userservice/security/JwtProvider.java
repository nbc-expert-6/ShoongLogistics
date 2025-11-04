package com.shoonglogitics.userservice.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtProvider {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private long jwtExpirationInMs;

	private final RedisTemplate<String, Object> redisTemplate;

	// JWT 토큰 생성
	public String generateToken(Long userId, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("X-USER-ID", userId);
		claims.put("X-USER-ROLE", role);

		String token = Jwts.builder()
			.setClaims(claims)
			.setSubject(userId.toString())
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
			.signWith(SignatureAlgorithm.HS256, jwtSecret)
			.compact();

		Map<String, String> tokenData = new HashMap<>();
		tokenData.put("userId", String.valueOf(userId));
		tokenData.put("userRole", role);

		redisTemplate.opsForHash().putAll(token, tokenData);

		return token;
	}

	// Jwt 토큰 검증
	public boolean validateToken(String token) {
		try {
			// Jwt 토큰 서명용 Key
			SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
			// Jwt 토큰 파싱을 통해 서명이 유효한지 확인
			Claims claims = Jwts.parser()
				.setSigningKey(key)
				.build().parseClaimsJws(token).getBody();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Jwt 토큰에서 Claims 추출 메소드
	private Claims getAllClaimsFromToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
		Claims claims = Jwts.parser().setSigningKey(key)
			.build().parseClaimsJws(token).getBody();

		return claims;
	}

	// Jwt 토큰에서 UserID 추출
	public String getUserIdFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return claims.get("X-USER-ID", String.class);
	}

	// Jwt 토큰에서 User Role 추출
	public String getUserRoleFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return claims.get("X-USER-ROLE", String.class);
	}

}
