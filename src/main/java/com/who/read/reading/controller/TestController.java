package com.who.read.reading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Classname TestController
 * @date 2020/3/25 2:56 PM
 * @Created by fengjiadong
 */
@Controller
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

}
