package com.who.read.reading.service;

import com.who.read.reading.mapper.MenuMapper;
import com.who.read.reading.who.datamodel.Menu;
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

	public List<Menu> allMenu(){
		List<Menu> menus = menuMapper.allParentMenu();
		for (Menu menu : menus) {
			menu.setNodes(chidrens(menu));
		}
		return menus;
	}

	public Menu getMenuById(@Param(value = "id") String id){
		return menuMapper.getMenuById(id);
	}

	public List<Menu>  getChildrensMenu(@Param(value = "parentId") String parentId){
		return menuMapper.getChildrensMenu(parentId);
	}

	public List<Menu>  getMenuByName(@Param(value = "name") String name){
		return menuMapper.getMenuByName(name);
	}

	private List<Menu> chidrens(Menu menu) {
		List<Menu> childrensMenus = menuMapper.getChildrensMenu(menu.getId());
		if(childrensMenus ==null || childrensMenus.isEmpty()){
			return null;
		}
		for (Menu childrensMenu : childrensMenus) {
			childrensMenu.setNodes(chidrens(childrensMenu));
		}
		return childrensMenus;
	}
}
