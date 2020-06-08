package com.who.read.reading.mapper;

import com.who.read.reading.entity.Role;
import com.who.read.reading.who.datamodel.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

	// 按关键字查询角色
	@Select("select * from `dm.role` where `name` like #{key} or `id` like #{key} or `displayName` like #{key} or `introduce` like #{key} or `parent` like #{key} ")
	List<Role> allRoleByKey(@Param("key")String key);

	@Select("select * from `dm.role` where id = #{id}  ")
	Menu getRoleById(@Param(value = "id") String id);

	@Select("select * from `dm.role` where name = #{name}  ")
	List<Role>  getRoleByName(@Param(value = "name") String name);

	@Select("select * FROM `dm.role` WHERE id in (select roleId from `dm.user_role` where parentId = #{userId})  ")
	List<Role>  getUserRole(@Param(value = "userId") String userId);

	@Insert("INSERT INTO `dm.role` VALUES(#{role.id},#{role.name},#{role.displayName},#{role.introduce},#{role.parent})")
	Integer createRole(@Param("role") Role role);

	@Update("UPDATE `dm.role` SET `name` = #{role.name}, `displayName` = #{role.displayName} , `introduce` = #{role.introduce} , `parent` = #{role.parent} where `id` = #{role.id}")
	Integer updateRole(@Param("role") Role role);

	// 获取菜单所对应的角色
	@Select("SELECT * FROM `dm.role` as r WHERE r.id in (select roleId FROM `dm.menu_role` where parentId = #{menuId})")
	List<Role> getMenuRole(@Param(value = "menuId") String menuId);

	// 给菜单添加角色
	@Insert("INSERT INTO `dm.menu_role` VALUES(#{menuId},#{roleId},'',#{id})")
	Integer menuAddRole(@Param(value = "menuId") String menuId, @Param(value = "roleId") String roleId, @Param(value = "id") String id);

	// 判断该菜单是否已拥有该角色
	@Select("select count(0) FROM `dm.menu_role` where parentId = #{menuId} and roleId = #{roleId}")
	Integer whetherMenuHasRole(@Param(value = "menuId") String menuId, @Param(value = "roleId") String roleId);

	// 移除菜单的所见角色
	@Delete(" DELETE FROM `dm.menu_role` WHERE parentId=#{menuId} and roleId = #{roleId}")
	Integer menuRemoveRole(@Param(value = "menuId") String menuId, @Param(value = "roleId") String roleId);
}
