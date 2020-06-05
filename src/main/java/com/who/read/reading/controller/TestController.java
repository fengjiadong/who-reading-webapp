package com.who.read.reading.controller;

import com.who.read.reading.entity.Role;
import com.who.read.reading.entity.User;
import com.who.read.reading.service.EntityService;
import com.who.read.reading.utils.Options;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.datamodel.EntityCondition;
import com.who.read.reading.who.manager.EntityManager;
import com.who.read.reading.who.manager.UserSystemManager;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname TestController
 * @date 2020/3/25 2:56 PM
 * @Created by fengjiadong
 */
@RestController
public class TestController {
	@RequestMapping("/Hh")
	public ModelAndView sayHelloH() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("hello");
		modelAndView.addObject("key", 12345);
		return modelAndView;
	}

	@RequestMapping("/Hi")
	public String sayHello() {
		System.out.println("hello");
		return "hello";
	}

	@RequestMapping("/static")
	public String sayHelloS() {
		System.out.println("hello.html");
		return "redirect:/hello.html";
	}

	@Autowired
	EntityService entityService;

	@Autowired
	UserSystemManager userSystemManager;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	EntityManager entityManager;


}
