package com.bruncheers.board.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bruncheers.board.entity.Board;
import com.bruncheers.user.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {


	private int bRn;// 페이징 처리
	private int bNo; // 게시판번호
	private String bTitle; // 제목
	private String bContent; // 내용
	private int bReadcount; // 조회수
	private Date bDate; // 등록일자
	private String bCategory; // 문의 종류
	private UserDto user;
	

	// 글의 논리적인 순서번호를 관리하기 위한 필드 (3개)
	private int bGroupno;
	private int bStep;
	private int bDepth;

	@Override
	public String toString() {
		return "Guest [boardNo=" + bNo + ", boardTitle=" + bTitle + ", boardDate=" + bDate + ", boardContent="
				+ bContent + ", boardReadCount=" + bReadcount + ", bGroupno=" + bGroupno + ", bCategory=" + bCategory
				+ ", bStep=" + bStep + ", bDepth=" + bDepth + " ]\n";
	}

	public static BoardDto toDto(Board board) {
	    Map<Integer, String> categoryMap = new HashMap<>();
	    categoryMap.put(Board.CATEGORY_NOTICE, "공지사항");
	    categoryMap.put(Board.CATEGORY_PRODUCT_INQUIRY, "상품문의");
	    categoryMap.put(Board.CATEGORY_SHIPPING_INQUIRY, "배송문의");
	    // 다른 카테고리들을 추가

	    String category = categoryMap.getOrDefault(board.getBCategory(), "기타");

	    return BoardDto.builder()
	            .bRn(board.getBRn())
	            .bNo(board.getBNo())
	            .bTitle(board.getBTitle())
	            .bContent(board.getBContent())
	            .bReadcount(board.getBReadcount())
	            .bDate(board.getBDate())
	            .bGroupno(board.getBGroupno())
	            .bStep(board.getBStep())
	            .bCategory(category)
	            .user(UserDto.toDto(board.getUser()))
	            .bDepth(board.getBDepth())
	            .build();
	}


}