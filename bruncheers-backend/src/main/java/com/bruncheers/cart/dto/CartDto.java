package com.bruncheers.cart.dto;

import com.bruncheers.cart.entity.Cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
	
	private Long cNo;		//장바구니번호
	private Integer cQty;	//상품수량
	private Integer poNo;	//옵션번호 		FK
	private Long userNo;	//유저번호 		FK
	
	/***************** [ENTITY → DTO] *****************/
	public static CartDto toDto(Cart cart) {
		return CartDto.builder().cNo(cart.getCNo()).cQty(cart.getCQty()).poNo(cart.getProductOption().getPoNo()).userNo(cart.getUser().getUserNo()).build();
	}
	
}
