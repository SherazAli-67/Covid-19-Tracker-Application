package com.android.app.covid19trackerapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DisplayData extends AppCompatActivity {

    TextView countryName, activeCases, activeDeaths, activeRecovered,newCases, newDeaths, newRecovered, activePercentage,
    deathPercentage,recoveredPercentage;
    Bundle bundle;
    PieChart pieChart;
    Retrofit retrofitInstance;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        initUI();
        bundle = getIntent().getExtras();

        int totalCase = bundle.getInt("Total");
        int recoverd = bundle.getInt("Recovered");
        int deaths = bundle.getInt("Deaths");
        int newActive = bundle.getInt("New Cases");
        int newActiveDeaths = bundle.getInt("New Deaths");
        int newActiveRecovered = bundle.getInt("New Recovered");
        String name = bundle.getString("Country");
        progressBar.setVisibility(View.VISIBLE);

        pieChart.addPieSlice(new PieModel("Total Cases",newActive, getResources().getColor(R.color.blue)));
        pieChart.addPieSlice(new PieModel("Recovered",newActiveRecovered, getResources().getColor(R.color.green)));
        pieChart.addPieSlice(new PieModel("Death",newActiveDeaths, getResources().getColor(R.color.red)));
        pieChart.startAnimation();

        int total = newActive+newActiveDeaths+newActiveRecovered;
        float activePercent = (newActive*100)/total;
        float deathPercent = (newActiveDeaths*100)/total;
        float recoveredPercent = (newActiveRecovered*100)/total;

        retrofitInstance = Retrofit_Instance.getInstance();
        RetrofitAPI api = retrofitInstance.create(RetrofitAPI.class);

        countryName.setText(name);
        activeCases.setText(""+totalCase);
        activeRecovered.setText(""+recoverd);
        activeDeaths.setText(""+deaths);
        newCases.setText("+"+newActive);
        newDeaths.setText("+"+newActiveDeaths);
        newRecovered.setText("+"+newActiveRecovered);
        activePercentage.setText(activePercent+"%");
        deathPercentage.setText(deathPercent+"%");
        recoveredPercentage.setText(recoveredPercent+"%");
}

    private void initUI() {
        countryName = findViewById(R.id.countryName);
        activeCases = findViewById(R.id.activeCases);
        activeDeaths = findViewById(R.id.activeDeaths);
        activeRecovered = findViewById(R.id.activeRecovered);

        newCases = findViewById(R.id.newCases);
        newDeaths = findViewById(R.id.newDeaths);
        newRecovered = findViewById(R.id.newRecovered);
        pieChart = findViewById(R.id.pieChart);
        progressBar = findViewById(R.id.display_progressBar);

        activePercentage = findViewById(R.id.activePercentage);
        deathPercentage = findViewById(R.id.deathPercentage);
        recoveredPercentage = findViewById(R.id.recoveredPercentage);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
}