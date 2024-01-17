package com.floksdev.weather.dto;

// API'den gelen JSON'a göre isimlendirme
public record Request(
        String type,
        String query,
        String language,
        String unit
) {
}
