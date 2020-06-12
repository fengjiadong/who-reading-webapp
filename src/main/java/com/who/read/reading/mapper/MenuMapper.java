package com.who.read.reading.mapper;

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
public interface MenuMapper {

	@Select("select * from `dm.menu`  ORDER BY `order`")
	List<Menu> allMenu();

	// 根据用户查询用户所拥有的角色下的所有可显示的父菜单
	@Select("SELECT * FROM `dm.menu` WHERE `id` IN (SELECT parentId FROM `dm.menu_role` WHERE roleId IN " +
			"(SELECT roleId FROM `dm.user_role` WHERE parentId= #{userId})) and `validity` = 1 and ( parentId IS NULL or parentId = '') ORDER BY `order`")
	List<Menu> allParentMenu(@Param(value = "userId") String userId);

	// 查询所有父菜单
	@Select("SELECT * FROM `dm.menu` WHERE parentId IS NULL or parentId = ''   ORDER BY `order`")
	List<Menu> allManagerParentMenu();

	@Select("select * from `dm.menu` where id = #{id}  ORDER BY `order`")
	Menu getMenuById(@Param(value = "id") String id);

	@Select("SELECT * FROM `dm.menu` WHERE `id` IN (SELECT parentId FROM `dm.menu_role` WHERE roleId IN  " +
			"(SELECT roleId FROM `dm.user_role` WHERE parentId= #{userId})) and `validity` = 1 and parentId = #{parentId} ORDER BY `order`")
	List<Menu> getChildrensMenu(@Param(value = "parentId") String parentId, @Param(value = "userId") String userId);

	@Select("select * from `dm.menu` where parentId = #{parentId}  ORDER BY `order`")
	List<Menu> getManagerChildrensMenu(@Param(value = "parentId") String parentId);

	@Select("select * from `dm.menu` where name = #{name}  ORDER BY `order`")
	List<Menu> getMenuByName(@Param(value = "name") String name);

	// 按地点查询所有菜单，不区分权限。
	@Select("select * from `dm.menu` where site = #{site} and `validity` = 1 ORDER BY `order` ")
	List<Menu> getMenuBySite(@Param(value = "site") String site);

	// 按地点查询所有菜单，区分角色权限。
	@Select("SELECT * FROM `dm.menu` WHERE `id` IN (SELECT parentId FROM `dm.menu_role` WHERE roleId IN  " +
			" (SELECT roleId FROM `dm.user_role` WHERE parentId= #{userId})) and `validity` = 1 and `site` = #{site}  ORDER BY `order`")
	List<Menu> getMenuBySiteAndRole(@Param(value = "site") String site, @Param(value = "userId") String userId);


	@Insert("INSERT INTO `dm.menu` (`id`,`name`,`parentId`,`icon`,`code`,`action`,`selectable`,`disabled`,`order`,`site`,`explain`,`validity`) " +
			"values(#{menu.id},#{menu.name},#{menu.parentId},#{menu.icon},#{menu.code},#{menu.action},#{menu.selectable},#{menu.disabled},#{menu.order},#{menu.site},#{menu.explain},#{menu.validity})")
	Integer createMenu(@Param("menu") Menu menu);

	@Delete("DELETE FROM `dm.menu` WHERE id=#{id}")
	Integer deleteMenu(@Param("id") String id);

	@Update("UPDATE `dm.menu` set `action` = #{menu.action},`icon` = #{menu.icon} , `name` = #{menu.name} , `explain` = #{menu.explain} , `disabled` = #{menu.disabled}, `validity` = #{menu.validity} ,`site` = #{menu.site} " +
			"where id = #{menu.id}")
	Integer updateMenu(@Param("menu") Menu menu);

	@Update("UPDATE `dm.menu` set `order` = #{order} where `id` = #{id}")
	Integer updateMenuOrder(@Param("id") String id, @Param("order") String order);

}
