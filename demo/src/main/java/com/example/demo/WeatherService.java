package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WeatherService {

    private final String API_KEY = "b672aae541f73489eed0ae52194f2bd9";
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";

    public List<WeatherInfo> getWeatherByCity(String cityName) {
        String url = BASE_URL + "?q=" + cityName + "&appid=" + API_KEY + "&lang=vi&units=metric";
        
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Check if the response is valid
        if (response == null) {
            return List.of(new WeatherInfo("N/A", 0, "Không thể lấy thông tin thời tiết."));
        }
        return parseWeatherData(response);
    }

    private List<WeatherInfo> parseWeatherData(String response) {
        List<WeatherInfo> weatherDataList = new ArrayList<>();
        JSONObject json = new JSONObject(response);
        JSONArray list = json.getJSONArray("list");

        // Current time
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Set to track collected dates
        Set<String> datesCollected = new HashSet<>();

        // Fetch data for 5 days starting from the current time
        for (int i = 0; i < list.length(); i++) {
            JSONObject dayData = list.getJSONObject(i);
            JSONObject main = dayData.getJSONObject("main");
            JSONObject weather = dayData.getJSONArray("weather").getJSONObject(0);

            String description = weather.getString("description");
            double temp = main.getDouble("temp");
            String dateTimeStr = dayData.getString("dt_txt"); // Date and time as a string

            // Parse the dateTime string
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

            // Only collect data if it's for a new day from the current time onward
            if (!dateTime.isBefore(now) && !datesCollected.contains(dateTime.toLocalDate().toString())) {
                String icon = weather.getString("icon");
                // Add the new WeatherInfo object to the list
                weatherDataList.add(new WeatherInfo(
                        dateTime.toLocalDate().toString(),
                        (int) Math.round(temp),  // Rounded temperature
                        description,
                        icon
                ));
                
                // Track collected date
                datesCollected.add(dateTime.toLocalDate().toString());

                // Stop after collecting data for 5 days
                if (datesCollected.size() >= 5) {
                    break;
                }
            }
        }

        return weatherDataList;
    }
}
