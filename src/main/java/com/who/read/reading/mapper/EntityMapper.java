package com.who.read.reading.mapper;

import com.who.read.reading.who.datamodel.Columns;
import org.apache.ibatis.annotations.Insert;
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

	@Insert("${sqlStr}")
	Integer create(@Param(value = "sqlStr")String value);

	@Select("SELECT * FROM `dm.type` where id = #{id}")
	Map<String,Object>  entityInfo(@Param(value = "id") String id);


	@Select("${sqlStr}")
	Object  fieldDisplay(@Param(value = "sqlStr") String value);

	@Select("select table_schema,table_name,column_name,column_type,column_key,is_nullable,column_default," +
			"column_comment,character_set_name,character_maximum_length " +
			" from information_schema.columns where table_schema=#{dbName} and table_name=#{table};")
	List<Columns> getColumnsList(@Param(value = "dbName") String dbName,@Param(value = "table") String table);

	@Select("select table_schema,table_name,column_name,column_type,column_key,is_nullable,column_default,column_comment,character_set_name,character_maximum_length" +
			" from information_schema.columns where table_schema=#{dbName} and column_comment like #{id}")
	Columns getColumnById(@Param(value = "dbName") String dbName, @Param(value = "id") String id);

}
