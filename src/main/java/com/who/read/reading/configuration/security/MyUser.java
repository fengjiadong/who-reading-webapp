package com.who.read.reading.configuration.security;

import com.who.read.reading.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Classname MyUser
 * @date 2020/5/29 5:14 PM
 * @Created by fengjiadong
 */
//@Configuration
public class MyUser extends org.springframework.security.core.userdetails.User {

	private User user;

	public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public MyUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}


	//
//	public MyUser(List<GrantedAuthority> authorities) {
//		this.authorities = authorities;
//	}




	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
