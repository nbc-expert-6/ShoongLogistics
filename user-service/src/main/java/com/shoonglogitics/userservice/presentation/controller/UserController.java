package com.shoonglogitics.userservice.presentation.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.userservice.application.command.CompanyManagerSignUpCommand;
import com.shoonglogitics.userservice.application.command.HubManagerSignUpCommand;
import com.shoonglogitics.userservice.application.command.MasterSignUpCommand;
import com.shoonglogitics.userservice.application.command.ShipperSignUpCommand;
import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.application.dto.PageResponse;
import com.shoonglogitics.userservice.application.service.UserService;
import com.shoonglogitics.userservice.domain.entity.ShipperType;
import com.shoonglogitics.userservice.domain.vo.CompanyId;
import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.HubId;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;
import com.shoonglogitics.userservice.presentation.dto.ApiResponse;
import com.shoonglogitics.userservice.presentation.dto.request.SignUpRequest;
import com.shoonglogitics.userservice.presentation.dto.response.SignUpResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<SignUpResponse>> createUser(@RequestBody SignUpRequest request) {
		SignUpUserCommand command;

		switch (request.getUserType()) {
			case "MASTER":
				command = MasterSignUpCommand.builder()
					.userName(request.getUserName())
					.password(request.getPassword())
					.email(new Email(request.getEmail()))
					.name(new Name(request.getName()))
					.slackId(new SlackId(request.getSlackId()))
					.phoneNumber(new PhoneNumber(request.getPhoneNumber()))
					.build();
				break;

			case "HUB_MANAGER":
				command = HubManagerSignUpCommand.builder()
					.userName(request.getUserName())
					.password(request.getPassword())
					.email(new Email(request.getEmail()))
					.name(new Name(request.getName()))
					.slackId(new SlackId(request.getSlackId()))
					.phoneNumber(new PhoneNumber(request.getPhoneNumber()))
					.hubId(new HubId(request.getHubId()))
					.build();
				break;

			case "COMPANY_MANAGER":
				command = CompanyManagerSignUpCommand.builder()
					.userName(request.getUserName())
					.password(request.getPassword())
					.email(new Email(request.getEmail()))
					.name(new Name(request.getName()))
					.slackId(new SlackId(request.getSlackId()))
					.phoneNumber(new PhoneNumber(request.getPhoneNumber()))
					.companyId(new CompanyId(request.getCompanyId()))
					.build();
				break;

			case "SHIPPER":
				command = ShipperSignUpCommand.builder()
					.userName(request.getUserName())
					.password(request.getPassword())
					.email(new Email(request.getEmail()))
					.name(new Name(request.getName()))
					.slackId(new SlackId(request.getSlackId()))
					.phoneNumber(new PhoneNumber(request.getPhoneNumber()))
					.hubId(request.getHubId() != null ? new HubId(request.getHubId()) : null)
					.shipperType(ShipperType.valueOf(request.getShipperType()))
					.order(request.getOrder())
					.isShippingAvailable(request.getIsShippingAvailable())
					.build();
				break;

			default:
				throw new IllegalArgumentException("지원하지 않는 회원 유형입니다.");
		}

		userService.signUp(command);

		SignUpResponse response = SignUpResponse.builder()
			.userName(request.getUserName())
			.userType(request.getUserType())
			.status("PENDING")
			.build();

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}

	@GetMapping
	public PageResponse<?> getUsers(@RequestParam String role,
		@RequestParam(required = false) UUID hubId,
		@PageableDefault(size = 10) Pageable pageable) {
		return userService.getUsers(role, pageable, hubId);
	}

}
