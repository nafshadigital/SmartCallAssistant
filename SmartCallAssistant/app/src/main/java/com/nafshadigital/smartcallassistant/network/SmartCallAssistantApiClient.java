package com.nafshadigital.smartcallassistant.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Class used to create the Retrofit API client
 */
public class SmartCallAssistantApiClient {

    public static final String BASE_URL = "http://134.209.117.226/SmartCallAssistantAPI/api/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if(retrofit==null)
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
               // .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit;
    }

    public static String getBaseUrl()
    {
        return MyRestAPI.PostCall("checkOTP",  null);
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
                            //.addHeader("Authorization", "bearer token") todo - add token later (after api completed)
                            .addHeader("Content-Type", "application/json");


                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            })

            .build();


}