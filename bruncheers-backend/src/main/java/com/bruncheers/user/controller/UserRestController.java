package com.bruncheers.user.controller;

import java.nio.charset.Charset;

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

import com.bruncheers.user.dto.NewPasswordDto;
import com.bruncheers.user.dto.PasswordVerificationRequestDto;
import com.bruncheers.user.dto.UserDto;
import com.bruncheers.user.dto.UserLoginDto;
import com.bruncheers.user.dto.UserUpdateDto;
import com.bruncheers.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import util.Response;
import util.ResponseMessage;
import util.ResponseStatusCode;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserRestController {

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 회원가입을 처리하는 API
	 * 
	 * @param userJoinDto 회원가입 정보가 담긴 DTO
	 * @return 회원가입 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@RequestBody UserDto userJoinDto) {
		UserDto newUser = userService.registerUser(userJoinDto);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.CREATED_USER);
		response.setMessage(ResponseMessage.CREATED_USER);
		response.setData(newUser);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 로그인을 처리하는 API
	 * 
	 * @param userLoginRequest 로그인 요청 정보가 담긴 DTO
	 * @return 로그인 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "로그인", description = "사용자를 로그인합니다.")
	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@RequestBody UserLoginDto userLoginRequest) {
		UserDto userDto = userService.loginUser(userLoginRequest);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.LOGIN_SUCCESS);
		response.setMessage(ResponseMessage.LOGIN_SUCCESS);
		response.setData(userDto);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 생년월일과 전화번호로 이메일을 찾는 API
	 * 
	 * @param userBirth 사용자의 생년월일
	 * @param userHp    사용자의 전화번호
	 * @return 이메일 찾기 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "아이디찾기", description = "사용자의 생년월일과 전화번호로 이메일을 찾습니다.")
	@GetMapping("/find/id")
	public ResponseEntity<Response> findEmailByBirthAndHp(@RequestParam(name = "userBirth") String userBirth,
			@RequestParam(name = "userHp") String userHp) {
		String email = userService.findEmailByBirthAndHp(userBirth, userHp);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.FIND_ID_SUCCESS);
		response.setMessage(ResponseMessage.FIND_ID_SUCCESS);
		response.setData(email);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 이메일과 전화번호로 비밀번호를 찾는 API
	 * 
	 * @param userEmail 사용자의 이메일
	 * @param userHp    사용자의 전화번호
	 * @return 비밀번호 찾기 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "비밀번호찾기", description = "사용자의 이메일과 전화번호로 비밀번호를 찾습니다.")
	@GetMapping("/find/pw")
	public ResponseEntity<Response> findEmailByHp(@RequestParam(name = "userEmail") String userEmail,
			@RequestParam(name = "userHp") String userHp) {
		String password = userService.findPwByEmailAndByHp(userEmail, userHp);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.FIND_PW_SUCCESS);
		response.setMessage(ResponseMessage.FIND_PW_SUCCESS);
		response.setData(password);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 닉네임 중복 여부를 확인하는 API
	 * 
	 * @param userNickname 사용자의 닉네임
	 * @return 닉네임 중복 확인 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "닉네임중복확인", description = "사용자의 닉네임 중복 여부를 확인합니다.")
	@GetMapping("/nickname-unique")
	public ResponseEntity<Response> isNicknameUnique(@RequestParam(name = "userNickname") String userNickname) {
		boolean isUnique = userService.isNicknameUnique(userNickname);
		Response response = new Response();

		if (isUnique) {
			response.setStatus(ResponseStatusCode.NICKNAME_AVAILABLE);
			response.setMessage(ResponseMessage.NICKNAME_AVAILABLE);
		} else {
			response.setStatus(ResponseStatusCode.CREATE_FAIL_EXISTED_NICKNAME);
			response.setMessage(ResponseMessage.CREATE_FAIL_EXISTED_NICKNAME);
		}

		response.setData(isUnique);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 이메일 중복 여부를 확인하는 API
	 * 
	 * @param userEmail 사용자의 이메일
	 * @return 이메일 중복 확인 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "이메일중복확인", description = "사용자의 이메일 중복 여부를 확인합니다.")
	@GetMapping("/email-unique")
	public ResponseEntity<Response> isEmailUnique(@RequestParam(name = "userEmail") String userEmail) {
		boolean isUnique = userService.isEmailUnique(userEmail);
		Response response = new Response();

		if (isUnique) {
			response.setStatus(ResponseStatusCode.EMAIL_AVAILABLE);
			response.setMessage(ResponseMessage.EMAIL_AVAILABLE);
		} else {
			response.setStatus(ResponseStatusCode.CREATE_FAIL_EXISTED_EMAIL);
			response.setMessage(ResponseMessage.CREATE_FAIL_EXISTED_EMAIL);
		}

		response.setData(isUnique);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 회원 정보를 수정하는 API
	 * 
	 * @param userNo        회원 번호
	 * @param userUpdateDto 회원 수정 정보가 담긴 DTO
	 * @return 회원 수정 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "회원수정", description = "회원 정보를 수정합니다.")
	@PutMapping("/update/{userNo}")
	public ResponseEntity<Response> updateUser(@PathVariable(name = "userNo") Long userNo,
			@RequestBody UserUpdateDto userUpdateDto) {
		UserDto updatedUser = userService.updateUser(userUpdateDto);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.UPDATE_USER);
		response.setMessage(ResponseMessage.UPDATE_USER);
		response.setData(updatedUser);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	/**
	 * 회원을 삭제하는 API
	 * 
	 * @param userNo 회원 번호
	 * @return 회원 삭제 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "회원삭제", description = "회원을 삭제합니다.")
	@DeleteMapping("/{userNo}")
	public ResponseEntity<Response> deleteUser(@PathVariable(name = "userNo") Long userNo) {
		userService.deleteUser(userNo);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.DELETE_USER);
		response.setMessage(ResponseMessage.DELETE_USER);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 회원 정보를 조회하는 API
	 * 
	 * @param userNo 회원 번호
	 * @return 회원 정보 조회 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "회원정보", description = "회원 정보를 조회합니다.")
	@GetMapping("/userInfo/{userNo}")
	public ResponseEntity<Response> getUserInfo(@PathVariable(name = "userNo") Long userNo) {
		UserDto userInfo = userService.getUserInfo(userNo);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.READ_USER);
		response.setMessage(ResponseMessage.READ_USER);
		response.setData(userInfo);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	/**
	 * 비밀번호 확인을 처리하는 API
	 * 
	 * @param userNo   회원 번호
	 * @param password 비밀번호 확인 요청 정보가 담긴 DTO
	 * @return 비밀번호 확인 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "비밀번호 확인", description = "비밀번호 확인(수정페이지로 넘어가기 위함)")
	@PostMapping("/verify-login/{userNo}")
	public ResponseEntity<Response> verifyLogin(@PathVariable(name = "userNo") Long userNo,
			@RequestBody PasswordVerificationRequestDto password) {
		UserDto userDto = userService.verifyLogin(userNo, password);
		Response response = new Response();

		response.setStatus(ResponseStatusCode.SUCCESS_PASSWORD_MISMATCH_USER);
		response.setMessage(ResponseMessage.SUCCESS_PASSWORD_MISMATCH_USER);
		response.setData(userDto);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	/**
	 * 비밀번호를 수정하는 API
	 * 
	 * @param userUpdateDto 비밀번호 수정 정보가 담긴 DTO
	 * @return 비밀번호 수정 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "비밀번호수정", description = "비밀번호찾기 누르면 새로운 비밀번호 입력")
	@PutMapping("/updatePassword")
	public ResponseEntity<Response> newPassword(@RequestBody NewPasswordDto userUpdateDto) {
		UserDto updatedUser = userService.newPassword(userUpdateDto);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.UPDATE_USER);
		response.setMessage(ResponseMessage.UPDATE_USER);
		response.setData(updatedUser);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	/**
	 * 회원의 역할을 GHOST로 변경하는 API
	 * 
	 * @param userNo 회원 번호
	 * @return 역할 변경 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "탈퇴(role변경)", description = "탈퇴하면 role이 ghost로 바뀜")
	@PutMapping("/{userNo}/role/ghost")
	public ResponseEntity<Response> changeUserRoleToGhost(@PathVariable(name = "userNo") Long userNo) {
		UserDto updatedUser = userService.changeGhost(userNo);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.UPDATE_USER);
		response.setMessage(ResponseMessage.UPDATE_USER);
		response.setData(updatedUser);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	/**
	 * 회원의 역할을 USER로 변경하는 API
	 * 
	 * @param userEmail 회원 이메일
	 * @param userHp    회원 전화번호
	 * @return 역할 변경 결과를 담은 ResponseEntity
	 */
	@Operation(summary = "복구(role변경)", description = "복구하면 role이 user로 바뀜")
	@PutMapping("/role/user")
	public ResponseEntity<Response> changeUserRoleToGhost(@RequestParam(name = "userEmail") String userEmail,
			@RequestParam(name = "userHp") String userHp) {
		UserDto updatedUser = userService.changeUser(userEmail, userHp);
		Response response = new Response();
		response.setStatus(ResponseStatusCode.UPDATE_USER);
		response.setMessage(ResponseMessage.UPDATE_USER);
		response.setData(updatedUser);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	/**
	 * 접근 금지 예외를 처리하는 API
	 * 
	 * @return 접근 금지 메시지를 담은 ResponseEntity
	 */
	@GetMapping("/exception")
	public ResponseEntity<String> handleAccessDenied() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근이 금지되었습니다.");
	}
}
