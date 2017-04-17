package com.larry.present.loginregister.activity;

import android.support.v4.app.Fragment;

import com.larry.present.common.basetemplate.BaseFragmentActivity;
import com.larry.present.loginregister.fragment.SubmitTeacherInfoFragment;

/*
*    
* 项目名称：present      
* 类描述： 老师提交个人信息的fragment
* 创建人：Larry-sea   
* 创建时间：2017/4/16 22:10   
* 修改人：Larry-sea  
* 修改时间：2017/4/16 22:10   
* 修改备注：   
* @version    
*    
*/
public class SubmitTeacherInforActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SubmitTeacherInfoFragment();
    }
}
