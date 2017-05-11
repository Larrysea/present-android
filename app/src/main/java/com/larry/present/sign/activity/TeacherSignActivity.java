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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.larry.present.R;
import com.larry.present.bean.sign.StudentCourseSignDto;
import com.larry.present.common.subscribers.ProgressSubscriber;
import com.larry.present.common.subscribers.SubscriberOnNextListener;
import com.larry.present.common.util.APUtil;
import com.larry.present.common.util.DividerItemDecoration;
import com.larry.present.config.Constants;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.sign.SignApi;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
*    
* 项目名称：present-android      
* 类描述：  老师查看签到结果activity
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

    CommonAdapter<StudentCourseSignDto> studentSignAdapter;

    SubscriberOnNextListener<List<StudentCourseSignDto>> studentCourseListener;

    /**
     * 学生签到的链表
     */
    List<StudentCourseSignDto> studentCourseSignDtoList;


    SubscriberOnNextListener<List<StudentCourseSignDto>> studentAbsenceListener;

    /**
     * 课程签到id
     */
    private String courseSignId;

    @BindView(R.id.btn_teacher_stop_sign)
    Button stopSignBtn;

    @OnClick(R.id.btn_teacher_stop_sign)
    public void stopSign(View view) {
        APUtil.setApEnabled(this, "fasdfasdfasd", "fasfasdfasdf", false);
        //查看签到的汇总信息
        signApi.getAbsenceStudentInfo(new ProgressSubscriber<List<StudentCourseSignDto>>(studentAbsenceListener, TeacherSignActivity.this), courseSignId);
        stopSignBtn.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign);
        ButterKnife.bind(this);
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
                //更新数据，显示缺勤的学生信息
                studentCourseSignDtoList = studentCourseSignDtos;
                studentSignAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCompleted() {

            }
        };

    }


    public void initData() {
        courseSignId = getIntent().getStringExtra("courseSignId");
        signApi = new SignApi(ApiService.getInstance(this).getmRetrofit());

        //获取所有学生的签到信息
        signApi.getCourseSignInfoOnceByCourseSignId(new ProgressSubscriber<List<StudentCourseSignDto>>(studentCourseListener, this), courseSignId);

        studentCourseListener = new SubscriberOnNextListener<List<StudentCourseSignDto>>() {
            @Override
            public void onNext(List<StudentCourseSignDto> studentCourseSignDtos) {
                studentCourseSignDtoList = studentCourseSignDtos;
                if (studentSignAdapter != null) {
                    //刷新数据
                    studentSignAdapter.notifyDataSetChanged();
                } else {
                    //初始化数据
                    initAdapter(studentCourseSignDtos);
                }

            }

            @Override
            public void onCompleted() {

            }
        };
        rvBases.setAdapter(studentSignAdapter);
        rvBases.setLayoutManager(new LinearLayoutManager(this));
        rvBases.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
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


    public void initAdapter(List<StudentCourseSignDto> studentCourseSignDtos) {
        studentSignAdapter = new CommonAdapter<StudentCourseSignDto>(TeacherSignActivity.this, R.layout.student_sign_state_item, studentCourseSignDtos) {
            @Override
            protected void convert(ViewHolder holder, StudentCourseSignDto studentCourseSignDto, int position) {
                holder.setText(R.id.tv_student_state_number, studentCourseSignDto.getStudentNumber());
                holder.setText(R.id.tv_student_state_name, studentCourseSignDto.getName());
                ImageView imageView = holder.getView(R.id.iv_student_state);
                Glide.with(TeacherSignActivity.this).load(initStateDrawable(studentCourseSignDto.getSignState())).into(imageView);
            }
        };
    }


}
