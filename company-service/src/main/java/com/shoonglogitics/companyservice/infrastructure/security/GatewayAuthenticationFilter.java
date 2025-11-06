package com.shoonglogitics.companyservice.infrastructure.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GatewayAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String userIdHeader = request.getHeader(HeaderType.USER_ID);
		String roleHeader = request.getHeader(HeaderType.USER_ROLE);

		if (userIdHeader != null && roleHeader != null) {
			try {
				Long userId = Long.parseLong(userIdHeader);
				AuthUser authUser = AuthUser.of(userId, roleHeader);

				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(
						authUser,
						null,
						List.of(new SimpleGrantedAuthority(authUser.getAuthority()))
					);

				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (Exception e) {
				log.error("인증 정보가 없습니다.");
			}
		}

		filterChain.doFilter(request, response);
	}
}
