package com.bruncheers.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruncheers.exception.OrderNotFoundException;
import com.bruncheers.order.dto.OrderDto;
import com.bruncheers.order.dto.OrderItemDto;
import com.bruncheers.order.entity.Order;
import com.bruncheers.order.entity.OrderItem;
import com.bruncheers.order.repository.OrderItemRepository;
import com.bruncheers.order.repository.OrderRepository;
import com.bruncheers.product.entity.ProductOption;
import com.bruncheers.product.repository.ProductOptionRepository;

@Service("OrderItemServiceImpl")
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	OrderItemRepository orderItemRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ProductOptionRepository productOptionRepository;
	
	// 주문아이템 추가
	@Override
	public OrderItemDto insertOrderItem(OrderItemDto orderItemDto) throws Exception {
		
		Integer oNo = orderItemDto.getONo();
		Integer poNo = orderItemDto.getPoNo();
		
		Order order = orderRepository.findById(oNo).get();
		ProductOption productOption = productOptionRepository.findById(poNo).get();
		
		OrderItem orderItem = orderItemRepository.save(OrderItem.toEntity(orderItemDto, productOption, order));
		OrderItemDto oiDto = OrderItemDto.toDto(orderItem);
		
		return oiDto;
	}
	// 주문아이템 수정
	@Override
	public OrderItemDto updateOrderItem(OrderItemDto orderItemDto) throws Exception {
		return null;
	}



	// 주문아이템 삭제
	@Override
	public void deleteOrderItem(Integer oiNo) throws Exception {
		orderItemRepository.deleteById(oiNo);
	}

	// 주문아이템 한개 조회
	@Override
	public OrderItemDto findOrderItem(Integer oiNo) throws Exception {
		Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(oiNo);
		if (optionalOrderItem.isPresent()) {
			OrderItem orderItem = optionalOrderItem.get();
			return OrderItemDto.toDto(orderItem);
		} else {
			throw new OrderNotFoundException("주문아이템: "+ oiNo + "을 찾을 수 없습니다.");
		}
	}
	
	
	
	// 한 주문의 주문아이템 목록 조회
	@Override
	public List<OrderItemDto> findAllOrderItemByONo(Integer oNo) throws Exception {
		Optional<Order> optionalOrder = orderRepository.findById(oNo);
		
		if(optionalOrder.isPresent()) {
			Order order = optionalOrder.get();
			
			List<OrderItem> orderOrderItems = order.getOrderItems();
			List<OrderItemDto> orderOrderItemDtos = new ArrayList<>();
			
			for (OrderItem orderItem : orderOrderItems) {
				orderOrderItemDtos.add(OrderItemDto.toDto(orderItem));
			}
			return orderOrderItemDtos;
		} else {
			throw new OrderNotFoundException("주문번호: "+ oNo +"을 찾을 수 없습니다.");
		}
		
	}
	/*//한 주문의 회원 아이템 목록 조회
	public List<OrderItemDto> findAllUserItem(OrderDto orderDto) throws Exception {
	    List<Order> orderList = orderRepository.findByUserNo(orderDto.getUserNo());
	    List<OrderItemDto> orderItemDtoList = new ArrayList<>();
	    for (Order order : orderList) {
	        List<OrderItem> orderItems = order.getOrderItems();
	        for (OrderItem orderItem : orderItems) {
	            OrderItemDto orderItemDto = OrderItemDto.toDto(orderItem);
	            orderItemDtoList.add(orderItemDto);
	        }
	    }
	    return orderItemDtoList;
	}*/

}