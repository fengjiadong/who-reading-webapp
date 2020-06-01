package com.who.read.reading.who.manager;

import com.who.read.reading.mapper.EntityMapper;
import com.who.read.reading.service.MenuService;
import com.who.read.reading.who.datamodel.Menu;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MenuManager
 * @date 2020/5/29 2:03 PM
 * @Created by fengjiadong
 */
public class MenuManager {
	@Autowired
	EntityMapper entityMapper;

	@Autowired
	MenuService menuService;

	public List<Menu> getMenu(){
		return menuService.allMenu();
	}

	public void menu(List<Menu> menus) {
		List<Menu> parentMenus = new ArrayList<>();
		List<String> parentIds = new ArrayList<>();
		for (Menu menu : menus) {
			String parentId = menu.getParentId();
			if(parentId == null || "".equals(parentId)){
				parentMenus.add(menu);
				parentIds.add(menu.getId());
			}else{
				if (parentIds.contains(parentId)) {

				}
			}
		}
	}

}
