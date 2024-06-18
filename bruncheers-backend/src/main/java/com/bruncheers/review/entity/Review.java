package com.bruncheers.review.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

import com.bruncheers.order.entity.OrderItem;
import com.bruncheers.product.entity.Product;
import com.bruncheers.review.dto.ReviewInputDto;
import com.bruncheers.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
@DynamicInsert
@Entity
public class Review {

	@Id
	@SequenceGenerator(name = "review_r_no_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_r_no_SEQ")
	private Integer rNo; // 리뷰번호
	private Integer rStar; // 별점
	private String rImage; // 리뷰사진
	private String rContent;// 리뷰내용
	@ColumnDefault("sysdate")
	@CreationTimestamp
	private Date rReg; // 리뷰등록일

	/* 참조관계 */
	// 상품
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "p_no")
	private Product product; // 상품번호

	// 주문아이템
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "oi_no")
	private OrderItem orderItem; // 주문아이템번호

	// 회원
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	private User user; // 회원아이디

	@Override
	public String toString() {
		return "review [rNo=" + rNo + ",pNo=" + product + ",uId=" + user + ",oiNo=" + orderItem + ",rStar=" + rStar
				+ ",rImage=" + rImage + ",rContent=" + rContent + ",rReg=" + rReg + "]";
	}

	/*
	 * public void addImage(List<String> imageList) {
	 * 
	 * this.rImage = imageList.get(0);
	 * 
	 * }
	 */

	public static Review toEntity(ReviewInputDto reviewDto) {
		return Review.builder().rNo(reviewDto.getRNo()).rStar(reviewDto.getRStar()).rContent(reviewDto.getRContent())
				.rReg(reviewDto.getRReg()).user(User.builder().userNo(reviewDto.getUser()).build())
				.build();
	}

}