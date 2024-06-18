package com.bruncheers.product.controller;

import java.nio.charset.Charset;
import java.util.List;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bruncheers.product.dto.ProductDto;
import com.bruncheers.product.dto.ProductOptionDto;
import com.bruncheers.product.dto.ProductOptionDtoInProduct;
import com.bruncheers.product.service.ProductOptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import util.Response;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/productOption")
public class ProductOptionController {

	@Autowired
	ProductOptionService productOptionService;

	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "상품옵션등록", description = "옵션 등록")
	@PostMapping(value = "/pono")
	public ResponseEntity<ProductOptionDto> createProductOption(@RequestBody ProductOptionDto productOptionDto)
			throws Exception {

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(productOptionService.InsertProductOption(productOptionDto));
	}

	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "상품옵션삭제", description = "옵션 삭제")
	@DeleteMapping("/poNo/{poNo}")
	public ResponseEntity<Response> deleteProductOption(@PathVariable(name = "poNo") int poNo) throws Exception {

		productOptionService.DeleteProductOption(poNo);
		/* return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>()); */

		Response response = new Response();
		response.setStatus(1);
		response.setMessage("성공");

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}
	
	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "상품옵션수정", description = "옵션 수정")
	@PutMapping("/poNo")
	public ResponseEntity<Response> updateProductOption(@RequestBody ProductOptionDto productOptionDto) throws Exception {
		
		Response response = new Response();
		response.setStatus(1);
		response.setMessage("성공");
		response.setData(productOptionService.UpdateProductOption(productOptionDto));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);
		
	}

	// 상품번호로 상품리스트 찾기
	@Operation(summary = "상품옵션으로 상품리스트", description = "상품옵션번호로 상품리스트 찾기")
	@GetMapping("/api/productOptions")
	public ResponseEntity<Response> findByProductOption(@RequestParam(name = "poNo") List<Integer> poNoList) {

	    List<ProductOptionDtoInProduct> productOptionList = productOptionService.FindByProductOption(poNoList);
	    
	    // 각 poNo에 대해 출력
	    for (Integer poNo : poNoList) {
	        System.out.println("상품 옵션 번호: " + poNo);
	    }

	    Response response = new Response();
	    response.setStatus(1);
	    response.setMessage("성공");
	    response.setData(productOptionList);

	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	    return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);
	}

	//상품옵션 리스트로 상품 번호 찾기
	@Operation(summary = "상품옵션 리스트로 상품번호 찾기", description = "상품옵션리스트로 상품번호찾기")
	@GetMapping("/api/productOptionPNo/{pNo}") 
	public ResponseEntity<Response> getProductOptionPNo(@PathVariable(name = "pNo") int pNo) {
		List<ProductOptionDto> productOptionPNo = productOptionService.findByProductNo(pNo);
		/***********************************************************/
		Response response = new Response();
		response.setStatus(1);
		response.setMessage("성공");
		response.setData(productOptionService.findByProductNo(pNo));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);
	}

}
