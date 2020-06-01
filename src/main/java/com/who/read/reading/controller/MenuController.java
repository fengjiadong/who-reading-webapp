package com.who.read.reading.controller;

import com.who.read.reading.service.MenuService;
import com.who.read.reading.who.datamodel.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Classname MenuController
 * @date 2020/5/29 2:19 PM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	MenuService menuService;

	@RequestMapping("/allMenu")
	public Object allMenu(HttpSession session){
		List<Menu> menus = menuService.allMenu();
		return menus;
	}
}
