package com.who.read.reading.mapper;

import com.who.read.reading.who.datamodel.Menu;
import com.who.read.reading.who.datamodel.Option;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname OptionMapper
 * @date 2020/6/9 7:00 PM
 * @Created by fengjiadong
 */
@Repository
public interface OptionMapper {

	// 查询所有选项
	@Select("SELECT * FROM `dm.option` WHERE `parent` is null or `parent` = '' order by `order`" )
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

}
