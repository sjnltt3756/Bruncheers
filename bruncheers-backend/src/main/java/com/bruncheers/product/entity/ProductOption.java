package com.bruncheers.product.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bruncheers.cart.entity.Cart;
import com.bruncheers.order.entity.OrderItem;
import com.bruncheers.product.dto.ProductOptionDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
@DynamicInsert
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductOption {

	@Id
	@SequenceGenerator(name = "product_option_po_no_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_option_po_no_seq")
	private Integer poNo; 	 // 옵션번호
	private Integer poPrice; // 옵션가격
	private String poName; 	 // 옵션명

	
	public static ProductOption toEntity(ProductOptionDto productOptionDto) {
		return ProductOption.builder()
				.poNo(productOptionDto.getPoNo())
				.poPrice(productOptionDto.getPoPrice())
				.poName(productOptionDto.getPoName())
				.product(Product.builder().pNo(productOptionDto.getPNo()).build())
				.build();
		
	}
	
	/*****************관계설정*****************/
	//장바구니
	@OneToMany(mappedBy = "productOption", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@ToString.Exclude
	@Builder.Default
	private List<Cart> cartList = new ArrayList<>();

	//주문아이템
	@OneToMany(mappedBy = "productOption", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@ToString.Exclude
	@Builder.Default
	private List<OrderItem> orderItems = new ArrayList<>();

	//상품
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@ToStringExclude
	@JoinColumn(name = "p_no") 
	private Product product; //상품번호
		
}
