package com.who.read.reading.service;

import com.who.read.reading.entity.Book;
import com.who.read.reading.entity.User;
import com.who.read.reading.mapper.BookMapper;
import com.who.read.reading.mapper.RoleMapper;
import com.who.read.reading.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
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
			user.setRoles(roleMapper.getUserRole(user.getId()));
		}
		return userByName;
	}

	// 模糊查询
	public List<User> queryUserByName(String name,Integer pageNo, Integer pageSize) {
		LOGGER.info("UserService:queryUserByName");
		Integer start = (pageNo - 1) * pageSize;
		List<User> userByName = userMapper.queryUserByName("%" + name + "%",start,pageSize);
		for (User user : userByName) {
			user.setRoles(roleMapper.getUserRole(user.getId()));
		}
		return userByName;
	}

	public List<User> selectUserList(Integer pageNo, Integer pageSize) {
		LOGGER.info("UserService:selectUserList");
		Integer start = (pageNo - 1) * pageSize;

		List<User> users = userMapper.selectUserList(start,pageSize);
		for (User user : users) {
			user.setRoles(roleMapper.getUserRole(user.getId()));
		}
		return users;
	}

	public List<User> login(String userName, String password) {
		LOGGER.info("UserService:login");
		return userMapper.login(userName, password);
	}

	public Integer selectUserListCount() {
		return userMapper.selectUserListCount();
	}
	public Integer queryUserByNameCount(String name) {
		return userMapper.queryUserByNameCount(name);
	}
}
