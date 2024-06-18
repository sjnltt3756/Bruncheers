package com.bruncheers.order.dto;

import com.bruncheers.order.entity.Pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayDto {
	
	private int paNo;	//결제번호
	private String paType;	//결제수단
	@Override
	public String toString() {
		return "Pay [paNo="+paNo+
					",paType="+paType+"]";
	}
	
	public static PayDto toDto(Pay pay) {
		return PayDto.builder()
				.paNo(pay.getPaNo())
				.paType(pay.getPaType())
				.build();
	}
	
	
}
