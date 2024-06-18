package com.bruncheers.order.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bruncheers.order.entity.Order;
import com.bruncheers.order.entity.OrderItem;
import com.bruncheers.order.entity.Pay;
import com.bruncheers.product.dto.ProductDto;
import com.bruncheers.product.dto.ProductOptionDto;
import com.bruncheers.user.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderWithOrderItemDto {
	
	private int oNo; 		// 번호
	private int oPrice; 	// 가격
	private String oName;	// 이름
	private int oZip; 		// 우편번호
	private String oAddr; 	// 주소
	private String oPhone;  // 휴대폰번호
	private Date oDate; 	// 주문일자
	private String oReq; 	// 주문 요청사항
	private String oSreq; 	// 배송 요청사항
	private Long userNo; 	// 회원번호 FK
	private Integer oDiscountprice; // 할인가격
	private int paNo;		// 결제번호 FK
	private String paType;  // 결제타입
	@Builder.Default
	private List<OrderItemWithProductOptionDto> orderItemDtoList = new ArrayList<>(); // orderItem
	
	public static OrderWithOrderItemDto toDto(Order order) {
		User user = order.getUser();
		Pay pay = order.getPay();
		List<OrderItem> orderItemList = order.getOrderItems();
		 List<OrderItemWithProductOptionDto> orderItemDtoList = new ArrayList<>();
		for(OrderItem orderItem : orderItemList) {
			orderItemDtoList.add(OrderItemWithProductOptionDto.toDto(orderItem));
		}
		
		
		return OrderWithOrderItemDto.builder()
				.oNo(order.getONo())
				.oPrice(order.getOPrice())
				.oName(order.getOName())
				.oZip(order.getOZip())
				.oAddr(order.getOAddr())
				.oPhone(order.getOPhone())
				.oDate(order.getODate())
				.oReq(order.getOReq())
				.oSreq(order.getOSreq())
				.oDiscountprice(order.getODiscountprice())
				.userNo(user.getUserNo())
				.orderItemDtoList(orderItemDtoList)
				.paNo(pay.getPaNo())
				.paType(pay.getPaType()).build();
				
	}

}


