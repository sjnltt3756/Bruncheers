package com.bruncheers.review.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bruncheers.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	 // 상품 하나에 대한 평점 합
    @Query("SELECT ROUND(SUM(r.rStar), -1) FROM Review r WHERE r.product.pNo = :pNo")
    Integer findByProductReviewRating(@Param("pNo") Integer pNo) throws Exception;

 // 프로덕트 아이디로 리뷰 리스트 찾기
    List<Review> findByProduct_pNo(Integer pNo);
    // user 아이디로 리뷰 리스트 찾기
    List<Review> findByUserUserNo(Long userNo);

    // 프로덕트 아이디로 리뷰 찾기
    Optional<Review> findByOrderItem_oiNo(Integer oiNo);
    
    
    
    Review findByOrderItemOiNo(Integer oiNo);

	
}