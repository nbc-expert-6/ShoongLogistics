package com.shoonglogitics.orderservice.domain.delivery.application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.orderservice.domain.delivery.application.command.CreateDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.command.DeleteDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.command.ProcessDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.command.UpdateDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.FindDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.ListDeliveryRouteResult;
import com.shoonglogitics.orderservice.domain.delivery.application.query.ListDeliveryRouteQuery;
import com.shoonglogitics.orderservice.domain.delivery.application.service.CompanyClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.HubClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.OrderClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.UserClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.CompanyInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.OrderInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.RoutesInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.ShipperInfo;
import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;
import com.shoonglogitics.orderservice.domain.delivery.domain.entity.DeliveryRoute;
import com.shoonglogitics.orderservice.domain.delivery.domain.repository.DeliveryRepository;
import com.shoonglogitics.orderservice.domain.delivery.domain.service.DeliveryDomainService;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.Address;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryEstimate;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryStatus;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.HubInfo;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.ProcessHubShippingCommand;
import com.shoonglogitics.orderservice.domain.order.domain.vo.GeoLocation;
import com.shoonglogitics.orderservice.global.common.vo.ShipperType;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "delivery-service")
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final DeliveryDomainService domainService;

	private final OrderClient orderClient;
	private final CompanyClient companyClient;
	private final HubClient hubClient;
	private final UserClient userClient;

	/**
	 * 배송 생성
	 * 주문 생성 시 자동 호출
	 */
	@Transactional
	public CreateDeliveryResult createDelivery(CreateDeliveryCommand command) {
		//주문 서비스에서 주문 정보 조회
		OrderInfo orderData = getOrderInfo(command.orderId(), command.userId(), command.role());

		//업체 서비스에서 수령업체, 공급업체 허브 정보 조회
		CompanyInfo receiverHub = getCompanyHub(orderData.receiverCompanyId(), command);
		CompanyInfo supplierHub = getCompanyHub(orderData.supplierCompanyId(), command);

		//허브 서비스에 허브 간 최적 배송 경로 요청
		List<RoutesInfo> routeInfos = getDeliveryRoutes(supplierHub, receiverHub, command);

		// 회원 서비스에서 허브별 배송 담당자 전부 조회 (타입별, 순서 오름차순)
		Map<ShipperType, Map<UUID, List<ShipperInfo>>> allHubShippers = getAllHubShippers(routeInfos,
			command);

		//배송 경로 엔티티 생성
		List<DeliveryRoute> deliveryRoutes = createDeliveryRoutes(routeInfos, allHubShippers);

		//최종 배송 담당자 배정 (수령업체 허브 기준)
		ShipperInfo finalShipper = assignShipper(receiverHub.hubId(), allHubShippers,
			ShipperType.COMPANY_SHIPPER);

		//Delivery 엔티티 생성
		Delivery delivery = Delivery.create(
			command.orderId(),
			Address.of(orderData.address(), orderData.addressDetail(), orderData.zipCode(),
				GeoLocation.of(orderData.latitude(), orderData.longitude())),
			com.shoonglogitics.orderservice.domain.delivery.domain.vo.ShipperInfo.of(finalShipper.shipperId(),
				finalShipper.shipperName(),
				finalShipper.shipperPhoneNumber(), finalShipper.slackId()),
			HubInfo.of(supplierHub.hubId()),
			HubInfo.of(receiverHub.hubId()),
			orderData.deliveryRequest(),
			deliveryRoutes
		);

		//배송 정보 저장
		Delivery saved = deliveryRepository.save(delivery);

		//응답
		return CreateDeliveryResult.from(saved.getId());
	}

	public FindDeliveryResult getDelivery(UUID orderId) {
		Delivery delivery = getDeliveryByOrderId(orderId);
		return FindDeliveryResult.from(delivery);
	}

	public Page<ListDeliveryRouteResult> getDeliveryRoutes(ListDeliveryRouteQuery query) {
		Page<DeliveryRoute> deliveryRoutes = deliveryRepository.getDeliveryRoutes(query.deliveryId(),
			query.pageRequest());
		return deliveryRoutes.map(ListDeliveryRouteResult::from);
	}

	//배송 수정
	@Transactional
	public UUID updateDelivery(UpdateDeliveryCommand command) {
		Delivery delivery = getDeliveryById(command.deliveryId());
		OrderInfo orderInfo = getOrderInfo(delivery.getOrderId(), command.userId(), command.role());
		if (command.role() != UserRoleType.MASTER && command.userId().longValue() != orderInfo.userId().longValue()) {
			throw new IllegalArgumentException("현재 로그인한 사용자가 주문한 배송정보만 수정할 수 있습니다.");
		}
		delivery.update(
			command.request(),
			command.shipperId(),
			command.shipperName(),
			command.shipperPhoneNumber(),
			command.shipperSlackId()
		);
		return delivery.getId();
	}

	//배송 삭제
	//배송 경로까지 전부 삭제
	@Transactional
	public UUID deleteDelivery(DeleteDeliveryCommand command) {
		Delivery delivery = getDeliveryById(command.deliveryId());
		delivery.delete(command.userId());
		return delivery.getId();
	}

	//허브 운송처리
	//이전 경로가 배송이 끝났는지 확인
	@Transactional
	public UUID processHubShipping(ProcessHubShippingCommand command) {
		Delivery delivery = getDeliveryById(command.deliveryId());
		List<DeliveryRoute> deliveryRoutes = delivery.getDeliveryRoutes();

		//대상 경로 찾기
		DeliveryRoute targetRoute = deliveryRoutes.stream()
			.filter(route -> route.getId().equals(command.deliveryRouteId()))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("해당 배송경로를 찾을 수 없습니다."));

		int targetSeq = targetRoute.getSequence();

		//이전 허브 이동이 처리되었는지 검사
		boolean allPreviousArrived = deliveryRoutes.stream()
			.filter(route -> route.getSequence() < targetSeq)
			.allMatch(route -> route.getStatus() == DeliveryStatus.ARRIVAL_HUB_ARRIVED);

		if (!allPreviousArrived) {
			throw new IllegalStateException("이전 허브 경로가 모두 도착 상태가 아닙니다.");
		}

		if (command.isDeparture()) {
			// 출발 처리
			targetRoute.departure();

			//만약 첫 운송 시작이면 배송의 상태를 허브 운송중으로 변경
			if (targetSeq == 1) {
				delivery.startHubTransit();
			}

		} else {
			// 도착 처리
			targetRoute.arrive(command.distance(), command.duration());

			//목적지 허브에 도착했다면 배송 상태를 목적지 허브 도착으로 변경
			if (targetSeq == deliveryRoutes.size()) {
				delivery.completeHubTransit();
			}
		}

		return delivery.getDeliveryRoutes().get(targetSeq - 1).getId();
	}

	//목적지 배송 처리
	@Transactional
	public UUID processDelivery(ProcessDeliveryCommand command) {
		Delivery delivery = getDeliveryById(command.deliveryId());
		if (command.isDeparture()) {
			delivery.startDelivery();
			//Todo 이벤트로 알림 서비스 호출
		}
		if (!command.isDeparture()) {
			delivery.completeDelivery();
		}
		return delivery.getId();
	}

	/*
	내부 유틸용 함수
	 */

	//id로 배송 정보 조회
	private Delivery getDeliveryById(UUID deliveryId) {
		return deliveryRepository.findById(deliveryId).orElseThrow(
			() -> new NoSuchElementException("배송 정보가 존재하지 않습니다.")
		);
	}

	//orderId로 배송 정보 조회
	private Delivery getDeliveryByOrderId(UUID orderId) {
		return deliveryRepository.findByOrderId(orderId).orElseThrow(
			() -> new NoSuchElementException("배송 정보가 존재하지 않습니다.")
		);
	}

	/** 주문 서비스에서 주문 정보 조회 */
	private OrderInfo getOrderInfo(UUID orderId, Long userId, UserRoleType role) {
		return orderClient.getOrderInfo(orderId, userId, role);
	}

	/** 업체 서비스에서 허브 정보 조회 */
	private CompanyInfo getCompanyHub(UUID companyId, CreateDeliveryCommand command) {
		return companyClient.getCompanyInfo(companyId, command.userId(), command.role());
	}

	/** 허브 서비스에서 허브 간 최적 배송 경로 조회 */
	private List<RoutesInfo> getDeliveryRoutes(CompanyInfo supplierHub,
		CompanyInfo receiverHub,
		CreateDeliveryCommand command) {
		return hubClient.getDeliveryRoutesInfo(
			supplierHub.hubId(),
			receiverHub.hubId(),
			command.userId(),
			command.role()
		);
	}

	/**
	 * 회원 서비스에서 허브별 배송 담당자 전부 조회
	 * 출발/도착 허브 기준, 배송/운송 타입별로 Map 구성
	 */
	private Map<ShipperType, Map<UUID, List<ShipperInfo>>> getAllHubShippers(
		List<RoutesInfo> routes, CreateDeliveryCommand command) {

		Map<UUID, List<ShipperInfo>> deliveryMap = new HashMap<>();
		Map<UUID, List<ShipperInfo>> transportMap = new HashMap<>();

		for (RoutesInfo route : routes) {
			// 출발/도착 허브 모두 조회
			for (UUID hubId : List.of(route.departureHubId(), route.arrivalHubId())) {
				if (deliveryMap.containsKey(hubId))
					continue;

				// 허브별 모든 담당자 조회 및 순서 기준 오름차순 정렬
				List<ShipperInfo> all = userClient.getShippers(hubId, command.userId(), command.role())
					.stream()
					.sorted(Comparator.comparingInt(ShipperInfo::order))
					.toList();

				// 배송 / 운송 타입별 분류
				deliveryMap.put(hubId, filterByType(all, ShipperType.HUB_SHIPPER));
				transportMap.put(hubId, filterByType(all, ShipperType.COMPANY_SHIPPER));
			}
		}

		Map<ShipperType, Map<UUID, List<ShipperInfo>>> result = new HashMap<>();
		result.put(ShipperType.HUB_SHIPPER, deliveryMap);
		result.put(ShipperType.COMPANY_SHIPPER, transportMap);
		return result;
	}

	/** 특정 타입으로 허브 담당자 필터링 */
	private List<ShipperInfo> filterByType(List<ShipperInfo> shippers, ShipperType type) {
		return shippers.stream()
			.filter(s -> s.type() == type)
			.toList();
	}

	/**
	 * 배송 경로 엔티티 생성
	 * 허브 담당자를 배정하여 DeliveryRoute 객체로 변환
	 */
	private List<DeliveryRoute> createDeliveryRoutes(List<RoutesInfo> routeInfos,
		Map<ShipperType, Map<UUID, List<ShipperInfo>>> allHubShippers) {

		List<DeliveryRoute> routes = new ArrayList<>();
		for (RoutesInfo info : routeInfos) {
			//허브 간 이동 담당자 배정
			ShipperInfo assigned = assignShipper(info.departureHubId(), allHubShippers,
				ShipperType.HUB_SHIPPER);

			//배송 경로 엔티티 생성
			routes.add(DeliveryRoute.create(
				com.shoonglogitics.orderservice.domain.delivery.domain.vo.ShipperInfo.of(assigned.shipperId(),
					assigned.shipperName(), assigned.shipperPhoneNumber(),
					assigned.slackId()),
				HubInfo.of(info.departureHubId()),
				HubInfo.of(info.arrivalHubId()),
				info.sequence(),
				DeliveryEstimate.of(info.estimateDistance(), info.estimatedDuration())
			));
		}
		return routes;
	}

	/**
	 * 해당 허브의 배송 담당자 배정
	 * 1순위: 배송 가능한 담당자 중 order가 가장 빠른 사람
	 * 2순위: 전체 중 order가 가장 빠른 사람
	 * 없으면 예외 발생
	 */
	private ShipperInfo assignShipper(UUID hubId,
		Map<ShipperType, Map<UUID, List<ShipperInfo>>> allHubShippers,
		ShipperType type) {
		List<ShipperInfo> shippers = allHubShippers.getOrDefault(type, Map.of())
			.getOrDefault(hubId, List.of());

		return shippers.stream()
			.filter(ShipperInfo::isShippingAvailable)
			.min(Comparator.comparingInt(ShipperInfo::order))
			.or(() -> shippers.stream().min(Comparator.comparingInt(ShipperInfo::order)))
			.orElseThrow(() -> new IllegalStateException("허브 " + hubId + "에 배송 담당자가 존재하지 않습니다."));
	}
}