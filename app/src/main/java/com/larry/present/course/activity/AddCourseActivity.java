package com.larry.present.course.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.larry.present.R;
import com.larry.present.account.AccountManager;
import com.larry.present.adapter.AddClassesAdapter;
import com.larry.present.bean.classes.Classes;
import com.larry.present.common.subscribers.ProgressSubscriber;
import com.larry.present.common.subscribers.SubscriberOnNextListener;
import com.larry.present.common.util.DividerItemDecoration;
import com.larry.present.listener.RecyclerviewClickInterface;
import com.larry.present.network.base.ApiService;
import com.larry.present.network.classes.ClassApi;
import com.larry.present.network.course.CourseApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
*    
* 项目名称：present-android      
* 类描述：  老师添加课程activity
* 创建人：Larry-sea   
* 创建时间：2017/5/12 16:43   
* 修改人：Larry-sea  
* 修改时间：2017/5/12 16:43   
* 修改备注：   
* @version    
*    
*/
public class AddCourseActivity extends AppCompatActivity implements RecyclerviewClickInterface {

    CourseApi courseApi;
    ClassApi classApi;
    @BindView(R.id.toolbar_select_identity)
    Toolbar toolbarSelectIdentity;
    @BindView(R.id.tv_add_course_name)
    EditText tvAddCourseName;
    @BindView(R.id.btn_add_course_submit)
    Button btnAddCourseSubmit;
    @BindView(R.id.et_add_course_class_name)
    EditText etAddCourseClassName;
    @BindView(R.id.rv_add_course_class)
    RecyclerView rvAddCourseClass;

    SubscriberOnNextListener<Classes> classInfoListener;

    AddClassesAdapter addClassesAdapter;

    List<Classes> classesList;


    SubscriberOnNextListener<String> addClassToCourseListener;

    String courseId;

    @OnClick(R.id.btn_add_course_submit)
    public void onClick(View view) {
        courseApi.addClassesToCourse(new ProgressSubscriber<String>(addClassToCourseListener, AddCourseActivity.this), courseId, classesList);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();

    }

    public void initData() {
        courseApi = new CourseApi(ApiService.getInstance(AddCourseActivity.this).getmRetrofit());
        classApi = new ClassApi(ApiService.getInstance(AddCourseActivity.this).getmRetrofit());
        etAddCourseClassName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable[] drawables = etAddCourseClassName.getCompoundDrawables();

                Drawable drawable = drawables[2];  //
                if (drawable == null) {
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if ((event.getX() > etAddCourseClassName.getWidth() - drawable.getIntrinsicWidth() - etAddCourseClassName.getPaddingRight()) && (event.getX() < etAddCourseClassName.getWidth() - etAddCourseClassName.getPaddingRight())) {
                        classApi.getClassesInfo(
                                new ProgressSubscriber<Classes>(classInfoListener, AddCourseActivity.this),
                                etAddCourseClassName.getText().toString().trim(),
                                AccountManager.getTeacher().getSchoolId());
                    }
                }
                return true;
            }
        });
    }


    public void initListener() {
        classInfoListener = new SubscriberOnNextListener<Classes>() {
            @Override
            public void onNext(Classes s) {
                //获取班级id
                if (s != null) {
                    if (classesList == null) {
                        classesList = new ArrayList<>();
                        addClassesAdapter = new AddClassesAdapter(AddCourseActivity.this, classesList);
                        rvAddCourseClass.setLayoutManager(new LinearLayoutManager(AddCourseActivity.this));
                        rvAddCourseClass.addItemDecoration(new DividerItemDecoration(AddCourseActivity.this, DividerItemDecoration.VERTICAL_LIST));
                        rvAddCourseClass.setAdapter(addClassesAdapter);
                    } else {
                        classesList.add(s);
                        addClassesAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCompleted() {

            }
        };

        addClassToCourseListener = new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {

            }

            @Override
            public void onCompleted() {

            }
        };

    }

    public void initView() {
        toolbarSelectIdentity.setTitle(R.string.check_sign_result);
        toolbarSelectIdentity.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        setSupportActionBar(toolbarSelectIdentity);
        toolbarSelectIdentity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }


    @Override
    public void onClick(View view, int position) {
        //删除不想添加的班级
        classesList.remove(position);
        addClassesAdapter.setData(classesList);
        addClassesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
