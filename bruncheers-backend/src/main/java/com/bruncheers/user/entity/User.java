package com.bruncheers.user.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.bruncheers.board.entity.Board;
import com.bruncheers.cart.entity.Cart;
import com.bruncheers.coupon.entity.Coupon;
import com.bruncheers.order.entity.Order;
import com.bruncheers.review.entity.Review;
import com.bruncheers.ship.entity.Ship;
import com.bruncheers.user.dto.FindPasswordDto;
import com.bruncheers.user.dto.FindUserEmailDto;
import com.bruncheers.user.dto.PasswordVerificationRequestDto;
import com.bruncheers.user.dto.UserDto;
import com.bruncheers.user.dto.UserLoginDto;
import com.bruncheers.user.dto.UserUpdateDto;
import com.bruncheers.user.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "user")
@Table(name = "userinfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

	@Id
	@SequenceGenerator(name = "userinfo_user_no_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userinfo_user_no_seq")
	private Long userNo; // 회원번호

	@Column(nullable = false, unique = true)
	private String userEmail; // 유저 이메일

	
	private String userPw; // 유저 비밀번호
	@Column(unique = true)
	private String userNickname; // 유저 닉네임
	private String userName; // 유저 이름
	private String userGender; // 유저 성별
	private String userBirth; // 유저 생년월일

	@Column(length = 20, nullable = false)
	private String userHp; // 유저 휴대폰번호
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE")
	private Role role;

	private String token;
	
	public void updatePassword(String newPassword) {
        this.userPw = newPassword;
    }
	
	// 쿠폰
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@ToString.Exclude
	@Builder.Default
	private List<Coupon> coupons = new ArrayList<Coupon>();

	// 카트
	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	@ToString.Exclude
	@Builder.Default
	private List<Cart> carts = new ArrayList<Cart>();

	// 주문
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@ToString.Exclude
	@Builder.Default
	private List<Order> orders = new ArrayList<Order>();

	// 배송
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@ToString.Exclude
	@Builder.Default
	private List<Ship> ships = new ArrayList<Ship>();

	// 리뷰
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@ToString.Exclude
	@Builder.Default
	private List<Review> reviews = new ArrayList<Review>();

	// 게시판
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@ToString.Exclude
	@Builder.Default
	private List<Board> boards = new ArrayList<Board>();

	public static User toEntity(UserDto dto) {
		return User.builder().userNo(dto.getUserNo()).userEmail(dto.getUserEmail()).userPw(dto.getUserPw())
				.userNickname(dto.getUserNickname()).userName(dto.getUserName()).userGender(dto.getUserGender())
				.userBirth(dto.getUserBirth()).userHp(dto.getUserHp()).role(dto.getRole()).token(dto.getToken()).build();
	}

	public static User toLoginEntity(UserLoginDto dto) {
		return User.builder().userEmail(dto.getUserEmail()).userPw(dto.getPassword()).build();
	}

	public static User toUpdateEntity(UserUpdateDto dto) {
		return User.builder().userEmail(dto.getUserEmail()).userPw(dto.getUserNewPw2()).userPw(dto.getUserPw()).userPw(dto.getUserNewPw()).userNickname(dto.getUserNickname()).userName(dto.getUserName())
				.userGender(dto.getUserGender()).userBirth(dto.getUserBirth()).userHp(dto.getUserHp()).build();
	}
	public static User toPasswordEntity(PasswordVerificationRequestDto dto) {
		return User.builder().userPw(dto.getPassword()).build();
	}

	
	/*
	 * public static User toFindUserEmailEntity(FindUserEmailDto dto) { return
	 * User.builder().userBirth(dto.getUserBirth()).userHp(dto.getUserHp()).build();
	 * }
	 * 
	 * public static User toFindPasswordEntity(FindPasswordDto dto) { return
	 * User.builder().userEmail(dto.getUserEmail()).userHp(dto.getUserHp()).build();
	 * }
	 */

}