package com.shoonglogitics.companyservice.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.companyservice.application.command.productcategory.CreateProductCategoryCommand;
import com.shoonglogitics.companyservice.application.command.productcategory.DeleteProductCategoryCommand;
import com.shoonglogitics.companyservice.application.command.productcategory.GetProductCategoriesCommand;
import com.shoonglogitics.companyservice.application.command.productcategory.UpdateProductCategoryCommand;
import com.shoonglogitics.companyservice.application.dto.productcategory.ProductCategoryResult;
import com.shoonglogitics.companyservice.application.service.CompanyClient;
import com.shoonglogitics.companyservice.application.service.dto.ProductInfo;
import com.shoonglogitics.companyservice.domain.productcategory.entity.ProductCategory;
import com.shoonglogitics.companyservice.domain.productcategory.repository.ProductCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductCategoryService {
	private final ProductCategoryRepository productCategoryRepository;
	private final CompanyClient companyClient;

	@Transactional
	public UUID createProductCategory(CreateProductCategoryCommand command) {
		validateDuplicateName(command.name());

		ProductCategory productCategory = ProductCategory.create(command.name());
		productCategoryRepository.save(productCategory);

		return productCategory.getId();
	}

	@Transactional
	public UUID updateProductCategory(UpdateProductCategoryCommand command) {
		ProductCategory productCategory = getProductCategoryById(command.productCategoryId());
		
		// 이름 변경 시에만 중복 체크
		if (!productCategory.getName().equals(command.name())) {
			validateDuplicateName(command.name());
		}

		productCategory.update(command.name());
		return productCategory.getId();
	}

	@Transactional
	public void deleteProductCategory(DeleteProductCategoryCommand command) {
		List<ProductInfo> productInfos = companyClient.getProductInfos(command.productCategoryId(), command.authUser().getUserId());
		if (productInfos.isEmpty()) {
			throw new IllegalStateException("해당 카테고리에 등록된 상품이 존재하여 삭제할 수 없습니다.");
		}

		ProductCategory productCategory = getProductCategoryById(command.productCategoryId());
		productCategory.delete(command.authUser().getUserId());
	}

	public ProductCategoryResult getProductCategory(UUID productCategoryId) {
		ProductCategory productCategory = getProductCategoryById(productCategoryId);
		return ProductCategoryResult.from(productCategory);
	}

	public Page<ProductCategoryResult> getProductCategories(GetProductCategoriesCommand command) {
		Page<ProductCategory> productCategories = productCategoryRepository.getProductCategories(
			command.name(),
			command.pageRequest().toPageable()
		);
		return productCategories.map(ProductCategoryResult::from);
	}

	private ProductCategory getProductCategoryById(UUID productCategoryId) {
		return productCategoryRepository.findById(productCategoryId)
			.orElseThrow(() -> new NoSuchElementException("상품 카테고리를 찾을 수 없습니다."));
	}

	private void validateDuplicateName(String name) {
		productCategoryRepository.findByName(name).ifPresent(category -> {
			throw new IllegalArgumentException("이미 존재하는 카테고리 이름입니다.");
		});
	}
}
