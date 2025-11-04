package com.shoonglogitics.userservice.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoonglogitics.userservice.application.command.LoginUserCommand;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtProvider jwtProvider;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
		JwtProvider jwtProvider) {
		super(authenticationManager);
		this.jwtProvider = jwtProvider;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			LoginUserCommand command = mapper.readValue(request.getInputStream(), LoginUserCommand.class);

			System.out.println("member = " + command);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				command.getUserName(), command.getPassword());

			Authentication authentication = getAuthenticationManager().authenticate(authenticationToken);

			return authentication;
		} catch (IOException e) {
			throw new AuthenticationServiceException("Failed to parse login request", e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		UserDetailsImpl userDetails = (UserDetailsImpl)authResult.getPrincipal();

		String token = jwtProvider.generateToken(
			userDetails.getId(),
			userDetails.getAuthorities().iterator().next().getAuthority()
		);

		// Response Header에 토큰 전달
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("X-USER-ID", String.valueOf(userDetails.getUser().getId()));
		response.addHeader("X-USER-ROLE", userDetails.getAuthorities().iterator().next().getAuthority());
	}
}
