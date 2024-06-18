package com.bruncheers.order.entity;


import java.util.ArrayList;
import java.util.List;

import com.bruncheers.order.dto.PayDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Pay {
	@Id
	@SequenceGenerator(name="pay_pa_no_seq",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pay_pa_no_seq")
	private Integer paNo;	//결제번호
	private String paType;	//결제수단
	
	public static Pay toEntity(PayDto payDto) {
		return Pay.builder()
				.paNo(payDto.getPaNo())
				.paType(payDto.getPaType())
				.build();
	}
	
	

	/*****************관계설정*****************/
	//주문
	// 1대 1
	@OneToMany(mappedBy = "pay",
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.LAZY)
	@Builder.Default
	private List<Order> orders=new ArrayList<>();

	
}
