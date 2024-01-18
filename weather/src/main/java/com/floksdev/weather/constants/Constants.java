package com.floksdev.weather.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static String API_URL;
    public static String API_KEY;
    public static String ACCESS_KEY_PARAM = "?access_key=";
    public static String QUERY_KEY_PARAM = "&query=";


    //@Value With Setter Injection
    @Value("${weather-stack.api-url}")
    public void setApiUrl(String apiUrl) {
        Constants.API_URL = apiUrl;
    }
    @Value("${weather-stack.api-key}")
    public void setAccessKey(String accessKey) {
        Constants.API_KEY = accessKey;
    }
}

/*
env değişkeni eklerken .env file ekle sonra Configuration'dan değişkeni ekle, .env file ekle veya ekleme
 */