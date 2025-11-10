package com.shoonglogitics.companyservice.presentation.productcategory;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.companyservice.application.command.productcategory.CreateProductCategoryCommand;
import com.shoonglogitics.companyservice.application.command.productcategory.DeleteProductCategoryCommand;
import com.shoonglogitics.companyservice.application.command.productcategory.GetProductCategoriesCommand;
import com.shoonglogitics.companyservice.application.command.productcategory.UpdateProductCategoryCommand;
import com.shoonglogitics.companyservice.application.dto.productcategory.ProductCategoryResult;
import com.shoonglogitics.companyservice.application.service.productcategory.ProductCategoryService;
import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;
import com.shoonglogitics.companyservice.presentation.company.common.dto.ApiResponse;
import com.shoonglogitics.companyservice.presentation.company.common.dto.PageRequest;
import com.shoonglogitics.companyservice.presentation.company.common.dto.PageResponse;
import com.shoonglogitics.companyservice.presentation.productcategory.dto.CreateProductCategoryRequest;
import com.shoonglogitics.companyservice.presentation.productcategory.dto.CreateProductCategoryResponse;
import com.shoonglogitics.companyservice.presentation.productcategory.dto.FindProductCategoryResponse;
import com.shoonglogitics.companyservice.presentation.productcategory.dto.SearchProductCategoryResponse;
import com.shoonglogitics.companyservice.presentation.productcategory.dto.UpdateProductCategoryRequest;
import com.shoonglogitics.companyservice.presentation.productcategory.dto.UpdateProductCategoryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/product-categories")
@RequiredArgsConstructor
@Validated
public class ProductCategoryController {
	private final ProductCategoryService productCategoryService;

	@PostMapping
	@PreAuthorize("hasRole('MASTER')")
	public ResponseEntity<ApiResponse<CreateProductCategoryResponse>> createProductCategory(
		@Valid @RequestBody CreateProductCategoryRequest request,
		@AuthenticationPrincipal AuthUser authUser) {
		CreateProductCategoryCommand command = CreateProductCategoryCommand.builder()
			.authUser(authUser)
			.name(request.name())
			.build();

		UUID productCategoryId = productCategoryService.createProductCategory(command);
		CreateProductCategoryResponse response = new CreateProductCategoryResponse(
			productCategoryId,
			"상품 카테고리가 정상적으로 생성되었습니다."
		);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}

	@PutMapping("/{productCategoryId}")
	@PreAuthorize("hasRole('MASTER')")
	public ResponseEntity<ApiResponse<UpdateProductCategoryResponse>> updateProductCategory(
		@PathVariable UUID productCategoryId,
		@Valid @RequestBody UpdateProductCategoryRequest request,
		@AuthenticationPrincipal AuthUser authUser) {
		UpdateProductCategoryCommand command = new UpdateProductCategoryCommand(
			authUser,
			productCategoryId,
			request.name()
		);

		UUID id = productCategoryService.updateProductCategory(command);
		UpdateProductCategoryResponse response = new UpdateProductCategoryResponse(
			id,
			"상품 카테고리가 정상적으로 수정되었습니다."
		);

		return ResponseEntity.ok().body(ApiResponse.success(response));
	}

	@DeleteMapping("/{productCategoryId}")
	@PreAuthorize("hasRole('MASTER')")
	public ResponseEntity<ApiResponse<String>> deleteProductCategory(
		@PathVariable UUID productCategoryId,
		@AuthenticationPrincipal AuthUser authUser) {
		DeleteProductCategoryCommand command = new DeleteProductCategoryCommand(
			authUser,
			productCategoryId
		);

		productCategoryService.deleteProductCategory(command);

		String responseMessage = "상품 카테고리가 정상적으로 삭제되었습니다.";

		return ResponseEntity.ok().body(ApiResponse.success(responseMessage));
	}

	@GetMapping("/{productCategoryId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<FindProductCategoryResponse>> getProductCategory(
		@PathVariable UUID productCategoryId) {

		ProductCategoryResult result = productCategoryService.getProductCategory(productCategoryId);
		FindProductCategoryResponse response = FindProductCategoryResponse.from(result);

		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<PageResponse<SearchProductCategoryResponse>>> getProductCategories(
		@RequestParam(required = false) String name,
		@ModelAttribute PageRequest pageRequest) {

		GetProductCategoriesCommand command = GetProductCategoriesCommand.builder()
			.name(name)
			.pageRequest(pageRequest)
			.build();

		Page<ProductCategoryResult> results = productCategoryService.getProductCategories(command);
		Page<SearchProductCategoryResponse> responses = results.map(SearchProductCategoryResponse::from);

		return ResponseEntity.ok(ApiResponse.success(PageResponse.of(responses)));
	}
}
