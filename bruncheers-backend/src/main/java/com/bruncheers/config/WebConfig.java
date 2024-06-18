
package com.bruncheers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Spring의 WebMvcConfigurer 인터페이스를 구현하여 CORS 설정을 구성하는 클래스
@Configuration
public class WebConfig implements WebMvcConfigurer {

 // CORS 매핑을 추가하는 메서드
 @Override
 public void addCorsMappings(CorsRegistry registry) {

     // 모든 경로에 대해 CORS 설정을 추가
     registry.addMapping("/**")
             // 모든 출처(origin)를 허용
             .allowedOrigins("*")
             // 허용되는 HTTP 메서드 지정
             .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
             // 브라우저의 preflight 요청에 대한 캐시 유효 기간(초 단위)
             .maxAge(300)
             // 허용되는 헤더를 지정
             .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
 }
}