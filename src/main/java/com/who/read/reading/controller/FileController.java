package com.who.read.reading.controller;

import com.who.read.reading.utils.Options;
import com.who.read.reading.who.datamodel.Entity;
import com.who.read.reading.who.manager.EntityManager;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
			base64 = resizeImageTo40K(base64, entity.getProperty("name", String.class));
			//去掉前面的“data:image/jpeg;base64,”的字样
			String content_type = base64.substring(5, base64.indexOf(";"));
			response.setHeader("Content-Type", content_type);
			String imgdata = base64.replace("data:image/jpeg;base64,", "").replace("data:image/png;base64,", "");
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

	public static String resizeImageTo40K(String base64Img, String type) {
		try {
			BufferedImage src = base64String2BufferedImage(base64Img);
			BufferedImage output = Thumbnails.of(src).size(src.getWidth() / 3, src.getHeight() / 3).asBufferedImage();
			String base64 = imageToBase64(output, type);
			if (base64.length() - base64.length() / 8 * 2 > 40000) {
				output = Thumbnails.of(output).scale(1 / (base64.length() / 40000)).asBufferedImage();
				base64 = imageToBase64(output, type);
			}
			return base64;
		} catch (Exception e) {
			return base64Img;
		}
	}

	public static String imageToBase64(BufferedImage bufferedImage, String type) {
		Base64 encoder = new Base64();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, type, baos);
		} catch (IOException e) {
//			LOG.info("");
		}
		return new String(encoder.encode((baos.toByteArray())));
	}

	private static InputStream BaseToInputStream(String base64string) {
		ByteArrayInputStream stream = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes1 = decoder.decodeBuffer(base64string);
			stream = new ByteArrayInputStream(bytes1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return stream;
	}


	public static BufferedImage base64String2BufferedImage(String base64string) {
		BufferedImage image = null;
		try {
			InputStream stream = BaseToInputStream(base64string);
			image = ImageIO.read(stream);
		} catch (IOException e) {
//			LOG.info("");
		}
		return image;
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
