package com.bruncheers.user.kakao;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruncheers.user.dto.UserDto;
import com.bruncheers.user.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class KaKaoController {
	@Value("${api.kakao.rest_api_key}")
	private String client_id;
	
	@Value("${api.kakao.redirect_url}")
	private String redirect_url;
	
	@Value("${api.kakao.logout_redirect_uri}")
	private String logout_redirect_uri;
	@Autowired
	private KaKaoService kakaoService;
	@Autowired
	private UserService userService;
   // kakao_main.html 페이지보여주기
    
	@GetMapping("/kakao_main")
	@ResponseBody
	public Map<String, String> kakao_main() {
	Map<String, String> response = new HashMap<>();
	response.put("client_id", client_id);
	response.put("redirect_url", redirect_url);
	response.put("logout_redirect_uri", logout_redirect_uri);
	return response;
	}
	
	
   // - redirection url로 등록된 요청
   // - kakao가 발행한 code를 파라메타로 붙혀서 kakao가 호출해준다.
   // - ajax방식으로는 불가능하다.
     
	

	 
	@GetMapping(value = "/kakao_login_action")
	public String  kakao_login_action(@RequestParam(value = "code", required = false) String code,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> userInfo = new HashMap<>();
		JSONObject tokenObject= kakaoService.getToken(code);
		String authorize_access_token=(String)tokenObject.get("access_token");
		int refresh_token_expires_in=(Integer)tokenObject.get("refresh_token_expires_in");
		String refresh_token=(String)tokenObject.get("refresh_token");
		
   		
		KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(authorize_access_token);
   		System.out.println("************************************");
   		System.out.println("<<< 이미가입한사용자라면 로그인진행 >>>");
   		System.out.println("<<< 미가입사용자라면 회원가입진행   >>>");
   		UserDto kakaoUser = kakaoService.saveUser(kakaoProfile,authorize_access_token);
		
   		System.out.println(">>>>>>>>>>>"+kakaoUser);
        
         Cookie authorize_access_token_cookie=new Cookie("authorize_access_token", authorize_access_token);
         Cookie refresh_token_expires_in_cookie=new Cookie("refresh_token_expires_in", refresh_token_expires_in+"");
         Cookie refresh_token_cookie=new Cookie("refresh_token", refresh_token);
         response.addCookie(authorize_access_token_cookie);
         response.addCookie(refresh_token_expires_in_cookie);
         response.addCookie(refresh_token_cookie);
         request.setAttribute("kakaoProfile", kakaoProfile);
         request.setAttribute("authorize_access_token", authorize_access_token);
         request.setAttribute("refresh_token_expires_in", refresh_token_expires_in);
         request.setAttribute("refresh_token", refresh_token);
         request.setAttribute("client_id",client_id);
         request.setAttribute("redirect_url",redirect_url);
         request.setAttribute("logout_redirect_uri",logout_redirect_uri);
         return "redirect:http://192.168.15.6:3000/main";
	}	
	
	
	@ResponseBody
	@GetMapping("/api/kakao_userinfo_with_token")
	public UserDto getKakaoUserInfoWithToken(@RequestParam(name ="authorize_access_token" )  String authorize_access_token, HttpSession session) throws Exception {
		System.out.println(authorize_access_token);
		KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(authorize_access_token);
		UserDto kakaoUser=userService.findUserByEmail(kakaoProfile.getKakao_account().getEmail());
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>요청성공");
		return kakaoUser;
		
		
	}
}