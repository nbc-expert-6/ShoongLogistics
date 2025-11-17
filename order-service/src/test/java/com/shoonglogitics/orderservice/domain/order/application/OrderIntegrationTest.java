package com.shoonglogitics.orderservice.domain.order.application;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import com.shoonglogitics.orderservice.domain.order.domain.repository.OrderRepository;
import com.shoonglogitics.orderservice.domain.order.domain.vo.OrderStatus;
import com.shoonglogitics.orderservice.domain.payment.domain.entity.Payment;
import com.shoonglogitics.orderservice.domain.payment.domain.repository.PaymentRepository;
import com.shoonglogitics.orderservice.domain.payment.domain.vo.PaymentStatus;
import com.shoonglogitics.orderservice.domain.product.domain.entity.Stock;
import com.shoonglogitics.orderservice.domain.product.domain.repository.StockRepository;
import com.shoonglogitics.orderservice.domain.product.domain.vo.Quantity;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

@SpringBootTest
@ActiveProfiles("test")
class OrderIntegrationTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private StockRepository stockRepository;

	@BeforeEach
	void setUp() {
		Stock stock = createStock();
		stockRepository.save(stock);
	}

	//요청에 쓸 공용 객체
	//정상요청
	private CreateOrderCommand createSuccessfulCommand() {
		CreateOrderItemCommand itemCommand = CreateOrderItemCommand.builder()
			.productId(UUID.randomUUID())
			.price(BigDecimal.valueOf(1000))
			.quantity(10)
			.build();

		return CreateOrderCommand.builder()
			.userId(1L)
			.role(UserRoleType.MASTER)
			.receiverCompanyId(UUID.randomUUID())
			.receiverCompanyName("수령업체")
			.supplierCompanyId(UUID.randomUUID())
			.supplierCompanyName("공급업체")
			.address("address")
			.addressDetail("addressDetail")
			.zipCode("zipCode")
			.latitude(35.1587)
			.longitude(129.1604)
			.totalPrice(BigDecimal.valueOf(10000))
			.orderItems(List.of(itemCommand))
			.build();
	}

	//비정상 요청(계산금액 != 주문총액)
	private CreateOrderCommand createUnsuccessfulCommand() {
		CreateOrderItemCommand itemCommand = CreateOrderItemCommand.builder()
			.productId(UUID.randomUUID())
			.price(BigDecimal.valueOf(1000))
			.quantity(10)
			.build();

		return CreateOrderCommand.builder()
			.userId(1L)
			.role(UserRoleType.MASTER)
			.receiverCompanyId(UUID.randomUUID())
			.receiverCompanyName("수령업체")
			.supplierCompanyId(UUID.randomUUID())
			.supplierCompanyName("공급업체")
			.address("address")
			.addressDetail("addressDetail")
			.zipCode("zipCode")
			.latitude(35.1587)
			.longitude(129.1604)
			.totalPrice(BigDecimal.valueOf(100000))
			.orderItems(List.of(itemCommand))
			.build();
	}

	private Stock createStock() {
		return Stock.create(
			UUID.randomUUID(),
			Quantity.of(100)
		);
	}

	@Test
	@DisplayName("주문 생성부터 재고 차감까지 전체 플로우가 정상 동작한다")
	void fullOrderFlow_ShouldWorkCorrectly() throws InterruptedException {
		// given
		// 주문 요청 생성
		CreateOrderCommand command = createSuccessfulCommand();
		Stock stock = Stock.create(
			command.orderItems().get(0).productId(),
			Quantity.of(100)
		);
		stockRepository.save(stock);

		// when
		//주문생성 실행
		UUID orderId = orderService.createOrder(command);

		// 비동기 처리를 위한 대기
		Thread.sleep(3000);

		// then
		// 주문 생성 확인
		// 주문은 결제 완료후 이벤트 처리해서 PAID -> SHIPPED로 바뀌어야함
		Order order = orderRepository.findById(orderId).orElseThrow();
		assertThat(order.getStatus()).isEqualTo(OrderStatus.SHIPPED);

		// 결제 생성 확인
		Payment payment = paymentRepository.findByOrderId(order.getId()).orElseThrow();
		assertThat(payment.getOrderId()).isEqualTo(orderId);
		assertThat(payment.getStatus()).isEqualTo(PaymentStatus.COMPLETED);

		// 재고 차감 확인
		Stock updatedStock = stockRepository.findByProductId(stock.getProductId()).orElseThrow();
		assertThat(updatedStock.getQuantity().getQuantity()).isEqualTo(90);
	}

	@Test
	@DisplayName("결제 실패 시 재고는 차감되지 않는다")
	void whenPaymentFails_StockShouldNotDecrease() throws InterruptedException {
		// given
		CreateOrderCommand command = createUnsuccessfulCommand();
		Stock stock = Stock.create(
			command.orderItems().get(0).productId(),
			Quantity.of(100)
		);
		stockRepository.save(stock);

		// when
		UUID orderId = orderService.createOrder(command);
		Thread.sleep(2000);

		// then

		// 주문 생성 확인
		// 결제가 실패하기 때문에 PENDING으로 유지
		Order order = orderRepository.findById(orderId).orElseThrow();
		assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);

		// 결제가 실패했으므로 재고는 유지
		Stock updatedStock = stockRepository.findByProductId(
			stock.getProductId()).orElseThrow();
		assertThat(updatedStock.getQuantity().getQuantity()).isEqualTo(100);
	}
}