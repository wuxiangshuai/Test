package com.example.demo.controller;

import com.example.demo.bean.UserInfo;
import com.example.demo.service.UserService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @ClassName: BaseController
 * @Author: WuXiangShuai
 * @Time: 12:06 2019/6/6.
 * @Description:
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("url")
    @ResponseBody
    public Result getUrl(@Validated UserInfo user){
        List<UserInfo> infos = userService.selectUsers();
        return Result.ok().data("infos", infos);
    }

}
