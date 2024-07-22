package com.abelatroz.WeatherApp;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

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
				scan = new Scanner(connection.getInputStream());
				StringBuilder response = new StringBuilder();
				while(scan.hasNext()){
					response.append(scan.nextLine());
				}
				scan.close();
				
				System.out.println("Resposta da API: " + response.toString());
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
