package com.who.read.reading.controller;

import com.who.read.reading.entity.Role;
import com.who.read.reading.entity.User;
import com.who.read.reading.service.EntityService;
import com.who.read.reading.service.UserService;
import com.who.read.reading.configuration.ServiceUtils;
import com.who.read.reading.utils.Options;
import com.who.read.reading.who.manager.EntityManager;
import com.who.read.reading.who.manager.UserSystemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname UserController
 * @date 2020/3/26 5:28 PM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


	@Autowired
	UserService userService;

	@RequestMapping("getUser")
	public Object getBook(@RequestParam String name) {
		LOGGER.info("UserController:getBook");
		List<User> user = this.userService.getUserByUserName(name);
		if (user == null || user.isEmpty()) {
			return ServiceUtils.returnRestlt("0", "未查询到用户!", "").toString();
		}
		return ServiceUtils.returnRestlt("1", "查询到用户。", user).toString();
	}

	@Autowired
	EntityService entityService;

	@Autowired
	UserSystemManager userSystemManager;

	@Autowired
	EntityManager entityManager;

	@RequestMapping("entity")
	public Object entity() {
		User user = new User();
		user.setName("超级管理员");
		user.setAge(22);
		user.setUserName("administrator");
		user.setGender("2");
		user.setPassword("159874623");
		user.setEmail("xiaoxi1598@163.com");
		ArrayList<Role> roles = new ArrayList<>();
//		roles.add(new Role(Options.Role_Develop));
//		roles.add(new Role(Options.Role_Staff));
		roles.add(new Role(Options.Role_Admin));
		user.setRoles(roles);
		String result = userSystemManager.createUser(user);
		System.out.println("-------------"+result);
		return user.getJson();
	}

}
