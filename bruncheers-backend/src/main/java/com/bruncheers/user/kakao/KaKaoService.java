package com.bruncheers.user.kakao;

import java.io.IOException;
import java.net.URI;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.bruncheers.config.JwtTokenProvider;
import com.bruncheers.user.dto.UserDto;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.enums.Role;
import com.bruncheers.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class KaKaoService {
	/***************************************************
	 * 인가코드로 토큰받기
	 *****************************************************/
	@Value("${api.kakao.rest_api_key}")
	private String kakaoRestApiKey;

	@Value("${api.kakao.javascript_key}")
	private String kakaoJavascriptApiKey;

	@Value("${api.kakao.redirect_url}")
	private String redirect_url;

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	
	public JSONObject getToken(String code) throws IOException {
		String kakaoAuthUrl = "https://kauth.kakao.com";
		String reqUrl = "/oauth/token";
		String accessToken = "";

		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(kakaoAuthUrl + reqUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Accept", "application/json");
		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		parameters.set("grant_type", "authorization_code");
		parameters.set("client_id", kakaoRestApiKey);
		parameters.set("redirect_uri", redirect_url);
		parameters.set("code", code);
		HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
		//http요청
		ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
		JSONObject responseBody = apiResponse.getBody();
		return responseBody;
	}

	/*********************************************
	 * 카카오아이디얻기
	 *********************************************/
	public String getKakaoId(String accessToken) throws Exception {
		String kakaoUniqueNo = "";
		String kakaoApiUrl = "https://kapi.kakao.com";
		// restTemplate을 사용하여 API 호출
		RestTemplate restTemplate = new RestTemplate();
		String reqUrl = "/v2/user/me";
		URI uri = URI.create(kakaoApiUrl + reqUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "bearer " + accessToken);
		headers.set("KakaoAK", kakaoRestApiKey);

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();

		HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
		ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
		JSONObject responseBody = apiResponse.getBody();
		kakaoUniqueNo = (Long) responseBody.get("id") + "";
		return kakaoUniqueNo;
	}

	/*********************************************
	 * 카카오유저정보얻기
	 *********************************************/
	public KakaoProfile getKakaoProfile(String accessToken) throws Exception {
		String kakaoApiUrl = "https://kapi.kakao.com";

		RestTemplate restTemplate = new RestTemplate();
		String reqUrl = "/v2/user/me";
		URI uri = URI.create(kakaoApiUrl + reqUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "bearer " + accessToken);
		headers.set("KakaoAK", kakaoRestApiKey);
		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);
		ResponseEntity<String> kakaoResponseEntity = restTemplate.postForEntity(uri, entity, String.class);

		String kakaoProfileStr = kakaoResponseEntity.getBody();
		System.out.println(">>12313>" + kakaoProfileStr);

		ObjectMapper objectMapper = new ObjectMapper();
		KakaoProfile kakaoProfile = objectMapper.readValue(kakaoProfileStr, KakaoProfile.class);
	
		return kakaoProfile;
	}

	public UserDto saveUser(KakaoProfile kakaoProfile, String code) throws IOException {
		// 이메일을 기준으로 데이터베이스에서 사용자 정보를 찾습니다.
		String email = kakaoProfile.getKakao_account().getEmail();
		User existingUser = userRepository.findByUserEmail(email).orElse(null);
		// 이미 가입된 사용자인지 확인합니다.
		if (existingUser != null) {
			String token = jwtTokenProvider.createToken(email, Role.USER);
			existingUser.setToken(token);
			// 이미 가입된 사용자라면 아무런 작업을 하지 않고 기존 사용자 정보를 반환합니다.
			return UserDto.toDto(existingUser);
		} else {
			// 가입되지 않은 사용자라면 새로운 사용자 정보를 저장합니다.
			User newUser = new User();
			String token = jwtTokenProvider.createToken(email, Role.USER);
			newUser.setUserEmail(email);
			newUser.setUserName(kakaoProfile.getKakao_account().getName());
			newUser.setUserNickname(kakaoProfile.getKakao_account().getProfile().getNickname());
			newUser.setUserGender (kakaoProfile.getKakao_account().getGender());     
			newUser.setUserHp(kakaoProfile.getKakao_account().getPhone_number());
			newUser.setUserBirth(kakaoProfile.getKakao_account().getBirthday());
			newUser.setRole(Role.USER);
			newUser.setToken(token);
			User savedUser = userRepository.save(newUser);
			return UserDto.toDto(savedUser);
			
			
		}
	}

}
