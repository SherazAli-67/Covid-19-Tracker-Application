package com.android.app.covid19trackerapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @GET("all")
    Call<CovidModel> getDetails();

    @GET("countries/{country}")
    Call<CovidModel> getCountry(@Path("country") String countryName);

    @GET("countries")
    Call<List<CovidModel>> getCountries();

}

