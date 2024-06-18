package com.bruncheers.coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruncheers.coupon.entity.Coupon;
@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
	// 회원 한 명의 쿠폰 삭제
	void deleteByUserUserNo(Long userNo);
	
	// 회원 번호로 회원이 보유한 모든 쿠폰 보기
	List<Coupon> findByUserUserNo(Long userNo);
	
}
   