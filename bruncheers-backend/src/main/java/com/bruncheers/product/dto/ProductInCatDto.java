package com.bruncheers.product.dto;

import java.util.Date;

import com.bruncheers.product.entity.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInCatDto {

	private int pNo; // 상품번호
	private String pName; // 상품명
	private int pPrice; // 상품가격
	private String pImage; // 상품이미지
	private String pDetail; // 상품설명
	private String pDeimg; // 상품설명이미지
	private Date pReg; // 상품등록일
	private ProductCategoryDto productCategory; // 상품 카테고리

	public static ProductInCatDto toDto(Product product) {
		return ProductInCatDto.builder().pNo(product.getPNo()).pName(product.getPName()).pPrice(product.getPPrice())
				.pImage(product.getPImage()).pDetail(product.getPDetail()).pDeimg(product.getPDeimg())
				.pReg(product.getPReg()).productCategory(ProductCategoryDto.toDto(product.getProductCategory()))
				.build();
	}

}