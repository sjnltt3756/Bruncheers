package com.bruncheers.product.dto;

import java.util.ArrayList;
import java.util.List;

import com.bruncheers.product.entity.Product;
import com.bruncheers.product.entity.ProductCategory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryInProductDto {

	private int catNo; // 카테고리번호
	private String catName; // 카테고리이름 (1일1식,1일2식)
	@Builder.Default
	private List<ProductInProductOptionDto> productDtoList = new ArrayList<>(); // 상품

	public static ProductCategoryInProductDto toDto(ProductCategory productCategory) {

		List<Product> productList = productCategory.getProducts();
		List<ProductInProductOptionDto> productDtoList = new ArrayList<>();

		for (Product product : productList) {
			productDtoList.add(ProductInProductOptionDto.toDto(product));
		}

		return ProductCategoryInProductDto.builder().catName(productCategory.getCatName())
				.catNo(productCategory.getCatNo()).productDtoList(productDtoList).build();
	}

}
