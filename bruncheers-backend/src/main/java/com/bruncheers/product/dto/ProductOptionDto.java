package com.bruncheers.product.dto;

import java.util.Date;

import com.bruncheers.product.entity.Product;
import com.bruncheers.product.entity.ProductOption;

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
public class ProductOptionDto {

	private int poNo; 	     // 옵션번호  (1주,2주,3주  
	private int poPrice;     // 옵션가격
	private String poName;   // 옵션명
	private int pNo; 		 //상품번호 FK
	
	@Override
	public String toString() {
		return "ProductOptionDto [poNo=" + poNo +
								",poPrice=" + poPrice + 
								",poName=" + poName + 
								",pNo=" + pNo + "]";

	}
	
	public static ProductOptionDto toDto(ProductOption productOption) {
		Product product = productOption.getProduct();
		return ProductOptionDto.builder()
								.poNo(productOption.getPoNo())
								.poName(productOption.getPoName())
								.poPrice(productOption.getPoPrice())
								.pNo(product.getPNo())
								.build();
	}
	
}
