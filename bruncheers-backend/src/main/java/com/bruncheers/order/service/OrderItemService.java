package com.bruncheers.order.service;

import java.util.List;

import com.bruncheers.order.dto.OrderItemDto;

public interface OrderItemService {
	
	// 주문아이템 추가
	OrderItemDto insertOrderItem(OrderItemDto orderItemDto) throws Exception;
	
	//주문아이템 수정
	OrderItemDto updateOrderItem(OrderItemDto orderItemDto) throws Exception;
	
	// 주문아이템 삭제
	void deleteOrderItem(Integer oiNo) throws Exception;
	
	// 주문아이템 번호로 한개 조회
	OrderItemDto findOrderItem(Integer oiNo) throws Exception;
	
	// 한 주문의 아이템 목록 조회
	List<OrderItemDto> findAllOrderItemByONo(Integer oNo) throws Exception;
	
	//한 주문의 회원 아이템 목록 조회
	//List<OrderItemDto> findAllUserItem (OrderDto orderDto)throws Exception;
}
