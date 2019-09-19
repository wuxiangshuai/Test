package com.example.demo.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: ParamException
 * @Author: WuXiangShuai
 * @Time: 16:00 2019/6/6.
 * @Description:
 */
@ApiModel("全局异常")
public class ParamException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "异常消息")
    private String msg;

    public ParamException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ParamException() {
    }
}
