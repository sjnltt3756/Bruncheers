package com.bruncheers.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bruncheers.order.dto.OrderItemDto;
import com.bruncheers.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
	
	// oNo의 주문목록 조회
		@Query("SELECT oi FROM OrderItem oi WHERE oi.order.oNo = :oNo")
	    List<OrderItem> findAllOrderItemByONo(@Param("oNo") Integer oNo);
	
}
