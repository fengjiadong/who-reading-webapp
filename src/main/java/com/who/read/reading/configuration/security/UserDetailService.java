package com.who.read.reading.configuration.security;

import com.who.read.reading.entity.Role;
import com.who.read.reading.entity.User;
import com.who.read.reading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @Classname UserDetailService
 * @date 2020/5/29 5:19 PM
 * @Created by fengjiadong
 */
@Configuration
public class UserDetailService implements UserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> userByName = userService.getUserByUserName(username);
		if (userByName.isEmpty()) {
			return new MyUser(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("visitors"));
		}
		User user1 = userByName.get(0);
		List<Role> roles = user1.getRoles();
		StringBuilder roleGranted = new StringBuilder();
		for (Role role : roles) {
			System.out.println(role.getName());
			roleGranted.append("ROLE_"+role.getId() + ",");
		}
		List<GrantedAuthority> admin = AuthorityUtils.commaSeparatedStringToAuthorityList(roleGranted.toString());
		// 从数据库取值登录
		MyUser user = new MyUser(username, user1.getPassword(), admin);
		user.setUser(user1);
		return user;
	}
}
