package com.bruncheers.order.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.bruncheers.order.dto.OrderCreateDto;
import com.bruncheers.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
@Builder
@Entity
public class Order {
	@Id
	@SequenceGenerator(name = "orders_o_no_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_o_no_seq")
	private Integer oNo; 	// 주문번호

	private Integer oPrice; // 가격
	private String oName;   // 이름
	private Integer oZip;   // 우편번호
	private String oAddr;   // 주소
	private String oPhone;  // 휴대폰번호

	@ColumnDefault("sysdate")
	@CreationTimestamp
	private Date oDate; 	// 주문일자
	private String oReq; 	// 주문 요청사항
	private String oSreq; 	// 배송 요청사항
	
	@Builder.Default()
	private Integer oDiscountprice = 0; // 할인가격
	

	public static Order toEntity(OrderCreateDto orderCreateDto,User user, Pay pay) {
		return Order.builder()
				.oPrice(orderCreateDto.getOPrice())
				.oName(orderCreateDto.getOName())
				.oZip(orderCreateDto.getOZip())
				.oAddr(orderCreateDto.getOAddr())
				.oPhone(orderCreateDto.getOPhone())
				.oReq(orderCreateDto.getOReq())
				.oSreq(orderCreateDto.getOSreq())
				.oDiscountprice(orderCreateDto.getODiscountprice())
				.user(user)
				.pay(pay)
				.build();
	}

	/*****************관계설정*****************/

	// 유저
	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	@Builder.Default // (클래스 생성 후)
	private User user = new User(); // FK

	// 결제
	// 다 대 1
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "pa_no")
	@Builder.Default
	private Pay pay = new Pay();

	// 주문아이템
	// 1대 다 OrderItem이 order 참조
	@OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@Builder.Default
	@ToString.Exclude
	private List<OrderItem> orderItems = new ArrayList<>();
	

}