package com.bruncheers.cart.service;

import java.util.List;

import com.bruncheers.cart.dto.CartDto;
import com.bruncheers.cart.dto.CartItemDto;
import com.bruncheers.cart.entity.Cart;

import jakarta.transaction.Transactional;


public interface CartService {
	
	// 회원 한 명의 장바구니에 같은 제품 존재여부 체크 (중복 제품 수량 체크)
	Integer isDuplicateProduct(Long userNo,Integer poNo) throws Exception;
	
	// 회원 한 명의 장바구니 보기 
	List<CartItemDto> getCarts(Long userNo) throws Exception;
	
	// 장바구니 생성
	Integer addCart(CartDto cartDto) throws Exception;
	
	/*
	// 장바구니에 담긴 상품 전체 삭제
	void deleteAllCartItemByUserNo();
	*/
	
	// 장바구니 체크 된 상품들 삭제
	void deleteCheckedCartItems(Iterable<Long> cNoList) throws Exception;
	
	/*
	// 장바구니 체크 된 상품들 조회
	List<CartDto> findCheckedCartItems(Iterable<Long> cNoList) throws Exception;
	*/
	
	// 장바구니에 담긴 상품 하나 삭제
	void deleteCartItem(Long cNo) throws Exception;
	
	// 장바구니에 담긴 상품 수량 체크
	/*Integer countCartItem(Long userNo) throws Exception; */
	
	// 카트에 있는 제품 수량 감소
	// 카트에 있는 제품 수량 증가

}
