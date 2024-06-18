package com.bruncheers.order.dto;

import java.util.List;

import com.bruncheers.order.entity.Order;
import com.bruncheers.order.entity.OrderItem;
import com.bruncheers.product.entity.ProductOption;
import com.bruncheers.review.entity.Review;

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
public class OrderItemDto {

	private int oiNo; // 주문상품번호
	private int oNo; // 주문번호 FK
	private int poNo; // 옵션번호 FK
	private int oiQty; // 수량
	private int reviewNo;

	@Override
	public String toString() {
		return "OrderItem [oiNo=" + oiNo + ",oNo=" + oNo + ",poNo=" + poNo + ",oiQty" + oiQty + "]";
	}

	public static OrderItemDto toDto(OrderItem orderItem) {
		ProductOption productOption = orderItem.getProductOption();
		Order order = orderItem.getOrder();

		return OrderItemDto.builder().oiNo(orderItem.getOiNo()).oiQty(orderItem.getOiQty())
				.poNo(productOption.getPoNo()).reviewNo(orderItem.getReview().getRNo())
				.build();
	}

}