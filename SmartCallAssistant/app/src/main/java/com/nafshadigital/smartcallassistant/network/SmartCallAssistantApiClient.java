package com.nafshadigital.smartcallassistant.network;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class used to create the Retrofit API client
 */
public class SmartCallAssistantApiClient {

    public static final String BASE_URL = "http://134.209.117.226/SmartCallAssistantAPI/api/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit==null)
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * Interceptor with all basic headers to call the api
     */
    public static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(240, TimeUnit.SECONDS)
            .readTimeout(240, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder()
                            .addHeader("Content-Type", "application/json");

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            })

            .build();



}