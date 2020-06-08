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

	// 普通用户得到菜单
	@RequestMapping("/allMenu")
	public Object allMenu(HttpSession session){
		Boolean isAdmin = UserSessionFactory.currentUser().hasRole(Options.Role_Admin);
		if(isAdmin){
			return menuService.allMenuManager(true);
		}
		return menuService.allMenu();
	}

	//管理员得到所有菜单
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/allMenuManager")
	public Object allMenuManager(HttpSession session){
		List<Menu> menus = menuService.allMenuManager();
		return menus;
	}

	// 创建一个菜单
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/create")
	public Object create(@PathParam("menu") Menu menu){
		if(menu.getName() == null || menu.getName().trim().equals("")){
			return ServiceUtils.returnMapRestlt("-1", "请输入菜单名称！", "");
		}
		Integer result = menuService.createMenu(menu);
		return ServiceUtils.returnMapRestlt("1", "新增成功！", result);
	}

	// 修改某个菜单
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/update")
	public Object update(@PathParam("menu") Menu menu){
		if(menu.getId() == null || "".equals(menu.getId().trim())){
			return ServiceUtils.returnMapRestlt("-1", "菜单Id不能为空", "");
		}
		if(menu.getName() == null || menu.getName().trim().equals("")){
			return ServiceUtils.returnMapRestlt("-1", "请输入菜单名称！", "");
		}
		Integer result = menuService.updateMenu(menu);
		return ServiceUtils.returnMapRestlt("1", "菜单信息更新成功！", result);
	}
	//updateMenu
	//获取一个菜单
	@RequestMapping("/getMenu/{id}")
	public Object getMenu(@PathVariable("id") String id){
		if(id == null || "".equals(id.trim())){
			return ServiceUtils.returnMapRestlt("1", "菜单Id不能为空！", "");
		}
		Menu menu = menuService.getMenuById(id);
		return ServiceUtils.returnMapRestlt("1", "查询成功！", menu);
	}
	// 删除一个菜单
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/delete/{id}")
	public Object delete(@PathVariable("id") String id){
		Integer menu = menuService.deleteMenu(id);
		if(menu > 0){
			return ServiceUtils.returnMapRestlt("1", "删除成功！", menu);
		}
		return ServiceUtils.returnMapRestlt("-1", "删除失败！", menu);
	}

	// 根据菜单得到菜单可见的角色
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/role/{id}")
	public Object getMenuRole(@PathVariable("id") String id){
		List<Role> menu = menuService.getMenuRoles(id);
		if(menu != null){
			return ServiceUtils.returnMapRestlt("1", "获取成功！", menu);
		}
		return ServiceUtils.returnMapRestlt("-1", "未找到对应的角色！", "");
	}
	// 给菜单添加角色
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/menuAddRole")
	public Object menuAddRole(@PathParam("menuId") String menuId,@PathParam("roleId") String roleId){
		Integer whetherMenuHasRole = menuService.whetherMenuHasRole(menuId, roleId);
		if(whetherMenuHasRole > 0 ){
			return ServiceUtils.returnMapRestlt("-1", "已添加该角色，不能重复添加！", "");
		}
		Integer integer = menuService.menuAddRole(menuId, roleId);
		if(integer > 0){
			return ServiceUtils.returnMapRestlt("1", "添加成功！", integer);
		}
		return ServiceUtils.returnMapRestlt("-1", "添加失败！", "");
	}

	// 给菜单添加角色
	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/menuRemoveRole")
	public Object menuRemoveRole(@PathParam("menuId") String menuId,@PathParam("roleId") String roleId){
		Integer whetherMenuHasRole = menuService.whetherMenuHasRole(menuId, roleId);
		if(whetherMenuHasRole < 1 ){
			return ServiceUtils.returnMapRestlt("-1", "角色已移除，不能重复移除。", "");
		}
		Integer integer = menuService.menuRemoveRole(menuId, roleId);
		if(integer > 0){
			return ServiceUtils.returnMapRestlt("1", "移除成功！", integer);
		}
		return ServiceUtils.returnMapRestlt("-1", "移除失败！", "");
	}



}
