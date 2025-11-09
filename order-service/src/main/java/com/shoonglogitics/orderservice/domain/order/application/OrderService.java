package com.shoonglogitics.orderservice.domain.order.application;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.application.dto.FindOrderResult;
import com.shoonglogitics.orderservice.domain.order.application.dto.ListOrderResult;
import com.shoonglogitics.orderservice.domain.order.application.query.ListOrderQuery;
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
import com.shoonglogitics.orderservice.global.common.dto.PageRequest;
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
		//주문자 검증(외부 호출 후 검증)
		validateCustomer(command.userId(), command.role());

		//주문 상품 검증(외부 호출 후 값 비교)
		validateItems(command.orderItems());

		//주문상품 엔티티 생성
		List<OrderItem> orderItems = createItems(command.orderItems());

		//총 주문 금액 vo 생성
		Money totalPrice = Money.of(command.totalPrice());

		//주문 가능한지 검증(상품이 1개 이상이고, 상품총액과 총 결제금액의 일치여부)
		validateOrder(orderItems, totalPrice);

		//주소 vo 생성
		Address address = Address.of(
			command.address(), command.addressDetail(), command.zipCode(),
			GeoLocation.of(command.latitude(), command.longitude()));

		//수령업체, 공급업체 정보 vo 생성
		CompanyInfo receiverInfo = CompanyInfo.of(command.receiverCompanyId(), command.receiverCompanyName());
		CompanyInfo supplierInfo = CompanyInfo.of(command.supplierCompanyId(), command.supplierCompanyName());

		//정보 조합하여 order 엔티티 생성
		Order order = Order.create(
			command.userId(),
			receiverInfo,
			supplierInfo,
			command.request(),
			command.deliveryRequest(),
			totalPrice,
			address,
			orderItems
		);

		//재고 차감 요청
		companyClient.decreaseStock(orderItems);

		//응답
		Order createdOrder = orderRepository.save(order);

		return createdOrder.getId();
	}

	//orderid로 상세조회
	public FindOrderResult getOrder(UUID orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(
			() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다.")
		);
		return FindOrderResult.from(order);
	}

	//목록조회
	public Page<ListOrderResult> listOrders(ListOrderQuery query) {
		Page<Order> orders = getOrdersByRole(query.role(), query.userId(), query.pageRequest());
		return orders.map(ListOrderResult::from);
	}

	private Page<Order> getOrdersByRole(UserRoleType role, Long userId, PageRequest pageRequest) {
		if (role == UserRoleType.MASTER) {
			return orderRepository.getOrdersByMaster(pageRequest);
		} else {
			return orderRepository.getOrdersByUserId(userId, pageRequest);
		}
	}
	/*
	내부용 유틸 함수들
	 */

	//문제가 없다면 엔티티로 만들어서 반환, 문제가 생기면 예외 발생
	private List<OrderItem> createItems(List<CreateOrderItemCommand> createOrderItemCommands) {
		if (createOrderItemCommands == null || createOrderItemCommands.isEmpty()) {
			throw new IllegalArgumentException("주문 항목은 최소 1개 이상이어야 합니다.");
		}
		return createOrderItemCommands.stream()
			.map(cmd -> OrderItem.create(
				ProductInfo.of(cmd.productId(), Money.of(cmd.price())),
				Quentity.of(cmd.quantity())
			)).toList();
	}

	//회원 서비스와 통신을 통해 유효한 회원인지 검증
	//문제가 생기면 예외 발생
	private void validateCustomer(Long userId, UserRoleType role) {
		//Todo: 클라이언트를 통해 외부 통신 후 로직수행
		userClient.canOrder(userId, role);
	}

	private void validateItems(List<CreateOrderItemCommand> orderItems) {
		//Todo: 클라이언트를 통해 외부 통신 후 로직수행
		//회원 컨텍스트에 해당 상품의 업체id로 담당자 id를 조회해오기
		//업체 담당자가 수정하는 것처럼 요청
		companyClient.validateItems(orderItems);
	}

	private void validateOrder(List<OrderItem> orderItems, Money totalPrice) {
		orderDomainService.validateOrder(orderItems, totalPrice);
	}
}
