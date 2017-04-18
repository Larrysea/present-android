package com.larry.present.loginregister.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.larry.present.R;
import com.larry.present.boot.MainActivity;
import com.larry.present.common.util.CheckETEmptyUtil;
import com.larry.present.config.Constants;
import com.larry.present.loginregister.dto.LoginSuccessDto;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.login.LoginApi;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import rx.Observer;

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
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息（此方法可以不调用）
                    Toast.makeText(LoginActivity.this, country + "   " + phone, Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerPage.show(LoginActivity.this);
    }


    /**
     * 登录事件的观察者
     */
    Observer<LoginSuccessDto> loginObserver = new Observer<LoginSuccessDto>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(LoginSuccessDto s) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(Constants.USER_TYPE, s.getUserType());
            startActivity(intent);

        }
    };


    /**
     * 用户登录aaaaaa
     *
     * @param userName
     * @param password
     */
    public void login(String userName, String password) {
        if (mCheckEmptyUtil == null) {
            mCheckEmptyUtil = new CheckETEmptyUtil(LoginActivity.this);
        }
        boolean isEmpty = mCheckEmptyUtil.addView(etLoginName).addTip(R.string.userName_cant_empty).addView(etLoginPassword).addTip(R.string.password_cant_empty).check();
        if (!isEmpty) {
            LoginApi loginApi = new LoginApi(ApiService.getInstance(LoginActivity.this).getmRetrofit());
            loginApi.userLogin(loginObserver, userName, password);
        }

    }

}
