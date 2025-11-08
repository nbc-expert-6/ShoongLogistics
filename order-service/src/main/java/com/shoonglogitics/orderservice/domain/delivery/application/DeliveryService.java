package com.shoonglogitics.orderservice.domain.delivery.application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.orderservice.domain.delivery.application.command.CreateDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryCompanyInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryOrderInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryRoutesInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryShipperInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.service.CompanyClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.HubClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.OrderClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.UserClient;
import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;
import com.shoonglogitics.orderservice.domain.delivery.domain.entity.DeliveryRoute;
import com.shoonglogitics.orderservice.domain.delivery.domain.repository.DeliveryRepository;
import com.shoonglogitics.orderservice.domain.delivery.domain.service.DeliveryDomainService;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.Address;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryEstimate;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.HubInfo;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.ShipperInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.GeoLocation;
import com.shoonglogitics.orderservice.global.common.vo.ShipperType;

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
		CreateDeliveryOrderInfo orderData = getOrderInfo(command);

		//업체 서비스에서 수령업체, 공급업체 허브 정보 조회
		CreateDeliveryCompanyInfo receiverHub = getCompanyHub(orderData.receiverCompanyId(), command);
		CreateDeliveryCompanyInfo supplierHub = getCompanyHub(orderData.supplierCompanyId(), command);

		//허브 서비스에 허브 간 최적 배송 경로 요청
		List<CreateDeliveryRoutesInfo> routeInfos = getDeliveryRoutes(supplierHub, receiverHub, command);

		// 회원 서비스에서 허브별 배송 담당자 전부 조회 (타입별, 순서 오름차순)
		Map<ShipperType, Map<UUID, List<CreateDeliveryShipperInfo>>> allHubShippers = getAllHubShippers(routeInfos,
			command);

		//배송 경로 엔티티 생성
		List<DeliveryRoute> deliveryRoutes = createDeliveryRoutes(routeInfos, allHubShippers);

		//최종 배송 담당자 배정 (수령업체 허브 기준)
		CreateDeliveryShipperInfo finalShipper = assignShipper(receiverHub.hubId(), allHubShippers,
			ShipperType.COMPANY_SHIPPER);

		//Delivery 엔티티 생성
		Delivery delivery = Delivery.create(
			command.orderId(),
			Address.of(orderData.address(), orderData.addressDetail(), orderData.zipCode(),
				GeoLocation.of(orderData.latitude(), orderData.longitude())),
			ShipperInfo.of(finalShipper.shipperId(), finalShipper.shipperName(),
				finalShipper.shipperPhoneNumber(), finalShipper.slackId()),
			HubInfo.of(supplierHub.hubId()),
			HubInfo.of(receiverHub.hubId()),
			orderData.deliveryRequest(),
			deliveryRoutes
		);

		//배송 정보 저장
		Delivery saved = deliveryRepository.save(delivery);

		//응답
		return CreateDeliveryResult.from(saved.getId(), "배송 정보가 생성되었습니다.");
	}


	/*
	내부 유틸용 함수
	 */

	/** 주문 서비스에서 주문 정보 조회 */
	private CreateDeliveryOrderInfo getOrderInfo(CreateDeliveryCommand command) {
		return orderClient.getOrderInfo(command.orderId(), command.userId(), command.role());
	}

	/** 업체 서비스에서 허브 정보 조회 */
	private CreateDeliveryCompanyInfo getCompanyHub(UUID companyId, CreateDeliveryCommand command) {
		return companyClient.getCompanyInfo(companyId, command.userId(), command.role());
	}

	/** 허브 서비스에서 허브 간 최적 배송 경로 조회 */
	private List<CreateDeliveryRoutesInfo> getDeliveryRoutes(CreateDeliveryCompanyInfo supplierHub,
		CreateDeliveryCompanyInfo receiverHub,
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
	private Map<ShipperType, Map<UUID, List<CreateDeliveryShipperInfo>>> getAllHubShippers(
		List<CreateDeliveryRoutesInfo> routes, CreateDeliveryCommand command) {

		Map<UUID, List<CreateDeliveryShipperInfo>> deliveryMap = new HashMap<>();
		Map<UUID, List<CreateDeliveryShipperInfo>> transportMap = new HashMap<>();

		for (CreateDeliveryRoutesInfo route : routes) {
			// 출발/도착 허브 모두 조회
			for (UUID hubId : List.of(route.departureHubId(), route.arrivalHubId())) {
				if (deliveryMap.containsKey(hubId))
					continue;

				// 허브별 모든 담당자 조회 및 순서 기준 오름차순 정렬
				List<CreateDeliveryShipperInfo> all = userClient.getShippers(hubId, command.userId(), command.role())
					.stream()
					.sorted(Comparator.comparingInt(CreateDeliveryShipperInfo::order))
					.toList();

				// 배송 / 운송 타입별 분류
				deliveryMap.put(hubId, filterByType(all, ShipperType.HUB_SHIPPER));
				transportMap.put(hubId, filterByType(all, ShipperType.COMPANY_SHIPPER));
			}
		}

		Map<ShipperType, Map<UUID, List<CreateDeliveryShipperInfo>>> result = new HashMap<>();
		result.put(ShipperType.HUB_SHIPPER, deliveryMap);
		result.put(ShipperType.COMPANY_SHIPPER, transportMap);
		return result;
	}

	/** 특정 타입으로 허브 담당자 필터링 */
	private List<CreateDeliveryShipperInfo> filterByType(List<CreateDeliveryShipperInfo> shippers, ShipperType type) {
		return shippers.stream()
			.filter(s -> s.type() == type)
			.toList();
	}

	/**
	 * 배송 경로 엔티티 생성
	 * 허브 담당자를 배정하여 DeliveryRoute 객체로 변환
	 */
	private List<DeliveryRoute> createDeliveryRoutes(List<CreateDeliveryRoutesInfo> routeInfos,
		Map<ShipperType, Map<UUID, List<CreateDeliveryShipperInfo>>> allHubShippers) {

		List<DeliveryRoute> routes = new ArrayList<>();
		for (CreateDeliveryRoutesInfo info : routeInfos) {
			//허브 간 이동 담당자 배정
			CreateDeliveryShipperInfo assigned = assignShipper(info.departureHubId(), allHubShippers,
				ShipperType.HUB_SHIPPER);

			//배송 경로 엔티티 생성
			routes.add(DeliveryRoute.create(
				ShipperInfo.of(assigned.shipperId(), assigned.shipperName(), assigned.shipperPhoneNumber(),
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
	private CreateDeliveryShipperInfo assignShipper(UUID hubId,
		Map<ShipperType, Map<UUID, List<CreateDeliveryShipperInfo>>> allHubShippers,
		ShipperType type) {
		List<CreateDeliveryShipperInfo> shippers = allHubShippers.getOrDefault(type, Map.of())
			.getOrDefault(hubId, List.of());

		return shippers.stream()
			.filter(CreateDeliveryShipperInfo::isShippingAvailable)
			.min(Comparator.comparingInt(CreateDeliveryShipperInfo::order))
			.or(() -> shippers.stream().min(Comparator.comparingInt(CreateDeliveryShipperInfo::order)))
			.orElseThrow(() -> new IllegalStateException("허브 " + hubId + "에 배송 담당자가 존재하지 않습니다."));
	}
}