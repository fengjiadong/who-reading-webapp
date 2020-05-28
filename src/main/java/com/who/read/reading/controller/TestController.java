package com.who.read.reading.controller;

import com.who.read.reading.service.EntityService;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.datamodel.EntityCondition;
import com.who.read.reading.who.manager.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
	EntityManager entityManager;
	@GetMapping(value = "/entity" ,produces = "application/json; charset=utf-8")
	public List<Entity> entity() {
//		EntityCondition entityCondition = new EntityCondition("95f791ca9b62462a9d8bc12215bb634d");
		EntityCondition entityCondition = new EntityCondition("91f791ca9b62462a9d8bc12215bb634d");
//		entityCondition.setProperty("type", "1");
//		entityCondition.setId("209");
//		entityCondition.setAddId("194");
		List<Entity> list = entityManager.list(entityCondition);
		for (Entity entity : list) {
			System.out.println(entity.getId());
		}
		return list;
	}

}
