package com.bruncheers.order.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruncheers.exception.PayNotFoundException;
import com.bruncheers.order.dto.PayDto;
import com.bruncheers.order.service.PayService;

import io.swagger.v3.oas.annotations.Operation;
import util.Response;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pay")
public class PayController {

	@Autowired
	private PayService payService;

	@Operation(summary = "결제방식 등록")
	@PostMapping
	public ResponseEntity<PayDto> createPay(@RequestBody PayDto payDto) {
		try {

			PayDto newPayDto = payService.creatPay(payDto);
			return ResponseEntity.ok(newPayDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "결제방식 삭제")
	@DeleteMapping("/{paNo}")
	public ResponseEntity<Void> deletePay(@PathVariable(name = "paNo") int paNo) {
		try {
			payService.deletePay(paNo);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "결제방식 한개 조회")
	@GetMapping("/{paNo}")
	public ResponseEntity<PayDto> findById(@PathVariable(name = "paNo") int paNo) {
		try {
			PayDto findsPaNo = payService.findByPay(paNo);
			return ResponseEntity.ok(findsPaNo);
		} catch (PayNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "결제방식으로 결제 번호 찾기")
	@GetMapping("/pay/{paType}")
	public ResponseEntity<Response> findBypaType(@PathVariable(name = "paType") String paType) throws Exception {
		Response response = new Response();
		response.setStatus(1);
		response.setMessage("결제 번호 찾기 대성공 개크게 성공");
		response.setData(payService.findBypaType(paType));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders,
				HttpStatus.CREATED);
		return responseEntity;
	}

}
