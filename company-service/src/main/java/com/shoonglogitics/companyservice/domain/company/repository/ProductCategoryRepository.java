package com.shoonglogitics.companyservice.domain.company.repository;

import com.shoonglogitics.companyservice.domain.productcategory.entity.ProductCategory;

public interface ProductCategoryRepository {
	ProductCategory save(ProductCategory productCategory);
}
