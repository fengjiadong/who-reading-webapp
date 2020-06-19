package com.who.read.reading.who.manager;

import com.who.read.reading.service.EntityService;
import com.who.read.reading.service.OptionService;
import com.who.read.reading.who.datamodel.Columns;
import com.who.read.reading.who.datamodel.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SystemManager
 * @date 2020/6/18 5:27 PM
 * @Created by fengjiadong
 */
@Component
public class SystemManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemManager.class);

	@Autowired
	EntityService entityService;
	@Autowired
	OptionService optionService;

	static Map<String,String> dateFormats; // 存储着日期格式
	static Map<String,String> types; // 存储着数据格式

	static {
		dateFormats = new HashMap<>();
		dateFormats.put("0", "");
		dateFormats.put("1", "yyyy-MM-dd HH:mm:ss");
		dateFormats.put("2", "yyyy-MM-dd HH:mm");
		dateFormats.put("3", "yyyy-MM-dd");
		dateFormats.put("4", "yyyy年MM月dd日 HH时mm分ss秒");
		dateFormats.put("5", "yyyy年MM月dd日 HH时mm分");
		dateFormats.put("6", "yyyy年MM月dd日");

		types = new HashMap<>();
		types.put("String", "字符串");
		types.put("boolean", "布尔");
		types.put("int", "整形");
		types.put("double", "双精度");
		types.put("redundant", "冗余");
		types.put("reference", "引用");
		types.put("date", "日期");
		types.put("option", "选项");
		types.put("file", "文件");
		types.put("img", "图片");
	}

	public Map<String,String> dateFormats() {
		return this.dateFormats;
	}
	public Map<String,String> types() {
		return this.types;
	}


	/**
	 * 得到数据库表结构
	 *
	 * @param table 表明
	 * @return
	 */
	public List<Columns> getColumnsList(String table) {
		LOGGER.info("getColumnsList(" + table + ")");
		return entityService.getColumnsList(table);
	}

	/**
	 * 根据Id得到字段的信息
	 *
	 * @param id
	 * @return
	 */
	public Field getFiledById(String id) {
		LOGGER.info("getFiledById(" + id + "");
		Columns columns = entityService.getColumnById(id);
		try {
			Field field = new Field();
			String column_comment = columns.getColumn_comment();
			String[] split = column_comment.split("-#-");
			for (String var : split) {
				setFiled(field, var);
			}
			field.setDefaultValue(columns.getColumn_default());
			field.setLength(columns.getCharacter_maximum_length());
			return field;
		} catch (Exception e) {
			LOGGER.info("getFiledById(" + id + "):FieldNullError", e.getLocalizedMessage());
			return null;
		}
	}


	/**
	 * 得到数据库表结构 按typeId查询
	 *
	 * @param table 表明
	 * @return
	 */
	public List<Field> getFields(String table, String typeId) {
		List<Columns> columnsList = entityService.getColumnsList(table);
		ArrayList<Field> fields = new ArrayList<>();
		for (Columns columns : columnsList) {
			try {
				Field field = new Field();
				String column_comment = columns.getColumn_comment();
				String[] split = column_comment.split("-#-");
				for (String var : split) {
					setFiled(field, var);
				}
				if (typeId.equals(field.getTypeId()) || field.getName().equals("id") || field.getName().equals("parent")) {
					fields.add(field);
				}
			} catch (Exception e) {
				LOGGER.info("getFields(" + table + "," + typeId + ")" + ":Error", e.getLocalizedMessage());
			}
		}
		return fields;
	}

	// 设置字段信息
	public void setFiled(Field filed, String var) {
		String content = var.substring(var.indexOf("#") + 1, var.lastIndexOf("#"));
		int i = content.indexOf(":");
		if (i < 0) {
			return;
		}
		String key = content.substring(0, i);
		String value = content.substring(i + 1);
		switch (key) {
			case "name":
				filed.setName(value);
				break;
			case "schema":
				filed.setSchema(value);
				setSchema(filed);
				return;
			case "displayAs":
				filed.setDisplayAs(value);
				return;
			case "describe":
				filed.setDescription(value);
				return;
			case "type":
				filed.setType(value);
				return;
			case "typeId":
				filed.setTypeId(value);
				return;
			case "id":
				filed.setId(value);
				return;
		}
	}

	/**
	 * 查询字段的时候 获取schema的所对应的值
	 *
	 * @param filed
	 */
	public void setSchema(Field filed) {
		String type = filed.getType();
		String schema = filed.getSchema();
		if (StringUtils.isEmpty(type) || StringUtils.isEmpty(schema)) {
			return;
		}
		switch (type) {
			case "option":
				// 选择类型
				filed.setSchemaAs(optionService.getOptionName(schema));
				return;
			case "reference":
				// 引用
				return;
			case "data":
				// 日期类型时间格式
				return;
			case "redundant":
				// 冗余
				return;
		}
	}


}
