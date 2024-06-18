package com.bruncheers.coupon.service;

import java.util.List;

import com.bruncheers.coupon.dto.CouponDto;

public interface CouponService {

	// 쿠폰 생성
	CouponDto createCoupon(CouponDto couponDto) throws Exception;

	// 쿠폰 삭제
	void deleteCoupon(Long coupNo) throws Exception;

	// 쿠폰 전체 삭제
	void deleteAllCouponsByUserNo(Long userNo) throws Exception;

	// 회원 한 명의 쿠폰 전체 불러오기
	List<CouponDto> getCoupons(Long userNo) throws Exception;

	// 쿠폰 할인 적용하기
	Double applyCoupon(Long coupNo, Double oPrice) throws Exception;

}