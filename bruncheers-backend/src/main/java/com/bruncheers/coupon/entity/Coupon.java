package com.bruncheers.coupon.entity;

import com.bruncheers.coupon.dto.CouponDto;
import com.bruncheers.order.entity.Order;
import com.bruncheers.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
	@Id
	@SequenceGenerator(name = "coupon_coup_no_seq", sequenceName = "coupon_coup_no_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coupon_coup_no_seq")
	private Long coupNo;     	 //쿠폰번호 
	private String coupName;	 //쿠폰명
	private Double coupDiscount;//쿠폰할인율
	private String coupDesc;	 //쿠폰설명
	@Builder.Default
	private String coupStatus="1";	 //쿠폰상태 0이면 사용불가 1이면 사용가능
	
	/* private Integer minOrderAmount; // 주문 금액 조건 coupStatus와 함께 사용 나중에 구현해보면 좋을 듯 */	
	
	/*****************관계설정*****************/
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	@ToString.Exclude
	private User user;
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name = "o_no")
	@ToString.Exclude
	@Builder.Default()
	private Order order=null;
	/***************** [DTO → ENTITY] *****************/
	public static Coupon toEntity(CouponDto couponDto,User user,Order order) {
		return Coupon.builder()
				.coupNo(couponDto.getCoupNo())
				.coupName(couponDto.getCoupName())
				.coupDiscount(couponDto.getCoupdiscount())
				.coupDesc(couponDto.getCoupDesc())
				.coupStatus(couponDto.getCoupStatus() != null ? couponDto.getCoupStatus() : "1")
				.user(user)
				.order(order)
				.build();
	}
	
	
}
