package com.bruncheers.product.service;

import java.util.List;

import com.bruncheers.product.dto.ProductCategoryDto;
import com.bruncheers.product.dto.ProductCategoryInProductDto;
import com.bruncheers.product.entity.ProductCategory;

public interface ProductCatService {
	
	// 상품카테고리 등록
	ProductCategoryDto createProductCategory(ProductCategoryDto productCategoryDto) throws Exception;
	
	// 상품카테고리 수정
	ProductCategoryDto updateProductCategory(ProductCategoryDto productCategoryDto) throws Exception;
	
	// 상품카테고리 삭제
	void deleteProductCategory(Integer catNo) throws Exception;
	
	// 상품 카테고리 조회
	List<ProductCategoryDto> getProductCategoryList() throws Exception;
	
	// 상품 카테고리 상품과 함께 조회
	List<ProductCategoryInProductDto> getProductCategoryWithProduct() throws Exception;
}