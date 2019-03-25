package com.nafshadigital.smartcallassistant.network;

import com.nafshadigital.smartcallassistant.helpers.AppRunning;
import com.nafshadigital.smartcallassistant.helpers.SmartCallAssistantSharedPrefs;

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

    /**
     * Get the client with base url without Authorization header
     * and parse the response itself
     *
     * @return retrofit
     */
    public static Retrofit getLoginClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClientNoAuthorizationHeader)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * Used to get the client with base url and with authorization key header
     * and the response will parse automatically
     * it will return the ResponseBody
     *
     * @return retrofit
     */
    public static Retrofit getClient() {
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
                    Request.Builder builder = originalRequest.newBuilder().header("Basic-Auth", "Basic SFVCVU46aHVidW5kZXYh")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", SmartCallAssistantSharedPrefs.getAccessToken(AppRunning.getAppContext()));

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            })

            .build();

    /**
     * Interceptor without Authorization key used for login
     */
    public static OkHttpClient okHttpClientNoAuthorizationHeader = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder().header("Basic-Auth", "Basic SFVCVU46aHVidW5kZXYh")
                    .addHeader("Content-Type", "application/json");

            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }
    }).build();


}