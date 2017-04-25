package com.larry.present.network.sign;

import com.alibaba.fastjson.JSONArray;
import com.larry.present.bean.school.School;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.base.RxjavaUtil;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observer;
import rx.Subscription;

/*
*    
* 项目名称：present-android      
* 类描述： 签到api的接口调用方法
* 创建人：Larry-sea   
* 创建时间：2017/4/25 11:42   
* 修改人：Larry-sea  
* 修改时间：2017/4/25 11:42   
* 修改备注：   
* @version    
*    
*/
public class SignApi {

    private static Retrofit mRetrofit;

    public SignApi(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }


    /**
     * 学生开始签到
     *
     * @param observer     订阅者
     * @param courseSignId 课程签到id
     * @param studentId    学生id
     * @param date         日期
     * @param type         类型
     */
    public Subscription studentSign(Observer<List<School>> observer, String courseSignId, String studentId, String date, String type) {
        return RxjavaUtil.subscribe(mRetrofit.create(IsignApi.class)
                .studentSign(courseSignId, studentId, date, type)
                .map(new ApiService.HttpResultFunc<List<School>>()), observer);
    }


    /**
     * 选择课程开始签到
     *
     * @param teacherId   老师id
     * @param courseId    课程id
     * @param signType    签到类型
     * @param validOfTime 有效时间
     * @return
     */
    public Subscription selectCourseToSign(Observer<String> observer, String teacherId, String courseId, String signType, int validOfTime) {
        return RxjavaUtil.subscribe(mRetrofit.create(IsignApi.class)
                .selectCourseToSign(teacherId, courseId, signType, validOfTime)
                .map(new ApiService.HttpResultFunc<String>()), observer);
    }


    /**
     * 选择班级进行签到
     *
     * @param observer       事件监听器
     * @param courseSignId   课程签到id
     * @param classArray     班级数组
     * @return
     */
    public Subscription selectClassToSign(Observer<String> observer, String courseSignId, JSONArray classArray) {
        return RxjavaUtil.subscribe(mRetrofit.create(IsignApi.class)
                .selectClassToSign(courseSignId, classArray)
                .map(new ApiService.HttpResultFunc<String>()), observer);
    }


}
