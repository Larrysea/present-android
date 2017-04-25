package com.larry.present.network.sign;

import com.alibaba.fastjson.JSONArray;
import com.larry.present.network.base.BaseCallModeal;

import retrofit2.http.Field;
import rx.Observable;

/*
*    
* 项目名称：present-android      
* 类描述：  签到相关的api
* 创建人：Larry-sea   
* 创建时间：2017/4/25 8:08   
* 修改人：Larry-sea  
* 修改时间：2017/4/25 8:08   
* 修改备注：   
* @version    
*    
*/
public interface IsignApi {
    /**
     * 学生签到接口
     *
     * @param courseSignId 课程签到id
     * @param studentId    学生id
     * @param date         日期
     * @param signType     改变签到账户类型，1 代表是学生自己签到，2 代表是老师修改的学生签到状态
     * @return
     */
    Observable<BaseCallModeal> studentSign(@Field("courseSignId") String courseSignId,
                                           @Field("studentId") String studentId, @Field("date") String date, @Field("signType") String signType);


    /**
     * 老师选择课程进行签到
     *
     * @param courseId      课程id
     * @param signStartType 发起签到类型
     * @param teacherId     老师id
     * @param validOfTime   有效时间
     * @return
     */
    Observable<BaseCallModeal<String>> selectCourseToSign(@Field("courseId") String courseId, @Field("signStartType") String signStartType, @Field("teacherId") String teacherId, @Field("validOfTime") int validOfTime);


    /**
     * 选择班级开始签到
     *
     * @param courseSignId 课程签到id
     * @param classArray   班级数组
     * @return
     */
    Observable<BaseCallModeal<String>> selectClassToSign(@Field("courseSignId") String courseSignId, @Field("classArray") JSONArray classArray);

}
