package com.bruncheers.product.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.bruncheers.product.dto.ProductCategoryDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductCategory {
	@Id
	@SequenceGenerator(name="product_category_cat_no_seq",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_category_cat_no_seq")
	private Integer catNo;      //카테고리번호
	
	private String catName; //카테고리이름
	
	public static ProductCategory toEntity(ProductCategoryDto productCategoryDto) {
		return ProductCategory.builder()
							.catNo(productCategoryDto.getCatNo())
							.catName(productCategoryDto.getCatName())
							.build();
	}
	
	/*****************관계설정*****************/
	
	// 1대 다 Product가 ProductCategory 참조
	@OneToMany(mappedBy = "productCategory",cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE },
			fetch = FetchType.LAZY)
	@Builder.Default
	@ToStringExclude
	private List<Product> products=new ArrayList<>();

}