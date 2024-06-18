package com.bruncheers.order.repository;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bruncheers.FinalProjectTeam2BruncheersApplicationTests;
import com.bruncheers.order.entity.Order;
import com.bruncheers.order.entity.Pay;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.repository.UserRepository;

import jakarta.transaction.Transactional;

class OrderRepositoryTest extends FinalProjectTeam2BruncheersApplicationTests {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderItemRepository orderItemRepository;
	@Autowired
	PayRepository payRepository;
	@Autowired
	UserRepository userRepository;

	
	@Rollback(false)
	@Test
	@Transactional
	void createOrder() throws Exception {
		Optional<User> optionalUser = userRepository.findById(2L);
	    if (!optionalUser.isPresent()) {
	        throw new Exception("User not found");
	    }
	    User user = optionalUser.get(); // 실제 User 객체 추출
	    

	    Optional<Pay> optionalPay = payRepository.findById(2);
	    if (!optionalPay.isPresent()) {
	    	throw new Exception("Pay not found");
	    }
	    
	    Pay pay = optionalPay.get(); // 실제 User 객체 추출
	    
	    Order order = Order.builder()
	    		.oNo(0)
	    		.oPrice(15000)
	    		.oPhone("01012345678")
	    		.oName("수빈")
	    		.oZip(12312)
	    		.oAddr("강남구")
	    		.oDate(new Date())
	    		.oReq("잘부탁드려요")
	    		.build();
	    
	   order.setUser(user);
	   order.setPay(pay);
	   orderRepository.save(order);
	   
	    
	}

	
	
	 

	/*
	@Test
	void testFindByUserNO() throws Exception {
		List<Order> orderListAllUserId = orderRepository.findByUserNo(1L);
		System.out.println(orderListAllUserId);
	}

	
	@Test
	void testFindAllOrderByUserId()throws Exception {
		List<Order>orderListAllUserId = orderRepository.findByUserNo(1);
	}
	*/

}