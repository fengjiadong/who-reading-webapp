package com.who.read.reading.mapper;

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
public interface MenuMapper {

	@Select("select * from menu  ORDER BY `order`")
	List<Menu> allMenu();

	@Select("SELECT * FROM menu WHERE parentId IS NULL or parentId = ''  ORDER BY `order`")
	List<Menu> allParentMenu();

	@Select("select * from menu where id = #{id}  ORDER BY `order`")
	Menu getMenuById(@Param(value = "id") String id);

	@Select("select * from menu where parentId = #{parentId}  ORDER BY `order`")
	List<Menu>  getChildrensMenu(@Param(value = "parentId") String parentId);

	@Select("select * from menu where name = #{name}  ORDER BY `order`")
	List<Menu>  getMenuByName(@Param(value = "name") String name);

}
