package com.who.read.reading.controller;

import com.who.read.reading.entity.User;
import com.who.read.reading.service.UserService;
import com.who.read.reading.utils.ServiceUtils;
import com.who.read.reading.utils.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Classname LoginController
 * @date 2020/3/26 5:39 PM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("")
public class LoginController {
	@Autowired
	UserService userService;

	@RequestMapping("/login")
	public Object login(String name ,String password,HttpSession session){
		System.out.println("进入login方法");
		List<User> user = userService.getUserByName("冯家栋");
		session.setAttribute(WebSecurityConfig.SESSION_KEY,name);
		Object attribute = session.getAttribute(WebSecurityConfig.SESSION_KEY);
		System.out.println("--"+attribute);
		return ServiceUtils.returnRestlt("1", "登录成功。", user.get(0)).toString();
	}

	@RequestMapping("/logged")
	public Object logged() {
		System.out.println("LoginController:logged");
		return ServiceUtils.returnRestlt("403", "未登录，请登录后再尝试。", "/login");
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		// 移除session
		session.removeAttribute(WebSecurityConfig.SESSION_KEY);
		return "redirect:/login";
	}


}
