package com.admin.common.util;

public class JsonConstant {
    //结果级代码提示
    public static final int SUCCESS = 200; //操作成功
    public static final int RELOGIN = 205; //操作成功
    public static final int ERROR = 500; //操作失败
    //业务级代码提示
    public static final int NOTLOGIN = 1003; //未登录
    public static final int LOGIN = 1004; //已登录
    public static final int PARAMETER_ERROR = 1005; //参数有误
    public static final int PARAMETER_NOT_MATCH = 1006; //请求参数类型不匹配
    public static final int LOGOUT = 1007; //注销成功
    public static final int OLD_PASSWORD_ERROR = 1008; //旧密码不正确
    public static final int PARSE_XML_FAIL = 1009; //解析XML失败
    public static final int GET_SIGN_FAIL = 1010; //获取签名失败
    public static final int VERIFY_CODE_WRITE_ERROR = 1011; //验证码填写错误
    public static final int VERIFY_CODE_FAIL = 1012; //验证码失效
    public static final int TIME_CONVERT_ERROR = 1013; //时间转换错误
    public static final int PASSWORD_NOT_MATCH_IN_TWICE = 1014; //两次密码输入不一致

    //广告管理
    public static final int NO_USING_ADVERTISEMENT = 1100; //没有广告需要展示

    public static final int BANKCARD_NOT_BIND = 2001; //银行卡未绑定
    public static final int INTEGRAL_LESS_THAN_TRADE = 2002; //积分余额剩余不足

    //用户注册START
    public static final int INDIVIDUAL_ID_REGISTERED = 2003; //个性ID已被注册
    public static final int PHONE_NUMBER_NOT_EXIST = 2004; //手机号不存在或服务器数据未录入
    public static final int PHONE_NUMBER_REGISTERED = 2005; //手机号已被注册
    public static final int REFERRAL_CODE_INVALID = 2006; //推荐码无效
    //用户注册END
    public static final int ORDER_CLOSED = 2007; //订单已关闭
    public static final int ORDER_DEAL_WITH_OR_CLOSED = 2008; //订单已处理或已关闭

    //商戶
    public static final int BUSINESS_USER_EXIST = 2100; //商戶存在
    public static final int BUSINESS_USER_NOT_EXIST = 2101; //商戶不存在

    public static final String RELOGIN_MSG = "请重新登录"; //操作成功
    public static final String REDIS_LOGIN_INFO_PREFIX = "user:";
}
