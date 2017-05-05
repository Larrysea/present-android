package com.larry.present.network.teacher;

import com.larry.present.bean.teacher.Teacher;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.base.RxjavaUtil;

import retrofit2.Retrofit;
import rx.Observer;

/*
*    
* 项目名称：present-android      
* 类描述：   
* 创建人：Larry-sea   
* 创建时间：2017/5/5 10:38   
* 修改人：Larry-sea  
* 修改时间：2017/5/5 10:38   
* 修改备注：   
* @version    
*    
*/
public class TeacherApi {
    private static Retrofit mRetrofit;

    public TeacherApi(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }


    /**
     * 提交学生信息
     */
    public void submitTeacherInfo(Observer<String> observer, Teacher teacher) {
        RxjavaUtil.subscribe(mRetrofit.create(Iteacher.class)
                .submitStudentInfo(teacher)
                .map(new ApiService.HttpResultFunc<String>()), observer);
    }
}
