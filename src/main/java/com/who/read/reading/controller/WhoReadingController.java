package com.who.read.reading.controller;

import com.who.read.reading.entity.Role;
import com.who.read.reading.entity.User;
import com.who.read.reading.service.MenuService;
import com.who.read.reading.service.RoleService;
import com.who.read.reading.service.UserService;
import com.who.read.reading.utils.HttpClient;
import com.who.read.reading.utils.Options;
import com.who.read.reading.who.condition.FieldExpression;
import com.who.read.reading.who.condition.NestedExpression;
import com.who.read.reading.who.condition.Operator;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.condition.EntityCondition;
import com.who.read.reading.who.datamodel.Field;
import com.who.read.reading.who.datamodel.Menu;
import com.who.read.reading.who.manager.EntityManager;
import com.who.read.reading.who.manager.Order;
import com.who.read.reading.who.manager.SystemManager;
import com.who.read.reading.who.manager.UserSystemManager;
import com.who.read.reading.who.util.UserSessionFactory;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Classname WhoReadingController
 * @date 2020/3/25 2:39 PM
 * @Created by fengjiadong
 */
@Controller
public class WhoReadingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WhoReadingController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private SystemManager systemManager;


	@RequestMapping("/login.html")
	public String login() {
		return "login";
	}

	@RequestMapping("/index.html")
	public String index(HttpServletRequest request) {
		Boolean isAdmin = UserSessionFactory.currentUser().hasRole(Options.Role_Admin);
		List<Menu> system = null;
		if (!isAdmin) {
			// 非管理员需要根据角色得到系统菜单
			system = menuService.getMenuBySiteAndRole("system", UserSessionFactory.currentUser().getId());
		} else {
			// 超级管理员可以得到所有的菜单
			system = menuService.getMenuBySite("system");
		}
		request.getSession().setAttribute("systemMenu", system);
		return "index/index";
	}

	@RequestMapping("/myInfo.html")
	public String myInfo(HttpServletRequest request) {
		User user = UserSessionFactory.currentUser();
		request.getSession().setAttribute("user", user);
		List<Role> roles = user.getRoles();
		request.getSession().setAttribute("roles", roles);
		return "index/my-info";
	}

	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/menu.html")
	public String menu(HttpServletRequest request) {
		return "index/menu/menu";
	}

	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/role.html")
	public String role(HttpServletRequest request) {
		String key = request.getParameter("key");
		List<Role> roles = roleService.allRoleByKey(key == null ? "" : key.trim());
		request.setAttribute("roles", roles);
		request.setAttribute("size", roles.size());
		request.setAttribute("key", key);
		return "index/role/role";
	}

	@PreAuthorize("hasAuthority('ROLE_" + Options.Role_Admin + "')")
	@RequestMapping("/user.html")
	public String user(HttpServletRequest request) {
		String name = request.getParameter("name");
		String pageNo = request.getParameter("pageNo") == null ? "1" : request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize") == null ? "10" : request.getParameter("pageSize");
		List<User> users = null;
		Integer count = 0;
		if (name != null && !"".equals(name.trim())) {
			users = userService.queryUserByName(name, Integer.valueOf(pageNo), Integer.valueOf(pageSize));
			count = userService.queryUserByNameCount(name);
		} else {
			users = userService.selectUserList(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
			count = userService.selectUserListCount();
		}
		Integer pageCount = (count / Integer.valueOf(pageSize)) + (count % Integer.valueOf(pageSize) > 0 ? 1 : 0);
		request.setAttribute("users", users);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("pageNo", Integer.valueOf(pageNo));
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("userSize", users.size());
		request.setAttribute("name", name);

		List<Integer> pageArr = new ArrayList<>();
		for (Integer integer = Integer.valueOf(pageNo) - 3 < 1 ? 1 : Integer.valueOf(pageNo) - 3; integer <= Integer.valueOf(pageNo); integer++) {
			pageArr.add(integer);
		}
		for (Integer i = Integer.parseInt(pageNo) + 1; i <= pageCount && i < Integer.parseInt(pageNo) + 3; i++) {
			pageArr.add(i);
		}
		request.setAttribute("pageArr", pageArr);
		return "index/user/user";
	}

	@RequestMapping("/hello.html")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/dataModule.html")
	public String dataModule(HttpServletRequest request) {
		List<Entity> moduleList = entityManager.listAll(Options.Module_Id);
		request.setAttribute("moduleList", moduleList);
		request.setAttribute("size", moduleList.size());
		return "index/dm/dataModule";
	}

	@RequestMapping("/tableList.html")
	public String tableList(HttpServletRequest request) {
		String id = request.getParameter("id");
		EntityCondition entityCondition = new EntityCondition(Options.Type_Id);
		entityCondition.setProperty("module", id);
		List<Entity> tableList = entityManager.list(entityCondition);
		request.setAttribute("tableList", tableList);
		request.setAttribute("size", tableList.size());
		return "index/dm/tableList";
	}

	@RequestMapping("/table.html")
	public String table(HttpServletRequest request) {
		String typeId = request.getParameter("id");
		Entity entity = entityManager.getEntity(typeId, Options.Type_Id);
		List<Field> fields = systemManager.getFields(entity.getProperty("name", String.class), typeId);
		request.setAttribute("fieldList", fields);
		request.setAttribute("entity", entity.getProperties());
		System.out.println(entity.getProperties().toString());
		request.setAttribute("size", fields.size());
		return "index/dm/table";
	}


	@RequestMapping("/option.html")
	public String option(HttpServletRequest request) {
		return "index/option/option";
	}

	@Autowired
	UserSystemManager userSystemManager;

	@RequestMapping("/person.html")
	public String person(HttpServletRequest request) {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String pageNo = request.getParameter("pageNo") == null ? "1" : request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize") == null ? "10" : request.getParameter("pageSize");


		EntityCondition entityCondition = new EntityCondition(Options.PersonTypeId);
		entityCondition.addNestedExpression(NestedExpression.Operator.OR,
				new FieldExpression("name", Operator.Contains, name),
				new FieldExpression("qq", Operator.Contains, name),
				new FieldExpression("weixin", Operator.Contains, name),
				new FieldExpression("phone", Operator.Contains, name)
		);
		entityCondition.addNestedExpression(NestedExpression.Operator.OR,
				new FieldExpression("address", Operator.Contains, address),
				new FieldExpression("zone", Operator.Contains, address)
		);

		entityCondition.setPageNo(Integer.valueOf(pageNo));
		entityCondition.setPageSize(Integer.valueOf(pageSize));
		Order order = new Order(Order.Sort.desc, Arrays.asList("num"));
		entityCondition.setOrder(order);
		List<Entity> list = entityManager.list(entityCondition);
		Integer count = entityManager.count(entityCondition);


		Integer pageCount = (count / Integer.valueOf(pageSize)) + (count % Integer.valueOf(pageSize) > 0 ? 1 : 0);
		request.setAttribute("users", list);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("pageNo", Integer.valueOf(pageNo));
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("userSize", list.size());
		request.setAttribute("name", name);
		request.setAttribute("address", address);

		List<Integer> pageArr = new ArrayList<>();
		for (Integer integer = Integer.valueOf(pageNo) - 3 < 1 ? 1 : Integer.valueOf(pageNo) - 3; integer <= Integer.valueOf(pageNo); integer++) {
			pageArr.add(integer);
		}
		for (Integer i = Integer.parseInt(pageNo) + 1; i <= pageCount && i < Integer.parseInt(pageNo) + 3; i++) {
			pageArr.add(i);
		}
		request.setAttribute("pageArr", pageArr);

		return "index/person/person";
	}


	@RequestMapping("/show/{fileId}")
	public String show(HttpServletRequest request, @PathVariable("fileId") String fileId) {
		Entity entity = entityManager.getEntity(fileId, Options.FileTypeId);
		request.setAttribute("name", entity.getProperty("name"));
		request.setAttribute("type", entity.getProperty("type"));
		request.setAttribute("base64", entity.getProperty("base64"));
		return "index/show/show";
	}

	@RequestMapping("/person/info/{id}")
	public String personInfo(HttpServletRequest request, @PathVariable("id") String id) {
		request.setAttribute("id", id);
		Entity entity = entityManager.getEntity(id, Options.PersonTypeId);
		request.setAttribute("entity", entity);
		LOGGER.info(getIpAddress(request) + "用户访问了[" + entity.getProperty("name") + "]详情页,时间是：" + DateFormat.getInstance().format(new Date()));
		return "index/person/person_info";
	}

	@RequestMapping("/person/img/list")
	public String personImgList(HttpServletRequest request) {
		LOGGER.info(getIpAddress(request) + "用户访问了 人员列表方法,时间是：" + DateFormat.getInstance().format(new Date()));
		String key = request.getParameter("key");
		Integer pageNo = request.getParameter("pageNo") == null ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		Integer pageSize = request.getParameter("pageSize") == null ? 20 : Integer.valueOf(request.getParameter("pageSize"));

		EntityCondition entityCondition = new EntityCondition(Options.PersonTypeId);
		entityCondition.setPageSize(pageSize);
		entityCondition.setPageNo(pageNo);
		entityCondition.addNestedExpression(NestedExpression.Operator.OR,
				new FieldExpression("name", Operator.Contains, key),
				new FieldExpression("qq", Operator.Contains, key),
				new FieldExpression("weixin", Operator.Contains, key),
				new FieldExpression("address", Operator.Contains, key),
				new FieldExpression("zone", Operator.Contains, key),
				new FieldExpression("remarks", Operator.Contains, key),
				new FieldExpression("phone", Operator.Contains, key)
		);
		Order order = new Order(Order.Sort.desc, Arrays.asList("num"));
		entityCondition.setOrder(order);

		entityCondition.addFieldExpression("imgs", Operator.NotNull, "string");
		List<Entity> list = entityManager.list(entityCondition);

		Integer count = entityManager.count(entityCondition);


		Integer pageCount = (count / Integer.valueOf(pageSize)) + (count % Integer.valueOf(pageSize) > 0 ? 1 : 0);
		request.setAttribute("key", key);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("userSize", list.size());
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("users", list);
		request.setAttribute("pageCount", pageCount);

		List<Integer> pageArr = new ArrayList<>();
		for (Integer integer = pageNo - 3 < 1 ? 1 : pageNo - 3; integer <= pageNo; integer++) {
			pageArr.add(integer);
		}
		for (Integer i = pageNo + 1; i <= pageCount && i < pageNo + 3; i++) {
			pageArr.add(i);
		}
		request.setAttribute("pageArr", pageArr);

		return "index/person/person_img_list";
	}


	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * <p>
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * <p>
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 * <p>
	 * 用户真实IP为： 192.168.1.110
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
