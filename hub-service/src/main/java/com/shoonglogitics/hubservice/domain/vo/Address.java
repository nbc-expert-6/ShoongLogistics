package com.shoonglogitics.hubservice.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private static final int ADDRESS_MAX_LENGTH = 100;

    private String value;


    private Address(String value) {
        validateAddress(value);
        this.value = value;
    }

    private void validateAddress(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("주소는 필수입니다");
        }
        if (value.length() > ADDRESS_MAX_LENGTH) {
            throw new IllegalArgumentException("주소는 500자를 초과할 수 없습니다");
        }
    }

    public static Address of(String value) {
        return new Address(value);
    }
}
