package com.who.read.reading.mapper;

import com.who.read.reading.entity.Book;
import com.who.read.reading.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname UserMapper
 * @date 2020/3/26 10:01 AM
 * @Created by fengjiadong
 */
@Repository
public interface UserMapper {

	List<User> getUserByUserName(String userName);

	List<User> selectUserList();

	List<User> login(@Param("userName")String userName,@Param("password")String password);


}
