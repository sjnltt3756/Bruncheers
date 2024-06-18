package com.bruncheers.ship.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruncheers.ship.dto.ShipDto;
import com.bruncheers.ship.service.ShipService;

import io.swagger.v3.oas.annotations.Operation;
import util.Response;
import util.ResponseMessage;
import util.ResponseStatusCode;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ship")
public class ShipRestController {

	@Autowired
	private ShipService shipService;

	@Operation(summary = "회원의 새 배송지 등록")
	@PostMapping
	public ResponseEntity<Response> createShip(@RequestBody ShipDto shipDto) throws Exception {
		/*
		 * try { ShipDto newShipDto = shipService.createShip(shipDto); return
		 * ResponseEntity.ok(newShipDto); } catch (Exception e) { e.printStackTrace();
		 * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); }
		 */
		shipService.createShip(shipDto);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.CREATED_SHIP);
		response.setMessage(ResponseMessage.CREATED_SHIP);
		response.setData(shipDto);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders, HttpStatus.CREATED);
		
		return responseEntity;
	}

	@Operation(summary = "배송지 수정")
	@PutMapping("/{sNo}")
	public ResponseEntity<Response> updateShip(@PathVariable(name = "sNo") int sNo, @RequestBody ShipDto shipDto) throws Exception {
		/*
		 * try { shipDto.setSNo(sNo); ShipDto updatedShipDto =
		 * shipService.updateShip(shipDto); return ResponseEntity.ok(updatedShipDto); }
		 * catch (ShipNotFoundException e) { return ResponseEntity.notFound().build(); }
		 * catch (Exception e) { e.printStackTrace(); return
		 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); }
		 */
		shipDto.setSNo(sNo);
		shipService.updateShip(shipDto);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.UPDATE_SHIP);
		response.setMessage(ResponseMessage.UPDATE_SHIP);
		response.setData(shipDto);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);
		
		return responseEntity;
		
	}

	@Operation(summary = "배송지 삭제")
	@DeleteMapping("/{sNo}")
	public ResponseEntity<Response> deleteShip(@PathVariable(name = "sNo") int sNo) throws Exception {
		shipService.deleteShip(sNo);
		
		Response response = new Response();
		response.setStatus(ResponseStatusCode.DELETE_SHIP);
		response.setMessage(ResponseMessage.DELETE_SHIP);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response,httpHeaders,HttpStatus.OK);
		
		return responseEntity;
	}

	@Operation(summary = "회원의 배송지 목록 조회")
	@GetMapping("/{userNo}/ships")
	public ResponseEntity<Response> selectByUserUserNo(@PathVariable(name = "userNo") Long userNo) throws Exception {
		Response response = new Response();
		response.setStatus(ResponseStatusCode.READ_SHIP_BY_USER);
		response.setMessage(ResponseMessage.READ_SHIP_BY_USER);
		response.setData(shipService.selectByUserUserNo(userNo));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response,httpHeaders,HttpStatus.OK);
		
		return responseEntity;
	}
	
	@Operation(summary = "회원의 첫번째 배송지(기본배송지) 조회")
	@GetMapping("/{userNo}/firstShip")
	public ResponseEntity<Response> findFirstShipByUserNo(@PathVariable(name = "userNo") Long userNo) throws Exception {
	    Response response = new Response();
	    response.setStatus(ResponseStatusCode.READ_SHIP_BY_USER_FIRST_SHIP);
	    response.setMessage(ResponseMessage.READ_SHIP_BY_USER_FIRST_SHIP);
	    response.setData(shipService.findFirstShipByUserNo(userNo));
	    
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	    
	    ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	    
	    return responseEntity;
	}

	@Operation(summary = "배송지 한개 조회")
	@GetMapping("/{sNo}")
	public ResponseEntity<Response> findById(@PathVariable(name = "sNo") int sNo) throws Exception {
		
		Response response = new Response();
		response.setStatus(ResponseStatusCode.READ_SHIP);
		response.setMessage(ResponseMessage.READ_SHIP);
		response.setData(shipService.findById(sNo));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response,httpHeaders,HttpStatus.OK);
		
		return responseEntity;
	}

	@Operation(summary = "배송지 유형별 조회")
	@GetMapping("type/{sType}/ships")
	  public ResponseEntity<Response> getShipsByType(@PathVariable(name = "sType") String sType) throws Exception {
		
		Response response = new Response();
		response.setStatus(ResponseStatusCode.READ_SHIP_BY_STYPE);
		response.setMessage(ResponseMessage.READ_SHIP_BY_STYPE);
		response.setData(shipService.selectBysType(sType));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response,httpHeaders,HttpStatus.OK);
		
		return responseEntity;
    }

}
