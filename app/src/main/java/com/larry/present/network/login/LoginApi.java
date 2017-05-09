package com.larry.present.network.login;

import com.alibaba.fastjson.JSONObject;
import com.larry.present.loginregister.dto.LoginSuccessDto;
import com.larry.present.loginregister.dto.StudentLoginSuccessDto;
import com.larry.present.loginregister.dto.TeacherLoginSuccessDto;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.base.JsonUtil;
import com.larry.present.network.base.RxjavaUtil;

import retrofit2.Retrofit;
import rx.Observer;
import rx.Subscription;

/*
*    
* 项目名称：present-android      
* 类描述： 登录接口
* 创建人：Larry-sea   
* 创建时间：2017/4/18 18:53   
* 修改人：Larry-sea  
* 修改时间：2017/4/18 18:53   
* 修改备注：   
* @version    
*    
*/
public class LoginApi {

    private static Retrofit mRetrofit;

    public LoginApi(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }


    /**
     * 用户登录方法
     *
     * @param observer 订阅者
     * @param password 用户注册手机号
     */
    public Subscription userLogin(Observer<LoginSuccessDto> observer, String userName, String password) {
        return RxjavaUtil.subscribe(mRetrofit.create(Ilogin.class)
                .login(userName, password)
                .map(new ApiService.HttpResultFunc<LoginSuccessDto>()), observer);
    }


    /**
     * 学生登录方法
     *
     * @param observer 订阅者
     * @param password 用户注册手机号
     */
    public Subscription studentLogin(Observer<StudentLoginSuccessDto> observer, String phone, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phone);
        jsonObject.put("password", password);
        return RxjavaUtil.subscribe(mRetrofit.create(Ilogin.class)
                .studentLogin(JsonUtil.convertObjectToRequestBody(jsonObject))
                .map(new ApiService.HttpResultFunc<StudentLoginSuccessDto>()), observer);
    }


    /**
     * 老师登录方法
     *
     * @param observer 订阅者
     * @param password 用户注册手机号
     */
    public Subscription teacherLogin(Observer<TeacherLoginSuccessDto> observer, String phone, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phone);
        jsonObject.put("password", password);
        return RxjavaUtil.subscribe(mRetrofit.create(Ilogin.class)
                .teacherLogin(JsonUtil.convertObjectToRequestBody(jsonObject))
                .map(new ApiService.HttpResultFunc<TeacherLoginSuccessDto>()), observer);
    }


}
