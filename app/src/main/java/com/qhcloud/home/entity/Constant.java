package com.qhcloud.home.entity;

/**
 * 常量
 * @author youngbin
 */
public class Constant {

    //调试
    public static boolean DEBUG = true;
    //是否异常崩溃
    public static boolean EXCEPTION = false;
    //APP名
    public static String APP_NAME = "qlink_demo";

    /**
     * 消息广播
     */
    public class Message{

        //app退出
        public static final String APP_FINISH = "com.qhcloud.home.app.exit";
    }

    /**
     * 配置文件KEY
     */
    public class Configure{
        //用户名
        public static final String USER = "user";
        //密码
        public static final String PASSWORD = "password";

    }

    /**
     * 网络配置
     */
    public class Service{
        //ip
        public static final String IP = "select.qihancloud.com";
        //端口
        public static final int PORT = 92;


    }



}
