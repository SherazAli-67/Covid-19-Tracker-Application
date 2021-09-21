package com.android.app.covid19trackerapplication;

public class CovidModel {
    Integer cases, deaths, recovered, todayCases, todayDeaths, todayRecovered;
    String country;

    public CovidModel(Integer cases, Integer deaths, Integer recovered, Integer todayCases, Integer todayDeaths, Integer todayRecovered, String name) {
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.todayCases = todayCases;
        this.todayDeaths = todayDeaths;
        this.todayRecovered = todayRecovered;
        this.country = name;
    }

    public String getCountryName(){
        return country;
    }
    public Integer getCases() {
        return cases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public Integer getTodayCases() {
        return todayCases;
    }

    public Integer getTodayDeaths() {
        return todayDeaths;
    }

    public Integer getTodayRecovered() {
        return todayRecovered;
    }
}
