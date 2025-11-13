package com.shoonglogitics.hubservice.domain;

import com.shoonglogitics.hubservice.domain.entity.Hub;
import com.shoonglogitics.hubservice.domain.event.HubCreatedEvent;
import com.shoonglogitics.hubservice.domain.vo.Address;
import com.shoonglogitics.hubservice.domain.vo.GeoLocation;
import com.shoonglogitics.hubservice.domain.vo.HubType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Hub 도메인 테스트")
public class HubTest {

    @Test
    @DisplayName("허브를 생성하고 생성 이벤트가 발행된다.")
    void createHub_Success(){
        // given
        String name = "서울 중앙 허브";
        String addressValue = "서울시 강남구 테헤란로 123";
        Double latitude = 37.5665;
        Double longitude = 126.9780;
        HubType hubType = HubType.CENTRAL;

        // when
        Hub hub = Hub.create(name, addressValue, latitude, longitude, hubType);

        // then
        assertThat(hub.getName()).isEqualTo(name);
        assertThat(hub.getAddress().getValue()).isEqualTo(addressValue);
        assertThat(hub.getLatitude()).isEqualTo(latitude);
        assertThat(hub.getLongitude()).isEqualTo(longitude);
        assertThat(hub.getHubType()).isEqualTo(hubType);
    }

    @Test
    @DisplayName("빌더를 통해 허브를 생성할 수 있다")
    void createHubWithBuilder_Success() {
        // given
        String name = "부산 허브";
        Address address = Address.of("부산시 해운대구");
        GeoLocation location = GeoLocation.of(35.1796, 129.0756);
        HubType hubType = HubType.CENTRAL;

        // when
        Hub hub = Hub.builder()
                .name(name)
                .address(address)
                .location(location)
                .hubType(hubType)
                .build();

        // then
        assertThat(hub.getName()).isEqualTo(name);
        assertThat(hub.getAddress()).isEqualTo(address);
        assertThat(hub.getLocation()).isEqualTo(location);
        assertThat(hub.getHubType()).isEqualTo(hubType);
    }

    @Test
    @DisplayName("허브 이름을 변경한다")
    void updateHubName_Success() {
        // given
        Hub hub = Hub.create(
                "서울 허브",
                "서울시 강남구",
                37.5665,
                126.9780,
                HubType.CENTRAL
        );
        String newName = "서울 중앙 허브";

        // when
        hub.update(newName);

        // then
        assertThat(hub.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("허브를 비활성화하면 삭제 상태가 된다")
    void deactivateHub_Success() {
        // given
        Hub hub = Hub.create(
                "서울 허브",
                "서울시 강남구",
                37.5665,
                126.9780,
                HubType.CENTRAL
        );
        Long userId = 1L;

        // when
        hub.deactivate(userId);

        // then
        assertThat(hub.isDeleted()).isTrue();
        assertThat(hub.getDeletedBy()).isEqualTo(userId);
        assertThat(hub.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("이미 삭제된 허브를 비활성화하면 예외가 발생한다")
    void deactivateDeletedHub_ThrowsException() {
        // given
        Hub hub = Hub.create(
                "서울 허브",
                "서울시 강남구",
                37.5665,
                126.9780,
                HubType.CENTRAL
        );
        hub.deactivate(1L);

        // when & then
        assertThatThrownBy(() -> hub.deactivate(2L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 삭제된 허브입니다.");
    }

    @Test
    @DisplayName("위도와 경도를 조회할 수 있다")
    void getCoordinates_Success() {
        // given
        Double latitude = 37.5665;
        Double longitude = 126.9780;
        Hub hub = Hub.create(
                "서울 허브",
                "서울시 강남구",
                latitude,
                longitude,
                HubType.CENTRAL
        );

        // when & then
        assertThat(hub.getLatitude()).isEqualTo(latitude);
        assertThat(hub.getLongitude()).isEqualTo(longitude);
    }

    @Test
    @DisplayName("소프트 삭제 시 삭제 정보가 기록된다")
    void softDelete_SetsDeletedAtAndDeletedBy() {
        // given
        Hub hub = Hub.create(
                "서울 허브",
                "서울시 강남구",
                37.5665,
                126.9780,
                HubType.CENTRAL
        );
        Long userId = 123L;

        // when
        hub.softDelete(userId);

        // then
        assertThat(hub.isDeleted()).isTrue();
        assertThat(hub.getDeletedAt()).isNotNull();
        assertThat(hub.getDeletedBy()).isEqualTo(userId);
    }

    @Test
    @DisplayName("삭제되지 않은 허브는 삭제 상태가 아니다")
    void isDeleted_WhenNotDeleted_ReturnsFalse() {
        // given
        Hub hub = Hub.create(
                "서울 허브",
                "서울시 강남구",
                37.5665,
                126.9780,
                HubType.CENTRAL
        );

        // when & then
        assertThat(hub.isDeleted()).isFalse();
    }




}
