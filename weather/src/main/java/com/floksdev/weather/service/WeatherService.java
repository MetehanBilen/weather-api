package com.floksdev.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.floksdev.weather.constants.Constants;
import com.floksdev.weather.dto.WeatherDto;
import com.floksdev.weather.dto.WeatherResponse;
import com.floksdev.weather.model.WeatherEntity;
import com.floksdev.weather.repository.WeatherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class WeatherService {
//API-KEY: 935c012cd37d9bd5890e63207b0cdf08
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    //Could not autowire hatası verirse @Bean olmadığı için config yazmak gerekir.
    public WeatherService(WeatherRepository weatherRepository,
                          RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public WeatherDto getWeatherByCityName(String city) {


        Optional<WeatherEntity> weatherEntityOptional
                = weatherRepository.findFirstByRequestedCityNameOrderByUpdatedTimeDesc(city);
        /* 1. yöntem
        if (!weatherEntityOptional.isPresent()) {
            return WeatherDto.convert(getWeatherFromWeatherStack(city));
        }

        if( weatherEntityOptional.get().getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30)))
        {
            return WeatherDto.convert(getWeatherFromWeatherStack(city));
        }
            return WeatherDto.convert(weatherEntityOptional.get());

         */

        //isPresentOrElse kullanılmaz çünkü void methodlar.

        //2. yöntem
        return weatherEntityOptional.map(weather -> {
            if( weather.getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30)))
            {
                return WeatherDto.convert(getWeatherFromWeatherStack(city));
            }
            return WeatherDto.convert(weather);
        }).orElseGet(()->  WeatherDto.convert(getWeatherFromWeatherStack(city)));


    }

    private WeatherEntity getWeatherFromWeatherStack(String city) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getWeatherStackUrl(city), String.class);

        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(),WeatherResponse.class);
            return saveWeatherEntity(city, weatherResponse);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private String getWeatherStackUrl(String city)
    {
        return Constants.API_URL + Constants.ACCESS_KEY_PARAM+ Constants.API_KEY + Constants.QUERY_KEY_PARAM+ city;
    }

    private  WeatherEntity saveWeatherEntity(String city, WeatherResponse weatherResponse)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        WeatherEntity weatherEntity = new WeatherEntity(city,
                                        weatherResponse.location().name(),
                                        weatherResponse.location().country(),
                                        weatherResponse.current().temperature(),
                                        LocalDateTime.now(),
                                        LocalDateTime.parse(weatherResponse.location().localtime(), dateTimeFormatter));

        return weatherRepository.save(weatherEntity);
    }
}

