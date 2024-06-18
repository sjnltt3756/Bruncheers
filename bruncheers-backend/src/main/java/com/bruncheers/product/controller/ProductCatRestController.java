package com.bruncheers.product.controller;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruncheers.coupon.dto.CouponDto;
import com.bruncheers.product.dto.ProductCategoryDto;
import com.bruncheers.product.dto.ProductOptionDto;
import com.bruncheers.product.service.ProductCatServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import util.Response;
import util.ResponseMessage;
import util.ResponseStatusCode;

@RestController
@RequestMapping("/productCat")
public class ProductCatRestController {

	@Autowired
	ProductCatServiceImpl productCatServiceImpl;
	
	
	@Operation(summary = "상품 카테고리 등록")
	@PostMapping
	public ResponseEntity<Response> createProductCat(@RequestBody ProductCategoryDto productCategoryDto) throws Exception {
		Response response = new Response();
		response.setStatus(ResponseStatusCode.CREATE_PRODUCTCATEGORY);
		response.setMessage(ResponseMessage.CREATE_PRODUCTCATEGORY);
		response.setData(productCatServiceImpl.createProductCategory(productCategoryDto));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders, HttpStatus.CREATED);
		
		return responseEntity;
	}
	
	@Operation(summary = "상품 카테고리 수정")
	@PutMapping
	public ResponseEntity<Response> updateProductCat(@RequestBody ProductCategoryDto productCategoryDto) throws Exception {
		Response response = new Response();
		response.setStatus(ResponseStatusCode.UPDATE_PRODUCTCATEGORY);
		response.setMessage(ResponseMessage.UPDATE_PRODUCTCATEGORY);
		response.setData(productCatServiceImpl.createProductCategory(productCategoryDto));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);
		
		return responseEntity;
	}
	
	@Operation(summary = "상품 카테고리 삭제")
	@DeleteMapping("/{catNo}")
	public ResponseEntity<Response> updateProductCat(@PathVariable(name = "catNo") Integer catNo) throws Exception {
		
		productCatServiceImpl.deleteProductCategory(catNo);
		
		Response response = new Response();
		response.setStatus(ResponseStatusCode.DELETE_PRODUCTCATEGORY);
		response.setMessage(ResponseMessage.DELETE_PRODUCTCATEGORY);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);
	}
	
	
	
	
	@Operation(summary = "상품 카테고리 조회", description = "모든 상품 카테고리를 조회합니다")
	@GetMapping
	public ResponseEntity<Response> getProductCat() throws Exception {
		Response response = new Response();
		response.setStatus(ResponseStatusCode.READ_PRODUCTCATEGORY);
		response.setMessage(ResponseMessage.READ_PRODUCTCATEGORY);
		response.setData(productCatServiceImpl.getProductCategoryList());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

		return responseEntity;
	}

	@Operation(summary = "상품 카테고리 상품과 함께 조회", description = "모든 상품 카테고리를 상품과 함께 조회합니다")
	@GetMapping("/product")
	public ResponseEntity<Response> getProductCatWithProduct() throws Exception {
		Response response = new Response();
		response.setStatus(ResponseStatusCode.READ_PRODUCTCATEGORY);
		response.setMessage(ResponseMessage.READ_PRODUCTCATEGORY);
		response.setData(productCatServiceImpl.getProductCategoryWithProduct());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

		return responseEntity;
	}

}