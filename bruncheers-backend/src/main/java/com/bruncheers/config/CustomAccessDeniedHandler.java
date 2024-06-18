package com.bruncheers.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 권한이 없는 예외가 발생했을 경우 핸들링하는 클래스
 */
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
			throws IOException {

		log.atDebug();
		LOGGER.info("[handle] 접근이 막혔을 경우 경로 리다이렉트");
		response.sendRedirect("http://localhost:3000/error");
	}
}