package com.who.read.reading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname WhoReadingController
 * @date 2020/3/25 2:39 PM
 * @Created by fengjiadong
 */
@RestController
public class WhoReadingController {


	@RequestMapping("/map")
	public Map<String,Object> map(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("key", "123");
		return map;
	}
}
