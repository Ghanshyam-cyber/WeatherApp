package com.ApiPractice.WheatherApp.Service;

import com.ApiPractice.WheatherApp.ResponseModel.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String weatherApiKey = "cee02627d6ff057a7da9e200f62f2e03";
    private final String weatherApiUrl = "http://api.weatherstack.com/current?access_key="; // access_key= {apikey}&query={city}

    @Autowired
    private final RestTemplate restTemplate;


    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeatherData(String cityName){
        String  url = weatherApiUrl + weatherApiKey + "&query=" + cityName;
        System.out.println("Requesting URL: " + url);

//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//
        WeatherResponse weatherResponse = parseWeatherResponse(response.getBody());
//
        return weatherResponse;
    }

    public WeatherResponse parseWeatherResponse(String jsonResponse) {
        WeatherResponse weatherResponse = new WeatherResponse();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);



            // Example of parsing response data
            weatherResponse.setCityName(rootNode.path("location").path("name").asText());
            weatherResponse.setTemperature(rootNode.path("current").path("temperature").asText());
            weatherResponse.setDescription(rootNode.path("current").path("weather_descriptions").get(0).asText());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle parsing error here
        }

//        wetherResponse.setCityName("Parsed City Name");
//        wetherResponse.setTemperature("Parsed Temperature");
//      wetherResponse.setDescription("Parsed Description");

        return weatherResponse;
    }
}
