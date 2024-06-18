package com.bruncheers.admin;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bruncheers.CustomFileUtil;
import com.bruncheers.board.dto.BoardDto;
import com.bruncheers.board.service.BoardService;
import com.bruncheers.order.dto.OrderWithAllDto;
import com.bruncheers.order.service.OrderService;
import com.bruncheers.product.dto.ProductDto;
import com.bruncheers.product.service.ProductService;
import com.bruncheers.user.dto.UserDto;
import com.bruncheers.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import util.Response;
import util.ResponseMessage;
import util.ResponseStatusCode;

@RestController
@RequestMapping("/admin")

public class AdminController {

	private UserService userService;
	private BoardService boardService;

	@Autowired
	private ProductService productService;
	private OrderService orderService;

	@Autowired
	private CustomFileUtil fileUtil;

	public AdminController(UserService userService, BoardService boardService, ProductService productService,
			OrderService orderService) {
		this.userService = userService;
		this.boardService = boardService;
		this.productService = productService;
		this.orderService = orderService;
	}

	/**
	 * 모든 회원의 목록을 조회합니다.
	 * @return 모든 회원의 목록을 포함한 ResponseEntity
	 */
	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "회원리스트", description = "회원 리스트를 조회합니다.")
	@GetMapping(value = "/user/list")
	public ResponseEntity<Response> getUserList() {
		List<UserDto> userList = userService.userList();
		Response response = new Response();
		response.setStatus(ResponseStatusCode.READ_USER);
		response.setMessage(ResponseMessage.READ_USER);
		response.setData(userList);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}


	/**
	 * 게시판에 댓글을 작성합니다.
	 * @param boardDto 댓글 정보를 담고 있는 BoardDto 객체
	 * @return 댓글 작성 결과를 포함한 Map
	 * @throws Exception 예외가 발생한 경우
	 */
	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "게시판 댓글 쓰기", description = "게시판 댓글 쓰기")
	@PostMapping(value = "/board/comment", produces = "application/json;charset=UTF-8")
	public Map board_reply_action(@RequestBody BoardDto boardDto) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		int status = 1;
		String msg = "성공";
		List<BoardDto> data = new ArrayList<>();

		try {
			boardService.createReply(boardDto);
			status = ResponseStatusCode.CREATED_COMMENT;
			msg = ResponseMessage.CREATED_COMMENT;
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseStatusCode.UNAUTHORIZED_BOARD;
			msg = ResponseMessage.UNAUTHORIZED_BOARD;
		}

		resultMap.put("status", status);
		resultMap.put("msg", msg);
		resultMap.put("data", data);
		return resultMap;
	}


	/**
	 * 새로운 상품을 추가합니다.
	 * @param productDto 추가할 상품 정보를 담고 있는 ProductDto 객체
	 * @return 추가된 상품 정보를 포함한 ResponseEntity
	 * @throws Exception 예외가 발생한 경우
	 */
	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "상품등록", description = "상품추가")
	@PostMapping("/product")
	public ResponseEntity<Response> saveProduct(@RequestPart(name = "product") ProductDto productDto,
			@RequestPart(name = "pImage", required = false) MultipartFile pImage,
			@RequestPart(name = "pDeimg", required = false) MultipartFile pDeimg) throws Exception {

		// 상품이미지
		if (pImage != null && !(pImage.isEmpty())) {
			MultipartFile pImageFile = pImage;
			String uploadPImageName = fileUtil.saveFile(pImageFile);
			productDto.setPImage(uploadPImageName);
		}
		// 상품상세이미지
		if (pDeimg != null && !(pDeimg.isEmpty())) {
			MultipartFile pDeImgFile = pDeimg;
			String uploadPDeImgName = fileUtil.saveFile(pDeImgFile);
			productDto.setPDeimg(uploadPDeImgName);
		}

		Response response = new Response();
		response.setStatus(ResponseStatusCode.CREATE_PRODUCT);
		response.setMessage(ResponseMessage.CREATE_PRODUCT);
		response.setData(productService.saveProduct(productDto));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders,
				HttpStatus.CREATED);

		return responseEntity;
	}

	/**
	 * 상품 정보를 수정합니다.
	 * @param productDto 수정할 상품 정보를 담고 있는 ProductDto 객체
	 * @return 수정된 상품 정보를 포함한 ResponseEntity
	 * @throws Exception 예외가 발생한 경우
	 */
	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "상품수정", description = "상품변경")
	@PutMapping("/product/update")
	public ResponseEntity<Response> updateProduct(@RequestPart(name = "product") ProductDto productDto,
			@RequestPart(name = "pImage", required = false) MultipartFile pImage,
			@RequestPart(name = "pDeimg", required = false) MultipartFile pDeimg) throws Exception {

		// 상품이미지
		if (pImage != null && !(pImage.isEmpty())) {
			MultipartFile pImageFile = pImage;
			String uploadPImageName = fileUtil.saveFile(pImageFile);
			productDto.setPImage(uploadPImageName);
		}
		// 상품상세이미지
		if (pDeimg != null && !(pDeimg.isEmpty())) {
			MultipartFile pDeImgFile = pDeimg;
			String uploadPDeImgName = fileUtil.saveFile(pDeImgFile);
			productDto.setPDeimg(uploadPDeImgName);
		}

		Response response = new Response();
		response.setStatus(ResponseStatusCode.CREATE_PRODUCT);
		response.setMessage(ResponseMessage.CREATE_PRODUCT);
		response.setData(productService.saveProduct(productDto));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders,
				HttpStatus.CREATED);

		return responseEntity;
	}

	/**
	 * 상품을 삭제합니다.
	 * @param pNo 삭제할 상품 번호
	 * @return 삭제 결과를 포함한 ResponseEntity
	 * @throws Exception 예외가 발생한 경우
	 */
	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "상품삭제", description = "상품삭제")
	@DeleteMapping("/product/{pNo}")
	public ResponseEntity<Map> deleteProduct(@PathVariable(name = "pNo") int pNo) throws Exception {
		productService.DeleteProduct(pNo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new HashMap<>());
	}

	/**
	 * 전체 주문내역리스트를 불러옵니다.
	 * @throws Exception 예외가 발생한 경우
	 */
	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "전체 주문리스트", description = "전체 주문리스트")
	@GetMapping("/order/list")
	public ResponseEntity<Response> getOrderListByAll() throws Exception {
		List<OrderWithAllDto> orderList = orderService.finfByAll();
		/***********************************************************/
		Response response = new Response();
		response.setStatus(1);
		response.setMessage("성공");
		response.setData(orderService.finfByAll());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

	/**
	 * 관리자가 공지사항 작성을 할 수 있습니다.
	 * @throws Exception 예외가 발생한 경우
	 */
	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "관리자 공지사항 작성", description = "공지사항 작성")
	@PostMapping(value = "/notice", produces = "application/json;charset=UTF-8")
	public Map board_write_action(@RequestBody BoardDto boardDto) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		int status = 1;
		String msg = "성공";
		List<Integer> data = new ArrayList<>();
		/***********************************************************/
		try {

			int boardNo = boardService.create(boardDto);
			data.add(boardNo);
			status = ResponseStatusCode.CREATED_BOARD;
			msg = ResponseMessage.CREATED_BOARD;

		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseStatusCode.CREATE_FAIL_BOARD;
			msg = ResponseMessage.CREATE_FAIL_BOARD;
		}

		/********************************************************/
		resultMap.put("status", status);
		resultMap.put("msg", msg);
		resultMap.put("data", data);

		return resultMap;

	}

}
