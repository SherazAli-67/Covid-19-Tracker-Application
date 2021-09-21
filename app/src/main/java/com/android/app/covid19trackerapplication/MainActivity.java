package com.android.app.covid19trackerapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.card.MaterialCardView;
import com.hbb20.CountryCodePicker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView global_totalcases,global_deaths,global_recovered,country_totalcase,country_deaths,country_recovered
            ,global_newcases,global_newDeaths, global_newRecovered, country_newcases, country_newDeaths, country_newRecovered;

    Retrofit retrofit;
    CountryCodePicker country;
    MaterialCardView global_card, country_card;
    LinearLayout mainLayout;
    ProgressBar progressBar;
    RelativeLayout no_internet;
    RecyclerView latest_updates;
    List<CovidModel> arrayList = new ArrayList<>();
    List<CovidModel> latest_news;
    Button referesh;
    RetrofitAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latest_news = new ArrayList<>();

        global_totalcases = findViewById(R.id.global_totalcase);
        global_deaths = findViewById(R.id.global_deaths);
        global_recovered = findViewById(R.id.global_recovered);

        country_totalcase = findViewById(R.id.country_totalcase);
        country_deaths = findViewById(R.id.country_deaths);
        country_recovered = findViewById(R.id.country_recovered);

        global_newcases = findViewById(R.id.global_newCase);
        global_newDeaths = findViewById(R.id.country_newDeaths);
        global_newRecovered = findViewById(R.id.global_newRecovered);

        country_newcases = findViewById(R.id.country_newCase);
        country_newDeaths = findViewById(R.id.country_newDeaths);
        country_newRecovered = findViewById(R.id.country_newRecovered);

        global_card = findViewById(R.id.global_card);
        country_card = findViewById(R.id.country_card);

        country = findViewById(R.id.country_name);

        progressBar = findViewById(R.id.progressBar);
        mainLayout = findViewById(R.id.mainLayout);

        no_internet = findViewById(R.id.no_internet);
        latest_updates = findViewById(R.id.latest_updates);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        latest_updates.setLayoutManager(layoutManager);
        referesh = findViewById(R.id.referesh);

        retrofit = Retrofit_Instance.getInstance();

        api = retrofit.create(RetrofitAPI.class);

        mainLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        if(!(isInternetConnected())){
            mainLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            no_internet.setVisibility(View.VISIBLE);
        }

        Call<List<CovidModel>> countries = api.getCountries();

        countries.enqueue(new Callback<List<CovidModel>>() {
            @Override
            public void onResponse(Call<List<CovidModel>> call, Response<List<CovidModel>> response) {
                List<CovidModel> list = response.body();

                for(CovidModel covidCases: list){
                    int cases = covidCases.getCases();
                    int deaths = covidCases.getDeaths();
                    int recovered = covidCases.getRecovered();
                    int newCases = covidCases.getTodayCases();
                    int newDeaths = covidCases.getTodayDeaths();
                    int newRecovered = covidCases.getTodayRecovered();
                    String country = covidCases.getCountryName();
                    arrayList.add(new CovidModel(cases, deaths,recovered,newCases,newDeaths,newRecovered,country));

                }
                LatestUpdates_Adapter adapter = new LatestUpdates_Adapter(response.body(),MainActivity.this);
                latest_updates.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CovidModel>> call, Throwable t) {

            }
        });

//



        //Fetching global data
        Call<CovidModel> call = api.getDetails();

        call.enqueue(new Callback<CovidModel>() {
            @Override
            public void onResponse(Call<CovidModel> call, Response<CovidModel> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    mainLayout.setVisibility(View.VISIBLE);
                    CovidModel covidCases = response.body();
                    int cases = covidCases.getCases();
                    int deaths = covidCases.getDeaths();
                    int recovered = covidCases.getRecovered();
                    int newCases = covidCases.getTodayCases();
                    int newDeaths = covidCases.getTodayDeaths();
                    int newRecovered = covidCases.getTodayRecovered();
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");

                    if(covidCases.getTodayRecovered() ==null){
                        Toast.makeText(MainActivity.this, "Zero", Toast.LENGTH_SHORT).show();
                    }else{
                        global_totalcases.setText(""+decimalFormat.format(cases));
                        global_deaths.setText(""+decimalFormat.format(deaths));
                        global_recovered.setText(""+decimalFormat.format(recovered));
                        global_newcases.setText("+"+decimalFormat.format(newCases));
                    global_newDeaths.setText("+"+decimalFormat.format(newDeaths));
                    global_newRecovered.setText("+"+decimalFormat.format(newRecovered));
                    }


                }
            }

            @Override
            public void onFailure(Call<CovidModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Fetching DefaultCountry
        Call<CovidModel> defaultCountry= api.getCountry(country.getDefaultCountryName());
        defaultCountry.enqueue(new Callback<CovidModel>() {
            @Override
            public void onResponse(Call<CovidModel> call, Response<CovidModel> response) {
                if(response.isSuccessful()){
                    CovidModel covidCases = response.body();

                    int cases = covidCases.getCases();
                    int deaths = covidCases.getDeaths();
                    int recovered = covidCases.getRecovered();
                    int newCases = covidCases.getTodayCases();
                    int newDeaths = covidCases.getTodayDeaths();
                    int newRecovered = covidCases.getTodayRecovered();

                    country_totalcase.setText(""+cases);
                    country_deaths.setText(""+deaths);
                    country_recovered.setText(""+recovered);
                    country_newcases.setText(""+newCases);
                    country_newDeaths.setText(""+newDeaths);
                    country_newRecovered.setText(""+newRecovered);
                }
            }

            @Override
            public void onFailure(Call<CovidModel> call, Throwable t) {

            }
        });
        //Fetching Country Selected
        country.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                progressBar.setVisibility(View.VISIBLE);
                String name = country.getSelectedCountryName();
                Call<CovidModel> country = api.getCountry(name);

                country.enqueue(new Callback<CovidModel>() {
                    @Override
                    public void onResponse(Call<CovidModel> call, Response<CovidModel> response) {
                        if(response.isSuccessful()){
                            progressBar.setVisibility(View.INVISIBLE);
                            mainLayout.setVisibility(View.VISIBLE);
                            CovidModel covidCases = response.body();

                            int cases = covidCases.getCases();
                            int deaths = covidCases.getDeaths();
                            int recovered = covidCases.getRecovered();
                            int newCases = covidCases.getTodayCases();
                            int newDeaths = covidCases.getTodayDeaths();
                            int newRecovered = covidCases.getTodayRecovered();

                            country_totalcase.setText(""+cases);
                            country_deaths.setText(""+deaths);
                            country_recovered.setText(""+recovered);
                            country_newcases.setText(""+newCases);
                            country_newDeaths.setText(""+newDeaths);
                            country_newRecovered.setText(""+newRecovered);
                        }
                    }

                    @Override
                    public void onFailure(Call<CovidModel> call, Throwable t) {

                    }
                });
            }
        });

    }

    public void onCountryClick(View view){
        int totalCases = Integer.parseInt(country_totalcase.getText().toString());
        int recoveredCases = Integer.parseInt(country_recovered.getText().toString());
        int deaths = Integer.parseInt(country_deaths.getText().toString());
        int newCases = Integer.parseInt(country_newcases.getText().toString());
        int newDeaths = Integer.parseInt(country_newDeaths.getText().toString());
        int newRecovered = Integer.parseInt(country_newRecovered.getText().toString());
        String countryName = country.getSelectedCountryName();
        String countryCode = country.getSelectedCountryNameCode();

        Intent moveTo = new Intent(MainActivity.this,DisplayData.class);
        moveTo.putExtra("Total",totalCases);
        moveTo.putExtra("Recovered", recoveredCases);
        moveTo.putExtra("Deaths", deaths);
        moveTo.putExtra("New Cases", newCases);
        moveTo.putExtra("New Deaths", newDeaths);
        moveTo.putExtra("New Recovered",newRecovered);
        moveTo.putExtra("Country",countryName);
        moveTo.putExtra("CountryCode", countryCode);
        startActivity(moveTo);
    }

    public boolean isInternetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }

    public void onRefreshClick(View view){
        progressBar.setVisibility(View.VISIBLE);
        if (isInternetConnected()){
            progressBar.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
            no_internet.setVisibility(View.GONE);
        }
    }


}