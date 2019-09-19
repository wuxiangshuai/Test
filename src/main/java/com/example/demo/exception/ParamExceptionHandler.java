package com.example.demo.exception;

import com.example.demo.utils.Result;
import com.example.demo.utils.ResultCode;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ParamExceptionHandler
 * @Author: WuXiangShuai
 * @Time: 16:28 2019/6/6.
 * @Description:
 */
@ControllerAdvice
public class ParamExceptionHandler {
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public String error(Exception e, HttpServletRequest request){
//        Map<Object, Object> map = new HashMap<>();
//        map.put("code", 500);
//        map.put("message",e.getMessage());
//        e.printStackTrace();
//        // 自定义状态码，用以进入定制错误页面解析流程
//        request.setAttribute("javax.servlet.error.status_code",500);
//        // 转发到/error
//        return "forward:/error";
//    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("出错了！");
    }

    /**
     * @Description: 拦截自定义异常
     * @Author: WuXiangShuai
     * @Date: 2019/6/10 11:15
     * @Param: [e 自定义异常]
     * @returns: com.example.demo.utils.Result
     */
    @ExceptionHandler(value = ParamException.class)
    @ResponseBody
    public Result paramError(ParamException e){
        e.printStackTrace();
        return Result.error().code(ResultCode.SQL_ERROR).message("出错了！");
    }

    /**
     * @Description: 拦截validation测试异常，即绑定数据格式异常
     * @Author: WuXiangShuai
     * @Date: 2019/6/10 11:16
     * @Param: [e]
     * @returns: com.example.demo.utils.Result
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result bindError(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
        return Result.error().code(ResultCode.SQL_ERROR).message(defaultMessage);
    }
}
