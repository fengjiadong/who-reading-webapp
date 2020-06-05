package com.who.read.reading.controller;

import com.who.read.reading.entity.Role;
import com.who.read.reading.entity.User;
import com.who.read.reading.service.MenuService;
import com.who.read.reading.service.UserService;
import com.who.read.reading.utils.Options;
import com.who.read.reading.who.datamodel.Menu;
import com.who.read.reading.who.util.UserSessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Classname WhoReadingController
 * @date 2020/3/25 2:39 PM
 * @Created by fengjiadong
 */
@Controller
public class WhoReadingController {


	@Autowired
	UserService userService;

	@Autowired
	MenuService menuService;

	@RequestMapping("/login.html")
	public String login() {
		return "login";
	}

	//	@PreAuthorize("hasAuthority('"+Options.Role_Admin +"')")
	@RequestMapping("/index.html")
	public String index(HttpServletRequest request) {
		List<Menu> system = menuService.getMenuBySite("system");
		request.getSession().setAttribute("systemMenu", system);
		return "index/index";
	}

	@RequestMapping("/myInfo.html")
	public String myInfo( HttpServletRequest request) {
		User user = UserSessionFactory.currentUser();
		request.getSession().setAttribute("user", user);
		List<Role> roles = user.getRoles();
		request.getSession().setAttribute("roles", roles);
		return "index/my-info";
	}

	@PreAuthorize("hasAuthority('" + Options.Role_Admin + "')")
	@RequestMapping("/menu.html")
	public String menu(HttpServletRequest request) {
		return "index/menu/menu";
	}

	@PreAuthorize("hasAuthority('" + Options.Role_Admin + "')")
	@RequestMapping("/user.html")
	public String user(HttpServletRequest request) {
		String name = request.getParameter("name");
		String pageNo = request.getParameter("pageNo") == null ? "1" : request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize") == null ? "10" : request.getParameter("pageSize");
		List<User> users = null;
		Integer count = 0;
		if (name != null && !"".equals(name.trim())) {
			users = userService.queryUserByName(name, Integer.valueOf(pageNo), Integer.valueOf(pageSize));
			count = userService.queryUserByNameCount(name);
		} else {
			users = userService.selectUserList(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
			count = userService.selectUserListCount();
		}
		Integer pageCount = (count / Integer.valueOf(pageSize)) + (count % Integer.valueOf(pageSize) > 0 ? 1 : 0);
		request.getSession().setAttribute("users", users);
		request.getSession().setAttribute("pageSize", pageSize);
		request.getSession().setAttribute("count", count);
		request.getSession().setAttribute("pageNo", Integer.valueOf(pageNo));
		request.getSession().setAttribute("pageCount", pageCount );
		request.getSession().setAttribute("userSize", users.size());
		request.getSession().setAttribute("name", name);

		List<Integer> pageArr = new ArrayList<>();
		for (Integer integer = Integer.valueOf(pageNo)-3<1?1:Integer.valueOf(pageNo)-3; integer <= Integer.valueOf(pageNo); integer++) {
			pageArr.add(integer);
		}
		for (Integer i = Integer.parseInt(pageNo)+1; i <= pageCount&& i < Integer.parseInt(pageNo)+3; i++) {
			pageArr.add(i);
		}
		request.getSession().setAttribute("pageArr", pageArr);
		return "index/user/user";
	}

	@RequestMapping("/hello.html")
	public String hello() {
		return "hello";
	}
}
