package com.bruncheers.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruncheers.product.dto.ProductCategoryDto;
import com.bruncheers.product.dto.ProductCategoryInProductDto;
import com.bruncheers.product.entity.ProductCategory;
import com.bruncheers.product.repository.ProductCategoryRepository;

@Service
public class ProductCatServiceImpl implements ProductCatService {

	@Autowired
	ProductCategoryRepository productCategoryRepository;

	// 상품 카테고리 등록
	@Override
	public ProductCategoryDto createProductCategory(ProductCategoryDto productCategoryDto) throws Exception {
		ProductCategory productCategoryEntity = productCategoryRepository
				.save(ProductCategory.toEntity(productCategoryDto));
		return ProductCategoryDto.toDto(productCategoryEntity);
	}

	// 상품 카테고리 수정
	@Override
	public ProductCategoryDto updateProductCategory(ProductCategoryDto productCategoryDto) throws Exception {
		ProductCategory productCategoryEntity = productCategoryRepository
				.save(ProductCategory.toEntity(productCategoryDto));
		return ProductCategoryDto.toDto(productCategoryEntity);
	}

	// 상품 카테고리 삭제
	@Override
	public void deleteProductCategory(Integer catNo) throws Exception {
		productCategoryRepository.deleteById(catNo);
	}

	// 상품 카테고리 조회
	@Override
	public List<ProductCategoryDto> getProductCategoryList() throws Exception {
		List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
		List<ProductCategoryDto> productCatDtoList = new ArrayList<>();
		for (ProductCategory productCategory : productCategoryList) {
			productCatDtoList.add(ProductCategoryDto.toDto(productCategory));
		}

		return productCatDtoList;
	}

	// 상품 카테고리 상품과 함께 조회
	@Override
	public List<ProductCategoryInProductDto> getProductCategoryWithProduct() throws Exception {
		List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
		List<ProductCategoryInProductDto> productCatDtoList = new ArrayList<>();
		for (ProductCategory productCategory : productCategoryList) {
			productCatDtoList.add(ProductCategoryInProductDto.toDto(productCategory));
		}
		return productCatDtoList;
	}

}