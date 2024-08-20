package com.abelatroz.WeatherApp;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApp {
	
	private static final String API_KEY = "365ba3b15a813aa62f189fcc4daa3d84"; // Substitua com sua chave da API

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		
		System.out.print("Digite o nome da cidade: ");
		String city = scan.next();
		
		scan.close();
		
		
		String apiUrl = String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s", city, API_KEY);
		
		try {
			URL url = new URL(apiUrl);
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
			    System.out.println("Data e hora da previsão: " + date.toString());

			    JSONObject main = firstWeatherEntry.getJSONObject("main");
			    double temperatureInKelvin = main.getDouble("temp");
			    double temperatureInCelsius = temperatureInKelvin - 273.15;
			    
			    System.out.printf("Temperatura: %.2f°C\n", temperatureInCelsius);
			}else {
				System.out.println("Erro na requisição: " + responseCode);
			}
		}catch (MalformedURLException e) {
		    System.out.println("URL malformada: " + e.getMessage());
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

}
