package com.who.read.reading.who.action;

import com.who.read.reading.controller.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname ActionService
 * @date 2020/6/23 1:25 PM
 * @Created by fengjiadong
 */
public class ActionService extends ActionController {

	@RequestMapping("search")
	public Object search(HttpServletRequest request){
		return null;
	}

}
