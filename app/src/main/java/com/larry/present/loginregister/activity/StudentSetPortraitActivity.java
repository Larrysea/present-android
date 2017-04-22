package com.larry.present.loginregister.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.larry.present.R;
import com.larry.present.common.util.CameraController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
*    
* 项目名称：present-android      
* 类描述： 学生设置头像接口
* 创建人：Larry-sea   
* 创建时间：2017/4/19 14:54   
* 修改人：Larry-sea  
* 修改时间：2017/4/19 14:54   
* 修改备注：   
* @version    
*    
*/
public class StudentSetPortraitActivity extends AppCompatActivity {
    @BindView(R.id.iv_portrait_portrait)
    ImageView ivPortraitPortrait;

    @OnClick(R.id.btn_portrait_take_picture)
    void takePicture(View view) {
        CameraController cameraController = new CameraController(StudentSetPortraitActivity.this);
        cameraController.getCameraInstance();
        cameraController.hasCamera();
        cameraController.takePicture();
        cameraController.releaseCamera();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_portrait);
        ButterKnife.bind(this);
    }
}
