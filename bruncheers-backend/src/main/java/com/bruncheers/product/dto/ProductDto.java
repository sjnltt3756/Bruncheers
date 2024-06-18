package com.bruncheers.product.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bruncheers.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

	private int pNo; // 상품번호
	private String pName; // 상품명
	private int pPrice; // 상품가격
	private String pImage; // 상품이미지
	private String pDetail; // 상품설명
	private String pDeimg; // 상품설명이미지
	private Date pReg; // 상품등록일
	private int catNo; // 카테고리번호 (1일1식,1일2식 FK
	

	@Builder.Default
	private List<MultipartFile> selectedfile = new ArrayList<>();

	@Builder.Default
	private List<String> pImagefileNames = new ArrayList<>();

	@Builder.Default
	private List<String> pDeimagefileNames = new ArrayList<>();

	@Override
	public String toString() {
		return "Product [pNo=" + pNo + ",catNo=" + catNo + ",pName=" + pName + ",pPrice=" + pPrice + ",pImage" + pImage
				+ ",pDetail" + pDetail + ",pDeimg" + pDeimg + ",pReg" + pReg + "]";
	}

	public static ProductDto toDto(Product product) {
		return ProductDto.builder().pNo(product.getPNo()).pName(product.getPName()).pPrice(product.getPPrice())
				.pImage(product.getPImage()).pDetail(product.getPDetail()).pReg(product.getPReg())
				.catNo(product.getProductCategory().getCatNo()).build();
	}

}