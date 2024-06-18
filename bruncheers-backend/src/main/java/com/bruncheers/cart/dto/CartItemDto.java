package com.bruncheers.cart.dto;

import com.bruncheers.cart.entity.Cart;
import com.bruncheers.product.dto.ProductOptionDto;
import com.bruncheers.product.dto.ProductOptionDtoInProduct;

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
public class CartItemDto {
	
	private Long cNo;		//장바구니번호
	private Integer cQty;	//상품수량
	private ProductOptionDtoInProduct productOption;	//옵션번호 		FK
	private Long userNo;	//유저번호 		FK
	
	/***************** [ENTITY → DTO] *****************/
	public static CartItemDto toDto(Cart cart) {
		return CartItemDto.builder().cNo(cart.getCNo()).cQty(cart.getCQty()).productOption(ProductOptionDtoInProduct.toDto(cart.getProductOption())).userNo(cart.getUser().getUserNo()).build();
	}
	
}
