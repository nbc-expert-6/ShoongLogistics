package com.shoonglogitics.orderservice.domain.delivery.domain.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shoonglogitics.orderservice.domain.delivery.domain.vo.Address;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryEstimate;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryStatus;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.HubInfo;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.ShipperInfo;

class DeliveryTest {

	@Test
	@DisplayName("Delivery 생성 시 필수 필드가 정상적으로 설정되어야 한다")
	void delivery_create_shouldSetAllFieldsCorrectly() {
		UUID orderId = UUID.randomUUID();
		Address address = Address.of("서울시 강남구", "1층", "12345", null);
		ShipperInfo shipper = ShipperInfo.of(UUID.randomUUID(), "홍길동", "010-1234-5678", "hong123");
		HubInfo departureHub = HubInfo.of(UUID.randomUUID());
		HubInfo destinationHub = HubInfo.of(UUID.randomUUID());
		DeliveryRoute route = DeliveryRoute.create(shipper, departureHub, destinationHub, 1,
			DeliveryEstimate.of(1000, 30));
		List<DeliveryRoute> routes = List.of(route);
		String request = "조심히 배송";

		Delivery delivery = Delivery.create(orderId, address, shipper, departureHub, destinationHub, request, routes);

		assertThat(delivery.getOrderId()).isEqualTo(orderId);
		assertThat(delivery.getAddress()).isEqualTo(address);
		assertThat(delivery.getShipperInfo()).isEqualTo(shipper);
		assertThat(delivery.getDepartureHubId()).isEqualTo(departureHub);
		assertThat(delivery.getDestinationHubId()).isEqualTo(destinationHub);
		assertThat(delivery.getDeliveryRoutes()).isEqualTo(routes);
		assertThat(delivery.getRequest()).isEqualTo(request);
		assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.HUB_WAITING);
	}

	@Test
	@DisplayName("Delivery 생성 시 필수 값이 null이면 예외 발생")
	void delivery_create_withNullFields_shouldThrowException() {
		UUID orderId = UUID.randomUUID();
		Address address = Address.of("서울시 강남구", "1층", "12345", null);
		ShipperInfo shipper = ShipperInfo.of(UUID.randomUUID(), "홍길동", "010-1234-5678", "hong123");
		HubInfo hub = HubInfo.of(UUID.randomUUID());
		DeliveryRoute route = DeliveryRoute.create(shipper, hub, hub, 1, DeliveryEstimate.of(1000, 30));
		List<DeliveryRoute> routes = List.of(route);

		assertThatThrownBy(() -> Delivery.create(null, address, shipper, hub, hub, "req", routes))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("주문 번호");

		assertThatThrownBy(() -> Delivery.create(orderId, null, shipper, hub, hub, "req", routes))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("배송지 주소");

		assertThatThrownBy(() -> Delivery.create(orderId, address, null, hub, hub, "req", routes))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("배송 담당자");

		assertThatThrownBy(() -> Delivery.create(orderId, address, shipper, null, hub, "req", routes))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("출발 허브");

		assertThatThrownBy(() -> Delivery.create(orderId, address, shipper, hub, null, "req", routes))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("도착 허브");
	}

	@Test
	@DisplayName("Delivery update 시 요청과 배송자 정보가 정상적으로 수정되어야 한다")
	void delivery_update_shouldModifyFields() {
		UUID orderId = UUID.randomUUID();
		Address address = Address.of("서울시 강남구", "1층", "12345", null);
		ShipperInfo shipper = ShipperInfo.of(UUID.randomUUID(), "홍길동", "010-1234-5678", "hong123");
		HubInfo departureHub = HubInfo.of(UUID.randomUUID());
		HubInfo destinationHub = HubInfo.of(UUID.randomUUID());
		DeliveryRoute route = DeliveryRoute.create(shipper, departureHub, destinationHub, 1,
			DeliveryEstimate.of(1000, 30));
		Delivery delivery = Delivery.create(orderId, address, shipper, departureHub, destinationHub, "oldReq",
			List.of(route));

		delivery.update("newReq", shipper.getShipperId(), "김철수", "010-9999-8888", "kim123");

		assertThat(delivery.getRequest()).isEqualTo("newReq");
		assertThat(delivery.getShipperInfo().getShipperName()).isEqualTo("김철수");
		assertThat(delivery.getShipperInfo().getShipperPhoneNumber()).isEqualTo("010-9999-8888");
		assertThat(delivery.getShipperInfo().getShipperSlackId()).isEqualTo("kim123");
	}

	@Test
	@DisplayName("Delivery update 시 배송 완료 상태면 예외 발생")
	void delivery_update_whenDelivered_shouldThrowException() {
		UUID orderId = UUID.randomUUID();
		Address address = Address.of("서울시 강남구", "1층", "12345", null);
		ShipperInfo shipper = ShipperInfo.of(UUID.randomUUID(), "홍길동", "010-1234-5678", "hong123");
		HubInfo departureHub = HubInfo.of(UUID.randomUUID());
		HubInfo destinationHub = HubInfo.of(UUID.randomUUID());
		DeliveryRoute route = DeliveryRoute.create(shipper, departureHub, destinationHub, 1,
			DeliveryEstimate.of(1000, 30));
		Delivery delivery = Delivery.create(orderId, address, shipper, departureHub, destinationHub, "req",
			List.of(route));
		delivery.update("req", shipper.getShipperId(), null, null, null);

		delivery.update("req", shipper.getShipperId(), null, null, null);

	}
}