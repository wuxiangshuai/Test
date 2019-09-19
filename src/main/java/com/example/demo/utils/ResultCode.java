package com.example.demo.utils;

/**
 * @ClassName: ResultCode
 * @Author: WuXiangShuai
 * @Time: 16:30 2019/6/6.
 * @Description:
 */
public interface ResultCode {

    int OK = 20000;//成功
    int ERROR = 20001;//失败
    int LOGIN_ERROR = 20002;//用户名或密码错误
    int ACCESS_ERROR = 20003;//权限不足
    int REMOTE_ERROR = 20004;//远程调用失败
    int REPEAT_ERROR = 20005;//重复操作
    int SQL_ERROR = 20006;//sql错误

    int NOT_NOTIFY = 20100;//未更新操作

    int CACHE_ERROR = 20200;//缓存查询失败
}
