package com.shoonglogitics.orderservice.domain.order.infrastructure.repository;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import com.shoonglogitics.orderservice.domain.order.domain.entity.OrderItem;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Address;
import com.shoonglogitics.orderservice.domain.order.domain.vo.CompanyInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.GeoLocation;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Money;
import com.shoonglogitics.orderservice.domain.order.domain.vo.ProductInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Quentity;
import com.shoonglogitics.orderservice.domain.order.infrastructure.repository.audit.TestAuditorConfig;
import com.shoonglogitics.orderservice.global.common.dto.PageRequest;
import com.shoonglogitics.orderservice.global.common.vo.PageSizeType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestAuditorConfig.class)
@Transactional
class OrderRepositoryAdapterTest {

	@Autowired
	private JpaOrderRepository jpaOrderRepository;

	@Test
	@DisplayName("주문 저장 시 주문이 정상적으로 DB에 저장되어야 한다")
	void save_ShouldPersistOrder() {
		// Given
		OrderRepositoryAdapter repository = new OrderRepositoryAdapter(jpaOrderRepository);

		OrderItem item = createOrderItem();
		List<OrderItem> items = new ArrayList<>();
		items.add(item);

		Order order = Order.create(
			1L,
			CompanyInfo.of(UUID.randomUUID(), "Receiver"),
			CompanyInfo.of(UUID.randomUUID(), "Supplier"),
			"Request",
			"DeliveryRequest",
			Money.of(new BigDecimal("1000")),
			createAddress(),
			items
		);

		// When
		Order saved = repository.save(order);

		// Then
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getOrderItems()).hasSize(1);
	}

	@Test
	@DisplayName("ID로 주문 조회 시 정상적으로 주문이 반환되어야 한다")
	void findById_ShouldReturnOrder() {
		// Given
		OrderRepositoryAdapter repository = new OrderRepositoryAdapter(jpaOrderRepository);

		OrderItem item = createOrderItem();
		List<OrderItem> items = new ArrayList<>();
		items.add(item);

		Order order = Order.create(
			1L,
			CompanyInfo.of(UUID.randomUUID(), "Receiver"),
			CompanyInfo.of(UUID.randomUUID(), "Supplier"),
			"Request",
			"DeliveryRequest",
			Money.of(new BigDecimal("1000")),
			createAddress(),
			items
		);
		repository.save(order);

		// When
		Order found = repository.findById(order.getId()).orElseThrow();

		// Then
		assertThat(found.getId()).isEqualTo(order.getId());
	}

	@Test
	@DisplayName("마스터 기준으로 페이징된 주문 목록을 반환해야 한다")
	void getOrdersByMaster_ShouldReturnPagedOrders() {
		// Given
		OrderRepositoryAdapter repository = new OrderRepositoryAdapter(jpaOrderRepository);

		for (int i = 0; i < 5; i++) {
			OrderItem item = createOrderItem();
			List<OrderItem> items = new ArrayList<>();
			items.add(item);

			Order order = Order.create(
				(long)i,
				CompanyInfo.of(UUID.randomUUID(), "Receiver" + i),
				CompanyInfo.of(UUID.randomUUID(), "Supplier" + i),
				"Request" + i,
				"DeliveryRequest" + i,
				Money.of(new BigDecimal("1000")),
				createAddress(),
				items
			);
			repository.save(order);
		}

		PageRequest pageRequest = new PageRequest(0, PageSizeType.SIZE_10, "createdAt", true);

		// When
		Page<Order> page = repository.getOrdersByMaster(pageRequest);

		// Then
		assertThat(page.getContent()).hasSize(5);
	}

	@Test
	@DisplayName("사용자 ID 기준으로 페이징된 주문 목록을 반환해야 한다")
	void getOrdersByUserId_ShouldReturnPagedOrders() {
		// Given
		OrderRepositoryAdapter repository = new OrderRepositoryAdapter(jpaOrderRepository);

		Long userId = 123L;
		for (int i = 0; i < 3; i++) {
			OrderItem item = createOrderItem();
			List<OrderItem> items = new ArrayList<>();
			items.add(item);

			Order order = Order.create(
				userId,
				CompanyInfo.of(UUID.randomUUID(), "Receiver" + i),
				CompanyInfo.of(UUID.randomUUID(), "Supplier" + i),
				"Request" + i,
				"DeliveryRequest" + i,
				Money.of(new BigDecimal("1000")),
				createAddress(),
				items
			);
			repository.save(order);
		}

		PageRequest pageRequest = new PageRequest(0, PageSizeType.SIZE_10, "createdAt", true);

		// When
		Page<Order> page = repository.getOrdersByUserId(userId, pageRequest);

		// Then
		assertThat(page.getContent()).hasSize(3);
		assertThat(page.getContent().stream().allMatch(o -> o.getUserId().equals(userId))).isTrue();
	}

	// ----- Helper methods -----
	private OrderItem createOrderItem() {
		return OrderItem.create(
			ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("1000"))),
			Quentity.of(1)
		);
	}

	private Address createAddress() {
		return Address.of(
			"Seoul",
			"Detail",
			"12345",
			GeoLocation.of(37.5665, 126.9780)  // 서울 시청 기준 위도·경도
		);
	}
}