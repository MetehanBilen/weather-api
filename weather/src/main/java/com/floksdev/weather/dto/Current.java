package com.floksdev.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Current(
       Integer temperature
) {
}
