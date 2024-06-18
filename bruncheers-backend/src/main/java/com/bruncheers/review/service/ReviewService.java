package com.bruncheers.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruncheers.order.entity.OrderItem;
import com.bruncheers.order.repository.OrderItemRepository;
import com.bruncheers.product.entity.Product;
import com.bruncheers.product.repository.ProductRepository;
import com.bruncheers.review.dao.ReviewRepository;
import com.bruncheers.review.dto.ReviewInputDto;
import com.bruncheers.review.dto.ReviewOutputDto;
import com.bruncheers.review.entity.Review;
import com.bruncheers.user.entity.User;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	ProductRepository productRepository;
	@Autowired
	OrderItemRepository orderItemRepository;

	@Transactional
	public int create(ReviewInputDto reviewDto) throws Exception {

		Review newReview = dtoToEntity(reviewDto);

		return reviewRepository.save(newReview).getRNo();
	}

	private Review dtoToEntity(ReviewInputDto reviewDto) {
		int pNo = reviewDto.getProduct();
		int oiNo = reviewDto.getOrderItem();
		Product findProduct = productRepository.findById(pNo)
				.orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + pNo));
		OrderItem findOItem = orderItemRepository.findById(oiNo)
				.orElseThrow(() -> new NoSuchElementException("OrderItem not found with ID: " + oiNo));
		Review newReview = Review.toEntity(reviewDto);
		newReview.setProduct(findProduct);
		newReview.setOrderItem(findOItem);
		List<String> uploadFileName = reviewDto.getRImagefileNames();
		if (uploadFileName == null || uploadFileName.size() == 0) {
			return newReview;
		} else {
			newReview.setRImage(uploadFileName.get(0));
		}

		return newReview;
	}

	private ReviewOutputDto entityToDTO(Review review) {
		List<String> imageList = new ArrayList<>();
		ReviewOutputDto reviewDTO = ReviewOutputDto.builder().rNo(review.getRNo()).rStar(review.getRStar())
				.rContent(review.getRContent()).rReg(review.getRReg()).product(review.getProduct().getPNo())
				.orderItem(review.getOrderItem().getOiNo()).user(review.getUser().getUserNickname())
				.rImagefileNames(imageList).build();

		if (review.getRImage() == null || review.getRImage() == "") {
			return reviewDTO;
		}
		imageList.add(review.getRImage());

		reviewDTO.setRImagefileNames(imageList);
		;

		return reviewDTO;
	}

	public List<ReviewOutputDto> findProductReview(int pNo) throws Exception {
		List<ReviewOutputDto> reviewDtoList = new ArrayList<>();
		List<Review> reviewList = new ArrayList<>();

		reviewList = reviewRepository.findByProduct_pNo(pNo);

		for (Review review : reviewList) {
			reviewDtoList.add(ReviewOutputDto.toDto(review));
		}

		return reviewDtoList;
	}

	public List<Review> findUserReview(long userNo) throws Exception {

		List<Review> reviewList = new ArrayList<>();

		reviewList = reviewRepository.findByUserUserNo(userNo);

		return reviewList;
	}

	public List<ReviewOutputDto> findAllReview() throws Exception {
		List<ReviewOutputDto> reviewDtoList = new ArrayList<>();
		List<Review> reviewList = new ArrayList<>();

		reviewList = reviewRepository.findAll();

		for (Review review : reviewList) {
			reviewDtoList.add(ReviewOutputDto.toDto(review));
		}

		return reviewDtoList;
	}

	public int delete(int rNo) throws Exception {
		Optional<Review> deleteReview = reviewRepository.findById(rNo);
		if (!deleteReview.isPresent())
			throw new Exception();
		else {
			reviewRepository.deleteById(deleteReview.get().getRNo());
		}

		return 1;
	}

	public int update(ReviewInputDto reviewDto) throws Exception {
		return reviewRepository.save(dtoToEntity(reviewDto)).getRNo();
	}

	public int sumProductRStar(int pNo) throws Exception {
		return reviewRepository.findByProductReviewRating(pNo) / findProductReview(pNo).size();

	}

	// 해당 오더 아이템에 해당하는 리뷰 번호 반환
	public int findRNobyOiNo(int oiNo) {
		return reviewRepository.findByOrderItem_oiNo(oiNo).get().getRNo();
	}

	// 해당 오더 아이템에 해당하는 리뷰 반환
	public ReviewOutputDto findReviewByOiNo(int oiNo) {
		return ReviewOutputDto.toDto(reviewRepository.findByOrderItem_oiNo(oiNo).get());

	}

	public int deletebyOrderItem(int oiNo) throws Exception {

		Optional<Review> deleteReview = reviewRepository.findByOrderItem_oiNo(oiNo);
		System.out.println(deleteReview.get().getRNo());
		if (!deleteReview.isPresent())
			throw new Exception();
		else {
			reviewRepository.deleteById(deleteReview.get().getRNo());
		}

		return 1;
	}

	public int updatebyOrderItem(ReviewInputDto reviewDto) throws Exception {

		return reviewRepository.save(dtoToEntity(reviewDto)).getRNo();
	}

}