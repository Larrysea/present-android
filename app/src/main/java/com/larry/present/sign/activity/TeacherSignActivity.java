package com.larry.present.sign.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.larry.present.R;
import com.larry.present.adapter.StudentAbsenceAdapter;
import com.larry.present.bean.sign.StudentCourseSignDto;
import com.larry.present.common.subscribers.ProgressSubscriber;
import com.larry.present.common.subscribers.SubscriberOnNextListener;
import com.larry.present.common.util.DividerItemDecoration;
import com.larry.present.config.Constants;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.sign.SignApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
*    
* 项目名称：present-android      
* 类描述：  老师查看签到结果activity,和老师查看签到历史记录activity(如果是查看历史签到记录activitiy则不显示停止按钮)
* 创建人：Larry-sea   
* 创建时间：2017/5/11 16:14   
* 修改人：Larry-sea  
* 修改时间：2017/5/11 16:14   
* 修改备注：   
* @version    
*    
*/
public class TeacherSignActivity extends AppCompatActivity {


    @BindView(R.id.sr_teacher_sign)
    SwipeRefreshLayout srTeacherSign;

    SignApi signApi;
    @BindView(R.id.toolbar_teacher_sign)
    Toolbar toolbarTeacherSign;
    @BindView(R.id.rv_bases)
    RecyclerView rvBases;


    StudentAbsenceAdapter studentSignAdapter;

    SubscriberOnNextListener<List<StudentCourseSignDto>> studentCourseListener;

    /**
     * 学生签到的链表
     */
    List<StudentCourseSignDto> studentCourseSignDtoList;


    SubscriberOnNextListener<List<StudentCourseSignDto>> studentAbsenceListener;

    /**
     * 课程签到id
     */
    private String courseSignId = "39bd95bceb49bd45";

    @BindView(R.id.btn_teacher_stop_and__start_sign)
    Button stopSignBtn;


    boolean hiddenStopBtn;

    @OnClick(R.id.btn_teacher_stop_and__start_sign)
    public void stopSign(View view) {
        // APUtil.setApEnabled(this, "fasdfasdfasd", "fasfasdfasdf", false);
        //查看签到的汇总信息
        signApi.getAbsenceStudentInfo(new ProgressSubscriber<List<StudentCourseSignDto>>(studentAbsenceListener, TeacherSignActivity.this), courseSignId);
        stopSignBtn.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();


    }


    public void initListener() {
        srTeacherSign.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                signApi.getCourseSignInfoOnceByCourseSignId(new ProgressSubscriber<List<StudentCourseSignDto>>(studentCourseListener, TeacherSignActivity.this), courseSignId);

            }
        });

        studentAbsenceListener = new SubscriberOnNextListener<List<StudentCourseSignDto>>() {
            @Override
            public void onNext(List<StudentCourseSignDto> studentCourseSignDtos) {
                stopSignBtn.setVisibility(View.VISIBLE);
                //更新数据，显示缺勤的学生信息

                studentCourseSignDtoList = studentCourseSignDtos;
                studentSignAdapter.setData(studentCourseSignDtoList);
                studentSignAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCompleted() {
                srTeacherSign.setRefreshing(false);
            }
        };

        studentCourseListener = new SubscriberOnNextListener<List<StudentCourseSignDto>>() {
            @Override
            public void onNext(List<StudentCourseSignDto> studentCourseSignDtos) {
                studentCourseSignDtoList = studentCourseSignDtos;
                if (studentSignAdapter != null) {
                    //刷新数据
                    studentSignAdapter.notifyDataSetChanged();
                } else {
                    studentSignAdapter = new StudentAbsenceAdapter(TeacherSignActivity.this, studentCourseSignDtoList);
                    //初始化数据
                    // initAdapter(studentCourseSignDtoList);
                    //获取所有学生的签到信息
                    rvBases.setLayoutManager(new LinearLayoutManager(TeacherSignActivity.this));
                    rvBases.addItemDecoration(new DividerItemDecoration(TeacherSignActivity.this, DividerItemDecoration.VERTICAL_LIST));
                    rvBases.setAdapter(studentSignAdapter);
                }

            }

            @Override
            public void onCompleted() {
                srTeacherSign.setRefreshing(false);
            }
        };


    }


    public void initData() {
        courseSignId = getIntent().getStringExtra("courseSignId");
        hiddenStopBtn = getIntent().getBooleanExtra("hiddenStopBtn", false);
        if (hiddenStopBtn) {
            stopSignBtn.setVisibility(View.INVISIBLE);
        }
        signApi = new SignApi(ApiService.getInstance(this).getmRetrofit());
        signApi.getCourseSignInfoOnceByCourseSignId(new ProgressSubscriber<List<StudentCourseSignDto>>(studentCourseListener, TeacherSignActivity.this), courseSignId);


    }

    public int initStateDrawable(String state) {
        int drawableId = R.drawable.ic_fork_48;
        switch (state) {
            case Constants.ABSENCE:
                drawableId = R.drawable.ic_fork_48;
                break;
            case Constants.SICK_LEAVE:
                drawableId = R.drawable.ic_circle_48;
                break;
            case Constants.STUDENT_SIGN:
                drawableId = R.drawable.ic_check_48;
                break;
        }
        return drawableId;

    }


    public void initView() {
        toolbarTeacherSign.setTitle(R.string.check_sign_result);
        toolbarTeacherSign.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        setSupportActionBar(toolbarTeacherSign);
        toolbarTeacherSign.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }


}
