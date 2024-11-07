package com.example.demo;

public class WeatherInfo {
    private String date;
    private int temp;
    private String description;
    private String icon; // New field for icon

    public WeatherInfo(String date, int temp, String description, String icon) {
        this.date = date;
        this.temp = temp;
        this.description = description;
        this.icon = icon; // Assign icon
    }

    public WeatherInfo(String string, int i, String string2) {
        //TODO Auto-generated constructor stub
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
