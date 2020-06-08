package com.who.read.reading.controller;

import com.who.read.reading.configuration.ServiceUtils;
import com.who.read.reading.entity.Role;
import com.who.read.reading.service.RoleService;
import com.who.read.reading.utils.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @Classname RoleController
 * @date 2020/6/5 6:20 PM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	RoleService roleService;

	//得到所有角色
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/allRole")
	public Object allRole(HttpSession session) {
		List<Role> roles = roleService.allRole();
		return roles;
	}

	//根据关键字得到所有角色
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/allRoleByKey")
	public Object allRoleByKey(HttpServletRequest request) {
		String key = request.getParameter("key");
		List<Role> roles = roleService.allRoleByKey(key);
		return roles;
	}

	//创建角色
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/create")
	public Object createRole(@PathParam("role") Role role) {
		if (role.getName() == null || role.getName().trim().equals("") ||
				role.getDisplayName() == null || role.getDisplayName().trim().equals("")) {
			return ServiceUtils.returnMapRestlt("-1", "请输入角色名称或显示名称！", "");
		}
		Integer result = roleService.createRole(role);
		return ServiceUtils.returnMapRestlt("1", "角色创建成功！", result);
	}

	//修改角色
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/update")
	public Object updateRole(@PathParam("role") Role role) {
		if (role.getId() == null || role.getId().trim().equals("")) {
			return ServiceUtils.returnMapRestlt("-1", "角色Id不能为空！", "");
		}
		if (role.getName() == null || role.getName().trim().equals("") ||
				role.getDisplayName() == null || role.getDisplayName().trim().equals("")) {
			return ServiceUtils.returnMapRestlt("-1", "请输入角色名称或显示名称！", "");
		}
		Integer result = roleService.createRole(role);
		return ServiceUtils.returnMapRestlt("1", "角色信息更新成功！", result);
	}
}
