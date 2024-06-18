package com.bruncheers.coupon.controller;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bruncheers.coupon.CoupResponseMessage;
import com.bruncheers.coupon.CoupResponseStatusCode;
import com.bruncheers.coupon.dto.CouponDto;
import com.bruncheers.coupon.service.CouponService;

import io.swagger.v3.oas.annotations.Operation;
import util.Response;
import util.ResponseMessage;
import util.ResponseStatusCode;

@CrossOrigin(origins = "*")
@RestController
public class CouponRestController {

	@Autowired
	CouponService couponService;

	@Operation(summary = "쿠폰 생성", description = "쿠폰를 생성합니다")
	@PostMapping("/coupon")
	public ResponseEntity<Response> createCoupon(@RequestBody CouponDto coupon) throws Exception {
		couponService.createCoupon(coupon);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.CREATED_COUP);
		response.setMessage(ResponseMessage.CREATED_COUP);
		response.setData(coupon);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders,
				HttpStatus.CREATED);

		return responseEntity;
	}

	@Operation(summary = "쿠폰 조회", description = "회원 번호로 쿠폰 조회")
	@GetMapping("/coupons/{userNo}")
	public ResponseEntity<Response> getCoupons(@PathVariable(name = "userNo") Long userNo) throws Exception {
		Response response = new Response();
		response.setStatus(ResponseStatusCode.READ_COUP);
		response.setMessage(ResponseMessage.READ_COUP);
		response.setData(couponService.getCoupons(userNo));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders,
				HttpStatus.CREATED);

		return responseEntity;
	}

	@Operation(summary = "쿠폰 삭제(1개)", description = "쿠폰 1개 삭제")
	@DeleteMapping("/coupon/{coupNo}")
	public ResponseEntity<Response> deleteCoupon(@PathVariable(name = "coupNo") Long coupNo) throws Exception {

		couponService.deleteCoupon(coupNo);

		Response response = new Response();
		response.setStatus(ResponseStatusCode.DELETE_COUP);
		response.setMessage(ResponseMessage.DELETE_COUP);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders,
				HttpStatus.CREATED);
		return responseEntity;
	}

	@Operation(summary = "쿠폰 삭제(회원이 가진 쿠폰 전체)", description = "회원이 가진 쿠폰 전체 삭제")
	@DeleteMapping("/coupons/{userNo}")
	public ResponseEntity<Response> deleteAllCouponsByUserNo(@PathVariable(name = "userNo") Long userNo)
			throws Exception {
		couponService.deleteAllCouponsByUserNo(userNo);

		Response response = new Response();
		response.setStatus(ResponseStatusCode.DELETE_ALL_COUP);
		response.setMessage(ResponseMessage.DELETE_ALL_COUP);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders,
				HttpStatus.CREATED);
		return responseEntity;
	}

}
