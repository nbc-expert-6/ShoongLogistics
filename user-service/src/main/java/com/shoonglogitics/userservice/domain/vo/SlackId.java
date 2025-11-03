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
public class SlackId {

	private String value;

	public SlackId(String value) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException("Slakc 정보는 필수값입니다.");
		}
		this.value = value;
	}

}
