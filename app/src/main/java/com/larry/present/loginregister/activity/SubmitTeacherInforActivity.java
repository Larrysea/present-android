package com.larry.present.loginregister.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.larry.present.R;
import com.larry.present.bean.teacher.Teacher;
import com.larry.present.common.subscribers.ProgressSubscriber;
import com.larry.present.common.subscribers.SubscriberOnNextListener;
import com.larry.present.common.util.CheckETEmptyUtil;
import com.larry.present.config.Constants;
import com.larry.present.network.teacher.TeacherApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 *
 */
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
public class SubmitTeacherInforActivity extends AppCompatActivity {
    @BindView(R.id.tv_teacher_mail)
    EditText tvTeacherMail;
    @BindView(R.id.tv_teacher_info_name)
    EditText tvTeacherInfoName;
    @BindView(R.id.tv_submit_teacher_password)
    EditText tvSubmitTeacherPassword;

    CheckETEmptyUtil checkETEmptyUtil = new CheckETEmptyUtil(SubmitTeacherInforActivity.this);

    /**
     * 学校id
     */
    String schoolId;

    /*
    * 手机号
    * */
    String phone;

    /**
     * 提交老师信息observer
     */
    Observer<String> submitTeacherObserver;

    TeacherApi mTeacherApi;

    SubscriberOnNextListener<String> subscriberOnNextListener;

    @OnClick(R.id.btn_teacher_submit)
    void onClick(View view) {
        //是否为空
        boolean isEmpty = checkETEmptyUtil.addView(tvTeacherInfoName).addTip(R.string.name_cant_empty)
                .addView(tvSubmitTeacherPassword).addTip(R.string.password_cant_empty)
                .addView(tvTeacherMail).addTip(R.string.mail_cant_empty).isEmpty();

        //内容不为空
        if (!isEmpty) {
            mTeacherApi.submitTeacherInfo(submitTeacherObserver, initTeacher());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_teacher_info);
        ButterKnife.bind(this);
        initIntentData();
        initObserver();

    }

    /**
     * 初始化监听器
     */
    private void initObserver() {
        subscriberOnNextListener = new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {

            }
            @Override
            public void onCompleted() {
                Toast.makeText(SubmitTeacherInforActivity.this, R.string.register_succeed, Toast.LENGTH_SHORT).show();
            }
        };
        submitTeacherObserver = new ProgressSubscriber<>(subscriberOnNextListener, SubmitTeacherInforActivity.this);
    }


    /**
     * 初始化传递过来的数据
     */
    public void initIntentData() {
        schoolId = getIntent().getStringExtra(Constants.SCHOOL_ID);
        phone = getIntent().getStringExtra(Constants.PHONE);
    }


    /**
     * 初始化老师信息
     *
     * @return 返回一个已经初始化信息完毕的老师信息
     */
    public Teacher initTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName(tvTeacherInfoName.getText().toString().trim());
        teacher.setSchoolId(schoolId);
        teacher.setPhone(phone);
        teacher.setMail(tvTeacherMail.getText().toString().trim());
        teacher.setPassword(tvSubmitTeacherPassword.getText().toString().trim());
        return teacher;
    }


}
