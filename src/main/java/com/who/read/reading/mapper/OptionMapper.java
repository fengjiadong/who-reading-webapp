package com.who.read.reading.mapper;

import com.who.read.reading.who.datamodel.Option;
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

	@Select("SELECT * FROM `dm.option` WHERE `parent` is null or `parent` = ''" )
	List<Option> allParentMenu();

	@Select("SELECT * FROM `dm.option` WHERE `parent` = #{parent}")
	List<Option> searchParentByParent(@Param("parent") String parent);

	@Select("SELECT * FROM `dm.option` WHERE `id` = #{id}")
	Option getOption(@Param("id") String id);
}
