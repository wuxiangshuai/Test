package com.example.demo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @ClassName: User
 * @Author: WuXiangShuai
 * @Time: 10:58 2019/6/10.
 * @Description:
 */
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("id")
    private BigInteger id;

    @TableField("login_name")
    private String loginName;

    @TableField("nick_name")
    private String nickName;

    @TableField("passwd")
    private String passwd;

    @TableField("name")
    private String name;

    @TableField("phone_num")
    private String phoneNum;

    @TableField("email")
    private String email;

    @TableField("head_img")
    private String headImg;

    @TableField("user_level")
    private String userLevel;

}
