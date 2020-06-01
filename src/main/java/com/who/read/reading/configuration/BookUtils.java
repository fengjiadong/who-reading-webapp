package com.who.read.reading.configuration;

import com.who.read.reading.entity.Book;
import com.who.read.reading.service.BookService;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @Classname BookUtils
 * @date 2020/3/31 10:27 AM
 * @Created by fengjiadong
 */
@Component
public class BookUtils {

	@Autowired
	BookService bookService;

	public JSONObject pachong(){
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.biquge.info/list/7_1.html").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element newscontent = doc.getElementById("newscontent");
		Elements images = doc.getElementsByClass("image");
		Integer resultCount = 0;
		String type = "";
		Elements li = newscontent.getElementsByTag("li");
		for (Element element : li) {
			Elements s1 = element.getElementsByClass("s1");
			Elements s2 = element.getElementsByClass("s2");
			Elements s4 = element.getElementsByClass("s4");
			Elements s5 = element.getElementsByClass("s5");
			String attr = s2.get(0).getElementsByTag("a").get(0).attr("href");
			String href = attr.substring(attr.indexOf("fo/")+3, attr.length() - 1);
			Book book = new Book();
			book.setName(s2.get(0).getElementsByTag("a").get(0).html());
			book.setNum(href);
			book.setType(s1.get(0).html());
			type = s1.get(0).html();
			if (s4.size() < 1) {
				book.setAuthor(s5.get(0).html());
			}else{
				book.setAuthor(s4.get(0).html());
			}
			List<Book> books = bookService.selectBookList(book);
			if (books == null || books.isEmpty()) {
				resultCount += this.bookService.addBook(book);
			}
		}
		for (Element image : images) {
			// 新建一本书
			Book book = new Book();
			book.setName(image.getElementsByTag("img").get(0).attr("alt"));
			book.setImg(image.getElementsByTag("img").get(0).attr("src"));
			String attr = image.getElementsByTag("a").get(0).attr("href");
			String href = attr.substring(attr.indexOf("fo/")+3, attr.length() - 1);
			book.setNum(href);
			book.setType(type);
			List<Book> books = bookService.selectBookList(book);
			if (books == null || books.isEmpty()) {
				resultCount += this.bookService.addBook(book);
			}
		}
		return null;
	}

	/**
	 * 传入book的num 得到简介和图片
	 * @param num
	 * @return
	 */
	public JSONObject getBookInfo(String num) {
		JSONObject jsonObject = new JSONObject();
		String url = "http://www.biquge.info/" + num + "/";
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			return null;
		}
		Element intro = doc.getElementById("intro");
		jsonObject.put("intro", intro.getElementsByTag("p").get(0).html());
		Element fmimg = doc.getElementById("fmimg");
		Elements img = fmimg.getElementsByTag("img");
		String src = img.attr("src");
		jsonObject.put("src", src);
		return jsonObject;
	}

}
