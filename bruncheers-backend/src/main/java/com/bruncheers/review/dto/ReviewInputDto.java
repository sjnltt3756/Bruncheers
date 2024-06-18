package com.bruncheers.review.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bruncheers.review.entity.Review;

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
public class ReviewInputDto {

	private int rNo; // 리뷰번호
	private int rStar; // 별점
	// private MultipartFile rImage; // 리뷰사진
	// private String uploadRImage; // 리뷰사진
	private String rContent;// 리뷰내용
	private Date rReg; // 리뷰등록일
	private int product; // 상품번호 FK
	private int orderItem; // 상품번호 FK
	private Long user; // 회원번호 KF

	@Builder.Default
	private List<MultipartFile> selectedfile = new ArrayList<>();

	@Builder.Default
	private List<String> rImagefileNames = new ArrayList<>();

	@Override
	public String toString() {
		return "review [rNo=" + rNo + ",pNo=" + product + ",userNo=" + user + ",oiNo=" + orderItem + ",rStar=" + rStar
				+ ",rImage=" + selectedfile +

				",rContent=" + rContent + ",rReg=" + rReg + "]";
	}

	public static ReviewInputDto toInputDto(Review review) {
		return ReviewInputDto.builder().rNo(review.getRNo()).rStar(review.getRStar()).rContent(review.getRContent())
				.rReg(review.getRReg()).user(review.getUser().getUserNo()).build();

	}

}