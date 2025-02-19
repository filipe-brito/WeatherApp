package com.abelatroz.weatherapp.model;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/*Serialização: Converter um objeto Java para uma string JSON.
Desserialização: Converter uma string JSON de volta para um objeto Java.*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
	@JsonProperty("dt")
	private long dt;
	@JsonProperty("main")
	private Main main;
	@JsonProperty("weather")
	private List<Weather> weather;
	
	public long getDt() {
		return dt;
	}
	public Main getMain() {
		return main;
	}
	public List<Weather> getWeather() {
		return weather;
	}
	
	@Override
	public String toString() {
	    return "Data: " + Instant.ofEpochSecond(dt).atZone(ZoneId.systemDefault()).toLocalDateTime()
	            + ", Temperatura: " + main.getTemp() + "°C";
	}
}
