package com.shoonglogitics.companyservice.presentation.stock;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.companyservice.application.StockService;
import com.shoonglogitics.companyservice.application.command.stock.CreateStockCommand;
import com.shoonglogitics.companyservice.application.command.stock.DecreaseStockCommand;
import com.shoonglogitics.companyservice.application.command.stock.DeleteStockCommand;
import com.shoonglogitics.companyservice.application.command.stock.IncreaseStockCommand;
import com.shoonglogitics.companyservice.application.dto.stock.StockHistoryResult;
import com.shoonglogitics.companyservice.application.dto.stock.StockResult;
import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;
import com.shoonglogitics.companyservice.presentation.common.dto.ApiResponse;
import com.shoonglogitics.companyservice.presentation.common.dto.PageRequest;
import com.shoonglogitics.companyservice.presentation.common.dto.PageResponse;
import com.shoonglogitics.companyservice.presentation.stock.dto.CreateStockRequest;
import com.shoonglogitics.companyservice.presentation.stock.dto.CreateStockResponse;
import com.shoonglogitics.companyservice.presentation.stock.dto.DecreaseStockRequest;
import com.shoonglogitics.companyservice.presentation.stock.dto.FindStockResponse;
import com.shoonglogitics.companyservice.presentation.stock.dto.IncreaseStockRequest;
import com.shoonglogitics.companyservice.presentation.stock.dto.StockHistoryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
@Validated
public class StockController {
	private final StockService stockService;

	@PostMapping
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<CreateStockResponse>> createStock(
		@Valid @RequestBody CreateStockRequest request,
		@AuthenticationPrincipal AuthUser authUser) {
		CreateStockCommand command = CreateStockCommand.builder()
			.authUser(authUser)
			.productId(request.productId())
			.initialAmount(request.amount())
			.build();

		UUID stockId = stockService.createStock(command);
		CreateStockResponse response = new CreateStockResponse(
			stockId,
			"재고가 정상적으로 생성되었습니다."
		);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}

	@PostMapping("/{stockId}/increase")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<String>> increaseStock(
		@PathVariable UUID stockId,
		@Valid @RequestBody IncreaseStockRequest request,
		@AuthenticationPrincipal AuthUser authUser) {
		IncreaseStockCommand command = new IncreaseStockCommand(
			authUser,
			stockId,
			request.quantity(),
			request.reason()
		);

		stockService.increaseStock(command);

		return ResponseEntity.ok().body(ApiResponse.success("재고가 정상적으로 증가되었습니다."));
	}

	@PostMapping("/{stockId}/decrease")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<String>> decreaseStock(
		@PathVariable UUID stockId,
		@Valid @RequestBody DecreaseStockRequest request,
		@AuthenticationPrincipal AuthUser authUser) {
		DecreaseStockCommand command = new DecreaseStockCommand(
			authUser,
			stockId,
			request.quantity(),
			request.reason()
		);

		stockService.decreaseStock(command);

		return ResponseEntity.ok().body(ApiResponse.success("재고가 정상적으로 감소되었습니다."));
	}

	@DeleteMapping("/{stockId}")
	@PreAuthorize("hasRole('MASTER')")
	public ResponseEntity<ApiResponse<String>> deleteStock(
		@PathVariable UUID stockId,
		@AuthenticationPrincipal AuthUser authUser) {
		DeleteStockCommand command = new DeleteStockCommand(authUser, stockId);

		stockService.deleteStock(command);

		return ResponseEntity.ok().body(ApiResponse.success("재고가 정상적으로 삭제되었습니다."));
	}

	@GetMapping("/{stockId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<FindStockResponse>> getStock(
		@PathVariable UUID stockId) {

		StockResult result = stockService.getStock(stockId);
		FindStockResponse response = FindStockResponse.from(result);

		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/product/{productId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<FindStockResponse>> getStockByProductId(
		@PathVariable UUID productId) {

		StockResult result = stockService.getStockByProductId(productId);
		FindStockResponse response = FindStockResponse.from(result);

		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/{stockId}/histories")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<PageResponse<StockHistoryResponse>>> getStockHistories(
		@PathVariable UUID stockId,
		@ModelAttribute PageRequest pageRequest) {

		Page<StockHistoryResult> results = stockService.getStockHistories(stockId, pageRequest);
		Page<StockHistoryResponse> responses = results.map(StockHistoryResponse::from);

		return ResponseEntity.ok(ApiResponse.success(PageResponse.of(responses)));
	}
}
