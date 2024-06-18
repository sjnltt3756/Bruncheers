package com.bruncheers.cart.controller;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruncheers.cart.dto.CartDto;
import com.bruncheers.cart.service.CartService;
import com.bruncheers.exception.DuplicateCartItemException;

import io.swagger.v3.oas.annotations.Operation;
import util.Response;
import util.ResponseMessage;
import util.ResponseStatusCode;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cart")
public class CartRestController {

	@Autowired
	CartService cartService;
	
	@Operation(summary = "장바구니 추가", description = "장바구니에 아이템을 추가합니다")
	@PostMapping
	public ResponseEntity<Response> addCart(@RequestBody CartDto CartDto) throws Exception{
		
		cartService.addCart(CartDto);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.ADD_CART);
		response.setMessage(ResponseMessage.ADD_CART);
		response.setData(CartDto);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
		
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response,httpHeaders,HttpStatus.CREATED);
		return responseEntity;
	}
	
	@Operation(summary = "장바구니 조회", description = "장바구니를 조회합니다")
	@GetMapping("/{userNo}")
	public ResponseEntity<Response> getCarts(@PathVariable(name="userNo") Long userNo) throws Exception{
		Response response = new Response();
		response.setStatus(ResponseStatusCode.READ_CART);
		response.setMessage(ResponseMessage.READ_CART);
		response.setData(cartService.getCarts(userNo));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
		
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response,httpHeaders,HttpStatus.CREATED);
		return responseEntity;
	}
	
	@Operation(summary = "장바구니 아이템 하나 삭제", description = "장바구니 아이템 하나를 삭제합니다")
	@DeleteMapping("/{cNo}")
	public ResponseEntity<Response> deleteCartItem(@PathVariable(name="cNo") Long cNo) throws Exception{
		
		cartService.deleteCartItem(cNo);
		
		Response response = new Response();
		response.setStatus(ResponseStatusCode.DELETE_CARTITEM);
		response.setMessage(ResponseMessage.DELETE_CARTITEM);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response,httpHeaders,HttpStatus.CREATED);
		return responseEntity;
	}
	
	@Operation(summary = "장바구니 체크 된 상품들 삭제", description = "체크된 상품들을 장바구니에서 삭제합니다")
	@DeleteMapping
	public ResponseEntity<Response> deleteCheckedCartItems(@RequestBody Iterable<Long> cNoList) throws Exception{
		
		cartService.deleteCheckedCartItems(cNoList);
		
		Response response = new Response();
		response.setStatus(ResponseStatusCode.DELETE_CHECKED_CARTITEM);
		response.setMessage(ResponseMessage.DELETE_CHECKED_CARTITEM);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response,httpHeaders,HttpStatus.CREATED);
		return responseEntity;
	}
	

	/**************************************** 예외 처리 ****************************************/
	
	@ExceptionHandler(DuplicateCartItemException.class)
	public ResponseEntity<Response> duplicate_cartItem_exception_handler(DuplicateCartItemException e){
		Response response=new Response();
		response.setStatus(ResponseStatusCode.DUPLICATED_CARTITEM);
		response.setMessage(ResponseMessage.DUPLICATED_CARTITEM);
		
		HttpHeaders httpHeaders=new HttpHeaders();
		httpHeaders.setContentType(
				new MediaType("application","json",Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response,httpHeaders,HttpStatus.OK);
	}
		
	
	
	
	
}
