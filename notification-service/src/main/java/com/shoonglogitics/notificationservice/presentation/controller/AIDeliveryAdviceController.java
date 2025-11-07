package com.shoonglogitics.notificationservice.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.notificationservice.application.command.CreateAdviceCommand;
import com.shoonglogitics.notificationservice.application.service.AIDeliveryAdviceService;
import com.shoonglogitics.notificationservice.domain.entity.AIDeliveryAdvice;
import com.shoonglogitics.notificationservice.presentation.dto.AIDeliveryAdviceRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ai-delivery")
@RequiredArgsConstructor
public class AIDeliveryAdviceController {

	private final AIDeliveryAdviceService aiDeliveryAdviceService;

	@PostMapping("/advice")
	public ResponseEntity<AIDeliveryAdvice> generateAIAdvice(
		@RequestBody AIDeliveryAdviceRequest request
	) {
		CreateAdviceCommand command = request.toCommand();
		AIDeliveryAdvice advice = aiDeliveryAdviceService.generateDeliveryAdvice(command);
		return ResponseEntity.ok(advice);
	}

}
