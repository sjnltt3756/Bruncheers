package com.bruncheers.product.dto;

import com.bruncheers.product.entity.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDto {

	private int catNo; // 카테고리번호
	private String catName; // 카테고리이름 (1일1식,1일2식)

	public static ProductCategoryDto toDto(ProductCategory productCategory) {
		return ProductCategoryDto.builder()
								.catName(productCategory.getCatName())
								.catNo(productCategory.getCatNo())
								.build();
	}

}
