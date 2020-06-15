package com.who.read.reading.service;

import com.who.read.reading.mapper.OptionMapper;
import com.who.read.reading.who.datamodel.Option;
import com.who.read.reading.who.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname OptionService
 * @date 2020/6/9 7:02 PM
 * @Created by fengjiadong
 */
@Service
public class OptionService {
	@Autowired
	private OptionMapper optionMapper;

	public List<Option> allParentMenu() {
		return optionMapper.allParentMenu();
	}

	public List<Option> searchParentByParent(String parent) {
		return optionMapper.searchParentByParent(parent);
	}

	public Option getOptionById(String id) {
		return optionMapper.getOption(id);
	}

	/**
	 * 拿到所有选项，所有选项的selectable全都默认为true;
	 * @return
	 */
	public List<Option> allOption() {
		List<Option> options = optionMapper.allParentMenu();
		for (Option option : options) {
			option.setNodes(chidrens(option));
		}
		return options;
	}

	/**
	 * 递归查询所有下级选项,所有的层级都可以点击选中。
	 *
	 * @param option
	 * @return
	 */
	private List<Option> chidrens(Option option) {
		option.setSelectable(true);
		List<Option> childrensMenus = optionMapper.searchParentByParent(option.getId());
		if (childrensMenus == null || childrensMenus.isEmpty()) {
			return null;
		}
		for (Option childrensMenu : childrensMenus) {
			childrensMenu.setParentName(option.getName());
			childrensMenu.setNodes(chidrens(childrensMenu));
		}
		return childrensMenus;
	}

	/**
	 * 创建option方法
	 * @param option
	 * @return
	 */
	public Integer createOption(Option option) {
		if (option.getId() == null || "".equals(option.getId().trim())) {
			option.setId(UUID.generateUUID());
		}
		Integer count = optionMapper.countByParentId(option.getParent());
		option.setOrder(count);
		return optionMapper.createOption(option);
	}
}
