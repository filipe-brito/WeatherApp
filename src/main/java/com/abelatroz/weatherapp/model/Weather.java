package com.abelatroz.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
	@JsonProperty("icon")
	private String icon;

	public String getIcon() {
		return icon;
	}
}
