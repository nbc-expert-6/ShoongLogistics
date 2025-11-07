package com.shoonglogitics.notificationservice.application.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.notificationservice.application.command.CreateAdviceCommand;
import com.shoonglogitics.notificationservice.domain.entity.AIDeliveryAdvice;
import com.shoonglogitics.notificationservice.infrastructure.AIDeliveryAdviceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIDeliveryAdviceService {

	private final AIDeliveryAdviceRepository repository;
	private final ChatClient chatClient;
	private final SlackService slackService;

	@Transactional
	public AIDeliveryAdvice generateDeliveryAdvice(CreateAdviceCommand request) {
		String prompt = String.format(
			"""
				당신은 물류 AI 전문가입니다.
				아래 주문 정보를 바탕으로 배송 납기일을 맞추기 위한 "최종 발송 시한"을 계산해주세요.
				근무시간은 09:00 ~ 18:00이며, 도로 사정은 평균 수준이라고 가정합니다.
				반드시 ISO-8601 형식으로 결과를 포함해주세요 (예시 : 2025-12-10ㅆ09:00:00).
				
				---
				주문 번호 : %s
				상품 및 수량: %s
				요청 사항: %s
				발송지: %s
				도착지: %s
				주문자: %s / %s
				담당자: %s / %s
				---
				""",
			request.getOrderId(),
			request.getProductInfo(),
			request.getDeliveryRequest(),
			request.getOrigin(),
			request.getDestination(),
			request.getCustomerName(),
			request.getCustomerEmail(),
			request.getManagerName(),
			request.getManagerEmail()
		);

		String aiResponse;
		try {
			aiResponse = chatClient.prompt()
				.user(prompt)
				.call()
				.content();
		} catch (Exception e) {
			log.error("AI 응답 생성 실패: {}", e.getMessage(), e);
			slackService.sendMessage(
				"/api/v1/ai-delivery/advice",
				"AI 응답 생성 중 오류 발생: " + e.getMessage()
			);
			throw new RuntimeException("AI 응답 생성 실패", e);
		}

		String finalDeadLineStr = extractIsoDatetime(aiResponse);
		if (finalDeadLineStr == null) {
			slackService.sendMessage(
				"/api/v1/ai-delivery/advice",
				"AI 응답에서 ISO-8601 날짜 형식을 추출하지 못했습니다. 응답 내용: " + aiResponse
			);
			throw new IllegalStateException("AI 응답에서 납기일을 추출할 수 없습니다.");
		}

		LocalDateTime deadline = LocalDateTime.parse(finalDeadLineStr, DateTimeFormatter.ISO_DATE_TIME);

		AIDeliveryAdvice advice = AIDeliveryAdvice.builder()
			.orderId(request.getOrderId())
			.productInfo(request.getProductInfo())
			.deliveryRequest(request.getDeliveryRequest())
			.routeInfo(String.format("%s -> %s", request.getOrigin(), request.getDestination()))
			.workingHours("09:00 ~ 18:00")
			.aiResponse(aiResponse)
			.finalDeadline(deadline)
			.build();

		AIDeliveryAdvice saved = repository.save(advice);

		// ✅ Slack 알림 전송 (성공 시)
		try {
			String message = String.format(
				"""
					🚚 *AI 배송 조언이 생성되었습니다!*
					• 주문번호: %s
					• 상품정보: %s
					• 경로: %s → %s
					• 요청사항: %s
					• 최종 발송 시한: %s
					• 담당자: %s (%s)
					""",
				request.getOrderId(),
				request.getProductInfo(),
				request.getOrigin(),
				request.getDestination(),
				request.getDeliveryRequest(),
				deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				request.getManagerName(),
				request.getManagerEmail()
			);

			slackService.sendMessage("/api/v1/ai-delivery/advice", message);
		} catch (Exception e) {
			log.warn("Slack 알림 전송 실패: {}", e.getMessage());
		}

		return saved;

	}

	private String extractIsoDatetime(String text) {
		// yyyy-MM-ddTHH:mm:ss or yyyy-MM-dd HH:mm:ss or with milliseconds
		Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}[T ]\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return matcher.group(0).replace(" ", "T"); // 공백이면 'T'로 교체
		}
		return null;
	}

}
