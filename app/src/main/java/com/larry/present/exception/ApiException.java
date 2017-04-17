package com.larry.present.exception;

/**
 * Created by larrysea on 16/3/10.
 *
 * 当api接口返回出现异常时使用目的是为了统一处理异常信息
 *
 */
public class ApiException extends RuntimeException {

    public static final int USER_NOT_EXIST = 100;                 //用户不存在
    public static final int WRONG_PASSWORD = 101;                 //错误密码
    public static final int ALREADY_REGISTER=452;                 //用户已经注册
    public static final int USER_DOSNT_EXIST=450;                 //用户不存在
    public static final int PASSWORD_WRONG=451;                   //密码错误
    public static final int CHANGE_MAC_IN_TWO_HOUR=461;           //更改两小时后生效
    public static final int UNBIND_MAC_FAIL=462;                  //设别解绑失败

    //老师用户已经注册

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;


        }
        return message;
    }
}

