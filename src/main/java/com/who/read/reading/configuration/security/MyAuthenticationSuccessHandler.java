package com.who.read.reading.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.who.read.reading.entity.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登陆成功逻辑
 * @Classname MyAuthenticationSuccessHandler
 * @date 2020/5/29 5:48 PM
 * @Created by fengjiadong
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		// 默认打印出登陆信息
//        httpServletResponse.setContentType("application/json;charset=utf-8");
//        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
		// 跳转访问页面
//		SavedRequest savedRequest = requestCache.getRequest(request, response);
//		redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, savedRequest.getRedirectUrl());
		// 跳转制定页面
//        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
//        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "index.html");

		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=utf-8");
		JSONObject result = new JSONObject().put("msg", "登录成功！").put("code", "200");
		result.put("redirect", "/index.html");
		MyUser user = (MyUser) authentication.getPrincipal();
		result.put("result", user.getUser().getJson());
		System.out.println(result.toString());
		response.getWriter().write(result.toString());
	}
}
