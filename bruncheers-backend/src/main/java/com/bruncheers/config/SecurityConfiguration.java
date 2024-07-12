package com.bruncheers.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.bruncheers.user.enums.Role;
import com.bruncheers.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 어플리케이션의 보안 설정
 */
@Configuration
// Spring Security에 대한 디버깅 모드를 사용하기 위한 어노테이션 (default : false)
@EnableWebSecurity(debug = true)
@PropertySource("jwt.properties")
public class SecurityConfiguration {
	/**
	 * Swagger 페이지 접근에 대한 예외 처리
	 *
	 * @param webSecurity
	 */

	@Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
	
	public static final String[] SwaggerPatterns = { "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**",
			"/v3/api-docs", "/swagger-ui.html" };

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public SecurityConfiguration(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userRepository = userRepository;
	}

	@Bean
	public BCryptPasswordEncoder encoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic((httpBasicConfig) -> {
			// REST API는 UI를 사용하지 않으므로 기본설정을 비활성화
			httpBasicConfig.disable();
		});
		httpSecurity.csrf((csrfConfig) -> {
			// REST API는 csrf 보안이 필요 없으므로 비활성화
			csrfConfig.disable();
		});
		httpSecurity.sessionManagement((sessionManagementConfig) -> {
			// JWT Token 인증방식으로 세션은 필요 없으므로 비활성화
			sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		});
		// 어드민전용 페이지 역할선정
		httpSecurity.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
				.requestMatchers("/productOption/pono/**").hasAuthority(Role.ADMIN.name())			
				.anyRequest().permitAll());

		httpSecurity.exceptionHandling((exceptionHandlingConfig) -> {
			exceptionHandlingConfig.accessDeniedHandler(new CustomAccessDeniedHandler());
			exceptionHandlingConfig.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		});
		// JWT Token 필터를 id/password 인증 필터 이전에 추가
		httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
				UsernamePasswordAuthenticationFilter.class);
		httpSecurity.exceptionHandling((exceptionHandling) ->
				exceptionHandling
						.accessDeniedPage("/http://localhost:3000/error"));

		return httpSecurity.build();
	}

	/**
	 * Swagger 페이지 접근에 대한 예외 처리
	 */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (webSecurity) -> {
			webSecurity.ignoring().requestMatchers(SwaggerPatterns);
			webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		};
	}

}