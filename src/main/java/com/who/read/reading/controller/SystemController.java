package com.who.read.reading.controller;

import com.who.read.reading.configuration.ServiceUtils;
import com.who.read.reading.who.datamodel.Field;
import com.who.read.reading.who.manager.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SystemController
 * @date 2020/6/18 4:37 PM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/sys")
public class SystemController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);


	@Autowired
	EntityManager entityManager;

	@RequestMapping("/getField/{id}")
	public Object getField(@PathVariable("id") String id) {
		LOGGER.info("getField(id)", id);
		if (id == null || "".equals(id.trim())) {
			return ServiceUtils.returnMapRestlt("1", "字段Id不能为空！", "");
		}
		Field field = entityManager.getFiledById(id);
		return ServiceUtils.returnMapRestlt("1", "查询成功！", field);
	}
}
