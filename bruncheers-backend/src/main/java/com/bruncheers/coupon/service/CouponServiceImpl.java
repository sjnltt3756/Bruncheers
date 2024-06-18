package com.bruncheers.coupon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruncheers.coupon.dto.CouponDto;
import com.bruncheers.coupon.entity.Coupon;
import com.bruncheers.coupon.repository.CouponRepository;
import com.bruncheers.exception.UserNotFoundException;
import com.bruncheers.order.entity.Order;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	UserRepository userRepository;

	// 쿠폰 생성
	@Transactional
	@Override
	public CouponDto createCoupon(CouponDto couponDto) throws Exception {
		/*Optional<User> userOptional = userRepository.findById(couponDto.getUserNo());
		User user = userOptional.orElseThrow(() -> new UserNotFoundException("찾을 수 없는 사용자 입니다"));*/
		User user = userRepository.findById(couponDto.getUserNo()).get();
		Order order = null;
		Coupon coupon = couponRepository.save(Coupon.toEntity(couponDto, user, order));
		CouponDto coupDto = CouponDto.toDto(coupon);
		return coupDto;
	}
	
	
	// 회원의 쿠폰 리스트 확인
		@Override
		public List<CouponDto> getCoupons(Long userNo) throws Exception {
			List<Coupon> couponList = couponRepository.findByUserUserNo(userNo);
			List<CouponDto> couponDtoList = new ArrayList<>();
			for (Coupon coupon : couponList) {
				if(coupon.getCoupStatus().equals("1")){
					couponDtoList.add(CouponDto.toDto(coupon));
				}
			}
			return couponDtoList;
		}

	// 쿠폰 1개 삭제
	@Transactional
	@Override
	public void deleteCoupon(Long coupNo) throws Exception {
		Optional<Coupon> couponOptional = couponRepository.findById(coupNo);
		if (couponOptional.isPresent()) {
			Coupon coupon = couponOptional.get(); // 존재하면 가져와서 삭제
			couponRepository.delete(coupon);
		} else { // 존재하지 않으면 예외처리
			throw new Exception("잘못된 접근입니다(COUPON NOT FOUND)");
		}
	}

	// 회원의 쿠폰 모두 삭제
	@Override
	public void deleteAllCouponsByUserNo(Long userNo) throws Exception {
		couponRepository.deleteByUserUserNo(userNo);
	}


	// 쿠폰 적용하기
	@Override
	public Double applyCoupon(Long coupNo, Double oPrice) throws Exception {
		Optional<Coupon> couponOptional = couponRepository.findById(coupNo);
		if (couponOptional.isPresent()) {
			Coupon coupon = couponOptional.get();
			// 쿠폰이 유효한지 확인
			if (coupon.getCoupStatus().equals("1")) {
				double discountedPrice = oPrice * (1 - coupon.getCoupDiscount() / 100.0);
				return discountedPrice;
			} else {
				throw new Exception("쿠폰이 유효하지 않습니다.");
			}
		} else {
			throw new Exception("쿠폰을 찾을 수 없습니다.");
		}
	}

}
