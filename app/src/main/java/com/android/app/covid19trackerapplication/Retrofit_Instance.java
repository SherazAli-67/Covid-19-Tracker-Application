package com.android.app.covid19trackerapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Instance {
    private static Retrofit instance;
    private static final String BASEURL = "https://corona.lmao.ninja/v3/covid-19/";
    public static Retrofit getInstance(){
        if(instance == null){
            instance = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).
                    build();
        }
        return instance;
    }
}
