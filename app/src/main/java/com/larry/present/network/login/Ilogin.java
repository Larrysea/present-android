package com.larry.present.network.login;

import com.larry.present.loginregister.dto.LoginSuccessDto;
import com.larry.present.network.base.BaseCallModeal;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/*
*    
* 项目名称：present-android      
* 类描述： 登录接口   使用范围老师登录，学生登录都是这个接口
* 创建人：Larry-sea   
* 创建时间：2017/4/18 18:44   
* 修改人：Larry-sea  
* 修改时间：2017/4/18 18:44   
* 修改备注：   
* @version    
*    
*/
public interface Ilogin {

    /**
     * 用户登录接口
     *
     * @param userName
     * @param password
     * @return  返回登录之后的唯一授权token
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("login")
    Observable<BaseCallModeal<LoginSuccessDto>> login(String userName, String password);




}
