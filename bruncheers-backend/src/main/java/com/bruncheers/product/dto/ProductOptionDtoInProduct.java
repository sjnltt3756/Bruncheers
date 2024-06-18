package com.bruncheers.product.dto;

import com.bruncheers.product.entity.ProductOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionDtoInProduct {

	private Integer poNo; 	     	// 옵션번호  (1주,2주,3주  
	private Integer poPrice;     	// 옵션가격
	private String poName;   		// 옵션명
	private ProductDto product ; //상품번호 FK

	public static ProductOptionDtoInProduct toDto(ProductOption productOption) {
		return ProductOptionDtoInProduct.builder()
								.poNo(productOption.getPoNo())
								.poName(productOption.getPoName())
								.poPrice(productOption.getPoPrice())
								.product(ProductDto.toDto(productOption.getProduct()))
								.build();
	}
	
}
