package com.mhealthkenya.psurvey.interfaces;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.fxn.stash.Stash;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.auth;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
   // private auth loggedInUser;

    public static final String BASE_URL = "https://psurvey-api.mhealthkenya.co.ke/";

    public static Retrofit retrofit;

    public static Retrofit getApiClient() {
        auth loggedInUser;
        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);
        String auth_token = loggedInUser.getAuth_token();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okhttpclient = new OkHttpClient.Builder();
        okhttpclient.addInterceptor(logging);
        okhttpclient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization","Token "+ auth_token)
                        .header("Content-Type", "application.json").build();
                return chain.proceed(request);
            }
        });


        OkHttpClient okHttpClient = okhttpclient.build();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
