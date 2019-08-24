package com.tsrj.qiezi.httprequest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: zhuxiaohong
 * Date: 2017/6/24 16:15
 */
public class UpLoadProgressInterceptor implements Interceptor {

    private ProgressListener progressListener;

    public UpLoadProgressInterceptor(ProgressListener listener) {
        this.progressListener = listener;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .method(original.method(), new ProgressRequestBody(original.body(), progressListener))
                .build();
        return chain.proceed(request);
    }
}
