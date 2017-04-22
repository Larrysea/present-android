package com.larry.present.network.schoolapi;

import com.larry.present.bean.school.School;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.base.RxjavaUtil;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observer;


/*
*    
* 项目名称：present      
* 类描述：   
* 创建人：Larry-sea   
* 创建时间：2017/4/9 11:53   
* 修改人：Larry-sea  
* 修改时间：2017/4/9 11:53   
* 修改备注：   
* @version    
*    
*/
public class GetSchoolApi {

    private static Retrofit mRetrofit;

    public GetSchoolApi(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }


    /**
     * 获取全国所有大学的方法
     *
     * @param observer 订阅者
     * @param phone    用户注册手机号
     */
    public void getAllSchool(Observer<List<School>> observer, RequestBody phone) {
        RxjavaUtil.subscribe(mRetrofit.create(IgetAllSchoolApi.class)
                .getAllSchool(phone)
                .map(new ApiService.HttpResultFunc<List<School>>()), observer);
    }


}
