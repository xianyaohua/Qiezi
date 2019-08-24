package com.tsrj.qiezi.httprequest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 下载
 */
public class DownloadProgressInterceptor implements Interceptor {

    private ProgressListener progressListener;

    public DownloadProgressInterceptor(ProgressListener listener) {
        this.progressListener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //拦截
        Response originalResponse = chain.proceed(chain.request());

        //包装响应体并返回
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                .build();
    }
}
