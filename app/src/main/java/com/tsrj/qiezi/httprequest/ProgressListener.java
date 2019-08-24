package com.tsrj.qiezi.httprequest;

public interface ProgressListener {

    void onProgress(long bytesWritten, long contentLength, boolean done);
}
