package com.tsrj.qiezi.httprequest;


import com.tsrj.qiezi.BuildConfig;
import com.tsrj.qiezi.app.MyApplication;
import com.tsrj.qiezi.constants.UrlConstant;
import com.tsrj.qiezi.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class ServiceGenerator {
    private static final int TIME_OUT=8;
    private static final String CER_NAME = "server.pem";
    private static DownloadProgressInterceptor downloadProgressInterceptor;
    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor(new HttpLogger())
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(UrlConstant.DOMAIN)
                    .addConverterFactory(FastJsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
//                    .addInterceptor(logging)
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(new MyInterceptors());

//    public static <S> S createService(Class<S> serviceClass) {
//        if (sslContext == null) {
//            try {
//                sslContext = getCertificates(MyApplication.getInstance().getAssets().open(CER_NAME));
////            if (sslContext != null) {
////                httpClient.sslSocketFactory(sslContext.getSocketFactory());
////            }
//            } catch (IOException e) {
//                e.printStackTrace();
//                LogUtil.info("createService getCertificates e:" + e.getMessage());
//            }
//        }
//
//        if (!httpClient.interceptors().contains(logging)) {
//            httpClient.addInterceptor(logging);
//            builder.client(httpClient.build());
//            retrofit = builder.build();
//        }
//        return retrofit.create(serviceClass);
//    }

    public static <S> S createService(Class<S> serviceClass) {
        /*if (URLConstants.hostname != null) {
            try {
                HostnameVerifier TRUST_HOST_NAME = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
//                        LogUtil.info("verify hostname:" + hostname);
                        // 设置接受的域名集合
                        if (hostname.equals(URLConstants.hostname)
                                || hostname.equals(URLConstants.hostnameWX)
                                || hostname.equals(URLConstants.hostnameDown)) {
                            return true;
                        }
                        return false;
                    }
                };
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, getTrustManager(), new SecureRandom());
                httpClient.sslSocketFactory(sslContext.getSocketFactory()).hostnameVerifier(TRUST_HOST_NAME);


            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.info("createService getCertificates e:" , e.getMessage());
            }
        }*/

        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }


    public static OkHttpClient getClient() {
        try {
            SSLContext sslContext = getCertificates(MyApplication.getInstance().getAssets().open(CER_NAME));
            if (sslContext != null) {
                httpClient.sslSocketFactory(sslContext.getSocketFactory());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
        }
        return httpClient.build();
    }


    public static <S> S createDownloadServiceNormal(Class<S> serviceClass, ProgressListener listener) {

//        if (sslContext == null && URLConstants.hostname != null) {
//            try {
//                HostnameVerifier TRUST_HOST_NAME = new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
////                        LogUtil.info("verify hostname:" + hostname);
//                        // 设置接受的域名集合
//                        if (hostname.equals(URLConstants.hostname)
//                                || hostname.equals(URLConstants.hostnameWX)
//                                || hostname.equals(URLConstants.hostnameDown)) {
//                            return true;
//                        }
//                        return false;
//                    }
//                };
//                SSLContext sslContext = SSLContext.getInstance("SSL");
//                sslContext.init(null, getTrustManager(), new SecureRandom());
//                httpClient.sslSocketFactory(sslContext.getSocketFactory()).hostnameVerifier(TRUST_HOST_NAME);
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                LogUtil.info("createService getCertificates e:" + e.getMessage());
//            }
//        }

        if (downloadProgressInterceptor == null || !httpClient.networkInterceptors().contains(downloadProgressInterceptor)) {
            downloadProgressInterceptor = new DownloadProgressInterceptor(listener);
            httpClient.addNetworkInterceptor(downloadProgressInterceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

    public static <S> S createUploadService(Class<S> serviceClass, ProgressListener listener) {
        httpClient.addInterceptor(new UpLoadProgressInterceptor(listener));
        try {
            SSLContext sslContext = getCertificates(MyApplication.getInstance().getAssets().open(CER_NAME));
            if (sslContext != null) {
                httpClient.sslSocketFactory(sslContext.getSocketFactory());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

    private static SSLContext getCertificates(InputStream... certificates) {
        try {
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "BC");
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                    LogUtil.e("getCertificates1 e:" , e.getMessage());
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            X509TrustManager trustManager = (X509TrustManager) trustManagerFactory.getTrustManagers()[0];
            httpClient.sslSocketFactory(sslContext.getSocketFactory(), trustManager);
            return sslContext;

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.info("getCertificates2 e:" ,e.getMessage());
            return null;
        }

    }

    //获取TrustManager
    private static TrustManager[] getTrustManager() {

        final TrustManager[] trustManagers = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        //1、判断证书是否是本地信任列表里颁发的证书
                        try {
                            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
                            tmf.init((KeyStore) null);
                            for (TrustManager trustManager : tmf.getTrustManagers()) {
                                ((X509TrustManager) trustManager).checkServerTrusted(chain, authType);
                            }
                        } catch (Exception e) {
                            LogUtil.info("verify CertificateException:" , e.getMessage());
                            throw new CertificateException(e);

                        }

                        //校验服务端证书，实现强校验，强烈不推荐弱校验
                        if (chain == null) {
                            throw new IllegalArgumentException("Check Server X509Certificate[] is null");
                        }

                        if (chain.length < 0) {
                            throw new IllegalArgumentException("Check Server X509Certificate[] is empty");
                        }
                        for (X509Certificate x509Certificate : chain) {
                            try {
                                //检测服务端证书是否过期
                                x509Certificate.checkValidity();
                            } catch (Exception e) {
                                LogUtil.info("verify X509Certificate:" , e.getMessage());
                                throw new CertificateException(e);
                            }
                        }
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        return trustManagers;
    }


    private static class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
           /* if (BuildConfig.Log_DEBUG) {//测试包 才打印HTTP日志
                LogUtil.info("HttpLogInfo:" , message);
            }*/
        }
    }


}
