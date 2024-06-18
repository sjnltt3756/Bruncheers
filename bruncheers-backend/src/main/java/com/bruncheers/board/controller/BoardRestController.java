package com.bruncheers.board.controller;
/*

1. GET     /boards/{page_no}    : 게시판리스트  (모든데이타요청)
2. GET     /board/{board_no}   : 게시물상세보기(board_no 데이타를요청)
3. POST    /board             : 게시물쓰기   (데이타추가)
4. PUT     /board/{board_no}    : 게시물수정    (guest_no 데이타를수정)
5. DELETE    /board/{board_no}   : 게시물삭제   (guest_no 데이타를삭제)
6. POST    /comment           : 게시물 댓글 쓰기   (guest_no 데이타를삭제)
 */

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.RestController;

import com.bruncheers.board.dto.BoardDto;
import com.bruncheers.board.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import util.Response;
import util.ResponseMessage;
import util.ResponseStatusCode;

@CrossOrigin(origins = "*")
@RestController
public class BoardRestController {

	@Autowired
	@Qualifier("boardServiceImpl")
	BoardService boardService;

	@Operation(summary = "게시판리스트", description = "게시판리스트를 모두 조회")
	@GetMapping(value = "/boards/{page_no}")
	public ResponseEntity<Response> board_list(@PathVariable(value = "page_no") int page_no) throws Exception {

		/***********************************************************/
		Response response = new Response();
		try {
			response.setData(boardService.findBoardList(page_no));
			response.setStatus(ResponseStatusCode.READ_BOARD_LIST);
			response.setMessage(ResponseMessage.READ_BOARD_LIST);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatusCode.RETRIEVE_FAIL_BOARD_LIST);
			response.setMessage(ResponseMessage.RETRIEVE_FAIL_BOARD_LIST);

		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);

	}

	@Operation(summary = "게시판상세보기", description = "게시판 상세 조회")
	@GetMapping(value = "/board_view/{board_no}", produces = "application/json;charset=UTF-8")
	public Map board_detail(@PathVariable(value = "board_no") int board_no) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		int status = 1;
		String msg = "성공";
		List<BoardDto> data = new ArrayList<BoardDto>();
		/***********************************************************/
		try {

			BoardDto boardDto = boardService.findBoard(board_no);
			data.add(boardDto);
			status = ResponseStatusCode.READ_BOARD;
			msg = ResponseMessage.READ_BOARD;

		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseStatusCode.RETRIEVE_FAIL_NOT_FOUND_BOARD;
			msg = ResponseMessage.RETRIEVE_FAIL_NOT_FOUND_BOARD;
		}
		resultMap.put("status", status);
		resultMap.put("msg", msg);
		resultMap.put("data", data);
		return resultMap;

	}

	@Operation(summary = "게시판쓰기", description = "게시판 작성")
	@PostMapping(value = "/board", produces = "application/json;charset=UTF-8")
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

	@Operation(summary = "게시판수정", description = "게시판 수정")
	@PutMapping(value = "/board/{board_no}", produces = "application/json;charset=UTF-8")
	public Map board_update_action(@RequestBody BoardDto boardDto, @PathVariable(value = "board_no") int board_no)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		int status = 1;
		String msg = "성공";
		List<BoardDto> data = new ArrayList<>();
		/***********************************************************/
		try {
			boardDto.setBNo(board_no);
			boardService.update(boardDto);
			status = ResponseStatusCode.UPDATE_BOARD;
			msg = ResponseMessage.UPDATE_BOARD;
			data.add(boardDto);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseStatusCode.UNAUTHORIZED_BOARD;
			msg = ResponseMessage.UNAUTHORIZED_BOARD;
		}

		/******************************************/

		resultMap.put("status", status);
		resultMap.put("msg", msg);
		resultMap.put("data", data);
		return resultMap;

	}

	@Operation(summary = "게시판삭제", description = "게시판 삭제")
	@DeleteMapping(value = "/board/{board_no}", produces = "application/json;charset=UTF-8")
	public Map board_remove_action(@PathVariable(value = "board_no") int board_no)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		int status = 1;
		String msg = "성공";
		List<BoardDto> data = new ArrayList<>();
		/***********************************************************/

		try {
			boardService.remove(board_no);
			status = ResponseStatusCode.DELETE_BOARD;
			msg = ResponseMessage.DELETE_BOARD;
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseStatusCode.UNAUTHORIZED_BOARD;
			msg = ResponseMessage.UNAUTHORIZED_BOARD;
		}
		/******************************************/

		resultMap.put("status", status);
		resultMap.put("msg", msg);
		resultMap.put("data", data);
		return resultMap;

	}
	
	
	@Operation(summary = "게시판 댓글 쓰기", description = "게시판 댓글 쓰기")
	   @PostMapping(value = "/comment/{board_no}", produces = "application/json;charset=UTF-8")
	   public Map board_reply_action(@RequestBody BoardDto boardDto) throws Exception {
	      Map<String, Object> resultMap = new HashMap<>();
	      int status = 1;
	      String msg = "성공";
	      List<BoardDto> data = new ArrayList<>();
	      /***********************************************************/

	      try {
	         boardService.createReply(boardDto);
	         status = 1;
	         msg = "성공";
	      } catch (Exception e) {
	         e.printStackTrace();
	         status = 2;
	         msg = "실패";
	      }
	      /******************************************/

	      resultMap.put("status", status);
	      resultMap.put("msg", msg);
	      resultMap.put("data", data);
	      return resultMap;

	   }

}