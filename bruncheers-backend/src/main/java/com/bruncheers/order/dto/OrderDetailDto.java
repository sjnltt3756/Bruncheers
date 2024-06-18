package com.bruncheers.order.dto;

import java.util.Date;

import com.bruncheers.order.entity.Order;
import com.bruncheers.order.entity.Pay;
import com.bruncheers.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderDetailDto {

	private int oNo; 		// 번호
	private int oPrice; 	// 가격
	private String oName;	// 이름
	private int oZip; 		// 우편번호
	private String oAddr; 	// 주소
	private String oPhone;  // 휴대폰번호
	private Date oDate; 	// 주문일자
	private String oReq; 	// 주문 요청사항
	private String oSreq; 	// 배송 요청사항
	private Integer oDiscountprice; // 할인가격
	private Long userNo; 	// 회원번호 FK
	private int paNo;		// 결제번호 FK
	
	public static OrderDetailDto toDto(Order order) {
		User user = order.getUser();
		Pay pay = order.getPay();
		
		return OrderDetailDto.builder()
				.oNo(order.getONo())
				.oPrice(order.getOPrice())
				.oName(order.getOName())
				.oZip(order.getOZip())
				.oAddr(order.getOAddr())
				.oPhone(order.getOPhone())
				.oDate(order.getODate())
				.oReq(order.getOReq())
				.oReq(order.getOSreq())
				.oDiscountprice(order.getODiscountprice())
				.userNo(user.getUserNo())
				.paNo(pay.getPaNo()).build();

	}


}