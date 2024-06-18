package com.bruncheers.order.entity;

import java.util.ArrayList;
import java.util.List;

import com.bruncheers.order.dto.OrderItemDto;
import com.bruncheers.product.entity.ProductOption;
import com.bruncheers.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
	@Id
	@SequenceGenerator(name = "order_item_oi_no_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_oi_no_seq")
	private Integer oiNo; // 주문상품번호
	private Integer oiQty; // 수량

	/***************** 관계설정 *****************/
	// 상품옵션
	// 1대 1
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "po_no")
	@ToString.Exclude
	private ProductOption productOption;

	// 주문
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "o_no")
	@ToString.Exclude
	private Order order;

	// 리뷰
	// 1 대 1
	@OneToOne(mappedBy = "orderItem", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@ToString.Exclude
	private Review review;

	public static OrderItem toEntity(OrderItemDto orderItemDto, ProductOption productOption, Order order) {

		return OrderItem.builder().oiNo(orderItemDto.getOiNo()).oiQty(orderItemDto.getOiQty())

				.productOption(productOption).order(order)
				.review(Review.builder().rNo(orderItemDto.getReviewNo()).build()).build();

	}

	@Override
	public String toString() {
		return "OrderItemDto [oiNo = " + oiNo + "," + "oiQty= " + oiQty + "," + "oNo= " + order.getONo() + ","
				+ "poNo= " + productOption.getPoNo() + "]\n";
	}

}