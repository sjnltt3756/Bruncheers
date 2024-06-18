
package com.bruncheers.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bruncheers.user.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * JWT 토큰을 생성하고 유효성을 검증하는 컴포넌트 클래스 JWT 는 여러 암호화 알고리즘을 제공하고 알고리즘과 비밀키를 가지고 토큰을 생성
 * claim 정보에는 토큰에 부가적으로 정보를 추가할 수 있음 claim 정보에 회원을 구분할 수 있는 값을 세팅하였다가 토큰이 들어오면
 * 해당 값으로 회원을 구분하여 리소스제공 JWT 토큰에 expire time을 설정할 수 있음
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
	private final UserDetailsService userDetailsService; // Spring Security 에서 제공하는 서비스 레이어

	@Value("${jwt.secret}")
	private String secretKey = "secretKey";
	private final long tokenValidMillisecond = 1000L * 60 * 60 * 100; // 100시간 토큰 유효
	private final long refreshTokenValidMillisecond = 1000L * 60 * 60*24; // 24시간 토큰 유효

	/**
	 * SecretKey 에 대해 인코딩 수행
	 */
	@PostConstruct
	protected void init() {
		LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
		System.out.println(secretKey);
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
		System.out.println(secretKey);
		LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
	}
	
//	// JWT 리프레시 토큰 생성
//	public String createRefreshToken(String userUid) {
//	    LOGGER.info("[createRefreshToken] 리프레시 토큰 생성 시작");
//	    Claims claims = Jwts.claims().setSubject(userUid);
//	    Date now = new Date();
//	    String refreshToken = Jwts.builder()
//	            .setClaims(claims)
//	            .setIssuedAt(now)
//	            .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond))
//	            .signWith(SignatureAlgorithm.HS256, secretKey)
//	            .compact();
//	    LOGGER.info("[createRefreshToken] 리프레시 토큰 생성 완료");
//	    return refreshToken;
//	}
//
//	// 리프레시 토큰으로 사용자 ID 조회
//	public String getRefreshTokenUserId(String refreshToken) {
//	    LOGGER.info("[getRefreshTokenUserId] 리프레시 토큰에서 사용자 ID 추출 시작");
//	    String userId = Jwts.parser()
//	            .setSigningKey(secretKey)
//	            .parseClaimsJws(refreshToken)
//	            .getBody()
//	            .getSubject();
//	    LOGGER.info("[getRefreshTokenUserId] 리프레시 토큰에서 사용자 ID 추출 완료: {}", userId);
//	    return userId;
//	}
	
	// JWT 토큰 생성
	public String createToken(String userUid, Role roles) {
		LOGGER.info("[createToken] 토큰 생성 시작");
		Claims claims = Jwts.claims().setSubject(userUid);
		claims.put("roles", roles);
		Date now = new Date();
		String token = Jwts.builder().setClaims(claims).setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + tokenValidMillisecond))
				.signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
				.compact();

		String bearerToken = token;

		LOGGER.info("[createToken] 토큰 생성 완료");
		return bearerToken;
	}

	// JWT 토큰으로 인증 정보 조회
	public Authentication getAuthentication(String token) {
		LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
		LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}", userDetails.getUsername());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	// JWT 토큰에서 회원 구별 정보 추출
	public String getUsername(String token) {
		LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
		String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

		// Jws<Claims> claims =
		// Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		Claims claim = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		LOGGER.info("id:{}", claim.getId());
		LOGGER.info("issuer:" + claim.getIssuer());
		LOGGER.info("issue:" + claim.getIssuedAt().toString());
		LOGGER.info("subject:" + claim.getSubject());
		LOGGER.info("Audience:" + claim.getAudience());
		LOGGER.info("expire:" + claim.getExpiration().toString());

		LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
		return info;
	}

	/**
	 * HTTP Request Header 에 설정된 토큰 값을 가져옴
	 *
	 * @param request Http Request Header
	 * @return String type Token 값
	 */
	public String resolveToken(HttpServletRequest request) {
		LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>token:" + bearerToken);
			return bearerToken.substring(7);
		}
		return "";
	}

	// JWT 토큰의 유효성 + 만료일 체크
	public boolean validateToken(String token) {
		try {
			LOGGER.info("[validateToken] 토큰 유효 체크 시작");
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			LOGGER.info("[validateToken] 토큰 유효 체크 완료");
			return true;
		} catch (SignatureException e) {
			LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생:{}", e);
			return false;
		} catch (MalformedJwtException e) {
			LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생:{}", e);
			return false;
		} catch (ExpiredJwtException e) {
			LOGGER.info("[validateToken] 토큰(만료) 유효 체크 예외 발생:{}", e);
			return false;
		} catch (UnsupportedJwtException e) {
			LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생:{}", e);
			return false;
		} catch (IllegalArgumentException e) {
			LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생:{}", e);
			return false;
		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생");
			return false;
		}
	}
}