package com.who.read.reading.service;

import com.who.read.reading.entity.Role;
import com.who.read.reading.mapper.MenuMapper;
import com.who.read.reading.mapper.RoleMapper;
import com.who.read.reading.who.datamodel.Menu;
import com.who.read.reading.who.util.UUID;
import com.who.read.reading.who.util.UserSessionFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname MenuService
 * @date 2020/5/29 2:14 PM
 * @Created by fengjiadong
 */
@Service
public class MenuService {
	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private RoleMapper roleMapper;

	public List<Menu> queryMenuByParent(String parentId) {
		String id = UserSessionFactory.currentUser().getId();
		Menu menu = getMenuById(parentId);
		return chidrens(menu, id);
	}

	public List<Menu> allMenu() {
		String id = UserSessionFactory.currentUser().getId();
		List<Menu> menus = menuMapper.allParentMenu(id);
		for (Menu menu : menus) {
			menu.setNodes(chidrens(menu, id));
		}
		return menus;
	}

	/**
	 * 菜单管理时查询使用本方法。
	 *
	 * @return
	 */
	public List<Menu> allMenuManager() {
		List<Menu> menus = menuMapper.allManagerParentMenu();
		for (Menu menu : menus) {
			menu.setNodes(chidrensByManager(menu));
		}
		return menus;
	}

	public List<Menu> allMenuManager(Boolean isAdmin) {
		List<Menu> menus = menuMapper.allManagerParentMenu();
		for (Menu menu : menus) {
			menu.setNodes(chidrensByAdmin(menu));
		}
		return menus;
	}

	public List<Menu> getMenuBySite(String site) {
		return this.menuMapper.getMenuBySite(site);
	}

	public List<Menu> getMenuBySiteAndRole(String site, String userId) {
		return this.menuMapper.getMenuBySiteAndRole(site, userId);
	}

	public Menu getMenuById(String id) {
		return menuMapper.getMenuById(id);
	}

	public List<Menu> getChildrensMenu(String parentId, String userId) {
		return menuMapper.getChildrensMenu(parentId, userId);
	}

	public List<Menu> getMenuByName(String name) {
		return menuMapper.getMenuByName(name);
	}

	/**
	 * 创建一个菜单
	 *
	 * @param menu
	 * @return
	 */
	public Integer createMenu(Menu menu) {
		if (menu.getId() == null || "".equals(menu.getId().trim())) {
			menu.setId(UUID.generateUUID());
		}
		Integer count = countByParentId(menu.getParentId());
		menu.setOrder(count);
		return menuMapper.createMenu(menu);
	}


	// updateMenu

	/**
	 * 修改菜单
	 *
	 * @param menu
	 * @return
	 */
	public Integer updateMenu(Menu menu) {
		return menuMapper.updateMenu(menu);
	}


	/**
	 * 删除某个菜单
	 *
	 * @param id
	 * @return
	 */
	public Integer deleteMenu(String id) {
		return menuMapper.deleteMenu(id);
	}

	/**
	 * 根据菜单Id获取菜单可见的角色
	 *
	 * @param id
	 * @return
	 */
	public List<Role> getMenuRoles(String id) {
		return roleMapper.getMenuRole(id);
	}

	/**
	 * 给菜单添加角色
	 *
	 * @param menuId 菜单Id
	 * @param roleId 角色Id
	 * @return
	 */
	public Integer menuAddRole(String menuId, String roleId) {
		return roleMapper.menuAddRole(menuId, roleId, UUID.generateUUID());
	}

	/**
	 * 判断菜单是否已存在该角色
	 *
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public Integer whetherMenuHasRole(String menuId, String roleId) {
		return roleMapper.whetherMenuHasRole(menuId, roleId);
	}

	/**
	 * 移除菜单的某个所见角色
	 *
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public Integer menuRemoveRole(String menuId, String roleId) {
		return roleMapper.menuRemoveRole(menuId, roleId);
	}

	/**
	 * 递归查询所有下级菜单，并根据是否有父菜单来设置可点击。
	 *
	 * @param menu
	 * @return
	 */
	private List<Menu> chidrens(Menu menu, String userId) {
		List<Menu> childrensMenus = menuMapper.getChildrensMenu(menu.getId(), userId);
		if (childrensMenus == null || childrensMenus.isEmpty()) {
			menu.setSelectable(true);
			return null;
		}
		menu.setSelectable(false);
		for (Menu childrensMenu : childrensMenus) {
			childrensMenu.setParentName(menu.getName());
			childrensMenu.setNodes(chidrens(childrensMenu, userId));
		}
		return childrensMenus;
	}

	/**
	 * 递归查询所有下级菜单,清除禁用和是否父菜单.
	 *
	 * @param menu
	 * @return
	 */
	private List<Menu> chidrensByManager(Menu menu) {
		menu.setDisabled(false);
		menu.setSelectable(true);
		List<Menu> childrensMenus = menuMapper.getManagerChildrensMenu(menu.getId());
		if (childrensMenus == null || childrensMenus.isEmpty()) {
			return null;
		}
		for (Menu childrensMenu : childrensMenus) {
			childrensMenu.setParentName(menu.getName());
			childrensMenu.setNodes(chidrensByManager(childrensMenu));
		}
		return childrensMenus;
	}

	/**
	 * 递归查询所有下级菜单，并根据是否有父菜单来设置可点击。
	 *
	 * @param menu
	 * @return
	 */
	public List<Menu> chidrensByAdmin(Menu menu) {
		List<Menu> childrensMenus = menuMapper.getManagerChildrensMenu(menu.getId());
		if (childrensMenus == null || childrensMenus.isEmpty()) {
			menu.setSelectable(true);
			return null;
		}
		menu.setSelectable(false);
		for (Menu childrensMenu : childrensMenus) {
			childrensMenu.setParentName(menu.getName());
			childrensMenu.setNodes(chidrensByAdmin(childrensMenu));
		}
		return childrensMenus;
	}

	/**
	 * 根据parentId得到下面的菜单数量
	 *
	 * @param parentId
	 * @return
	 */
	public Integer countByParentId(String parentId) {
		return menuMapper.countByParentId(parentId);
	}

	/**
	 * 移动菜单位置
	 *
	 * @param id
	 * @param type
	 * @return
	 */
	public Integer moveMenu(String id, String type) {
		String parentId = menuMapper.getParentId(id);
		Menu menu = menuMapper.getMenuById(id);
		if (menu != null) {
			List<Menu> exMenus = null;
			Integer order = menu.getOrder(); // order
			if ("up".equals(type)) {
				menu.setOrder(menu.getOrder() - 1);
				exMenus = menuMapper.getMenuByLtOrderAndParentId(order, parentId,id);
			} else {
				menu.setOrder(menu.getOrder() + 1);
				exMenus = menuMapper.getMenuByGtOrderAndParentId(order, parentId,id);
			}
			if (!exMenus.isEmpty()) {
				Menu exMenu = exMenus.get(0);
				menuMapper.updateMenuOrder(exMenu.getId(), order);
			}
			menuMapper.updateMenuOrder(menu.getId(), menu.getOrder());
			return 1;
		}
		return 0;
	}

}
