package com.shoonglogitics.userservice.domain.vo;

import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubId {

	private UUID id;

	public HubId(UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("허브의 id가 필요합니다.");
		}
		this.id = id;
	}

}
