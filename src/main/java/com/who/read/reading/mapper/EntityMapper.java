package com.who.read.reading.mapper;

import com.who.read.reading.entity.User;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.datamodel.EntityCondition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Classname UserMapper
 * @date 2020/3/26 10:01 AM
 * @Created by fengjiadong
 */
@Repository
public interface EntityMapper {

	@Select("${sqlStr}")
	List<Map<String, Object>> list(@Param(value = "sqlStr")String value);


	@Select("SELECT * FROM entity where id = #{id}")
	Map<String,Object>  entityInfo(@Param(value = "id") String id);


	@Select("${sqlStr}")
	Object  fieldDisplay(@Param(value = "sqlStr") String value);

}