package com.who.read.reading.service;

import com.who.read.reading.entity.Book;
import com.who.read.reading.entity.User;
import com.who.read.reading.mapper.BookMapper;
import com.who.read.reading.mapper.RoleMapper;
import com.who.read.reading.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname UserService
 * @date 2020/3/26 5:26 PM
 * @Created by fengjiadong
 */
@Service
public class UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	BookMapper bookMapper;

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	UserMapper userMapper;


	public List<User> getUserByUserName(String name) {
		LOGGER.info("UserService:getUserByName");
		List<User> userByName = userMapper.getUserByUserName(name);
		for (User user : userByName) {
			user.setRoles(roleMapper.getUserRole(userByName.get(0).getId()));
		}
		return userByName;
	}

	public List<User> selectUserList() {
		LOGGER.info("UserService:selectUserList");
		return userMapper.selectUserList();
	}

	public List<User> login(String userName, String password) {
		LOGGER.info("UserService:login");
		return userMapper.login(userName, password);
	}
}
