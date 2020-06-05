package com.who.read.reading.mapper;

import com.who.read.reading.entity.Book;
import com.who.read.reading.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

	List<User> selectUserList(@Param("start")Integer start,@Param("end")Integer end);

	@Select("select count(0) from `dm.user`")
	Integer selectUserListCount();

	List<User> login(@Param("userName")String userName,@Param("password")String password);

	List<User> queryUserByName(@Param("name")String name,@Param("start")Integer start,@Param("end")Integer end);

	@Select("select count(0) from `dm.user` where `name` like #{name} or `userName` like #{name} or `phone` like #{name} or `id` like #{name} ")
	Integer queryUserByNameCount(@Param("name")String name);

}
