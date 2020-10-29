package com.who.read.reading.controller;

import com.who.read.reading.utils.Options;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.manager.EntityManager;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 文件系统.
 *
 * @Classname FileController
 * @date 2020/10/22 1:51 PM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	EntityManager entityManager;


	@RequestMapping("/show/{fileId}")
	public Object saveFile(HttpServletResponse response, @PathVariable("fileId") String fileId) {
		Entity entity = entityManager.getEntity(fileId, Options.FileTypeId);

		try {
			ServletOutputStream out = response.getOutputStream();
			String base64 = entity.getProperty("base64", String.class);
			//去掉前面的“data:image/jpeg;base64,”的字样
			String content_type = base64.substring(5, base64.indexOf(";"));
			response.setHeader("Content-Type", content_type);
			String imgdata = base64.replace("data:image/jpeg;base64,", "");
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] data = decoder.decodeBuffer(imgdata);
			for (int i = 0; i < data.length; i++) {
				if (data[i] < 0) {
					data[i] += 256;
				}
			}
			out.write(data);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entity.getProperty("base64");
	}

	@RequestMapping("/saveFile")
	public Object saveFile(HttpServletRequest request) {
		String base64 = request.getParameter("base64");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("msg", "请传入base64");

		if (StringUtils.isEmpty(base64) || StringUtils.isEmpty(name)) {
			return result.toString();
		}
		if (StringUtils.isEmpty(type)) {
			type = name.substring(name.lastIndexOf("."));
		}

		Entity entity = new Entity(Options.FileTypeId);
		entity.setProperty("base64", base64);
		entity.setProperty("name", name);
		entity.setProperty("dateTime", new Date());
		entity.setProperty("type", type);
		String fileId = entityManager.create(entity);
		result.put("fileId", fileId);
		result.put("code", 1);
		result.put("msg", "保存成功");
		return result.toString();
	}

}
