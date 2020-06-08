package com.who.read.reading.service;

import com.who.read.reading.entity.Role;
import com.who.read.reading.mapper.RoleMapper;
import com.who.read.reading.who.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname RoleService
 * @date 2020/6/5 6:19 PM
 * @Created by fengjiadong
 */
@Service
public class RoleService {
	@Autowired
	RoleMapper roleMapper;

	public List<Role> allRole(){
		return roleMapper.allRole();
	}
	public List<Role> allRoleByKey(String key){
		return roleMapper.allRoleByKey("%"+key+"%");
	}

	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	public Integer createRole(Role role) {
		if(role.getId() == null || "".equals(role.getId().trim())){
			role.setId(UUID.generateUUID());
		}
		return roleMapper.createRole(role);
	}

	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public Integer updateRole(Role role) {
		return roleMapper.updateRole(role);
	}

	/**
	 * 移除菜单中的角色
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public Integer menuAddRole(String menuId,String roleId) {
		return roleMapper.menuAddRole(menuId, roleId, UUID.generateUUID());
	}
}
