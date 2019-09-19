package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.bean.UserInfo;

import java.util.List;

/**
 * @ClassName: UserService
 * @Author: WuXiangShuai
 * @Time: 10:56 2019/6/10.
 * @Description:
 */
public interface UserService extends IService<UserInfo> {
    List<UserInfo> selectUsers();
}
