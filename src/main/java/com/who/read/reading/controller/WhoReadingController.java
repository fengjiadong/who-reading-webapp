package com.who.read.reading.controller;

import com.who.read.reading.entity.Role;
import com.who.read.reading.entity.User;
import com.who.read.reading.service.MenuService;
import com.who.read.reading.service.RoleService;
import com.who.read.reading.service.UserService;
import com.who.read.reading.utils.Options;
import com.who.read.reading.who.datamodel.Menu;
import com.who.read.reading.who.util.UserSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname WhoReadingController
 * @date 2020/3/25 2:39 PM
 * @Created by fengjiadong
 */
@Controller
public class WhoReadingController {


	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleService roleService;

	@RequestMapping("/login.html")
	public String login() {
		return "login";
	}

	//	@PreAuthorize("hasAuthority('"+Options.Role_Admin +"')")
	@RequestMapping("/index.html")
	public String index(HttpServletRequest request) {
		Boolean isAdmin = UserSessionFactory.currentUser().hasRole(Options.Role_Admin);
		List<Menu> system = null;
		if (!isAdmin) {
			// 非管理员需要根据角色得到系统菜单
			system = menuService.getMenuBySiteAndRole("system", UserSessionFactory.currentUser().getId());
		} else {
			// 超级管理员可以得到所有的菜单
			system = menuService.getMenuBySite("system");
		}
		request.getSession().setAttribute("systemMenu", system);
		return "index/index";
	}

	@RequestMapping("/myInfo.html")
	public String myInfo(HttpServletRequest request) {
		User user = UserSessionFactory.currentUser();
		request.getSession().setAttribute("user", user);
		List<Role> roles = user.getRoles();
		request.getSession().setAttribute("roles", roles);
		return "index/my-info";
	}

	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/menu.html")
	public String menu(HttpServletRequest request) {
		return "index/menu/menu";
	}

	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/role.html")
	public String role(HttpServletRequest request) {
		String key = request.getParameter("key");
		List<Role> roles = roleService.allRoleByKey(key == null ? "" : key.trim());
		request.setAttribute("roles", roles);
		request.setAttribute("size", roles.size());
		request.setAttribute("key", key);
		return "index/role/role";
	}

	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
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
		request.setAttribute("users", users);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("pageNo", Integer.valueOf(pageNo));
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("userSize", users.size());
		request.setAttribute("name", name);

		List<Integer> pageArr = new ArrayList<>();
		for (Integer integer = Integer.valueOf(pageNo) - 3 < 1 ? 1 : Integer.valueOf(pageNo) - 3; integer <= Integer.valueOf(pageNo); integer++) {
			pageArr.add(integer);
		}
		for (Integer i = Integer.parseInt(pageNo) + 1; i <= pageCount && i < Integer.parseInt(pageNo) + 3; i++) {
			pageArr.add(i);
		}
		request.setAttribute("pageArr", pageArr);
		return "index/user/user";
	}

	@RequestMapping("/hello.html")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/dataModule.html")
	public String dataModule() {
		return "index/dm/dataModule";
	}
}
