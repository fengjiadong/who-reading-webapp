package com.who.read.reading.configuration.security;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 被拦截后进入该类
 * @Classname SecurityController
 * @date 2020/5/29 5:43 PM
 * @Created by fengjiadong
 */
@RestController
public class SecurityController {
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@GetMapping("/authentication/require")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
//			String url = savedRequest.getRedirectUrl();
			String type = request.getHeader("X-Requested-With");
			System.out.println("访问方式："+type);
			if(type==null || !type.contains("XMLHttpRequest")){
				redirectStrategy.sendRedirect(request, response, "/login.html");
			}
		}
		return new JSONObject().put("code", "401").put("msg","访问资源需要身份认证").toString();
	}
}
