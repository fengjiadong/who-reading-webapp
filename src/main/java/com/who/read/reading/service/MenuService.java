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

	public List<Menu> allMenu(){
		String id = UserSessionFactory.currentUser().getId();
		List<Menu> menus = menuMapper.allParentMenu(id);
		// 判断是否有权限获取
		for (Menu menu : menus) {
			menu.setNodes(chidrens(menu,id));
		}
		return menus;
	}

	/**
	 * 菜单管理时查询使用本方法。
	 * @return
	 */
	public List<Menu> allMenuManager(){
		List<Menu> menus = menuMapper.allManagerParentMenu();
		for (Menu menu : menus) {
			menu.setNodes(chidrensByManager(menu));
		}
		return menus;
	}

	public List<Menu> getMenuBySite(String site) {
		return this.menuMapper.getMenuBySite(site);
	}

	public Menu getMenuById(String id){
		return menuMapper.getMenuById(id);
	}

	public List<Menu>  getChildrensMenu( String parentId,String userId){
		return menuMapper.getChildrensMenu(parentId,userId);
	}

	public List<Menu>  getMenuByName( String name){
		return menuMapper.getMenuByName(name);
	}

	public Integer createMenu(Menu menu) {
		if(menu.getId() == null || "".equals(menu.getId().trim())){
			menu.setId(UUID.generateUUID());
		}
		return menuMapper.createMenu(menu);
	}

	public Integer deleteMenu(String id) {
		return menuMapper.deleteMenu(id);
	}
	/**
	 * 递归查询所有下级菜单，并根据是否有父菜单来设置可点击。
	 * @param menu
	 * @return
	 */
	private List<Menu> chidrens(Menu menu,String userId) {
		List<Menu> childrensMenus = menuMapper.getChildrensMenu(menu.getId(),userId);
		if(childrensMenus ==null || childrensMenus.isEmpty()){
			menu.setSelectable(true);
			return null;
		}
		menu.setSelectable(false);
		for (Menu childrensMenu : childrensMenus) {
			childrensMenu.setParentName(menu.getName());
			childrensMenu.setNodes(chidrens(childrensMenu,userId));
		}
		return childrensMenus;
	}
	/**
	 * 递归查询所有下级菜单,清除禁用和是否父菜单.
	 * @param menu
	 * @return
	 */
	private List<Menu> chidrensByManager(Menu menu) {
		menu.setDisabled(false);
		menu.setSelectable(true);
		List<Menu> childrensMenus = menuMapper.getManagerChildrensMenu(menu.getId());
		if(childrensMenus ==null || childrensMenus.isEmpty()){
			return null;
		}
		for (Menu childrensMenu : childrensMenus) {
			childrensMenu.setParentName(menu.getName());
			childrensMenu.setNodes(chidrensByManager(childrensMenu));
		}
		return childrensMenus;
	}
}
