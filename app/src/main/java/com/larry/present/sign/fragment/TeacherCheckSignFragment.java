package com.larry.present.sign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larry.present.R;
import com.larry.present.account.AccountManager;
import com.larry.present.bean.course.Course;
import com.larry.present.bean.sign.CourseSign;
import com.larry.present.common.subscribers.ProgressSubscriber;
import com.larry.present.common.subscribers.SubscriberOnNextListener;
import com.larry.present.common.util.DividerItemDecoration;
import com.larry.present.network.course.CourseApi;
import com.larry.present.network.sign.SignApi;
import com.larry.present.sign.activity.TeacherSignActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
*    
* 项目名称：present-android      
* 类描述： 老师查看所有课程的签到页面
* 创建人：Larry-sea   
* 创建时间：2017/5/12 21:50   
* 修改人：Larry-sea  
* 修改时间：2017/5/12 21:50   
* 修改备注：   
* @version    
*    
*/
public class TeacherCheckSignFragment extends Fragment {
    @BindView(R.id.rv_bases)
    RecyclerView rvBases;
    Unbinder unbinder;

    CourseApi courseApi;

    SignApi signApi;

    //获取所有课程的监听回调
    SubscriberOnNextListener<List<Course>> teacherGetCourseListener;

    //获取所有课程的签到记录回调
    SubscriberOnNextListener<List<CourseSign>> courseGetAllListener;


    //课程名
    String courseName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_course, container, false);
        unbinder = ButterKnife.bind(this, view);
        initListener();
        initData();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void initData() {
        courseApi.teacherGetAllCourse(new ProgressSubscriber<List<Course>>(teacherGetCourseListener, getActivity()), AccountManager.getTeacher().getId());
    }


    public void initListener() {

        courseGetAllListener = new SubscriberOnNextListener<List<CourseSign>>() {
            @Override
            public void onNext(List<CourseSign> courseSigns) {
                rvBases.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvBases.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                rvBases.setAdapter(new CommonAdapter<CourseSign>(getActivity(), R.layout.course_sign_detail_item, courseSigns) {
                    @Override
                    protected void convert(ViewHolder holder, CourseSign courseSign, int position) {
                        holder.setOnClickListener(R.id.rl_course_sign, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), TeacherSignActivity.class);
                                intent.putExtra("courseSignId", courseSign.getId());
                                startActivity(intent);
                            }
                        });
                    }
                });
            }

            @Override
            public void onCompleted() {

            }
        };

        teacherGetCourseListener = new SubscriberOnNextListener<List<Course>>() {
            @Override
            public void onNext(List<Course> courseList) {
                rvBases.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvBases.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                rvBases.setAdapter(new CommonAdapter<Course>(getActivity(), R.layout.course_item, courseList) {
                    @Override
                    protected void convert(ViewHolder holder, Course course, int position) {
                        holder.setText(R.id.tv_course_item_name, course.getCourseName());
                        holder.setOnClickListener(R.id.ll_course_item, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                signApi.getCourseAllSignInfo(new ProgressSubscriber<List<CourseSign>>(courseGetAllListener, getActivity()), course.getId());
                            }
                        });
                    }
                });
            }

            @Override
            public void onCompleted() {

            }
        };

    }

}

