package com.bruncheers.order.dto;

import lombok.Data;

@Data
public class OrderCreateDirectDto {
	private OrderDto orderDto;
	private Integer poNo;
	private Long coupNo;
}
