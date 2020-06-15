package com.who.read.reading.controller;

import com.who.read.reading.configuration.ServiceUtils;
import com.who.read.reading.service.OptionService;
import com.who.read.reading.utils.Options;
import com.who.read.reading.who.datamodel.Menu;
import com.who.read.reading.who.datamodel.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
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
		Option option = optionService.getOptionById(id);
		return ServiceUtils.returnMapRestlt("1", "查询成功！", option);
	}

	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/create")
	public Object create(@PathParam("option") Option option) {
		if (option.getName() == null || option.getName().trim().equals("")) {
			return ServiceUtils.returnMapRestlt("-1", "请输入选项名称！", "");
		}
		Integer result = optionService.createOption(option);
		if (result > 0) {
			Option optionById = optionService.getOptionById(option.getId());
			optionById.setSelectable(true);
			return ServiceUtils.returnMapRestlt("1", "新增成功！", optionById);
		}
		return ServiceUtils.returnMapRestlt("-1", "新增失败！", "");
	}
}
