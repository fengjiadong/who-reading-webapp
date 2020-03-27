package com.who.read.reading.service;

import com.who.read.reading.controller.BookController;
import com.who.read.reading.entity.Book;
import com.who.read.reading.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname BookService
 * @date 2020/3/26 9:58 AM
 * @Created by fengjiadong
 */
@Service
public class BookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

	@Autowired
	BookMapper bookMapper;

	public Book selectBookById(int id) {
		LOGGER.info("进入BookService类的selectBookById方法");
		return bookMapper.selectBookById(id);

	}

	public List<Book> selectBookListByName(int name) {
		LOGGER.info("进入BookService类的selectBookListByName方法");
		return bookMapper.selectBookListByName(name);
	}

	public Integer addBook(Book book) {
		LOGGER.info("进入BookService类的addBook方法");
		return bookMapper.addBook(book);
	}
}
