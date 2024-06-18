package com.bruncheers.review.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.bruncheers.review.entity.Review;

@SpringBootTest
class ReviewRepositoryTest2 {

	@Autowired
	ReviewRepository reviewRepository;

	@Test
	//@Disabled
	@Rollback(false)
	void test() {
		Optional<Review> deleteReview = reviewRepository.findByOrderItem_oiNo(6);

		System.out.println(deleteReview);

	}

}
