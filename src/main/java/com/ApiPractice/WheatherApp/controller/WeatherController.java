package com.ApiPractice.WheatherApp.controller;

import com.ApiPractice.WheatherApp.ResponseModel.WeatherResponse;
import com.ApiPractice.WheatherApp.Service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String cityName){
        WeatherResponse weatherResponse = weatherService.getWeatherData(cityName);
        return ResponseEntity.ok(weatherResponse);
    }
}
