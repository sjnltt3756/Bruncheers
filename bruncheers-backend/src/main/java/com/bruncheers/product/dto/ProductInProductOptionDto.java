package com.bruncheers.product.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bruncheers.product.entity.Product;
import com.bruncheers.product.entity.ProductOption;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInProductOptionDto {

	private int pNo; // 상품번호
	private String pName; // 상품명
	private int pPrice; // 상품가격
	private String pImage; // 상품이미지
	private String pDetail; // 상품설명
	private String pDeimg; // 상품설명이미지
	private Date pReg; // 상품등록일
	@Builder.Default
	private List<ProductOptionDto> productOptionList = new ArrayList<>(); // 상품옵션
	

	@Builder.Default 
	private MultipartFile selectedfile = null; // 업로드할 이미지 파일


	public static ProductInProductOptionDto toDto(Product product) {
		
		List<ProductOption> productOptionList = product.getProductOptions();
		List<ProductOptionDto> productOptionDtoList = new ArrayList<>();

		for (ProductOption productOption : productOptionList) {
			productOptionDtoList.add(ProductOptionDto.toDto(productOption));
		}
		
		return ProductInProductOptionDto.builder().pNo(product.getPNo()).pName(product.getPName()).pPrice(product.getPPrice())
				.pImage(product.getPImage()).pDetail(product.getPDetail()).pReg(product.getPReg()).pDeimg(product.getPDeimg())
				.productOptionList(productOptionDtoList).build();
	}

}
