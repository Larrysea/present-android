package com.larry.present.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.larry.present.R;
import com.larry.present.bean.sign.StudentCourseSignDto;
import com.larry.present.config.Constants;
import com.larry.present.listener.RecyclerviewClickInterface;
import com.larry.present.viewholder.StudentCourseSignStateViewHolder;

import java.util.List;

;


/**
 * 学生缺勤的适配器
 */
public class StudentAbsenceAdapter extends RecyclerView.Adapter<StudentCourseSignStateViewHolder> {

    List<StudentCourseSignDto> studentCourseSignDtoList;
    Context mcontext;
    RecyclerviewClickInterface mrecyclerClickInterface;


    public StudentAbsenceAdapter(Context context, List<StudentCourseSignDto> creditCards) {
        this.mcontext = context;
        this.studentCourseSignDtoList = creditCards;

    }

    @Override
    public StudentCourseSignStateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StudentCourseSignStateViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.student_sign_state_item, parent, false), (Activity) mcontext);
    }

    @Override
    public void onBindViewHolder(StudentCourseSignStateViewHolder holder, int position) {
        if (holder != null) {
            holder.name.setText(studentCourseSignDtoList.get(position).getName());
            holder.number.setText(studentCourseSignDtoList.get(position).getStudentNumber());
            Glide.with(mcontext).load(initStateDrawable(studentCourseSignDtoList.get(position).getSignState())).into(holder.stateIv);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mrecyclerClickInterface.onClick(v, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return studentCourseSignDtoList != null ? studentCourseSignDtoList.size() : 0;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnClickListener(RecyclerviewClickInterface recyclerviewClickInterface) {
        this.mrecyclerClickInterface = recyclerviewClickInterface;
    }

    public static int initStateDrawable(String state) {
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


    public void setData(List<StudentCourseSignDto> studentCourseSignDtos) {
        this.studentCourseSignDtoList = studentCourseSignDtos;

    }

}
