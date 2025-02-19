package com.abelatroz.weatherapp.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abelatroz.weatherapp.model.Forecast;

public class WeatherService {
	
	private LocalDate today = LocalDate.now();
	
	public Map <String, List<Forecast>> daysOrganizer(List<Forecast> allForecasts) {
		Map <String, List<Forecast>> forecastMap = new HashMap<>(); // Criamos o Map
		
		// Definimos as chaves e inicializamos as listas para receberem as previsões. Criamos o Map com as chaves automaticamente
		for(int i = 1; i <= 5; i++) {
			forecastMap.put("day" + i, new ArrayList<>());
		}
		for(Forecast entry : allForecasts) {
			LocalDate entryDate = Instant.ofEpochSecond(entry.getDt()).atZone(ZoneId.systemDefault()).toLocalDate();
			// Calculamos a diferença em dias entre a data de hoje e a data de entrada do loop
			int daysDifference = (int)ChronoUnit.DAYS.between(today, entryDate);
			if(daysDifference >= 0 && daysDifference < 5) {
				String key = "day" + (daysDifference + 1);
				forecastMap.get(key).add(entry);
			}
		}
		return forecastMap;
	}
}
