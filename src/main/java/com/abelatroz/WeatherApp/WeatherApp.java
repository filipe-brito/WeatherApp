package com.abelatroz.WeatherApp;

import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
	
	private static final String API_KEY = "365ba3b15a813aa62f189fcc4daa3d84"; // Substitua com sua chave da API

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		
		System.out.println("Digite o nome da cidade: ");
		String city = scan.next();
		
		scan.close();
		
		
		String apiUrl = String.format("http://api.openweathermap.org/data/2.5/forecast?q%s&appid=%s", city, API_KEY);
		URL url = new URL(apiUrl);
	}

}
