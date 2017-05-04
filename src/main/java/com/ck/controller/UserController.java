package com.ck.controller;

import com.ck.entity.User;
import com.ck.service.IUserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dudycoco on 17-5-4.
 */
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/user")
    @ResponseBody
    public String index(){
        Gson gson = new Gson();
        return gson.toJson(userService.findAll());
    }

    @RequestMapping("/insert")
    @ResponseBody
    public String insert() throws Exception {
        Gson gson = new Gson();
        User user = new User();
        user.setName("a");
        userService.insert(user);
        return gson.toJson(userService.findAll());
    }
}
