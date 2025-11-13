package com.shoonglogitics.hubservice.domain.vo;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Duration implements Comparable<Duration>{
    private static final long WORKING_HOURS_MINUTES = 540;
    private static final long MIN_MINUTES = 0;
    private static final double SPEED_AVG = 60.0;

    private final long minutes;

    private Duration(long minutes) {
        validate(minutes);
        this.minutes = minutes;
    }


    private void validate(long minutes) {
        if (minutes < MIN_MINUTES) {
            throw new IllegalArgumentException("소요 시간은 "+ MIN_MINUTES +"이상이어야 합니다: "+minutes);
        }
    }

    public static Duration ofMinutes(long minutes) {
        return new Duration(minutes);
    }

    public static Duration ofHours(long hours) {
        return new Duration(hours*60);
    }

    public static Duration ofHoursAndMinutes(long hours, long minutes) {
        return new Duration(hours*60+minutes);
    }

    public long toHours(){
        return minutes/60;
    }

    public Duration add(Duration other) {
        if(other == null) {
            throw new IllegalArgumentException("더할 시간은 null일 수 없습니다");
        }
        return new Duration(minutes + other.minutes);
    }

    public Duration subtract(Duration other) {
        if(other == null) {
            throw new IllegalArgumentException("뺄 시간은 null일 수 없습니다");
        }
        return new Duration(minutes - other.minutes);
    }


    public boolean isGreaterThan(Duration other) {
        if (other == null) {
            throw new IllegalArgumentException("비교 대상은 null일 수 없습니다");
        }
        return this.minutes > other.minutes;
    }

    public boolean isLessThan(Duration other) {
        if (other == null) {
            throw new IllegalArgumentException("비교 대상은 null일 수 없습니다");
        }
        return this.minutes < other.minutes;
    }

    public boolean isWithinWorkingHours() {
        return this.minutes <= WORKING_HOURS_MINUTES;
    }

    @Override
    public int compareTo(Duration other) {
        if(other == null) {
            throw new IllegalArgumentException("비교 대상은 null일 수 없습니다");
        }
        return Long.compare(this.minutes, other.minutes);
    }

    public static Duration fromDistance(Distance distance) {
        double distanceKm = distance.getMeters() / 1000.0;
        double hours = distanceKm / SPEED_AVG;
        int minutes = (int) Math.round(hours * 60);
        return new Duration(minutes);
    }

}
