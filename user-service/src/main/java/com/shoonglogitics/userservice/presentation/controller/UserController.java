package com.shoonglogitics.userservice.presentation.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.application.dto.PageResponse;
import com.shoonglogitics.userservice.application.service.UserService;
import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.presentation.dto.ApiResponse;
import com.shoonglogitics.userservice.presentation.dto.request.SignUpRequest;
import com.shoonglogitics.userservice.presentation.dto.request.UpdateSignupStatusRequest;
import com.shoonglogitics.userservice.presentation.dto.response.SignUpResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<SignUpResponse>> createUser(@RequestBody SignUpRequest request) {
		SignUpUserCommand command;

		/*switch (request.getUserType()) {
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
		}*/

		userService.signUp(request.toCommand());

		SignUpResponse response = SignUpResponse.builder()
			.userName(request.getUserName())
			.userType(request.getClass().getSimpleName().replace("SignUpRequest", "").toUpperCase())
			.status("PENDING")
			.build();

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}

	// 회원 목록 조회
	@GetMapping
	public PageResponse<?> getUsers(@RequestParam String role,
		@RequestParam(required = false) UUID hubId,
		@PageableDefault(size = 10) Pageable pageable) {
		return userService.getUsers(role, pageable, hubId);
	}

	// 회원 단건 조회
	@GetMapping("/{role}/{id}")
	public ResponseEntity<?> getUser(@PathVariable String role, @PathVariable Long id) {
		Optional<?> user = userService.getUser(role, id);
		return user.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// 회원가입 승인/거절
	@PutMapping("/{id}/signup-status")
	//@PreAuthorize("hasAnyRole('HUB_MANAGER', 'MASTER')")
	public ResponseEntity<Void> updateSignupStatus(@PathVariable Long id,
		@RequestBody UpdateSignupStatusRequest request) {
		userService.updateSignupStatus(id, request.getStatus());

		User user = userService.findUser(id);

		return ResponseEntity.ok().build();
	}

}
