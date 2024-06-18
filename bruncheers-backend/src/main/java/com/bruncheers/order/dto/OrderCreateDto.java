package com.bruncheers.order.dto;

import lombok.Data;

@Data
public class OrderCreateDto {
	private int oPrice; 		// 가격
	private String oName; 		// 이름
	private int oZip;			// 우편번호
	private String oAddr; 		// 주소
	private String oPhone; 		// 휴대폰번호
	private String oReq; 		// 요청사항
	private String oSreq; 		// 요청사항
	private Long userNo; 		// 회원번호 FK
	private Integer oDiscountprice; // 할인가격
	private Iterable<Long> cNoList;	// 카트번호 리스트
	private Iterable<Integer> poNoList; // 상품옵션 리스트
	private Long coupNo; 		// 쿠폰번호
	private String payType;		// 결제수단
}
