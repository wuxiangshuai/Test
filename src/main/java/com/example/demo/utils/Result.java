package com.example.demo.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Result
 * @Author: WuXiangShuai
 * @Time: 16:29 2019/6/6.
 * @Description:
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result {

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private Result() {
    }

    /**
     * 成功方法
     *
     * @return
     */
    public static Result ok() {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.OK);
        r.setMessage("成功！");
        return r;
    }

    /**
     * 失败方法
     *
     * @return
     */
    public static Result error() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败！");
        return r;
    }

    /**
     * 接收消息
     *
     * @return
     */
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 接收代码
     *
     * @param code
     * @return
     */
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 接收数据
     */
    public Result data(String key, Object obj) {
        this.data.put(key, obj);
        return this;
    }

    /**
     * 接收数据
     */
    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
