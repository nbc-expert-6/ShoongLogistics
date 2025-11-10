package com.shoonglogitics.userservice.infrastructure.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuditorAwareImpl implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated() ||
			"anonymousUser".equals(authentication.getName())) {

			return Optional.empty();
		}

		try {
			String userId = (String)authentication.getPrincipal();
			return Optional.of(Long.parseLong(userId));
		} catch (Exception e) {
			log.error("현재 감사자 정보를 SecurityContext에서 가져오는데 실패했습니다. Principal : {}, "
				+ "에외 메세지 : {}", authentication.getPrincipal(), e.getMessage(), e);
			return Optional.empty();
		}
	}
}
