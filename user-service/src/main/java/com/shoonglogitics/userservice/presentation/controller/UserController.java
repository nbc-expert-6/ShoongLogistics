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

import com.shoonglogitics.userservice.application.command.LoginUserCommand;
import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.application.dto.LoginUserResponseDto;
import com.shoonglogitics.userservice.application.dto.PageResponse;
import com.shoonglogitics.userservice.application.service.UserService;
import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.presentation.dto.ApiResponse;
import com.shoonglogitics.userservice.presentation.dto.request.LoginRequestDto;
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

		userService.signUp(request.toCommand());

		SignUpResponse response = SignUpResponse.builder()
			.userName(request.getUserName())
			.userType(request.getClass().getSimpleName().replace("SignUpRequest", "").toUpperCase())
			.status("PENDING")
			.build();

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginUserResponseDto>> loginUser(@RequestBody LoginRequestDto dto) {
		LoginUserCommand from = LoginUserCommand.from(dto);
		LoginUserResponseDto responseDto = userService.loginUser(from);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDto));
	}

	// 회원 목록 조회
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<?>>> getUsers(@RequestParam String role,
		@RequestParam(required = false) UUID hubId,
		@PageableDefault(size = 10) Pageable pageable) {
		PageResponse<Object> users = userService.getUsers(role, pageable, hubId);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(users));
	}

	// 회원 단건 조회
	@GetMapping("/{role}/{id}")
	public ResponseEntity<ApiResponse<?>> getUser(@PathVariable String role, @PathVariable Long id) {
		Optional<?> user = userService.getUser(role, id);

		if (user.isPresent()) {
			return ResponseEntity.ok(ApiResponse.success(user.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 회원가입 승인/거절
	@PutMapping("/{id}/signup-status")
	//@PreAuthorize("hasAnyRole('HUB_MANAGER', 'MASTER')")
	public ResponseEntity<ApiResponse<Void>> updateSignupStatus(@PathVariable Long id,
		@RequestBody UpdateSignupStatusRequest request) {
		userService.updateSignupStatus(id, request.getStatus());

		User user = userService.findUser(id);

		return ResponseEntity.ok().body(ApiResponse.success("회원가입 요청 상태 변경이 완료되었습니다."));
	}

}
