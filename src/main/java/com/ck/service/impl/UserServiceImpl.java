package com.ck.service.impl;

import com.ck.dao.UserDao;
import com.ck.entity.User;
import com.ck.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dudycoco on 17-5-4.
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void insert(User user)throws Exception{
        userDao.insert(user);
        int x = 4/0;
    }
}
