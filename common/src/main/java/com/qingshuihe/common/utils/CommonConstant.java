package com.qingshuihe.common.utils;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/8/29
 **/
public interface CommonConstant {

    /**
     * @Description: 请求成功
     * @Date: 2022/8/29
     * @Param null:
     **/
    int STATUS_SUCCESS = 200;
    /**
     * @Description: 请求失败
     * @Date: 2022/8/29
     * @Param null:
     **/
    int STATUS_ERROR = 201;
    /**
     * @Description: 默认用户名
     * @Date: 2022/8/29
     * @Param null:
     **/
    String USER_NAME = "qingshuihe";
    /**
     * @Description: 默认密码
     * @Date: 2022/8/29
     * @Param null:
     **/
//    String PASS_WORD = "$10$8nOb303yc701dni94v2UmufHVrH5fgqwU4e8ftbhUTwAATzCWnM6m";
    String PASS_WORD = "qingshuihe";

    /**
     * @Description: 请求头的token
     * @Date: 2022/8/29
     **/
    String TOKEN_STR = "token";
    /**
     * @Description: 重置密码时为用户设置的默认权限点
     * @Date: 2023/1/5
     **/
    String DEFUALT_PERMISSION = "resetPassword";

    //mq日志topic
    String KAFKA_TOPIC_QINGSHUIHE_LOG = "qingshuiheLog";
    //mq日志group
    String KAFKA_GROUP_QINGSHUIHE_LOG ="qingshuiheLogGroup";

    String REST_USER_NAME = "REST_USER_NAME";
    String FRONT_PAGE_SETPW = "FRONT_PAGE_SETPW";
    String VAILD_TIME = "VAILD_TIME";
    String VERIFY_CODE = "VERIFY_CODE";
}
