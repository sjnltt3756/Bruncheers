
package com.bruncheers.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bruncheers.order.entity.Order;
import com.bruncheers.user.entity.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	// 주문 전체 삭제
	@Modifying
	@Transactional
	@Query("DELETE FROM Order o WHERE o.user.userNo = :userNo")
	void deleteUserNo(@Param("userNo") long userNo);

	// 한명의 회원 주문전체리스트(최신순으로)
	@Query("SELECT o FROM Order o WHERE o.user.userNo = :userNo")
	List<Order> findByUserNo(@Param("userNo") long userNo);
	
	List<Order> findByUserUserNo(Long userNo);

	void deleteByUserUserNo(Long userNo);
	
	// 기간별 주문 목록 조회
	
	/*
	//회원 한명의 주문리스트
	@Query("SELECT o FROM Order o WHERE o.user.userNo = :userNo")
	List<OrderDto> findListByUserId(@Param("userNo")Integer userNo);
	*/
	
	//한명의 회원 주문리스트 찾기
	@Query("SELECT o FROM Order o WHERE o.user.userNo = :userNo")
	Order findUserNo(@Param("userNo") long userNo);
	
	/*
	 * // 한 주문의 사용자 정보 조회
	 * 
	 * @Query("SELECT o.user FROM Order o WHERE o.oNo = :orderNo") User
	 * findUserByOrderNo(@Param("orderNo") Integer orderNo);
	 */
}
