package com.who.read.reading.who.manager;

import com.who.read.reading.entity.Role;
import com.who.read.reading.entity.User;
import com.who.read.reading.service.EntityService;
import com.who.read.reading.who.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname UserSystemManager
 * @date 2020/6/3 2:06 PM
 * @Created by fengjiadong
 */
@Component
public class UserSystemManager {

	@Autowired
	EntityService entityService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String createUser(User user) {
		if (user.getId() == null) {
			user.setId(UUID.generateUUID());
		}
		if (user.getUserName() == null || user.getUserName().length() < 6) {
			return "请设置正确的userName";
		}
		if (get(user.getPassword()).toString().length() < 6) {
			return "请设置正确的密码";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		StringBuilder sb = new StringBuilder();
		sb.append("insert into `dm.user` (`password`,`img`,`gender`,`phone`,`name`,`id`,`userName`,`idNumber`,`age`,`email`) " +
				"values ('" + get(user.getPassword()) + "','" + get(user.getImg()) + "','" + get(user.getGender()) + "','"
				+ get(user.getPhone()) + "','" + get(user.getName()) + "','" + get(user.getId()) + "','" + get(user.getUserName()) + "','" + get(user.getIdNumber()) + "','" + get(user.getAge()) + "','" + get(user.getEmail()) + "')");
		Integer result = entityService.create(sb.toString());
		if (result < 1) {
			return "创建失败";
		}

		List<Role> roles = user.getRoles();
		for (Role role : roles) {
			String roleSql = "insert into `dm.user_role` (`parentId`,`roleId`,`id`) values('" + get(user.getId()) + "','" + get(role.getId()) + "','" + UUID.generateUUID() + "')";
			entityService.create(roleSql);
		}
		return user.getId();
	}

	public Object get(Object o) {
		if (o == null) {
			return "";
		}
		return o;
	}
}
