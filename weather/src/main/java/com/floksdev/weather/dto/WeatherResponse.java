package com.floksdev.weather.dto;


// API'den gelen JSON'a göre isimlendirme
public record WeatherResponse(
        Request request,
        Location location,
        Current current
) {
}
