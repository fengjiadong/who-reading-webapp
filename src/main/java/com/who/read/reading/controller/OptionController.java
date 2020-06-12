package com.who.read.reading.controller;

import com.who.read.reading.configuration.ServiceUtils;
import com.who.read.reading.service.OptionService;
import com.who.read.reading.who.datamodel.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Classname OptionController
 * @date 2020/6/9 9:39 AM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/option")
public class OptionController {

	@Autowired
	OptionService optionService;

	// 得到所有的选项
	@RequestMapping("/allOption")
	public Object allOption(HttpSession session) {
		List<Option> allOption = optionService.allOption();
		return allOption;
	}

	// 传入选项的id 得到第一级子选项，不包括第二级。
	@RequestMapping("/searchParentByParent/{id}")
	public Object searchParentByParent(@PathVariable("id") String id) {
		if(id == null || "".equals(id.trim())){
			return ServiceUtils.returnMapRestlt("1", "选项Id不能为空！", "");
		}
		List<Option> allOption = optionService.searchParentByParent(id);
		return ServiceUtils.returnMapRestlt("1", "查询成功！", allOption);
	}

	@RequestMapping("/getOption/{id}")
	public Object getOption(@PathVariable("id") String id) {
		if(id == null || "".equals(id.trim())){
			return ServiceUtils.returnMapRestlt("1", "选项Id不能为空！", "");
		}
		Option option = optionService.getOption(id);
		return ServiceUtils.returnMapRestlt("1", "查询成功！", option);
	}
}
