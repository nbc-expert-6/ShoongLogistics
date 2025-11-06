package com.shoonglogitics.orderservice.domain.order.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.application.service.CompanyClient;
import com.shoonglogitics.orderservice.domain.order.application.service.UserClient;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j(topic = "order-service")
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderDomainService orderDomainService;

	private final UserClient userClient;
	private final CompanyClient companyClient;

	@Transactional
	public UUID createOrder(CreateOrderCommand command) {
		log.info("Create order: {}", command);
		//주문자 검증
		validateCustomer(command.userId(), command.role());

		//주문상품 엔티티 생성 (별도 함수로 분리, 함수에서 상품 유효성 검증)
		//내부에서 quantity vo 생성
		List<OrderItem> orderItems = createItems(command.orderItems());

		//주문 상품 검증
		validateItems(orderItems);

		//총 주문 금액 vo 생성
		Money totalPrice = Money.of(command.totalPrice());

		//주소 vo 생성
		Address address = Address.of(
			command.address(), command.addressDetail(), command.zipCode(),
			GeoLocation.of(command.latitude(), command.longitude()));

		//수령업체, 공급업체 정보 vo 생성
		CompanyInfo receiverInfo = CompanyInfo.of(command.receiverCompanyId(), command.receiverCompanyName());
		CompanyInfo supplierInfo = CompanyInfo.of(command.supplierCompanyId(), command.supplierCompanyName());

		//정보 조합하여 order 엔티티 생성
		Order order = Order.create(
			receiverInfo,
			supplierInfo,
			command.request(),
			totalPrice,
			address,
			orderItems
		);

		//Order 검증(도메인 서비스 사용)
		validateOrder(order, totalPrice);

		//응답
		Optional<Order> createdOrder = orderRepository.save(order);
		if (createdOrder.isEmpty()) {
			throw new IllegalStateException("주문이 생성되지 않았습니다.");
		}
		return createdOrder.get().getId();
	}

	/*
	내부용 유틸 함수들
	 */

	//업체 서비스와 통신을 통해 현재 주문하려는 상품들이 존재하는지, 가격이 일치하는지, 재고는 있는지 검증
	//문제가 없다면 엔티티로 만들어서 반환, 문제가 생기면 예외 발생
	public List<OrderItem> createItems(List<CreateOrderItemCommand> createOrderItemCommands) {
		return createOrderItemCommands.stream()
			.map(cmd -> OrderItem.create(
				ProductInfo.of(cmd.productId(), Money.of(cmd.price())),
				Quentity.of(cmd.quantity())
			)).toList();
	}

	//회원 서비스와 통신을 통해 유효한 회원인지 검증
	//문제가 생기면 예외 발생
	public void validateCustomer(Long userId, UserRoleType role) {
		//Todo: 클라이언트를 통해 외부 통신 후 로직수행
		userClient.canOrder(userId, role);
	}

	public void validateItems(List<OrderItem> orderItems) {
		//Todo: 클라이언트를 통해 외부 통신 후 로직수행
		//회원 컨텍스트에 해당 상품의 업체id로 담당자 id를 조회해오기
		//업체 담당자가 수정하는 것처럼 요청
		companyClient.validateItems(orderItems);
	}

	private void validateOrder(Order order, Money totalPrice) {
		orderDomainService.validateOrder(order, totalPrice);
	}
}
