package com.bruncheers.cart.repository;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.bruncheers.cart.entity.Cart;
import com.bruncheers.product.entity.ProductOption;
import com.bruncheers.product.repository.ProductOptionRepository;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class CartRepositoryTest {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	ProductOptionRepository productOptionRepository;

	@Autowired
	UserRepository userRepository;

	@DisplayName("회원 한 명의 장바구니에 제품번호 존재여부")
	@Test
	@Disabled
	void testCountByUserEmailAndPoNo() {
		// 존재하는 상품
		Integer countNo1 = cartRepository.countByUserUserNoAndProductOptionPoNo(1L, 10);
		//assertThat(countNo1).isGreaterThan(0);
		// 존재하지 않는 상품
		Integer countNo2 = cartRepository.countByUserUserNoAndProductOptionPoNo(1L, 5);
		//assertThat(countNo2).isEqualTo(0);

		System.out.println(countNo1);
		System.out.println(countNo2);
	}
	
	@DisplayName("상품옵션 번호와 회원 번호로 카트 찾기(동일한 상품이 있는지 확인하기 위함)")
	@Test
	@Disabled
	void findByUserUserNoAndProductOptionPoNo() {
//	Cart foundcart1 = cartRepository.findByUserUserNoAndProductOptionPoNo(3L, 2);
//	assertThat(foundcart1== null);
	Cart foundcart2 = cartRepository.findByUserUserNoAndProductOptionPoNo(3L, 5);
	//assertThat(foundcart2!= null);
	System.out.println(foundcart2);
	}

	@DisplayName("회원 한 명의 장바구니")
	@Test
	@Disabled
	@Transactional
	void testFindByUserUserNo() {
		List<Cart> cartItem = cartRepository.findByUserUserNo(1L);
		//assertTrue(cartItem != null && !cartItem.isEmpty());
		for (Cart cart : cartItem) {
			System.out.println(cart);
		}
	}

	@DisplayName("장바구니 추가")
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void save() {
		ProductOption findProductOption = productOptionRepository.findById(3).get();
		System.out.println(findProductOption);
		User findUser = userRepository.findById(4L).get();

		Cart cart = Cart.builder().cQty(1).productOption(findProductOption).user(findUser).build();
		Cart savedCart = cartRepository.save(cart);

		//assertTrue(savedCart.getUser().getUserNo().equals(4L));
		System.out.println(savedCart);
	}

	/*
	전체 삭제 구현하면 사용할 것
	@DisplayName("회원 한 명의 장바구니에 담긴 상품 전체 삭제")
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void deleteByUserNo() {
		cartRepository.deleteByUserUserNo(6L);
		cartRepository.flush();
		assertTrue(userRepository.findById(6L).get().getCarts().isEmpty());
	}
	*/
	
	
	@DisplayName("장바구니에 담긴 상품 하나 삭제")
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void deleteByCNo() {
		cartRepository.deleteById(3L);
		cartRepository.flush();
		//assertThat(cartRepository.findById(3L).isPresent()==false);
	}
	
	
	@DisplayName("장바구니 체크 된 상품들 삭제")
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void deleteAllByCartcNoIn() {
		List<Long> cNoList = new ArrayList<>();
		cNoList.add(1L);
		cNoList.add(2L);
		cartRepository.deleteAllBycNoIn(cNoList);
		cartRepository.flush();
		//assertTrue(cartRepository.findByUserUserNo(1L).isEmpty());
	}
	
	@DisplayName("장바구니 체크 된 상품들 조회")
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void findAllBycNoIn() {
		List<Long> cNoList = new ArrayList<>();
		cNoList.add(1L);
		cNoList.add(2L);
		List<Cart> cartList = cartRepository.findAllBycNoIn(cNoList);
		for(Cart cart : cartList) {
			System.out.println(cart);
		}
	}
	@DisplayName("장바구니에 담긴 상품 수량 체크")
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void countByUserUserNo() {
		Integer countCartItem = cartRepository.countByUserUserNo(3L);
	//	assertThat(countCartItem == 2);
	}
	
	
	
	
}
