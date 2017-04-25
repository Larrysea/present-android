package com.larry.present.network.course;

import com.larry.present.bean.course.Course;
import com.larry.present.network.base.BaseCallModeal;

import java.util.List;

import retrofit2.http.Field;
import rx.Observable;

/*
*    
* 项目名称：present-android      
* 类描述： 课程的相关接口
* 创建人：Larry-sea   
* 创建时间：2017/4/25 22:29   
* 修改人：Larry-sea  
* 修改时间：2017/4/25 22:29   
* 修改备注：   
* @version    
*    
*/
public interface Icourse {


    /**
     * 老师获取所有的课程
     *
     * @param teacherId 老师id
     * @return 返回老师所教学的课程
     */
    Observable<BaseCallModeal<List<Course>>> teacherGetAllCourse(@Field("teacherId") String teacherId);


    /**
     * 学生获取所有的班级id
     *
     * @param studentId 学生id
     * @return 返回学生所在班级的所有课程
     */
    Observable<BaseCallModeal<List<Course>>> studentGetAllCourse(@Field("studentId") String studentId);


    /**
     * 添加课程接口
     *
     * @param courseName 课程名
     * @param teacherId  老师id
     * @return
     */
    Observable<BaseCallModeal<String>> addCourse(@Field("courseName") String courseName, @Field("tacherId") String teacherId);

}
