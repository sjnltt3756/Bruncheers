package com.bruncheers.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruncheers.cart.dto.CartDto;
import com.bruncheers.cart.dto.CartItemDto;
import com.bruncheers.cart.entity.Cart;
import com.bruncheers.cart.repository.CartRepository;
import com.bruncheers.exception.DuplicateCartItemException;
import com.bruncheers.product.entity.ProductOption;
import com.bruncheers.product.repository.ProductOptionRepository;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	ProductOptionRepository productOptionRepository;

	@Autowired
	UserRepository userRepository;

	// 회원 한 명의 장바구니에 같은 제품 존재여부 체크 (중복 제품 수량 체크)
	@Override
	@Transactional
	public Integer isDuplicateProduct(Long userNo, Integer poNo) throws Exception {
		cartRepository.countByUserUserNoAndProductOptionPoNo(userNo, poNo);
		return null;
	}

	// 회원 한 명의 장바구니 보기
	@Override
	@Transactional
	public List<CartItemDto> getCarts(Long userNo) throws Exception {
		List<Cart> cartList = cartRepository.findByUserUserNo(userNo);
		List<CartItemDto> cartItemDtoList = new ArrayList<>();
		for (Cart cart : cartList) {
			cartItemDtoList.add(CartItemDto.toDto(cart));
		}
		return cartItemDtoList;
	}

	// 장바구니 생성
	@Override
	@Transactional
	public Integer addCart(CartDto cartDto) throws Exception {
		Optional<ProductOption> productOptionOptional = productOptionRepository.findById(cartDto.getPoNo());
		if (productOptionOptional.isEmpty()) {
			throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");
		}
		ProductOption productOption = productOptionOptional.get();

		Optional<User> userOptional = userRepository.findById(cartDto.getUserNo());
		if (userOptional.isEmpty()) {
			throw new IllegalArgumentException("해당 회원이 존재하지 않습니다.");
		}
		User user = userOptional.get();
		Cart foundCart = cartRepository.findByUserUserNoAndProductOptionPoNo(cartDto.getUserNo(), cartDto.getPoNo());
		if (foundCart != null) {
			DuplicateCartItemException e = new DuplicateCartItemException("이미 장바구니에 있는 상품입니다.");// 이미 장바구니에 같은 아이템이 존재
			throw e;
		}
		cartRepository.save(Cart.toEntity(cartDto, productOption, user));
		return 1; // 장바구니에 새로운 아이템 추가
	}

	// 장바구니에 담긴 상품 하나 삭제
	@Override
	@Transactional
	public void deleteCartItem(Long cNo) throws Exception {
		Optional<Cart> cartOptional = cartRepository.findById(cNo);
		if (cartOptional.isPresent()) {
			Cart cart = cartOptional.get();
			cartRepository.delete(cart);
		} else {
			throw new Exception("잘못된 접근입니다(CART NOT FOUND)");
		}

	}

	// 장바구니 체크 된 상품들 삭제
	@Override
	@Transactional
	public void deleteCheckedCartItems(Iterable<Long> cNoList) throws Exception {
		cartRepository.deleteAllBycNoIn(cNoList);
	}

	// 장바구니 체크 된 상품들 조회 (주문에서 해야 할 로직! 참고하길 바람)
	/*
	@Override
	@Transactional
	public List<CartDto> findCheckedCartItems(Iterable<Long> cNoList) throws Exception {
		List<Cart> cartList = cartRepository.findAllBycNoIn(cNoList);
		List<CartDto> cartDtoList = new ArrayList<>();
		for (Cart cart : cartList) {
			cartDtoList.add(CartDto.toDto(cart));
		}
		return cartDtoList;
	}
	*/
	
	
	/*
	// 장바구니에 담긴 상품 수량 체크
	@Override
	@Transactional
	public Integer countCartItem(Long userNo) throws Exception {
		return cartRepository.countByUserUserNo(userNo);
	}
	*/
}
