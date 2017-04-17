package com.larry.present.common.context;

import android.content.Context;

import com.larry.present.common.basetemplate.BaseApplication;

import cn.smssdk.SMSSDK;


/*
*    
* 项目名称：Present      
* 类描述： 应用的设备上下文
* 创建人：Larry-sea   
* 创建时间：2017/4/5 16:31   
* 修改人：Larry-sea  
* 修改时间：2017/4/5 16:31   
* 修改备注：   
* @version    
*    
*/
public class AppContext extends BaseApplication {

    private static Context context;

    public static Context getContext() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        SMSSDK.initSDK(this, "1cda9753bd63d", "c107a5e6412c2650b64d6185c3dcb292");
    }

    @Override
    public void initConfigs() {
    }


}
