package com.shoonglogitics.hubservice.domain.vo;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Distance {

    private static final int MIN_DISTANCE_METERS = 0;
    private static final int MAX_DISTANCE_METERS = 1_000_000;

    @Column(name = "distance_meters", nullable = false)
    private Integer meters;

    private Distance(Integer meters) {
        validateDistance(meters);
        this.meters = meters;
    }

    private void validateDistance(Integer meters) {
        if(meters == null || meters <=MIN_DISTANCE_METERS){
            throw new IllegalArgumentException("거리는 0보다 커야 합니다");
        }
        if(meters > MAX_DISTANCE_METERS){
            throw new IllegalArgumentException("거리는 "+MAX_DISTANCE_METERS+"미터를 초과할 수 없습니다");
        }
    }

    public static Distance ofMeters(Integer meters) {
        return new Distance(meters);
    }

    public boolean isLongerThan(int thresholdMeters) {
        return this.meters > thresholdMeters;
    }

}
