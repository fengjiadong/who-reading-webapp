package com.who.read.reading.controller;

import com.who.read.reading.entity.User;
import com.who.read.reading.service.UserService;
import com.who.read.reading.configuration.JsonManager;
import com.who.read.reading.configuration.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname LoginController
 * @date 2020/3/26 5:39 PM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/api")
public class LoginController {
	@Autowired
	UserService userService;
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/login")
	public Object login(String userName ,String password,HttpSession session){
		LOGGER.info("LoginController:login");
		List<User> userList = userService.login(userName, password);
		if (userList == null || userList.isEmpty()) {
			return ServiceUtils.returnRestlt("0", "账号或密码不正确。", "账号或密码不正确。").toString();
		}
		User user = userList.get(0);
		session.setAttribute("userName", user.getName());
		Object attribute = session.getAttribute("userName");
		LOGGER.info("login-" + attribute);
		List<String> filed = new ArrayList<>();
		filed.add("password");
		filed.add("phone");
		filed.add("email");
		filed.add("idNumber");
		return ServiceUtils.returnRestlt("1", "登录成功。", JsonManager.getJsonExcludeField(user,filed)).toString();
	}

	@RequestMapping("/logged")
	public Object logged(HttpSession session) {
		LOGGER.info("LoginController:logged");
		Object userName = session.getAttribute("userName");
		if (userName == null) {
			return ServiceUtils.returnRestlt("403", "未登录，请登录后再尝试。", "/login").toString();
		}else{
			return ServiceUtils.returnRestlt("1", "已登录。", userName).toString();
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request) {
		// 移除session
		LOGGER.info("LoginController:logout");
		session.removeAttribute("userName");
		String contentType = request.getAuthType();
		System.out.println(contentType);
		return ServiceUtils.returnRestlt("1", "退出登录成功。","退出登录成功！").toString();
	}


}
