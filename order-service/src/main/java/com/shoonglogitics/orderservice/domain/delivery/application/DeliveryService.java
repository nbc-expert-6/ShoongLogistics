package com.shoonglogitics.orderservice.domain.delivery.application;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shoonglogitics.orderservice.domain.delivery.application.command.CreateDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryCompanyInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryOrderInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryRoutesInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.service.CompanyClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.HubClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.OrderClient;
import com.shoonglogitics.orderservice.domain.delivery.domain.repository.DeliveryRepository;
import com.shoonglogitics.orderservice.domain.delivery.domain.service.DeliveryDomainService;

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

	//배송 생성
	//주문 시 자동호출
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
		List<CreateDeliveryRoutesInfo> deliveryRoutes = getDeliveryRouteInfo(receiverCompanyInfo, supplierCompanyInfo,
			command);
		log.info("deliveryRoutes={}", deliveryRoutes);

		// 배송 담당자 배정
		// 회원 컨텍스트에 요청 여러번 보내서 허브별 담당자 현황 가져오기

		// 가져온 담당자를 순번에 맞게 경로마다 배정

		// 필요한 vo 생성

		// 배송 경로 생성

		// 배송 생성

		// 배송정보 저장

		// 응답

		return null;
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
	private List<CreateDeliveryRoutesInfo> getDeliveryRouteInfo(CreateDeliveryCompanyInfo receiverCompanyInfo,
		CreateDeliveryCompanyInfo supplierCompanyInfo, CreateDeliveryCommand command) {
		return hubClient.getDeliveryRoutesInfo(
			supplierCompanyInfo.hubId(), receiverCompanyInfo.hubId(), command.userId(), command.role()
		);
	}
}
