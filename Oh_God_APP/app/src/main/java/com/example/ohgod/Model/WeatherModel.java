package com.example.ohgod.Model;

public class WeatherModel {

    String percent = "";
    String weather = "";

    public void setWeather(String p, String w) {
        this.percent = p;
        this.weather = w;
    }
    public String getPercent() {
        return percent;
    }
    public String getWeather() {
        return weather;
    }
}