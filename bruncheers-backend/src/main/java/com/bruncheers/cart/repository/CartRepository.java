package com.bruncheers.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruncheers.cart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	// 상품옵션 번호와 회원 번호로 카트 찾기(동일한 상품이 있는지 확인하기 위함)
	Cart findByUserUserNoAndProductOptionPoNo(Long userNo, Integer poNo);
	
	// 회원 한 명의 장바구니에 제품번호 존재여부
	Integer countByUserUserNoAndProductOptionPoNo(Long userNo, Integer poNo);
	
	// 회원 한 명의 장바구니
	List<Cart> findByUserUserNo(Long userNo);
	
	/*
	필요없어보임 체크박스로 지울 것이기 떄문! (전체삭제 구현하면 풀 것)
	//회원 한 명의 장바구니에 담긴 상품 전체 삭제
	void deleteByUserUserNo(Long userNo);
	*/
	
	// 장바구니 체크 된 상품들 삭제
	void deleteAllBycNoIn(Iterable<Long> cNoList);
	
	// 장바구니 체크 된 상품들 조회
	List<Cart> findAllBycNoIn(Iterable<Long> cNoList);
	
	// 장바구니에 담긴 상품 수량 체크
	Integer countByUserUserNo(Long userNo);

}
