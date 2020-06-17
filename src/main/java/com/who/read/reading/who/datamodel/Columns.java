package com.who.read.reading.who.datamodel;

/**
 * @Classname Columns
 *  数据库获取到表结构信息
 * @date 2020/6/17 2:21 PM
 * @Created by fengjiadong
 */
public class Columns {
	String table_schema;
	String table_name;
	String column_name;
	String column_key;
	String column_type;
	String is_nullable;
	String column_default;
	String column_comment;
	String character_set_name;
	Integer character_maximum_length;




	public String getTable_schema() {
		return table_schema;
	}

	public void setTable_schema(String table_schema) {
		this.table_schema = table_schema;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getColumn_key() {
		return column_key;
	}

	public void setColumn_key(String column_key) {
		this.column_key = column_key;
	}

	public String getColumn_type() {
		return column_type;
	}

	public void setColumn_type(String column_type) {
		this.column_type = column_type;
	}

	public String getIs_nullable() {
		return is_nullable;
	}

	public void setIs_nullable(String is_nullable) {
		this.is_nullable = is_nullable;
	}

	public String getColumn_default() {
		return column_default;
	}

	public void setColumn_default(String column_default) {
		this.column_default = column_default;
	}

	public String getColumn_comment() {
		return column_comment;
	}

	public void setColumn_comment(String column_comment) {
		this.column_comment = column_comment;
	}

	public String getCharacter_set_name() {
		return character_set_name;
	}

	public void setCharacter_set_name(String character_set_name) {
		this.character_set_name = character_set_name;
	}

	public Integer getCharacter_maximum_length() {
		return character_maximum_length;
	}

	public void setCharacter_maximum_length(Integer character_maximum_length) {
		this.character_maximum_length = character_maximum_length;
	}
}
