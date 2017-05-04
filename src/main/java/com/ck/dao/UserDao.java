package com.ck.dao;

import com.ck.dao.sql.SqlProvider;
import com.ck.entity.User;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dudycoco on 17-5-4.
 */
@Mapper
public interface UserDao {

    @Select("select * from user ")
    List<User> findAll();

    @InsertProvider(type = SqlProvider.class,method = "insert")
    void insert(User user);
}
