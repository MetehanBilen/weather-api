package com.floksdev.weather.controller;

import com.floksdev.weather.controller.validation.CityNameConstraint;
import com.floksdev.weather.dto.WeatherDto;
import com.floksdev.weather.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/weather")
@Validated //string diye ekledik.
public class WeatherAPI {

    private final WeatherService weatherService;


    public WeatherAPI(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    /*
    (@PathVariable("city")@CityNameConstraint @NotBlank String city), String değil de başka nesne olsaydı
    (@PathVariable("city") @Valid @CityNameConstraint @NotBlank String city) yeterli olacaktır.
     */
    public ResponseEntity<WeatherDto> getWeather(@PathVariable("city")@CityNameConstraint @NotBlank String city)
    {
        return ResponseEntity.ok(weatherService.getWeatherByCityName(city));
    }
}
