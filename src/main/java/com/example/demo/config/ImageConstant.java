package com.example.demo.config;

import java.io.File;

/**
 * com.ctrl.education.core.file
 *
 * @author liyang
 * @name ImageConstant
 * @description
 * @date 2018-06-10 下午9:11
 */
public class ImageConstant {
    // 上传文件根路径
    // public static final String ROOT_PATH = "/www/wwwroot/
    //133服务器路径
//     public static final String ROOT_PATH = "/usr/local/project/uploadDir/";//正式
//    public static final String ROOT_PATH = "/usr/local/project_qizhi/uploadDir/";//测试
     public static final String ROOT_PATH = "F:\\uploadFile\\";


    // 图片路径
    public static final String EDITOR_PATH = File.separator + "uploadFile" + File.separator + "image" + File.separator + "editor"; // 富文本编译器图片路径
    public static final String ENTERPRISE_FILE_PATH = File.separator + "uploadFile" + File.separator + "excel" + File.separator + "enterprise"; //企业模块附件
    public static final String ENTERPRISE_SETTING_FILE_PATH = File.separator + "uploadFile" + File.separator + "excel" + File.separator + "enterpriseSetting"; //企业设置模块附件
    public static final String COVER_FILE_PATH = File.separator + "uploadFile" + File.separator + "image" + File.separator + "cover";//封面图路径
    public static final String AVATAR_FILE_PATH = File.separator + "uploadFile" + File.separator + "image" + File.separator + "avatar";//头像
    public static final String ICON_FILE_PATH = File.separator + "uploadFile" + File.separator + "image" + File.separator + "icon";//前端图标
    public static final String TEACHER_FILE_PATH = File.separator + "uploadFile" + File.separator + "image" + File.separator + "teacher";//教师照片
    public static final String TRAIN_FILE_PATH = File.separator + "uploadFile" + File.separator + "image" + File.separator + "train";//培训照片
    public static final String BANNER_FILE_PATH = File.separator + "uploadFile" + File.separator + "image" + File.separator + "banner";//banner
    public static final String QUESTIONNAIRE_FILE_PATH = File.separator + "uploadFile" + File.separator + "image" + File.separator + "questionnaire";//调查问卷封面图
    //视频路径
    public static final String COURSEWARE_VIDEO_PATH = File.separator + "uploadFile" + File.separator + "video" + File.separator + "courseware";//课程视频
    //录入试题提交路径
    public static final String TEMPLATE_PATH = File.separator + "uploadFile" + File.separator + "Template" + File.separator + "diploma";//证书文件
    //证书路径
    public static final String QUESTION_EXCEL_PATH = File.separator + "uploadFile" + File.separator + "excel" + File.separator + "question";//Excel文件
    // 异常编码及提示
    public static final String UPLOAD_IMG_SUCCESS_MSG = "图片上传成功";
    public static final String NULL_EXCEPTION_CODE = "I0001";
    public static final String NULL_EXCEPTION_MSG = "上传图片不可为空";
}
