package com.bruncheers.coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bruncheers.FinalProjectTeam2BruncheersApplicationTests;
import com.bruncheers.coupon.entity.Coupon;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.enums.Role;
import com.bruncheers.user.repository.UserRepository;

import jakarta.transaction.Transactional;

class CouponRepositoryTest extends FinalProjectTeam2BruncheersApplicationTests {

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	private UserRepository userRepository;

	@DisplayName("쿠폰 생성")
	@Test
	@Transactional
	@Rollback(false)
	//@Disabled
	void save() {
		User user = User.builder().userName("김ㅇㅇ").userEmail("email").userGender("여").userNickname("뇸")
				.userHp("010-3333-4343").userPw("1111").userBirth("2023-05-20").role(Role.USER).build();

		user = userRepository.save(user);
		assertNotNull(user.getUserNo()); // 저장 후 userNo가 생성되었는지 확인

		Coupon coupon1 = Coupon.builder().coupName("할인쿠폰이에용1").coupDiscount(0.1).coupDesc("굉장해 엄청나").coupStatus("1")
				.user(user).build();
		Coupon coupon2 = Coupon.builder().coupName("할인쿠폰이에용2").coupDiscount(0.2).coupDesc("굉장해 엄청나!!!").coupStatus("1")
				.user(user).build();

		Coupon saveCoup1 = couponRepository.save(coupon1);
		Coupon saveCoup2 = couponRepository.save(coupon2);

		System.out.println(saveCoup1);
		System.out.println(saveCoup2);
	}

	@Disabled
	@DisplayName("회원 번호로 쿠폰 찾기")
	@Test
	@Rollback(false)
	@Transactional
	void findByUserNo() {
		
		List<Coupon> couponList = couponRepository.findByUserUserNo(1L);
		assertThat(couponList.size() == 2);
		System.out.println(couponList);
		
		/*	Optional<User> optionalUser = userRepository.findById(1L);
			assertNotNull(optionalUser);
		
			if (optionalUser.isPresent()) {
				List<Coupon> coupons = optionalUser.get().getCoupons();
				System.out.println(coupons);
			}*/

	}

	@Disabled
	@DisplayName("쿠폰 한 개 삭제하기")
	@Test
	@Rollback(false)
	@Transactional
	void deleteByCoupNo() {
		couponRepository.deleteById(10L);
	}

	@Disabled
	@DisplayName("회원 한 명의 쿠폰 삭제")
	@Test
	@Rollback(false)
	@Transactional
	void deleteByUserUserNo() {
		couponRepository.deleteByUserUserNo(4L);
	}

	@Disabled
	@Test
	@Rollback(false)
	@Transactional
	@DisplayName("쿠폰 사용 가능여부 확인")
	void testCouponAvailability() {

		User user = User.builder().userName("김ㅇㅇ").userEmail("email").userGender("여").userNickname("뇸")
				.userHp("010-3333-4343").userPw("1111").userBirth("2023-05-20").role(Role.USER).build();

		user = userRepository.save(user);
		assertNotNull(user.getUserNo()); // 저장 후 userNo가 생성되었는지 확인

		// 사용 가능한 쿠폰 생성
		Coupon availableCoupon = Coupon.builder().coupName("할인 쿠폰").coupDiscount(0.2).coupDesc("테스트 용 쿠폰").user(user)
				.coupStatus("1") // 사용 가능
				.build();

		// 사용 불가능한 쿠폰 생성
		Coupon unavailableCoupon = Coupon.builder().coupName("만료된 쿠폰").coupDiscount(0.1).coupDesc("테스트 용 만료된 쿠폰")
				.user(user).coupStatus("0") // 사용 불가능
				.build();

		availableCoupon = couponRepository.save(availableCoupon);
		unavailableCoupon = couponRepository.save(unavailableCoupon);

		// 사용 가능한 쿠폰 확인
		assertEquals("1", availableCoupon.getCoupStatus(), "사용 가능한 쿠폰입니다.");

		// 사용 불가능한 쿠폰 확인
		assertEquals("0", unavailableCoupon.getCoupStatus(), "사용 불가능한 쿠폰입니다.");
	}

}
