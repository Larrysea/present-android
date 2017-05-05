package com.larry.present.loginregister.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.larry.present.R;
import com.larry.present.boot.MainActivity;
import com.larry.present.common.subscribers.ProgressSubscriber;
import com.larry.present.common.subscribers.SubscriberOnNextListener;
import com.larry.present.common.util.CheckETEmptyUtil;
import com.larry.present.config.Constants;
import com.larry.present.loginregister.dto.LoginSuccessDto;
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
* 类描述： 登录activity   此activity是老师端和学生端同时使用的activity
* 创建人：Larry-sea   
* 创建时间：2017/4/18 18:25   
* 修改人：Larry-sea  
* 修改时间：2017/4/18 18:25   
* 修改备注：   
* @version    
*    
*/
public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    CheckETEmptyUtil mCheckEmptyUtil;

    //用户登录的subscriber
    ProgressSubscriber<LoginSuccessDto> loginSubscriber;

    // 注册Subscriber
    ProgressSubscriber<String> registerSubscriber;

    @OnClick(R.id.btn_login_login)
    void loginClick(View view) {
        login(etLoginName.getText().toString().trim(), etLoginPassword.getText().toString().trim());
    }

    @OnClick(R.id.tv_login_register)
    void registerClick(View view) {
        openRegisterActivity();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initSbuscriber();

    }


    /**
     * 初始化订阅者
     */
    public void initSbuscriber() {
        SubscriberOnNextListener<LoginSuccessDto> loginOnNextListener
                = new SubscriberOnNextListener<LoginSuccessDto>() {
            @Override
            public void onNext(LoginSuccessDto loginSuccessDto) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(Constants.USER_TYPE, loginSuccessDto.getUserType());
                startActivity(intent);
            }

            @Override
            public void onCompleted() {

            }
        };

        SubscriberOnNextListener<String> registerOnNextListener = new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                //TODO  做一些保存用唯一id的操作，以后好使用
                //startActivity(new Intent(LoginActivity.this,SelectSchoolActivity.class));
            }

            @Override
            public void onCompleted() {

            }
        };
        loginSubscriber = new ProgressSubscriber<LoginSuccessDto>(loginOnNextListener, LoginActivity.this);

        registerSubscriber = new ProgressSubscriber<String>(registerOnNextListener, LoginActivity.this);


    }


    /**
     * 打开注册页面
     */
    public void openRegisterActivity() {
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String phone = (String) phoneMap.get("phone");
                    RegisterApi registerApi = new RegisterApi(ApiService.getInstance(LoginActivity.this).getmRetrofit());
                    registerApi.register(registerSubscriber, phone);

                }
            }
        });
        registerPage.show(LoginActivity.this);
    }


    /**
     * 用户登录
     *
     * @param userName  用户名
     * @param password  密码
     */
    public void login(String userName, String password) {
        if (mCheckEmptyUtil == null) {
            mCheckEmptyUtil = new CheckETEmptyUtil(LoginActivity.this);
        }
        boolean isEmpty = mCheckEmptyUtil.addView(etLoginName).addTip(R.string.userName_cant_empty).addView(etLoginPassword).addTip(R.string.password_cant_empty).isEmpty();
        if (!isEmpty) {
            LoginApi loginApi = new LoginApi(ApiService.getInstance(LoginActivity.this).getmRetrofit());
            loginApi.userLogin(loginSubscriber, userName, password);
        }
    }

}
