package com.who.read.reading.controller;

import com.who.read.reading.who.manager.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ActionService
 * @date 2020/6/23 1:26 PM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/api/entity")
public class ActionController {
	@Autowired
	EntityManager entityManager;

}
