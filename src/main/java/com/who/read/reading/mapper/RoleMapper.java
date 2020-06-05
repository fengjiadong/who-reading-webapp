package com.who.read.reading.mapper;

import com.who.read.reading.entity.Role;
import com.who.read.reading.who.datamodel.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Classname MenuMapper
 * @date 2020/5/29 2:09 PM
 * @Created by fengjiadong
 */
@Repository
public interface RoleMapper {

	@Select("select * from `dm.role` ")
	List<Role> allRole();

	@Select("select * from `dm.role` where id = #{id}  ")
	Menu getMenuById(@Param(value = "id") String id);

	@Select("select * from `dm.role` where name = #{name}  ")
	List<Role>  getRoleByName(@Param(value = "name") String name);

	@Select("select * FROM `dm.role` WHERE id in (select roleId from `dm.user_role` where parentId = #{userId})  ")
	List<Role>  getUserRole(@Param(value = "userId") String userId);

//	@Select("select * from `dm.menu_role` where parentId = #{userId} ")
//	List<Map<String,Object>>  getMenuRole(@Param(value = "userId") String userId);

	@Select("SELECT * FROM `dm.role` as r WHERE r.id in (select roleId FROM `dm.menu_role` where parentId = #{menuId})")
	List<Role> getMenuRole(@Param(value = "menuId") String menuId);

}
