package com.bruncheers.order.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruncheers.order.dto.OrderCreateDto;
import com.bruncheers.order.dto.OrderWithOrderItemDto;
import com.bruncheers.order.service.OrderService;
import com.bruncheers.user.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import util.Response;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@Operation(summary = "주문한건삭제", description = "한명의 회원 주문한건삭제")
	@DeleteMapping("{oNo}")
	public ResponseEntity<?> deleteOrderNo(@PathVariable(value = "oNo") int oNo) {
		try {
			orderService.deleteOrder(oNo);
			return ResponseEntity.status(HttpStatus.OK).body("주문번호" + oNo + "번이 삭제되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	@Operation(summary = "주문 전체 삭제", description = "한 명의 회원의 모든 주문을 삭제합니다.")
	@DeleteMapping("/orderDeleteAll/{userNo}")
	public ResponseEntity<Map<String, String>> deleteAllOrdersByUserNo(@PathVariable(value = "userNo") long userNo) {
		try {
			orderService.deleteAllOrder(userNo);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	/*
	 * @Operation(summary = "주문 전체 삭제",description = "한 명의 회원의 모든 주문을 삭제합니다.")
	 * 
	 * @DeleteMapping("/orderDeleteAll/{userNo}") public ResponseEntity<Response>
	 * deleteProductOption(
	 * 
	 * @PathVariable(name = "userNo") long userNo) throws Exception {
	 * 
	 * orderService.deleteAllOrder(userNo);
	 * 
	 * Response response = new Response(); response.setStatus(1);
	 * response.setMessage("성공");
	 * 
	 * HttpHeaders httpHeaders = new HttpHeaders(); httpHeaders.setContentType(new
	 * MediaType("application", "json", Charset.forName("UTF-8"))); return new
	 * ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);
	 * 
	 * 
	 * }
	 */

	/*
	 * @Operation(summary = "주문생성", description = "상품에서바로주문생성")
	 * 
	 * @PostMapping("/direct") public ResponseEntity<Response>
	 * createOrderDirect(@RequestBody OrderCreateDirectDto orderCreateDto) throws
	 * Exception { try { orderService.ProductOrderSave(orderCreateDto);
	 * 
	 * Response response = new Response(); response.setStatus(1);
	 * response.setMessage("성공");
	 * 
	 * return ResponseEntity.ok().body(response); } catch (Exception e) {
	 * e.printStackTrace(); Response response = new Response();
	 * response.setStatus(0); response.setMessage("실패"); response.setData(null);
	 * 
	 * return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); } }
	 */

	@Operation(summary = "주문생성", description = "주문생성")
	@PostMapping
	public ResponseEntity<Response> createOrder(@RequestBody OrderCreateDto orderCreateDto) throws Exception {
		try {
			orderService.createOrder(orderCreateDto);

			Response response = new Response();
			response.setStatus(1);
			response.setMessage("성공");

			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			e.printStackTrace();
			Response response = new Response();
			response.setStatus(0);
			response.setMessage("실패");
			response.setData(null);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/*
	 * @Operation(summary = "주문생성", description = "주문생성")
	 * 
	 * @PostMapping("/orderCreate") public ResponseEntity<OrderDto>
	 * createOrder(@RequestBody OrderDto orderDto)throws Exception{
	 * 
	 * 
	 * DTO에서 엔티티로 매핑 Order order = new Order(); User user = new User();
	 * order.setONo(orderDto.getONo()); order.setOPrice(orderDto.getOPrice());
	 * order.setOName(orderDto.getOName()); order.setOZip(orderDto.getOZip());
	 * order.setOAddr(orderDto.getOAddr()); order.setOPhone(orderDto.getOPhone());
	 * order.setODate(orderDto.getODate()); order.setOReq(orderDto.getOReq());
	 * user.setUserNo(orderDto.getUserNo()); order.setUser(user);
	 * 
	 * 
	 * //orderService.insertOrder(orderDto); // return ResponseEntity.ok().build();
	 * return
	 * ResponseEntity.status(HttpStatus.CREATED).body(orderService.insertOrder(
	 * orderDto));
	 * 
	 * }
	 */

	@Operation(summary = "주문한개의 상세주문", description = "주문리스트한개의 상세주문리스트")
	@GetMapping("{oNo}")
	public ResponseEntity<Response> getOrderNo(@PathVariable(name = "oNo") int oNo) throws Exception {
		OrderWithOrderItemDto orderDto = orderService.findListByOrderNo(oNo);
		/***********************************************************/
		Response response = new Response();
		response.setStatus(1);
		response.setMessage("성공");
		response.setData(orderService.findListByOrderNo(oNo));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

	// 회원 한명의 전체 주문리스트
	/*
	 * @Operation(summary = "회원한명의 주문리스트", description = "회원한명이 주문한 전체주문리스트")
	 * 
	 * @GetMapping("/userNo/{userNo}") public ResponseEntity<Response>
	 * getOrderListUserNo(@PathVariable(name="userNo")long userNo)throws Exception{
	 * List<OrderDto> userAllList=orderService.findListByUserId(userNo);
	 */
	/***********************************************************/
	/*
	 * Response response = new Response(); response.setStatus(1);
	 * response.setMessage("성공");
	 * response.setData(orderService.findListByUserId(userNo));
	 * 
	 * HttpHeaders httpHeaders = new HttpHeaders(); httpHeaders.setContentType(new
	 * MediaType("application", "json", Charset.forName("UTF-8"))); return new
	 * ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);
	 * 
	 * }
	 */
	@Operation(summary = "회원한명의 주문리스트", description = "회원한명이 주문한 전체주문리스트")
	@GetMapping("/userNo/{userNo}")
	public ResponseEntity<Response> getOrderListUserNo(@PathVariable(name = "userNo") long userNo) throws Exception {
		List<OrderWithOrderItemDto> orderList = orderService.findListByUserId(userNo);
		/***********************************************************/
		Response response = new Response();
		response.setStatus(1);
		response.setMessage("성공");
		response.setData(orderService.findListByUserId(userNo));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}


}
