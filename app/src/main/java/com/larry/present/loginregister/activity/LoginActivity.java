package com.larry.present.loginregister.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.larry.present.R;
import com.larry.present.account.AccountManager;
import com.larry.present.bean.student.Student;
import com.larry.present.bean.teacher.Teacher;
import com.larry.present.boot.MainActivity;
import com.larry.present.common.subscribers.ProgressSubscriber;
import com.larry.present.common.subscribers.SubscriberOnNextListener;
import com.larry.present.common.util.CheckETEmptyUtil;
import com.larry.present.common.util.MD5EncipherUtil;
import com.larry.present.config.Constants;
import com.larry.present.loginregister.dto.StudentLoginSuccessDto;
import com.larry.present.loginregister.dto.TeacherLoginSuccessDto;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.login.LoginApi;
import com.larry.present.network.register.RegisterApi;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/*
*    
* 项目名称：present-android      
* 类描述： 登录activity,此activity是老师端和学生端同时使用的activity
* 创建人：Larry-sea   
* 创建时间：2017/4/18 18:25   
* 修改人：Larry-sea  
* 修改时间：2017/4/18 18:25   
* 修改备注：   
* @version    
*    
*/
public class LoginActivity extends AppCompatActivity {


    CheckETEmptyUtil mCheckEmptyUtil;

    /**
     * 老师登录的subscriber
     */
    ProgressSubscriber<TeacherLoginSuccessDto> teacherLoginSubscriber;

    SubscriberOnNextListener<String> registerOnNextListener;

    /**
     * 学生登录回调
     */
    ProgressSubscriber<StudentLoginSuccessDto> studentLoginSubscriber;
    // 注册Subscriber
    ProgressSubscriber<String> registerSubscriber;


    SubscriberOnNextListener<TeacherLoginSuccessDto> teacherLoginListener;

    SubscriberOnNextListener<StudentLoginSuccessDto> studentLoginListener;


    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.iv_login_type)
    ImageView ivLoginType;

    @BindView(R.id.btn_login_forget_pwd)
    Button btnLoginForgetPwd;

    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.toolbar_login)
    Toolbar toolbarLogin;


    @OnClick(R.id.btn_login_login)
    void loginClick(View view) {
        login(etLoginPhone.getText().toString().trim(), MD5EncipherUtil.md5(etLoginPassword.getText().toString().trim()));
    }

    @BindView(R.id.btn_login_login)
    Button loginBtn;

    @OnClick(R.id.btn_login_register)
    void registerClick(View view) {
        openRegisterActivity();
    }

    /**
     * 是否是老师
     */
    boolean isTeacher;


    //手机号
    String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        ButterKnife.bind(this);
        initToolbar();
        initListener();
        initSbuscriber();


    }


    /**
     * 初始化订阅者
     */
    public void initSbuscriber() {
        teacherLoginListener
                = new SubscriberOnNextListener<TeacherLoginSuccessDto>() {
            @Override
            public void onNext(TeacherLoginSuccessDto loginSuccessDto) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(Constants.USER_TYPE, Constants.TEACHER_TYPE);
                startActivity(intent);
                AccountManager.setTeacher(converTeacherLoginDtoToTeacher(loginSuccessDto));
            }

            @Override
            public void onCompleted() {
            }
        };

        studentLoginListener = new SubscriberOnNextListener<StudentLoginSuccessDto>() {
            @Override
            public void onNext(StudentLoginSuccessDto loginSuccessDto) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(Constants.USER_TYPE, Constants.STUDENT_TYPE);
                startActivity(intent);
                AccountManager.setStudent(converStudentLoginDtoToStudent(loginSuccessDto));
            }

            @Override
            public void onCompleted() {

            }
        };


    }


    /**
     * 打开注册页面
     */
    public void openRegisterActivity() {
        registerOnNextListener = new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                Intent intent = new Intent(LoginActivity.this, SelectSchoolActivity.class);
                intent.putExtra(Constants.PHONE, phone);
                startActivity(intent);
            }

            @Override
            public void onCompleted() {

            }
        };
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {

                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    phone = (String) phoneMap.get("phone");
                    RegisterApi registerApi = new RegisterApi(ApiService.getInstance(LoginActivity.this).getmRetrofit());
                    registerApi.registerVerfication(new ProgressSubscriber<String>(registerOnNextListener, LoginActivity.this), phone);


                }
            }
        });
        registerPage.show(LoginActivity.this);
    }


    /**
     * 用户登录
     *
     * @param phone    手机号
     * @param password 密码
     */
    public void login(String phone, String password) {
        if (mCheckEmptyUtil == null) {
            mCheckEmptyUtil = new CheckETEmptyUtil(LoginActivity.this);
        } else if (mCheckEmptyUtil.isRecycler()) {
            mCheckEmptyUtil = new CheckETEmptyUtil(LoginActivity.this);
        }
        boolean isEmpty = mCheckEmptyUtil.addView(etLoginPhone).addTip(R.string.phone_cant_empty).addView(etLoginPassword).addTip(R.string.password_cant_empty).isEmpty();
        if (!isEmpty) {
            LoginApi loginApi = new LoginApi(ApiService.getInstance(LoginActivity.this).getmRetrofit());

            if (isTeacher) {
                loginApi.teacherLogin(new ProgressSubscriber<TeacherLoginSuccessDto>(teacherLoginListener, LoginActivity.this), phone, password);
            } else {
                loginApi.studentLogin(new ProgressSubscriber<StudentLoginSuccessDto>(studentLoginListener, LoginActivity.this), phone, password);
            }
        }
    }

    public void initListener() {
        ivLoginType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTeacher = !isTeacher;
                if (isTeacher) {
                    Glide.with(LoginActivity.this).load(R.drawable.ic_teacher).into(ivLoginType);
                    loginBtn.setText(R.string.teacher_login);
                } else {
                    Glide.with(LoginActivity.this).load(R.drawable.ic_student).into(ivLoginType);
                    loginBtn.setText(R.string.student_login);
                }
            }
        });
    }


    /**
     * 将返回数据转化成为实体类
     *
     * @param studentLoginSuccessDto
     * @return
     */
    public Student converStudentLoginDtoToStudent(StudentLoginSuccessDto studentLoginSuccessDto) {
        Student student = new Student();
        student.setStudentNumber(studentLoginSuccessDto.getStudentNumber());
        student.setSexual(studentLoginSuccessDto.getSexual());
        student.setSchoolId(studentLoginSuccessDto.getSchoolId());
        student.setPhone(studentLoginSuccessDto.getPhone());
        student.setClassId(studentLoginSuccessDto.getClassId());
        student.setClassPosition(studentLoginSuccessDto.getClassPosition());
        student.setName(studentLoginSuccessDto.getName());
        student.setPortraitUrl(studentLoginSuccessDto.getPortraitUrl());
        student.setImel(studentLoginSuccessDto.getImel());
        student.setMail(studentLoginSuccessDto.getMail());
        student.setSchoolId(studentLoginSuccessDto.getSchoolId());
        student.setId(studentLoginSuccessDto.getId());
        return student;
    }


    /**
     * 将老师登录成功dto转换成为实体类
     *
     * @param teacherLoginSuccessDto
     * @return
     */
    public Teacher converTeacherLoginDtoToTeacher(TeacherLoginSuccessDto teacherLoginSuccessDto) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherLoginSuccessDto.getUserId());
        teacher.setMail(teacherLoginSuccessDto.getMail());
        teacher.setName(teacherLoginSuccessDto.getName());
        teacher.setPhone(teacherLoginSuccessDto.getName());
        teacher.setSchoolId(teacherLoginSuccessDto.getSchoolId());
        return teacher;
    }


    public void initToolbar() {
        toolbarLogin.setTitle("");
        setSupportActionBar(toolbarLogin);
    }

}
