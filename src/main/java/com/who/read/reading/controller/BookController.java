package com.who.read.reading.controller;

import com.who.read.reading.entity.Book;
import com.who.read.reading.service.BookService;
import com.who.read.reading.utils.BookUtils;
import com.who.read.reading.utils.JsonManager;
import com.who.read.reading.utils.ServiceUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname BookController
 * @date 2020/3/26 9:57 AM
 * @Created by fengjiadong
 */
@RestController
@RequestMapping("/api/book")
public class BookController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

	@Autowired
	BookService bookService;

	@Autowired
	BookUtils bookUtils;

	@RequestMapping("getBook/{id}")
	public Object getBook(@PathVariable int id) {
		LOGGER.info("进入BookController类的getBook方法");
		Book sel = this.bookService.selectBookById(id);
		if (sel == null) {
			return ServiceUtils.returnRestlt("0", "未查询到书籍!", "").toString();
		}
		return ServiceUtils.returnRestlt("1", "查询到书籍。", JsonManager.getJson(sel)).toString();
	}

	@RequestMapping("selectBookListByName/{name}")
	public Object selectBookListByName(@PathVariable String name) {
		LOGGER.info("进入BookController类的selectBookListByName方法");
		List<Book> sel = this.bookService.selectBookListByName(name);
		if (sel == null) {
			return ServiceUtils.returnRestlt("0", "未查询到书籍!", "").toString();
		}
		return ServiceUtils.returnRestlt("1", "查询到书籍。", sel).toString();
	}

	@RequestMapping("selectBookListByType/{type}")
	public Object selectBookListByType(@PathVariable String type) {
		Book book = new Book();
		book.setType(type);
		List<Book> books = bookService.selectBookList(book);
		return ServiceUtils.returnRestlt("1", "查询到书籍。", books).toString();
	}
	@RequestMapping(path = "addBook", method = RequestMethod.POST)
	public Object addBook(@RequestParam("info") JSONObject info) {
		LOGGER.info("进入BookController类的addBook方法");
		Integer resultCount = 0;
		try {
			Book book = JsonManager.setEntityValue(Book.class, info);
			resultCount = this.bookService.addBook(book);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage());
		} catch (InstantiationException e) {
			LOGGER.error(e.getMessage());
		}
		if (resultCount > 0) {
			return ServiceUtils.returnRestlt("1", "新增成功!", resultCount).toString();
		} else {
			return ServiceUtils.returnRestlt("0", "新增失败!", resultCount).toString();
		}
	}

	// 爬虫拿到数据
	@RequestMapping(path = "insert", method = RequestMethod.GET)
	public Object insert(){
		Book book = new Book();
		List<Book> books = bookService.selectBookList(book);
		for (Book book1 : books) {
			JSONObject bookInfo = bookUtils.getBookInfo(book1.getNum());
			if (bookInfo == null) {
				continue;
			}
			book1.setImg(bookInfo.get("src").toString());
			book1.setIntroduce(bookInfo.get("intro").toString());
			bookService.updateById(book1);
			System.out.println(bookInfo.toString());
		}
		return "";
	}


}
