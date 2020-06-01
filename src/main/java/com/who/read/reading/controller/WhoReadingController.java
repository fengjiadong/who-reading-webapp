package com.who.read.reading.controller;

import com.who.read.reading.entity.Role;
import com.who.read.reading.entity.User;
import com.who.read.reading.utils.Options;
import com.who.read.reading.who.util.UserSessionFactory;
import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Classname WhoReadingController
 * @date 2020/3/25 2:39 PM
 * @Created by fengjiadong
 */
@Controller
public class WhoReadingController {

	@RequestMapping("/login.html")
	public String login() {
		return "login";
	}

	@PreAuthorize("hasAuthority('"+Options.Role_Admin +"')")
	@RequestMapping("/index.html")
	public String index() {
		return "index/index";
	}

	@RequestMapping("/myInfo.html")
	public String myInfo(Map<String, Object> paramMap, HttpServletRequest request) {
		User user = UserSessionFactory.currentUser();
		request.getSession().setAttribute("user", user);
		List<Role> roles = user.getRoles();
		request.getSession().setAttribute("roles", roles);
		return "index/my-info";
	}


	@RequestMapping("/hello.html")
	public String hello() {
		return "hello";
	}
}
