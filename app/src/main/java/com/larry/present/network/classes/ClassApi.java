package com.larry.present.network.classes;

import com.larry.present.network.base.ApiService;
import com.larry.present.network.base.RxjavaUtil;

import retrofit2.Retrofit;
import rx.Observer;
import rx.Subscription;

/*
*    
* 项目名称：present-android      
* 类描述：   
* 创建人：Larry-sea   
* 创建时间：2017/4/20 17:28   
* 修改人：Larry-sea  
* 修改时间：2017/4/20 17:28   
* 修改备注：   
* @version    
*    
*/
public class ClassApi {


    private static Retrofit mRetrofit;

    public ClassApi(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }


    /**
     * 根据班级名称查询班级id
     *
     * @param observer
     * @param className
     * @return
     */
    public Subscription queryClassId(Observer<String> observer, String className) {
        return RxjavaUtil.subscribe(mRetrofit.create(Iclasses.class)
                .getClassId(className)
                .map(new ApiService.HttpResultFunc<String>()), observer);

    }

    /**
     * 添加班级信息
     * @param observer      观察者
     * @param className     班级名称
     * @param schoolId      学校id
     * @return
     */
    public Subscription addClasses(Observer<String> observer, String className, String schoolId) {
        return RxjavaUtil.subscribe(mRetrofit.create(Iclasses.class)
                .addClasses(className, schoolId)
                .map(new ApiService.HttpResultFunc<String>()), observer);
    }


}
