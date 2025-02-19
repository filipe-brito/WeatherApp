package com.abelatroz.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {
	@JsonProperty("temp")
	private double temp;

	public double getTemp() {
		return temp;
	}
}
