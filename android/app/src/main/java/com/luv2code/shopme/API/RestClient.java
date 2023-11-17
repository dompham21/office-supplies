package com.luv2code.shopme.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
//    public static final String BASE_URL = "http://10.0.2.2:8080";

    public static final String BASE_URL = "http://172.20.10.2:8080";
    public static Retrofit RETROFIT = null;
    public static RestService restService = null;


    public static RestService getRestService() {
        if (restService == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                    .create();
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .authenticator(new TokenAuthenticator())
                    .build();

            Retrofit RETROFIT = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            restService = RETROFIT.create(RestService.class);

        }
        return restService;
    }


    public static Retrofit getClient() {
        if (RETROFIT == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .authenticator(new TokenAuthenticator())
                    .build();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT;
    }


}
