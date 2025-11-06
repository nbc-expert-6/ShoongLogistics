package com.shoonglogitics.orderservice.domain.order.application;

import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import com.shoonglogitics.orderservice.domain.order.domain.entity.OrderItem;
import com.shoonglogitics.orderservice.domain.order.domain.repository.OrderRepository;
import com.shoonglogitics.orderservice.domain.order.domain.service.OrderDomainService;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Address;
import com.shoonglogitics.orderservice.domain.order.domain.vo.CompanyInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.GeoLocation;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Money;
import com.shoonglogitics.orderservice.domain.order.domain.vo.ProductInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Quentity;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderDomainService orderDomainService;

	GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

	@InjectMocks
	private OrderService orderService;

	@DisplayName("재고가 충분하고 상품 금액의 총합과 주문금액이 일치하면 주문이 생성된다.")
	@Test
	void createOrder_WithSufficientStockAndMatchPrice_ShouldCreatedOrder() {

		// given
		double longitude = 126.978388;
		double latitude = 37.566610;

		List<CreateOrderItemCommand> items = List.of(
			new CreateOrderItemCommand(UUID.randomUUID(), 1000, 2),
			new CreateOrderItemCommand(UUID.randomUUID(), 1000, 3)
		);

		CreateOrderCommand command = new CreateOrderCommand(
			1L,
			UserRoleType.MASTER,
			UUID.randomUUID(),
			"수령업체",
			UUID.randomUUID(),
			"공급업체",
			"빨리 배송해주세요",
			"서울특별시 중구 세종대로 110",
			"서울시청",
			"00000",
			longitude,
			latitude,
			10000L,
			items
		);

		List<OrderItem> orderItems = List.of(
			OrderItem.create(
				ProductInfo.of(items.get(0).productId(), 2000), Quentity.of(items.get(0).amount())),
			OrderItem.create(
				ProductInfo.of(items.get(1).productId(), 2000), Quentity.of(items.get(1).amount()))
		);

		// 실제 save될 Order 엔티티
		Order savedOrder = Order.create(
			CompanyInfo.of(command.receiverCompanyId(), command.receiverCompanyName()),
			CompanyInfo.of(command.supplierCompanyId(), command.supplierCompanyName()),
			command.request(),
			Money.of(command.totalPrice()),
			Address.of(
				command.address(),
				command.addressDetail(),
				command.zipCode(),
				GeoLocation.of(latitude, longitude)
			),
			orderItems
		);

		// 도메인 검증은 정상 동작
		willDoNothing()
			.given(orderDomainService)
			.validateOrder(any(Order.class), any(Money.class));

		// 저장 성공 시 Optional.of(order) 반환
		given(orderRepository.save(any(Order.class))).willReturn(Optional.of(savedOrder));

		// when
		UUID result = orderService.createOrder(command);

		// then
		//UUID 값 검증 불가능
		verify(orderRepository, times(1)).save(any(Order.class));
	}
}