package com.ck.service;

import com.ck.entity.User;

import java.util.List;

/**
 * Created by dudycoco on 17-5-4.
 */
public interface IUserService {

    List<User> findAll();

    void insert(User user)throws Exception;
}
