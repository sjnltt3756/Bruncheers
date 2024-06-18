package com.bruncheers.board.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bruncheers.board.dto.BoardDto;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
@DynamicInsert
@Entity
public class Board {

   // 카테고리 상수 정의
   public static final int CATEGORY_NOTICE = 0;
   public static final int CATEGORY_PRODUCT_INQUIRY = 1;
   public static final int CATEGORY_SHIPPING_INQUIRY = 2;
   public static final int CATEGORY_OTHER_INQUIRY = 3;

   @Id
   @SequenceGenerator(name = "board_b_no_seq", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_b_no_seq")
   private Integer bNo; // 게시판번호
   private String bTitle; // 제목
   private String bContent; // 내용
   private Integer bReadcount; // 조회수
   private Integer bCategory; // 문의 종류
   private Integer bRn;// 페이징 처리


   @Column(updatable = false)
   @ColumnDefault("sysdate")
   @CreationTimestamp
   private Date bDate; // 등록일자


	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	@ToStringExclude
	@Builder.Default
	private User user = new User();

	/*
	 * @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "a_id")
	 * 
	 * @Builder.Default //(클래스 생성 후) private Admin admin = new Admin(); // FK
	 */
	// 글의 논리적인 순서번호를 관리하기 위한 필드 (3개)
	private Integer bGroupno;
	private Integer bStep;
	private Integer bDepth;

   @Override
   public String toString() {
      return "Guest [boardNo=" + bNo + ", boardTitle=" + bTitle + ", boardDate=" + bDate + ", boardContent="
            + bContent + ", boardReadCount=" + bReadcount + ", userId=" + user + ", bGroupno=" + bGroupno
            + ", bStep=" + bStep + ", bDepth=" + bDepth + " ]\n";
   }

	public static Board toEntity(BoardDto boardDto) {
		 Map<String, Integer> categoryMap = new HashMap<>();
		    categoryMap.put("공지사항", Board.CATEGORY_NOTICE);
		    categoryMap.put("상품문의", Board.CATEGORY_PRODUCT_INQUIRY);
		    categoryMap.put("배송문의", Board.CATEGORY_SHIPPING_INQUIRY);
		    categoryMap.put("기타문의", Board.CATEGORY_OTHER_INQUIRY);
		    // 다른 카테고리들을 추가

		    // `boardDto`의 카테고리를 Integer로 변환
		    int category = categoryMap.getOrDefault(boardDto.getBCategory(), Board.CATEGORY_OTHER_INQUIRY);

		    return Board.builder()
		            .bRn(boardDto.getBRn())
		            .bNo(boardDto.getBNo())
		            .bTitle(boardDto.getBTitle())
		            .bContent(boardDto.getBContent())
		            .bReadcount(boardDto.getBReadcount())
		            .bDate(boardDto.getBDate())
		            .bCategory(category)
		            .user(User.builder()
		            		.userNo(boardDto.getUser().getUserNo())
		            		.build())
		            .bGroupno(boardDto.getBGroupno())
		            .bStep(boardDto.getBStep())
		            .bDepth(boardDto.getBDepth())
		            .build();


   }
}