package com.who.read.reading.controller;

import com.who.read.reading.configuration.ServiceUtils;
import com.who.read.reading.entity.Role;
import com.who.read.reading.service.MenuService;
import com.who.read.reading.utils.Options;
import com.who.read.reading.who.datamodel.Menu;
import com.who.read.reading.who.util.UserSessionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

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
		Boolean isAdmin = UserSessionFactory.currentUser().hasRole(Options.Role_Admin);
		if(isAdmin){
			return menuService.allMenuManager();
		}
		return menuService.allMenu();
	}


	@PreAuthorize("hasAuthority('" + Options.Role_Admin + "')")
	@RequestMapping("/allMenuManager")
	public Object allMenuManager(HttpSession session){
		List<Menu> menus = menuService.allMenuManager();
		return menus;
	}


	@PreAuthorize("hasAuthority('" + Options.Role_Admin + "')")
	@RequestMapping("/create")
	public Object create(@PathParam("menu") Menu menu){
		if(menu.getName() == null || menu.getAction() == null){
			return ServiceUtils.returnMapRestlt("-1", "参数不全！", "");
		}
		Integer result = menuService.createMenu(menu);
		return ServiceUtils.returnMapRestlt("1", "新增成功！", result);
	}


	@RequestMapping("/getMenu/{id}")
	public Object getMenu(@PathVariable("id") String id){
		Menu menu = menuService.getMenuById(id);
		return menu;
	}
	@PreAuthorize("hasAuthority('" + Options.Role_Admin + "')")
	@RequestMapping("/delete/{id}")
	public Object delete(@PathVariable("id") String id){
		Integer menu = menuService.deleteMenu(id);
		if(menu > 0){
			return ServiceUtils.returnMapRestlt("1", "删除成功！", menu);
		}
		return ServiceUtils.returnMapRestlt("-1", "删除失败！", menu);
	}


}
