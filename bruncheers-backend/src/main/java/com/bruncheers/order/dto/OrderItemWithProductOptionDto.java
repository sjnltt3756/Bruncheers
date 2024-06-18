package com.bruncheers.order.dto;

import java.util.ArrayList;
import java.util.List;

import com.bruncheers.order.entity.Order;
import com.bruncheers.order.entity.OrderItem;
import com.bruncheers.product.dto.ProductOptionDto;
import com.bruncheers.product.dto.ProductOptionDtoInProduct;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemWithProductOptionDto {

	private int oiNo; // 주문상품번호
	private int oNo; // 주문번호 FK
	private int oiQty; // 수량
	private int reviewNo;
	private ProductOptionDtoInProduct productOption; // 상품옵션




	public static OrderItemWithProductOptionDto toDto(OrderItem orderItem) {
		Order order = orderItem.getOrder();

		return OrderItemWithProductOptionDto.builder().oiNo(orderItem.getOiNo()).oiQty(orderItem.getOiQty())
				.oNo(order.getONo()).productOption(ProductOptionDtoInProduct.toDto(orderItem.getProductOption()))
				.build();
	}

}