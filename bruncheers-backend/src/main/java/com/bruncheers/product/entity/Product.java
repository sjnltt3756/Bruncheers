package com.bruncheers.product.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.bruncheers.product.dto.ProductDto;
import com.bruncheers.review.entity.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
	@Id
	@SequenceGenerator(name="product_p_no_seq",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_p_no_seq")
	private Integer pNo;    //상품번호
	
	private String pName;   //상품명
	private Integer pPrice; //상품가격
	private String pImage;  //상품이미지
	private String pDetail; //상품설명
	private String pDeimg;  //상품설명이미지
	
	@ColumnDefault("sysdate")
	@CreationTimestamp
	private Date pReg;    //상품등록일
	
	
	public static Product toEntity(ProductDto productDto) {
		return Product.builder()
					.pNo(productDto.getPNo())
					.pName(productDto.getPName())
					.pPrice(productDto.getPPrice())
					.pImage(productDto.getPImage())
					.pDetail(productDto.getPDetail())
					.productCategory(ProductCategory.builder()
					.catNo(productDto.getCatNo())
							.build())
					.pReg(productDto.getPReg())
					.pDeimg(productDto.getPDeimg())
					.build();
	}
	
	
	/*****************관계설정*****************/
	//상품카탈로그
	@ManyToOne // n대1
	@JoinColumn(name = "cat_no")
	@Builder.Default
	@ToStringExclude
	private ProductCategory productCategory = new ProductCategory();
	
	//상품옵션
	// 1대 다 ProductOption이 Product 참조
	@OneToMany(mappedBy = "product",
				cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
				fetch = FetchType.LAZY)
	@Builder.Default
	private List<ProductOption> productOptions=new ArrayList<>();

	//리뷰
	// 1대 다 Review이 Product 참조
	@OneToMany(mappedBy = "product",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	@Builder.Default
	@ToString.Exclude
	private List<Review> reviews=new ArrayList<>();

}