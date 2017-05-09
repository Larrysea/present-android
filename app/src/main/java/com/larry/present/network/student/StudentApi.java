package com.larry.present.network.student;

import com.alibaba.fastjson.JSONObject;
import com.larry.present.bean.student.Student;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.base.JsonUtil;
import com.larry.present.network.base.RxjavaUtil;

import retrofit2.Retrofit;
import rx.Observer;

/*
*    
* 项目名称：present-android      
* 类描述： 学生信息的相关api
* 创建人：Larry-sea   
* 创建时间：2017/4/20 15:47   
* 修改人：Larry-sea  
* 修改时间：2017/4/20 15:47   
* 修改备注：   
* @version    
*    
*/
public class StudentApi {

    private static Retrofit mRetrofit;

    public StudentApi(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }


    /**
     * 提交学生信息
     */
    public void submitStudentInfo(Observer<String> observer, Student student) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("student", student);
        RxjavaUtil.subscribe(mRetrofit.create(Istudent.class)
                .submitStudentInfo(JsonUtil.convertObjectToRequestBody(jsonObject))
                .map(new ApiService.HttpResultFunc<String>()), observer);
    }


}
