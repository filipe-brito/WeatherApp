package com.abelatroz.WeatherApp;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApp {
	
	private static final String API_KEY = "365ba3b15a813aa62f189fcc4daa3d84"; // Substitua com sua chave da API

	public static String getWeather(String city) {
		
		String cityCoded = null;
		try {
			cityCoded = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
		} catch (Exception e) {
			return "Erro na codificação do nome da cidade: " + e.getMessage();
		}
		
		
		String apiUrl = String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s", cityCoded, API_KEY);
		
		try {
			URI uri = new URI(apiUrl);
			URL url = uri.toURL();
			HttpURLConnection connection= (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			System.out.println("código da resposta: " + responseCode);
			if(responseCode == 200) {
			    Scanner responseScanner = new Scanner(connection.getInputStream());
			    StringBuilder response = new StringBuilder();
			    while (responseScanner.hasNext()) {
			        response.append(responseScanner.nextLine());
			    }
			    responseScanner.close();
			    
			    JSONObject jsonResponse = new JSONObject(response.toString());
			    JSONArray weatherList = jsonResponse.getJSONArray("list");
			    JSONObject firstWeatherEntry = weatherList.getJSONObject(0);
			    
			    // Obtendo a data e hora da previsão
			    long timestamp = firstWeatherEntry.getLong("dt");
			    Date date = new Date(timestamp * 1000); // Converter para milissegundos
			    String weatherDate = date.toString();

			    JSONObject main = firstWeatherEntry.getJSONObject("main");
			    double temperatureInKelvin = main.getDouble("temp");
			    double temperatureInCelsius = temperatureInKelvin - 273.15;
			    
			    return String.format("Data e hora da previsão: %s\nTemperatura: %.2f°C", weatherDate, temperatureInCelsius);
			    
			}else {
				return "Erro na requisição: " + responseCode;
			}
		}catch (MalformedURLException e) {
		    return "URL malformada: " + e.getMessage();
			
		} catch (Exception e) {
		    return "Erro inesperado: " + e.getMessage();
		}
	}

}