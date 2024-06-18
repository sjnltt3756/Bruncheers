package com.bruncheers.cart.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bruncheers.cart.dto.CartDto;
import com.bruncheers.product.entity.ProductOption;
import com.bruncheers.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@DynamicUpdate
@DynamicInsert
@Entity
public class Cart {
	@Id
	@SequenceGenerator(name = "cart_c_no_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_c_no_seq")
	private Long cNo; // 장바구니번호
	private Integer cQty; // 상품수량

	/* 관계 설정 */
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name="po_no")
	@ToString.Exclude
	private ProductOption productOption;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	@ToString.Exclude
	private User user;

	/***************** [DTO → ENTITY] *****************/
	public static Cart toEntity(CartDto cartDto, ProductOption productOption, User user) {
			return Cart.builder().cNo(cartDto.getCNo()).cQty(cartDto.getCQty()).productOption(productOption).user(user).build();
	}
	
}