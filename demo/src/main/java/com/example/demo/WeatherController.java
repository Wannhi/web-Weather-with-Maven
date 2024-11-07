package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
public String getWeather(@RequestParam(name = "city", required = false, defaultValue = "Ho Chi Minh") String city, Model model) {
    List<WeatherInfo> weatherInfoList = weatherService.getWeatherByCity(city);
    model.addAttribute("weatherInfo", weatherInfoList);
    model.addAttribute("city", city);
    return "weather";
}

}
