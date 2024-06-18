package com.bruncheers.coupon.dto;

import com.bruncheers.coupon.entity.Coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CouponDto {

	private Long coupNo;     	 //쿠폰번호 
	private String coupName;	 //쿠폰명
	private Double coupdiscount;//쿠폰할인율
	private String coupDesc;	 //쿠폰설명
	private String coupStatus;	 //쿠폰상태
	private Long userNo;          //회원번호 FK
	
	
	/***************** [ENTITY → DTO ] *****************/
	public static CouponDto toDto(Coupon coupon) {
		return CouponDto.builder()
				.coupNo(coupon.getCoupNo())
				.coupName(coupon.getCoupName())
				.coupdiscount(coupon.getCoupDiscount())
				.coupDesc(coupon.getCoupDesc())
				.coupStatus(coupon.getCoupStatus())
				.userNo(coupon.getUser().getUserNo())
				.build();
	}
}


