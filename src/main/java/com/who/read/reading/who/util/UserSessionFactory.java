package com.who.read.reading.who.util;

import com.who.read.reading.configuration.security.MyUser;
import com.who.read.reading.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Classname UserSessionFactory
 * @date 2020/5/29 3:51 PM
 * @Created by fengjiadong
 */
public class UserSessionFactory {
	private UserSessionFactory() {
	}

	public static User currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MyUser principal = (MyUser) authentication.getPrincipal();
		return principal.getUser();
	}

	public static boolean isUserSession() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}
}
