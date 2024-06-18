/*
package com.bruncheers.order.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bruncheers.order.entity.Order;
import com.bruncheers.order.repository.OrderRepository;

@Repository
public class OrderDaoImpl implements OrderDao{

	@Autowired
	OrderRepository orderRepository;

	@Override //주문 전체 삭제
	public int deleteByUserId(String uId) throws Exception {
		int deletUserId = orderRepository.deleteByUserId(uId);
		return deletUserId;
	}

	@Override  //주문 한건 삭제
	public int deleByOrderNo(int oNo) throws Exception {
		orderRepository.deleteById(oNo);
		return 0;
	}

	@Override  //주문 생성
	public Order insertOrder(Order order) throws Exception {
		Order orderInsert = orderRepository.save(order);
		return orderInsert;
	}

	@Override  //주문 한개의 주문리스트  
	public Order findListByOrderNo(int oNo) throws Exception {
		Order order = orderRepository.findById(oNo).get();
		return order;
	}

	@Override  //주문 전체 리스트 보여주기
	public List<Order> findAllOrderByUserId(String uId) throws Exception {
		return orderRepository.findAllOrderByUserId(uId);
	}

	@Override  //회원 한명의 주문리스트
	public List<Order> findListByUserId(String uId) throws Exception {
		return orderRepository.findListByUserId(uId);
	}
	
	
	
}
*/ 
