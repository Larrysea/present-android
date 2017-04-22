package com.larry.present.network.classes;

import com.larry.present.network.base.BaseCallModeal;

import retrofit2.http.Field;
import rx.Observable;

/*
*    
* 项目名称：present-android      
* 类描述： 班级的接口
* 创建人：Larry-sea   
* 创建时间：2017/4/20 16:53   
* 修改人：Larry-sea  
* 修改时间：2017/4/20 16:53   
* 修改备注：   
* @version    
*    
*/
public interface Iclasses {

    Observable<BaseCallModeal<String>>  getClassId(@Field("className")String className);

}
