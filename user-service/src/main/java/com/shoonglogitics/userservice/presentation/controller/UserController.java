package com.shoonglogitics.userservice.presentation.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.userservice.application.command.LoginUserCommand;
import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.application.command.UpdateUserCommand;
import com.shoonglogitics.userservice.application.dto.LoginUserResponseDto;
import com.shoonglogitics.userservice.application.dto.PageResponse;
import com.shoonglogitics.userservice.application.service.UserService;
import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.presentation.dto.ApiResponse;
import com.shoonglogitics.userservice.presentation.dto.request.LoginRequest;
import com.shoonglogitics.userservice.presentation.dto.request.SignUpRequest;
import com.shoonglogitics.userservice.presentation.dto.request.UpdateSignupStatusRequest;
import com.shoonglogitics.userservice.presentation.dto.request.UpdateUserRequest;
import com.shoonglogitics.userservice.presentation.dto.response.SignUpResponse;
import com.shoonglogitics.userservice.security.JwtProvider;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;
	private final JwtProvider jwtProvider;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<SignUpResponse>> createUser(@RequestBody @Valid SignUpRequest request) {
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
	public ResponseEntity<ApiResponse<Void>> loginUser(@RequestBody LoginRequest dto,
		HttpServletResponse response) {
		LoginUserCommand from = LoginUserCommand.from(dto);
		LoginUserResponseDto responseDto = userService.loginUser(from);

		response.setHeader("Authorization", "Bearer " + responseDto.accessToken());

		return ResponseEntity.ok(ApiResponse.success("로그인이 성공적으로 진행되었습니다."));
	}

	// 회원 목록 조회
	@PreAuthorize("hasRole('MASTER')")
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<?>>> getUsers(@RequestParam String role,
		@RequestParam(required = false) UUID hubId,
		@PageableDefault(size = 10) Pageable pageable) {
		PageResponse<Object> users = userService.getUsers(role, pageable, hubId);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(users));
	}

	// 회원 단건 조회
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> getUser(@RequestHeader("X-User-Role") String role,
		@PathVariable Long id) {
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

	// 회원 수정
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> updateUser(@RequestHeader("X-User-Role") String role,
		@RequestHeader("X-User-Id") Long requesterId,
		@PathVariable Long id,
		@RequestBody UpdateUserRequest request) {

		if (!userService.canUpdateUser(role, requesterId, id)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ApiResponse.error("수정 권한이 없습니다."));
		}

		UpdateUserCommand command = request.toCommand();

		userService.updateUser(id, command);

		return ResponseEntity.ok(
			ApiResponse.success("회원 정보가 성공적으로 수정되었습니다.")
		);
	}

	// 회원 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> deleteUser(@RequestHeader("X-User-Role") String role,
		@RequestHeader("X-User-Id") Long requesterId,
		@PathVariable Long id) {

		if (!userService.canDeleteUser(role, requesterId, id)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ApiResponse.error("삭제 권한이 없습니다."));
		}

		userService.deleteUser(id);
		return ResponseEntity.ok(ApiResponse.success("회원이 성공적으로 삭제 되었습니다."));
	}

}
