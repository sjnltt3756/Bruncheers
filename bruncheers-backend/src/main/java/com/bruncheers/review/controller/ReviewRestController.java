package com.bruncheers.review.controller;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bruncheers.CustomFileUtil;
import com.bruncheers.review.dto.ReviewInputDto;
import com.bruncheers.review.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;

import util.Response;
import util.ResponseMessage;
import util.ResponseStatusCode;

@CrossOrigin(origins = "*")
@RestController
public class ReviewRestController {

	@Autowired
	ReviewService reviewService;
	@Autowired
	private CustomFileUtil fileUtil;

	@Operation(summary = "리뷰전체리스트")
	@GetMapping(value = "/review")
	public ResponseEntity<Response> review_list() throws Exception {

		/***********************************************************/
		Response response = new Response();

		try {
			response.setStatus(ResponseStatusCode.READ_REVIEW_LIST);
			response.setMessage(ResponseMessage.READ_REVIEW_LIST);
			response.setData(reviewService.findAllReview());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatusCode.RETRIEVE_FAIL_REVIEW_LIST);
			response.setMessage(ResponseMessage.RETRIEVE_FAIL_REVIEW_LIST);
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGET(@PathVariable(name = "fileName") String fileName) {
		System.out.println("경로로 이미지 파일 호출"+fileUtil.getFile(fileName));
		return fileUtil.getFile(fileName);
	}

	@Operation(summary = "한 상품 리뷰리스트")
	@GetMapping(value = "/review/{product_no}")
	public ResponseEntity<Response> review_product_list(@PathVariable(value = "product_no") int product_no)
			throws Exception {

		/***********************************************************/
		Response response = new Response();

		try {
			response.setStatus(ResponseStatusCode.READ_PRODUCT_REVIEW_RIST);
			response.setMessage(ResponseMessage.READ_PRODUCT_REVIEW_RIST);
			response.setData(reviewService.findProductReview(product_no));
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatusCode.RETRIEVE_FAIL_REVIEW_LIST);
			response.setMessage(ResponseMessage.RETRIEVE_FAIL_REVIEW_LIST);

		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

	@Operation(summary = "한 오더아이템의 리뷰상세")
	@GetMapping(value = "/review_view/{orderItem_no}")
	public ResponseEntity<Response> review_orderItem_detail(@PathVariable(value = "orderItem_no") int orderItem_no)
			throws Exception {

		/***********************************************************/
		Response response = new Response();

		try {
			response.setStatus(ResponseStatusCode.READ_REVIEW);
			response.setMessage(ResponseMessage.READ_REVIEW);
			response.setData(reviewService.findReviewByOiNo(orderItem_no));
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatusCode.RETRIEVE_FAIL_REVIEW);
			response.setMessage(ResponseMessage.RETRIEVE_FAIL_REVIEW);

		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

	@Operation(summary = "리뷰 작성")
	@PostMapping(value = "/review")
	public ResponseEntity<Response> review_create_action(@RequestPart(name = "review") ReviewInputDto reviewDto,
			@RequestPart(name = "selectedfile", required = false) List<MultipartFile> files) throws Exception {

		
		List<String> uploadFileNames = null;
		
		List<MultipartFile> file = files;
		if(file!=null&&!file.isEmpty()) {
			 uploadFileNames = fileUtil.saveFiles(file);
		}
		reviewDto.setRImagefileNames(uploadFileNames);

		/***********************************************************/
		Response response = new Response();
		try {

			response.setStatus(ResponseStatusCode.CREATED_REAVIEW);
			response.setMessage(ResponseMessage.CREATED_REAVIEW);
			response.setData(reviewService.create(reviewDto));
		} catch (Exception e) {

			e.printStackTrace();
			response.setStatus(ResponseStatusCode.CREATE_FAIL_REVIEW);
			response.setMessage(ResponseMessage.CREATE_FAIL_REVIEW);

		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.CREATED);

	}

	@Operation(summary = "리뷰 수정")
	@PutMapping(value = "/review/{oiNo}")
	public ResponseEntity<Response> review_update(@PathVariable(name = "oiNo") int oiNo,
			@RequestPart(name = "review") ReviewInputDto reviewDto,
			@RequestPart(name = "updateSelectedfile", required = false) List<MultipartFile> files) throws Exception {

		reviewDto.setRNo(reviewService.findRNobyOiNo(oiNo));

		/***********************************************************/
		Response response = new Response();
		try {
			response.setStatus(ResponseStatusCode.UPDATE_REVIEW);
			response.setMessage(ResponseMessage.UPDATE_REVIEW);
			response.setData(reviewService.update(reviewDto));
		} catch (Exception e) {

			e.printStackTrace();
			response.setStatus(ResponseStatusCode.CREATE_FAIL_REVIEW);
			response.setMessage(ResponseMessage.CREATE_FAIL_REVIEW);

		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.CREATED);

	}

	
	
	
	@Operation(summary = "리뷰 삭제")
	@DeleteMapping(value = "/review/{oiNo}")
	public ResponseEntity<Response> review_delete_action(@PathVariable(value = "oiNo") int oiNo)
			throws Exception {

		/***********************************************************/
		Response response = new Response();

		try {
			response.setStatus(ResponseStatusCode.DELETE_REVIEW);
			response.setMessage(ResponseMessage.DELETE_REVIEW);
			response.setData(reviewService.deletebyOrderItem(oiNo));
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatusCode.UNAUTHORIZED_REVIEW);
			response.setMessage(ResponseMessage.UNAUTHORIZED_REVIEW);
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

	@Operation(summary = "한 상품 리뷰 평점 평균")
	@GetMapping(value = "/review_rate/{product_no}")
	public ResponseEntity<Response> review_product_rating(@PathVariable(value = "product_no") int product_no)
			throws Exception {

		/***********************************************************/
		Response response = new Response();

		try {
			response.setStatus(ResponseStatusCode.READ_REVIEW_RATING);
			response.setMessage(ResponseMessage.READ_REVIEW_RATING);
			response.setData(reviewService.sumProductRStar(product_no));
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatusCode.UNAUTHORIZED_REVIEW);
			response.setMessage(ResponseMessage.UNAUTHORIZED_REVIEW);
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

}