package com.who.read.reading.mapper;

import com.who.read.reading.who.datamodel.Menu;
import com.who.read.reading.who.datamodel.Option;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Classname OptionMapper
 * @date 2020/6/9 7:00 PM
 * @Created by fengjiadong
 */
@Repository
public interface OptionMapper {

	// 查询所有选项
	@Select("SELECT * FROM `dm.option` WHERE `parent` is null or `parent` = '' order by `order`")
	List<Option> allParentMenu();

	// 根据parent查询选项
	@Select("SELECT * FROM `dm.option` WHERE `parent` = #{parent} order by `order`")
	List<Option> searchParentByParent(@Param("parent") String parent);

	// 根据Id查询选项
	@Select("SELECT * FROM `dm.option` WHERE `id` = #{id}")
	Option getOption(@Param("id") String id);

	// 根据parent找到下面有选项数量
	@Select("SELECT COUNT(0) FROM `dm.option` WHERE `parent` = #{parent}")
	Integer countByParentId(@Param("parent") String parentId);

	@Insert("INSERT INTO `dm.option` (`id`,`name`,`explain`,`group`,`parent`,`code`,`isSys`,`disabled`,`order`,`selectable`) " +
			"values(#{option.id},#{option.name},#{option.explain},#{option.group},#{option.parent},#{option.code},#{option.isSys},#{option.disabled},#{option.order},#{option.selectable})")
	Integer createOption(@Param("option") Option option);


	@Delete("DELETE FROM `dm.option` WHERE id=#{id}")
	Integer delete(@Param("id") String id);

	@Update("UPDATE `dm.option` set `name` = #{option.name} , `explain` = #{option.explain} ,`group` = #{option.group}, `code` = #{option.code}, `disabled` = #{option.disabled}, `selectable` = #{option.selectable}" +
			" where id = #{option.id}")
	Integer update(@Param("option") Option option);

	// 更新order
	@Update("UPDATE `dm.option` set `order` = #{order} where `id` = #{id}")
	Integer updateOrder(@Param("id") String id, @Param("order") Integer order);

	// 查询小于order的第一条数据
	@Select("SELECT * FROM `dm.option` WHERE  `parent` = #{parent} and `order` <= #{order} and `id` != #{id} order  by  `order` desc LIMIT 1 ")
	List<Option> getOptionByLtOrderAndParentId(@Param(value = "order") Integer order, @Param(value = "parent") String parentId, @Param(value = "id") String id);

	// 查询大于order的第一条数据
	@Select("SELECT * FROM `dm.option` WHERE  `parent` = #{parent} and `order` >= #{order} and `id` != #{id} order  by  `order`  LIMIT 1")
	List<Option> getOptionByGtOrderAndParentId(@Param(value = "order") Integer order, @Param(value = "parent") String parentId, @Param(value = "id") String id);

	// 查询菜单的parentId
	@Select("SELECT `parent` FROM `dm.option` where `id` = #{id}")
	String getParentId(@Param(value = "id") String id);


	@Select("SELECT `name` FROM `dm.option` where `id` = #{id}")
	String getOptionName(@Param("id") String id);
}
