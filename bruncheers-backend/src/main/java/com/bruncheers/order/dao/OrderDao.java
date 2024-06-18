package com.bruncheers.order.dao;

import java.util.List;

import com.bruncheers.order.entity.Order;

public interface OrderDao {

	//주문 전체 삭제
	int deleteByUserId(String uId)throws Exception;
	
	//주문 한건 삭제
	int deleByOrderNo(int oNo)throws Exception;
	
	//주문 생성
	Order insertOrder(Order order)throws Exception;
	
	//주문 한개의 주문리스트
	Order findListByOrderNo(int oNo)throws Exception;
	
	//주문 전체 리스트 보여주기
	List<Order> findAllOrderByUserId(String uId) throws Exception;
	
	//회원 한명의 주문리스트
	List<Order> findListByUserId(String uId) throws Exception;
	
	
}
