package com.larry.present.network.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Larry-sea on 2016/11/14.
 * <p>
 * <p>
 * retrofit  中使用缓存的客户端
 */

public class OkhttpClient {

    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }

    public static OkHttpClient getCacheOkHttpClient(final Context context) {
        final File baseDir = context.getCacheDir();
        final File cacheDir = new File(baseDir, "HttpResponseCache");
        Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);   //缓存可用大小为10M

        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
            Request request = chain.request();
            if (!isNetworkReachable(context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            if (isNetworkReachable(context)) {
                int maxAge = 60;                  //在线缓存一分钟
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();

            } else {
                int maxStale = 60 * 60 * 24 * 4 * 7;     //离线缓存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        };

        return new OkHttpClient.Builder()
                //设置网络缓存的拦截器
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                //设置日志拦截器
                .addInterceptor(new LoggingInterceptor())
                .cache(cache)
                .build();
    }
}
