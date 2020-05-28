package com.who.read.reading.controller;

import com.who.read.reading.entity.Book;
import com.who.read.reading.entity.User;
import com.who.read.reading.service.UserService;
import com.who.read.reading.utils.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		List<User> user = this.userService.getUserByName(name);
		if (user == null || user.isEmpty()) {
			return ServiceUtils.returnRestlt("0", "未查询到用户!", "").toString();
		}
		return ServiceUtils.returnRestlt("1", "查询到用户。", user).toString();
	}


}
