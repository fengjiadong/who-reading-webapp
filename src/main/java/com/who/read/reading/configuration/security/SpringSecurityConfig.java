package com.who.read.reading.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @Classname SpringSecurityConfig
 * @date 2020/5/29 4:59 PM
 * @Created by fengjiadong
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	MyAuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	MyAuthenticationFailureHandler authenticationFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.formLogin() // 表单登录
//				.loginPage("/login")       // 登录跳转url
				.loginPage("/authentication/require")       // 登录跳转url
				.loginProcessingUrl("/login")   // 处理表单登录url
				.successHandler(authenticationSuccessHandler) // 自定义成功登录逻辑
				.failureHandler(authenticationFailureHandler) // 自定义登录失败逻辑
				.and()
				.authorizeRequests()            // 授权配置
				.antMatchers("/login", "/login.html", "/api/login", "/api/logged", "/authentication/require", "/css/**", "/js/**").permitAll()  // 无需认证
//				.antMatchers("/index.html").hasAnyRole(Options.Role_Admin)
				.anyRequest()                   // 所有请求
				.authenticated().and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login.html")
				.deleteCookies("JSESSIONID")
				// 都需要认证
				.and().csrf().disable();
	}

}
