package com.larry.present.network.student;

import com.larry.present.network.base.BaseCallModeal;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/*
*    
* 项目名称：present-android      
* 类描述：  学生的接口
* 创建人：Larry-sea   
* 创建时间：2017/4/20 15:19   
* 修改人：Larry-sea  
* 修改时间：2017/4/20 15:19   
* 修改备注：   
* @version    
*    
*/
public interface Istudent {
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("submitStudentInfo")
    Observable<BaseCallModeal<String>> submitStudentInfo(@Body RequestBody student);
}
