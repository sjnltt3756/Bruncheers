package com.bruncheers.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruncheers.exception.OrderNotFoundException;
import com.bruncheers.order.dto.OrderItemDto;
import com.bruncheers.order.service.OrderItemService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/oi")
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;

	@Operation(summary = "주문아이템 추가")
	@PostMapping
	public ResponseEntity<OrderItemDto> insertOrderItem(@RequestBody OrderItemDto orderItemDto) {
		try {
			OrderItemDto newOrderItemDto = orderItemService.insertOrderItem(orderItemDto);
			return ResponseEntity.ok(newOrderItemDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "주문아이템 삭제")
	@DeleteMapping("/{oiNo}")
	public ResponseEntity<Void> deleteOrderItem(@PathVariable(name = "oiNo") int oiNo) {
		try {
			orderItemService.deleteOrderItem(oiNo);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "주문아이템 한개 조회")
	@GetMapping("/{oiNo}")
	public ResponseEntity<OrderItemDto> findById(@PathVariable(name = "oiNo") int oiNo) {
		try {
			OrderItemDto findOi = orderItemService.findOrderItem(oiNo);
			return ResponseEntity.ok(findOi);
		} catch (OrderNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "한 주문의 주문아이템 목록 조회")
	@GetMapping("/{oNo}/ois")
	public ResponseEntity<List<OrderItemDto>> findAllOrderItemByONo(@PathVariable(name = "oNo") Integer oNo) {
		try {
			List<OrderItemDto> orderOrderItems = orderItemService.findAllOrderItemByONo(oNo);
			return ResponseEntity.ok(orderOrderItems);
		} catch (OrderNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

}
