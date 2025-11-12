package com.shoonglogitics.orderservice.domain.delivery.infrastructure.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;
import com.shoonglogitics.orderservice.domain.delivery.domain.entity.DeliveryRoute;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.Address;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryEstimate;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.HubInfo;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.ShipperInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.GeoLocation;
import com.shoonglogitics.orderservice.domain.order.infrastructure.repository.audit.TestAuditorConfig;
import com.shoonglogitics.orderservice.global.common.dto.PageRequest;
import com.shoonglogitics.orderservice.global.common.vo.PageSizeType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestAuditorConfig.class)
@Transactional
class DeliveryRepositoryAdapterTest {

	@Autowired
	private JpaDeliveryRepository jpaDeliveryRepository;

	@Test
	@DisplayName("Delivery를 저장하면 DB에 정상적으로 저장되어야 한다")
	void saveDelivery_ShouldPersistDelivery() {
		DeliveryRepositoryAdapter adapter = new DeliveryRepositoryAdapter(jpaDeliveryRepository);
		ShipperInfo shipper = ShipperInfo.of(1L, "홍길동", "010-1234-5678", "hong123");
		HubInfo departureHub = HubInfo.of(UUID.randomUUID());
		HubInfo destinationHub = HubInfo.of(UUID.randomUUID());
		DeliveryRoute route = DeliveryRoute.create(shipper, departureHub, destinationHub, 1,
			DeliveryEstimate.of(1000, 30));

		Delivery delivery = Delivery.create(
			UUID.randomUUID(),
			Address.of("서울시 강남구", "1층", "12345", GeoLocation.of(37.5665, 126.9780)),
			shipper,
			departureHub,
			destinationHub,
			"조심히 배송",
			List.of(route)
		);

		Delivery saved = adapter.save(delivery);

		assertThat(saved.getId()).isNotNull();
		Delivery found = adapter.findById(saved.getId()).orElseThrow();
		assertThat(found.getOrderId()).isEqualTo(delivery.getOrderId());
		Delivery byOrder = adapter.findByOrderId(delivery.getOrderId()).orElseThrow();
		assertThat(byOrder.getId()).isEqualTo(saved.getId());
	}

	@Test
	@DisplayName("DeliveryRoute를 조회하면 페이징된 결과가 반환되어야 한다")
	void getDeliveryRoutes_ShouldReturnPagedResults() {
		DeliveryRepositoryAdapter adapter = new DeliveryRepositoryAdapter(jpaDeliveryRepository);
		ShipperInfo shipper = ShipperInfo.of(1L, "홍길동", "010-1234-5678", "hong123");
		HubInfo departureHub = HubInfo.of(UUID.randomUUID());
		HubInfo destinationHub = HubInfo.of(UUID.randomUUID());
		DeliveryRoute route1 = DeliveryRoute.create(shipper, departureHub, destinationHub, 1,
			DeliveryEstimate.of(1000, 30));
		DeliveryRoute route2 = DeliveryRoute.create(shipper, departureHub, destinationHub, 2,
			DeliveryEstimate.of(2000, 60));

		Delivery delivery = Delivery.create(
			UUID.randomUUID(),
			Address.of("서울시 강남구", "1층", "12345", GeoLocation.of(37.5, 127.0)),
			shipper,
			departureHub,
			destinationHub,
			null,
			List.of(route1, route2)
		);
		adapter.save(delivery);

		PageRequest pageRequest = new PageRequest(0, PageSizeType.SIZE_10, "created_at", true);
		Page<DeliveryRoute> page = adapter.getDeliveryRoutes(delivery.getId(), pageRequest);

		assertThat(page.getContent()).hasSize(2);
	}

	@Test
	@DisplayName("Delivery 조회 시 존재하지 않으면 Optional이 비어 있어야 한다")
	void findById_ShouldReturnEmptyIfNotFound() {
		DeliveryRepositoryAdapter adapter = new DeliveryRepositoryAdapter(jpaDeliveryRepository);
		UUID randomId = UUID.randomUUID();
		var result = adapter.findById(randomId);
		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("DeliveryOrderId 조회 시 존재하지 않으면 Optional이 비어 있어야 한다")
	void findByOrderId_ShouldReturnEmptyIfNotFound() {
		DeliveryRepositoryAdapter adapter = new DeliveryRepositoryAdapter(jpaDeliveryRepository);
		UUID randomOrderId = UUID.randomUUID();
		var result = adapter.findByOrderId(randomOrderId);
		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("여러 Delivery를 저장하고 ID로 조회하면 각각 정상적으로 반환되어야 한다")
	void saveMultipleDeliveries_ShouldReturnCorrectlyById() {
		DeliveryRepositoryAdapter adapter = new DeliveryRepositoryAdapter(jpaDeliveryRepository);
		ShipperInfo shipper = ShipperInfo.of(1L, "홍길동", "010-1234-5678", "hong123");
		HubInfo hub1 = HubInfo.of(UUID.randomUUID());
		HubInfo hub2 = HubInfo.of(UUID.randomUUID());

		Delivery delivery1 = Delivery.create(
			UUID.randomUUID(),
			Address.of("서울시 강남구", "1층", "12345", GeoLocation.of(37.5, 127.0)),
			shipper,
			hub1,
			hub2,
			null,
			List.of(DeliveryRoute.create(shipper, hub1, hub2, 1, DeliveryEstimate.of(1000, 30)))
		);

		Delivery delivery2 = Delivery.create(
			UUID.randomUUID(),
			Address.of("서울시 서초구", "2층", "67890", GeoLocation.of(37.5, 127.0)),
			shipper,
			hub2,
			hub1,
			null,
			List.of(DeliveryRoute.create(shipper, hub2, hub1, 1, DeliveryEstimate.of(2000, 45)))
		);

		adapter.save(delivery1);
		adapter.save(delivery2);

		var found1 = adapter.findById(delivery1.getId()).orElseThrow();
		var found2 = adapter.findById(delivery2.getId()).orElseThrow();
		assertThat(found1.getOrderId()).isEqualTo(delivery1.getOrderId());
		assertThat(found2.getOrderId()).isEqualTo(delivery2.getOrderId());
	}
}