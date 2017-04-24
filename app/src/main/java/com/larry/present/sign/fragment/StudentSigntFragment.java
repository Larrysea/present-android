package com.larry.present.sign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.larry.present.R;
import com.larry.present.common.basetemplate.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
*    
* 项目名称：present-android      
* 类描述： 学生开始签到fragment
* 创建人：Larry-sea   
* 创建时间：2017/4/24 20:47   
* 修改人：Larry-sea  
* 修改时间：2017/4/24 20:47   
* 修改备注：   
* @version    
*    
*/
public class StudentSigntFragment extends BaseFragment {

    @OnClick(R.id.btn_student_sign)
    void onClick(View view) {
        //todo 学生签到逻辑
        //   WifiSignUtil.checkHasWifiHost();

    }

    Button btnStudentSign;
    @BindView(R.id.iv_sign_defaullt)
    ImageView ivSignDefaullt;
    Unbinder unbinder;

    @Override
    public void initViews() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_student_sign;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
