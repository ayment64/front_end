package com.aymentlili.aamoomor.Services;/*
 * Decompiled with CFR 0.0.
 *
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  okhttp3.OkHttpClient
 *  okhttp3.OkHttpClient$Builder
 *  retrofit2.Converter
 *  retrofit2.Converter$Factory
 *  retrofit2.Retrofit
 *  retrofit2.Retrofit$Builder
 *  retrofit2.converter.gson.GsonConverterFactory
 */
;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL = "http://192.168.43.80:3000";
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://192.168.43.80:3000").addConverterFactory((Converter.Factory)GsonConverterFactory.create());
    private static OkHttpClient.Builder httpClient;
    private static Retrofit retrofit;

    static {
        retrofit = builder.build();
        httpClient = new OkHttpClient.Builder();
    }

    public static <S> S createService(Class<S> class_) {
        return (S)retrofit.create(class_);
    }
}

