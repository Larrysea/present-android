package com.larry.present.loginregister.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.larry.present.R;
import com.larry.present.bean.student.Student;
import com.larry.present.common.subscribers.ProgressSubscriber;
import com.larry.present.common.subscribers.SubscriberOnNextListener;
import com.larry.present.common.util.CheckETEmptyUtil;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.student.StudentApi;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
*    
* 项目名称：present-android      
* 类描述：  学生提交个个人信息接口
* 创建人：Larry-sea   
* 创建时间：2017/4/19 14:48   
* 修改人：Larry-sea  
* 修改时间：2017/4/19 14:48   
* 修改备注：   
* @version    
*    
*/
public class SubmitStudentInfoActivity extends AppCompatActivity {


    @BindView(R.id.cb_student_male)
    CheckBox cbStudentMale;
    @BindView(R.id.cb_student_female)
    CheckBox cbStudentFemale;
    @BindView(R.id.iv_student_portrait)
    ImageView ivStudentPortrait;
    @BindView(R.id.et_student_name)
    EditText etStudentName;
    @BindView(R.id.et_student_student_number)
    EditText etStudentStudentNumber;
    @BindView(R.id.et_student_class)
    EditText etStudentClass;
    @BindView(R.id.et_student_school)
    EditText etStudentSchool;
    @BindView(R.id.et_student_position)
    EditText etStudentPosition;
    @BindView(R.id.et_student_mail)
    EditText etStudentMail;
    @BindView(R.id.et_student_phone)
    EditText etStudentPhone;

    ProgressSubscriber<String> mSubmitInfoSubscriber;

    SubscriberOnNextListener mSubscriberOnNextListener;

    StudentApi mStudentApi;

    //获取班级的idsubscriber
    ProgressSubscriber<String> getClassIdSubscriber;


    //获取班级id的listener
    SubscriberOnNextListener getClassIdOnNextListener;


    /*
    * 学生个人信息
    * */
    Student mStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_student_info);
        ButterKnife.bind(this);
        initData();
    }


    public void initData() {

        mStudentApi = new StudentApi(ApiService.getInstance(SubmitStudentInfoActivity.this).getmRetrofit());

        mSubscriberOnNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onCompleted() {

            }
        };
        mSubmitInfoSubscriber = new ProgressSubscriber<String>(mSubscriberOnNextListener, SubmitStudentInfoActivity.this);
        mStudent = initStudentInfo();
        if (mStudent != null) {
            mStudentApi.submitStudentInfo(mSubmitInfoSubscriber, mStudent);
        }

    }


    /**
     * 获取所有学生信息
     *
     * @return
     */
    public Student initStudentInfo() {
        CheckETEmptyUtil checkETEmptyUtil = new CheckETEmptyUtil(SubmitStudentInfoActivity.this);
        Student student = null;
        boolean result = checkETEmptyUtil.addView(etStudentName).addTip(R.string.name_cant_empty)
                .addView(etStudentStudentNumber).addTip(R.string.student_number_cant_empty)
                .addView(etStudentClass).addTip(R.string.class_cant_empty)
                .addView(etStudentPosition).addTip(R.string.position_cant_empty)
                .addView(etStudentMail).addTip(R.string.mail_cant_empty)
                .addView(etStudentPhone).addTip(R.string.smssdk_write_mobile_phone).check();
        if (result) {
            student = new Student();
            student.setName(etStudentName.getText().toString().trim());
            student.setStudentNumber(etStudentStudentNumber.getText().toString().trim());
            //TODO 设置班级id
            // student.setClassId();

        }
        return student;
    }


    public void getClassId(String className) {
        getClassIdSubscriber = new ProgressSubscriber<String>(getClassIdOnNextListener, SubmitStudentInfoActivity.this);


    }


}
