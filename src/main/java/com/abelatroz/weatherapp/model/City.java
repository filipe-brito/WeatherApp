package com.abelatroz.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
	@JsonProperty("name")
	private String name;
	@JsonProperty("country")
	private String country;
	
	
	public String getName() {
		return name;
	}
	public String getCountry() {
		return country;
	}
}
