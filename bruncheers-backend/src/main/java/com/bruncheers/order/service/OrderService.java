package com.bruncheers.order.service;

import java.util.List;

import com.bruncheers.order.dto.OrderCreateDto;
import com.bruncheers.order.dto.OrderDto;
import com.bruncheers.order.dto.OrderWithAllDto;
import com.bruncheers.order.dto.OrderWithOrderItemDto;
import com.bruncheers.user.dto.UserDto;
import com.bruncheers.user.entity.User;

public interface OrderService {

	 /*****************테스트완료*********************/
	//주문1건 삭제
	OrderDto deleteOrder(int oNo)throws Exception;


	//한 회원의 주문 전체 삭제
	void deleteAllOrder(long userNo) throws Exception;
	//OrderDto deleteAllOrder(long userNo) throws Exception;

	//주문 상품에서 바로 구매
	/*Integer ProductOrderSave(OrderCreateDirectDto orderCreateDto)throws Exception;*/
	
	//주문 장바구니에서 전체구매
	Integer createOrder(OrderCreateDto orderCreateDto) throws Exception;
	

	
	 /*****************테스트완료*********************/
	// 주문 한개의 주문리스트 (주문상세정보 + 상품정보)
	OrderWithOrderItemDto findListByOrderNo(int oNo) throws Exception;
	
	// 회원 한명의 주문리스트 (주문상세정보 + 상품정보)
//	List<OrderDto> findListByUserId(long userNo);
	List<OrderWithOrderItemDto> findListByUserId(Long userNo);
	
	// 오더(오더아이템이랑 상품 다) 전체리스트
	List<OrderWithAllDto> finfByAll();
	
}
