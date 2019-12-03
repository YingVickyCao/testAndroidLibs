package com.example.hades.SimpleXML.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.util.concurrent.TimeUnit;

public class StuService {
    private volatile static StuService sInstance;
    private StuAPI mTestService;

    private static final int DEFAULT_TIMEOUT = 8000;

//     RxJava2 + Retrofit2
    private StuService() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new LoggingInterceptor()); // 拦截器

        mTestService = new Retrofit.Builder()
                .baseUrl("https://gitee.com/YingVickyCao/testAndroidLibs/raw/master/SimpleXML/src/main/resources/")
                .client(okHttpClientBuilder.build())
                //                .addConverterFactory(GsonConverterFactory.create())
                //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(StuAPI.class);

    }

    public static StuService getInstance() {
        if (sInstance == null) {
            synchronized (StuService.class) {
                if (sInstance == null) {
                    sInstance = new StuService();
                }
            }
        }
        return sInstance;
    }

    public StuAPI getTestService() {
        return mTestService;
    }
}
