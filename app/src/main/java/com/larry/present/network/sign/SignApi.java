package com.larry.present.network.sign;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.larry.present.bean.sign.CourseSignInfoDto;
import com.larry.present.bean.sign.StudentCourseSignDto;
import com.larry.present.bean.sign.StudentSignInfoOfTermDto;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.base.JsonUtil;
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
     * @param courseSignId 所有发现的课程签到id
     * @param studentId    学生id
     * @param date         日期
     * @param classId      学生所在的班级id
     * @return
     */

    public Subscription studentSign(Observer<String> observer, List<String> courseSignId, String studentId, String date, String classId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courseSignIdList", courseSignId);
        jsonObject.put("studentId", studentId);
        jsonObject.put("signTime", date);
        jsonObject.put("classId", classId);
        return RxjavaUtil.subscribe(mRetrofit.create(IsignApi.class)
                .studentSign(JsonUtil.convertObjectToRequestBody(jsonObject))
                .map(new ApiService.HttpResultFunc<String>()), observer);
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("teacherId", teacherId);
        jsonObject.put("courseId", courseId);
        jsonObject.put("signStartType", signType);
        jsonObject.put("validOfTime", validOfTime);
        return RxjavaUtil.subscribe(mRetrofit.create(IsignApi.class)
                .selectCourseToSign(JsonUtil.convertObjectToRequestBody(jsonObject))
                .map(new ApiService.HttpResultFunc<String>()), observer);
    }


    /**
     * 选择班级进行签到
     *
     * @param observer     事件监听器
     * @param courseSignId 课程签到id
     * @param classArray   班级数组
     * @return
     */

    public Subscription selectClassToSign(Observer<String> observer, String courseSignId, JSONArray classArray) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courseSignId", courseSignId);
        jsonObject.put("classArray", classArray);
        return RxjavaUtil.subscribe(mRetrofit.create(IsignApi.class)
                .selectClassToSign(JsonUtil.convertObjectToRequestBody(jsonObject))
                .map(new ApiService.HttpResultFunc<String>()), observer);
    }


    /**
     * 获取某个课程的一个学期的记录
     *
     * @param observer
     * @param teacherId 老师id
     * @param courseId  课程id
     * @param classId   班级id
     * @return
     */

    public Subscription getCourseSignInfoInTerm(Observer<List<StudentSignInfoOfTermDto>> observer, String teacherId, String courseId, String classId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("teacherId", teacherId);
        jsonObject.put("courseId", courseId);
        jsonObject.put("classId", classId);
        return RxjavaUtil.subscribe(mRetrofit.create(IsignApi.class)
                .getCourseSignInfoInTerm(JsonUtil.convertObjectToRequestBody(jsonObject))
                .map(new ApiService.HttpResultFunc<List<StudentSignInfoOfTermDto>>()), observer);
    }


    /**
     * 返回某一次某个班级的所有学生的签到记录
     *
     * @param observer     观察者
     * @param courseSignId 课程签到id
     * @param classId      班级id
     * @return
     */

    public Subscription getCourseSignInfoOfOnce(Observer<List<StudentCourseSignDto>> observer, String courseSignId, String classId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courseSignId", courseSignId);
        jsonObject.put("classId", classId);
        return RxjavaUtil.subscribe(mRetrofit.create(IsignApi.class)
                .getCourseSignInfoOfOnce(JsonUtil.convertObjectToRequestBody(jsonObject))
                .map(new ApiService.HttpResultFunc<List<StudentCourseSignDto>>()), observer);

    }


    /**
     * 学生获取某门课程的每次签到信息
     *
     * @param courseId  课程id
     * @param studentId 学生id
     * @return
     */

    public Subscription studentGetCourseSignInfoDto(Observer<List<CourseSignInfoDto>> observer, String courseId, String studentId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courseId", courseId);
        jsonObject.put("studentId", studentId);
        return RxjavaUtil.subscribe(mRetrofit.create(IsignApi.class)
                .studentGetCourseSignInfoDto(JsonUtil.convertObjectToRequestBody(jsonObject))
                .map(new ApiService.HttpResultFunc<List<CourseSignInfoDto>>()), observer);

    }


    /**
     * 判断学生是否加入了这门课程
     *
     * @param observer 回调观察者
     * @param courseId 课程id
     * @param classId  班级id
     * @return
     */
    public Subscription isJoinTheCourse(Observer<String> observer, String courseId, String classId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courseId", courseId);
        jsonObject.put("classId", classId);
        return RxjavaUtil.subscribe(mRetrofit.create(IsignApi.class)
                .isJoinCourse(JsonUtil.convertObjectToRequestBody(jsonObject))
                .map(new ApiService.HttpResultFunc<String>()), observer);

    }


}
