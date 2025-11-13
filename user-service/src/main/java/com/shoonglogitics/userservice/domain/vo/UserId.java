package com.shoonglogitics.userservice.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId {

	private Long id;

	public UserId(Long id) {
		if(id == null){
			throw new IllegalArgumentException("인증된 회원 식별자 id가 필요합니다.");
		}
		this.id = id;
	}

}
