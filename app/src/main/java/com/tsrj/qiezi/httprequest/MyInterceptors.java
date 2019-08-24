package com.tsrj.qiezi.httprequest;

import android.os.Build;


import com.tsrj.qiezi.R;
import com.tsrj.qiezi.app.MyApplication;
import com.tsrj.qiezi.utils.AppInfoUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * okHttp拦截器，对请求头进行处理
 * Author: zhuxiaohong
 * Date: 2017/6/6 16:52
 */
public class MyInterceptors implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String channel = MyApplication.getContext().getString(R.string.app_channel_id);
        //封装headers
        String userAgent = "Android/" + Build.VERSION.RELEASE+" "
                + MyApplication.getInstance().getPackageName()
                +"/"+ AppInfoUtils.getCurVersionName(MyApplication.getContext())
                +" channel/"+channel;
        Headers headers = originalRequest.headers().newBuilder().set("User-Agent", userAgent).build();
//        LogUtil.info("userAgent:"+userAgent);
//        Request request = originalRequest.newBuilder()
//                .header("Cache-Control", "max-age=60")
//                .addHeader("Content-Type", "application/json;charset=UTF-8") //添加请求头信息
//                .addHeader("Content-Encoding", "gzip")
//                .addHeader("Connection", "close")
//                .addHeader("accept", "*/*")
//                //                .method(originalRequest.method(), gzip(originalRequest.body()))//对请求的数据压缩后再上传
//                .build();

        Request request = originalRequest.newBuilder()
                .headers(headers)
                .addHeader("Content-Type", "application/json;charset=UTF-8") //添加请求头信息
                .addHeader("Content-Encoding", "gzip")
                .addHeader("Connection", "close")
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "zh-cn")
                .build();
//        LogUtil.info("getLanguage:"+ Locale.getDefault().getLanguage()
//                +"---getCountry():"+Locale.getDefault().getCountry());
        Response response = chain.proceed(request);

        return response;
    }

    private RequestBody gzip(final RequestBody body) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() {
                return -1; // We don't know the compressed length in advance!
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                body.writeTo(gzipSink);
                gzipSink.close();
            }
        };
    }
}
