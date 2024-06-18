package com.bruncheers.product.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruncheers.CustomFileUtil;
import com.bruncheers.product.dto.ProductDto;
import com.bruncheers.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import util.Response;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	 private CustomFileUtil fileUtil;

	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "상품입력", description = "상품추가")
	@PostMapping("/admin/insert") // 상품입력
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productDto));
	}

	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "상품수정", description = "상품변경")
	@PutMapping("/admin/update") // 상품수정
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) throws Exception {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productService.UpdateProduct(productDto));
	}

	/*	@SecurityRequirement(name = "BearerAuth")
		@Operation(summary = "상품삭제", description = "상품삭제")
		@DeleteMapping("/admin/delete{pNo}") // 상품삭제
		public ResponseEntity<Map> deleteProduct(@PathVariable(name = "pNo") int pNo) throws Exception {
			productService.DeleteProduct(pNo);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new HashMap<>());
		}*/

	@Operation(summary = "상품전체리스트", description = "상품전체리스트출력")
	@GetMapping // 상품전체리스트 출력
	public ResponseEntity<Response> getProductList() {
		List<ProductDto> products = productService.SeleteAll();
		/***********************************************************/
		Response response = new Response();
		response.setStatus(1);
		response.setMessage("성공");
		response.setData(productService.SeleteAll());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

	@Operation(summary = "상품번호로 찾기", description = "상품번호로 찾기")
	@GetMapping("/{pNo}") // 상품번호로 찾기
	public ResponseEntity<Response> getProductNo(@PathVariable(name = "pNo") int pNo) {
		ProductDto productDto = productService.SeleteByNo(pNo);
		/***********************************************************/
		Response response = new Response();
		response.setStatus(1);
		response.setMessage("성공");
		response.setData(productService.SeleteByNo(pNo));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

	@Operation(summary = "상품카탈로그번호로 찾기", description = "상품카탈로그번호로 찾기")
	@GetMapping("catNo/{catNo}") // 상품번호로 찾기
	public ResponseEntity<Response> getProductCategoryNo(@PathVariable(name = "catNo") int catNo) {
		List<ProductDto> productCategory = productService.SeleteByCategory(catNo);
		/***********************************************************/
		Response response = new Response();
		response.setStatus(1);
		response.setMessage("성공");
		response.setData(productService.SeleteByCategory(catNo));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);
	}

	@Operation(summary = "상품옵션번호로 찾기", description = "상품옵션번호로 찾기")
	@GetMapping("/poNo/{poNo}") // 상품번호로 찾기
	public ResponseEntity<Response> getProductOptionNo(@PathVariable(name = "poNo") int poNo) {
		ProductDto productDto = productService.SeleteByProductOption(poNo);
		/***********************************************************/
		Response response = new Response();
		response.setStatus(1);
		response.setMessage("성공");
		response.setData(productService.SeleteByProductOption(poNo));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

	@Operation(summary = "상품을 상품카테고리와 같이 찾기", description = "상품을 상품 카테고리와 함께 찾습니다")
	@GetMapping("/pcat") // 상품번호로 찾기
	public ResponseEntity<Response> SeleteAllWithProductCat() {
		Response response = new Response();
		response.setStatus(333);
		response.setMessage("상품(상품카테고리 포함) 조회 성공");
		response.setData(productService.SeleteAllWithProductCat());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders,
				HttpStatus.CREATED);

		return responseEntity;

	}
	
	// 상품이미지 가져오기
	  @GetMapping("/view/{fileName}")
	  public ResponseEntity<Resource> viewFileGET(@PathVariable(name = "fileName") String fileName){
		  System.err.println("*******************"+fileName);
	    return fileUtil.getFile(fileName);
	  }

}
