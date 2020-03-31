package com.who.read.reading.mapper;

import com.who.read.reading.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname BookMapper
 * @date 2020/3/26 10:01 AM
 * @Created by fengjiadong
 */
@Repository
public interface BookMapper {
	/**
	 * 查询一条书籍信息
	 * @param id id
	 * @return Book实体
	 */
	Book selectBookById(int id);

	List<Book> selectBookListByName(String name);

	Integer addBook(Book book);

	List<Book> selectBookList(Book book);

	Integer updateById(Book book);

}
