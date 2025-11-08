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

	//배송 생성
	//주문 시 자동호출
	@Transactional
	public CreateDeliveryResult createDelivery(CreateDeliveryCommand command) {
		// 주문 서비스에 주문 정보 조회
		CreateDeliveryOrderInfo orderData = getOrderInfo(command);
		log.info("orderData={}", orderData);

		// 업체 서비스에 수령업체 허브 조회
		CreateDeliveryCompanyInfo receiverCompanyInfo = getCompanyInfo(orderData.receiverCompanyId(), command);
		log.info("receiver={}", receiverCompanyInfo);

		// 업체 서비스에 공급업체 허브 조회
		CreateDeliveryCompanyInfo supplierCompanyInfo = getCompanyInfo(orderData.supplierCompanyId(), command);
		log.info("supplier={}", supplierCompanyInfo);

		// 허브 서비스에 혀브간 운송경로 요청
		// 출발허브id, 도착허브id 넘겨주고 허브 운송경로 받기
		List<CreateDeliveryRoutesInfo> deliveryRoutesInfo = getDeliveryRoutesInfo(receiverCompanyInfo,
			supplierCompanyInfo,
			command);
		log.info("deliveryRoutes={}", deliveryRoutesInfo);

		// 회원 컨텍스트에 요청 여러번 보내서 허브별 담당자 현황 가져오기
		// 가져올 때 배송 가능한 담당자만 가져오고 순번대로 오름차순
		// 타입: Map<허브Id : List<허브별 타입 담당자>
		Map<ShipperType, Map<UUID, List<CreateDeliveryShipperInfo>>> allHubShippers = getAllHubShippers(
			deliveryRoutesInfo, command);

		List<DeliveryRoute> deliveryRoutes = new ArrayList<>();
		// 만들어진 경로 순회하며 배송 경로 엔티티 생성
		for (CreateDeliveryRoutesInfo routesInfo : deliveryRoutesInfo) {
			CreateDeliveryShipperInfo assignedShipper = assignShipper(routesInfo.departureHubId(), allHubShippers,
				ShipperType.HUB_SHIPPER);

			DeliveryRoute route = DeliveryRoute.create(
				ShipperInfo.of(assignedShipper.shipperId(), assignedShipper.shipperName(),
					assignedShipper.shipperPhoneNumber(), assignedShipper.slackId()),
				HubInfo.of(routesInfo.departureHubId()),
				HubInfo.of(routesInfo.arrivalHubId()),
				routesInfo.sequence(),
				DeliveryEstimate.of(routesInfo.estimateDistance(), routesInfo.estimatedDuration())
			);

			deliveryRoutes.add(route);
		}

		// 배송 생성, 만들어진 배송경로 목록 함께 저장
		// 최종 배송담당자 배정
		CreateDeliveryShipperInfo destinationShipper = assignShipper(receiverCompanyInfo.hubId(), allHubShippers,
			ShipperType.COMPANY_SHIPPER);
		Delivery delivery = Delivery.create(
			command.orderId(),
			Address.of(
				orderData.address(),
				orderData.addressDetail(),
				orderData.zipCode(),
				GeoLocation.of(
					orderData.latitude(),
					orderData.longitude()
				)
			),
			ShipperInfo.of(
				destinationShipper.shipperId(),
				destinationShipper.shipperName(),
				destinationShipper.shipperPhoneNumber(),
				destinationShipper.slackId()
			),
			HubInfo.of(supplierCompanyInfo.hubId()),
			HubInfo.of(receiverCompanyInfo.hubId()),
			orderData.deliveryRequest(),
			deliveryRoutes
		);

		// 배송정보 저장
		Delivery savedDelivery = deliveryRepository.save(delivery);

		// 응답
		log.info("savedDeliveryId={}", savedDelivery.getId());
		return CreateDeliveryResult.from(savedDelivery.getId(), "배송 정보가 생성되었습니다.");
	}

	/*
	내부용 유틸 함수
	 */

	//배송 생성에 필요한 필수 주문 정보 조회
	private CreateDeliveryOrderInfo getOrderInfo(CreateDeliveryCommand command) {
		return orderClient.getOrderInfo(command.orderId(), command.userId(), command.role());
	}

	//업체가 속한 hubId 조회
	private CreateDeliveryCompanyInfo getCompanyInfo(UUID uuid, CreateDeliveryCommand command) {
		return companyClient.getCompanyInfo(uuid, command.userId(), command.role());
	}

	//허브에 최적 경로 요청
	private List<CreateDeliveryRoutesInfo> getDeliveryRoutesInfo(CreateDeliveryCompanyInfo receiverCompanyInfo,
		CreateDeliveryCompanyInfo supplierCompanyInfo, CreateDeliveryCommand command) {
		return hubClient.getDeliveryRoutesInfo(
			supplierCompanyInfo.hubId(), receiverCompanyInfo.hubId(), command.userId(), command.role()
		);
	}

	//회원 서비스에 요청하여 허브별 배송 담당자 전부조회
	//타입별로 반환 (순서 기준 오름차순)
	private Map<ShipperType, Map<UUID, List<CreateDeliveryShipperInfo>>> getAllHubShippers(
		List<CreateDeliveryRoutesInfo> deliveryRoutes, CreateDeliveryCommand command
	) {
		Map<UUID, List<CreateDeliveryShipperInfo>> hubDeliveryShippers = new HashMap<>();
		Map<UUID, List<CreateDeliveryShipperInfo>> hubTransportShippers = new HashMap<>();

		for (CreateDeliveryRoutesInfo route : deliveryRoutes) {
			for (UUID hubId : List.of(route.departureHubId(), route.arrivalHubId())) {
				if (hubDeliveryShippers.containsKey(hubId))
					continue;

				List<CreateDeliveryShipperInfo> allShippers = userClient.getShippers(hubId, command.userId(),
						command.role())
					.stream()
					.sorted(Comparator.comparingInt(CreateDeliveryShipperInfo::order))
					.toList();

				List<CreateDeliveryShipperInfo> deliveryShippers = allShippers.stream()
					.filter(s -> s.type() == ShipperType.HUB_SHIPPER)
					.toList();

				List<CreateDeliveryShipperInfo> transportShippers = allShippers.stream()
					.filter(s -> s.type() == ShipperType.COMPANY_SHIPPER)
					.toList();

				hubDeliveryShippers.put(hubId, deliveryShippers);
				hubTransportShippers.put(hubId, transportShippers);
			}
		}

		Map<ShipperType, Map<UUID, List<CreateDeliveryShipperInfo>>> result = new HashMap<>();
		result.put(ShipperType.HUB_SHIPPER, hubDeliveryShippers);
		result.put(ShipperType.COMPANY_SHIPPER, hubTransportShippers);

		return result;
	}

	//전체 담당자에서 해당 허브의 배송 타입 담당자를 할당 (우선순위대로)
	private CreateDeliveryShipperInfo assignShipper(
		UUID hubId,
		Map<ShipperType, Map<UUID, List<CreateDeliveryShipperInfo>>> allHubShippers,
		ShipperType shipperType
	) {
		List<CreateDeliveryShipperInfo> shippers =
			allHubShippers.getOrDefault(shipperType, Map.of())
				.getOrDefault(hubId, List.of());

		// 1순위 - 배송 가능한 담당자 중 order가 가장 빠른 사람
		// 2순위 - 없으면 전체 중 order가 가장 빠른 사람
		return shippers.stream()
			.filter(CreateDeliveryShipperInfo::isShippingAvailable)
			.min(Comparator.comparingInt(CreateDeliveryShipperInfo::order))
			.or(() -> shippers.stream()
				.min(Comparator.comparingInt(CreateDeliveryShipperInfo::order))
			)
			.orElseThrow(() -> new IllegalStateException(
				"허브 " + hubId + "에 배송 담당자가 존재하지 않습니다."
			));
	}
}