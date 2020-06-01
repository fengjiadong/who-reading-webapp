package com.who.read.reading.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登陆失败逻辑
 * @Classname MyAuthenticationFailureHandler
 * @date 2020/5/29 5:50 PM
 * @Created by fengjiadong
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=utf-8");
		String result = new JSONObject().put("msg", e.getMessage()).put("code","403").toString();
		response.getWriter().write(result);
	}
}
