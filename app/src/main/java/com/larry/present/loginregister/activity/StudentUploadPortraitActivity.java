package com.larry.present.loginregister.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.larry.present.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

public class StudentUploadPortraitActivity extends Activity {
    private TextView uploadInfo;

    private ImageView portrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_upload);

        uploadInfo = (TextView) findViewById(R.id.upload_info);
        portrait = (ImageView) findViewById(R.id.iv_student_portrait);
        String filePath = "C:\\Users\\Larry-sea\\OneDrive\\code\\gitpath\\present\\target\\present\\temp\\portrait.jpg";
        String path = "http://192.168.1.106:8080/present/upload" + filePath;
        Glide.with(this).load(path).into(portrait);
        uploadFile();
    }

    public void uploadFile() {

        String portraitPaht = Environment.getExternalStorageDirectory() + "/here/portrait.jpg";
        //服务器端地址
        String url = "http://192.168.1.106:8080/present/upload";
        //手机端要上传的文件，首先要保存你手机上存在该文件


        AsyncHttpClient httpClient = new AsyncHttpClient();

        RequestParams param = new RequestParams();
        try {
            param.put("file", new File(portraitPaht));
            param.put("content", "liucanwen");

            httpClient.post(url, param, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();

                    uploadInfo.setText("正在上传...");
                }

                @Override
                public void onSuccess(String arg0) {
                    super.onSuccess(arg0);

                    Log.i("ck", "success>" + arg0);

                    if (arg0.equals("success")) {
                        Toast.makeText(StudentUploadPortraitActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                    }

                    uploadInfo.setText(arg0);
                }

                @Override
                public void onFailure(Throwable arg0, String arg1) {
                    super.onFailure(arg0, arg1);

                    uploadInfo.setText("上传失败！");
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(StudentUploadPortraitActivity.this, "上传文件不存在！", Toast.LENGTH_SHORT).show();
        }
    }
}
