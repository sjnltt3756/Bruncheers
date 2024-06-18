package com.bruncheers.order.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bruncheers.FinalProjectTeam2BruncheersApplicationTests;
import com.bruncheers.order.entity.Order;
import com.bruncheers.order.entity.OrderItem;
import com.bruncheers.product.entity.ProductOption;

import jakarta.transaction.Transactional;

class OrderItemRepositoryTest extends FinalProjectTeam2BruncheersApplicationTests{
	
	@Autowired
	OrderItemRepository orderItemRepository;
	@Autowired
	OrderRepository orderRepository;
	
	// 오더아이템 추가
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void savaOrderItem() {
		
		ProductOption productOption = ProductOption.builder().poNo(25).build();
		Order order = Order.builder().oNo(2).build(); 
		
		OrderItem orderItem = OrderItem.builder().oiQty(1).build();
		
		orderItem.setProductOption(productOption);
		orderItem.setOrder(order);
		
		orderItemRepository.save(orderItem);
		
	}
	
	// 오더아이템 삭제
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void deleteOrderItem() {
		orderItemRepository.deleteById(4);
		orderItemRepository.flush();
	}
	
	// 오더아이템 한개 조회
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void findOrderItem() {
		System.out.println(orderItemRepository.findById(3));
	}
	
	// 한 주문의 오더아이템 목록 조회
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void findAllOrderItemByONo() {
		Optional<Order> optionalOrder = orderRepository.findById(2);
		assertNotNull(optionalOrder);
		
		if(optionalOrder.isPresent()) {
			List<OrderItem> orderItems = optionalOrder.get().getOrderItems();
			System.out.println(orderItems);
		}
		
	}
	
}
