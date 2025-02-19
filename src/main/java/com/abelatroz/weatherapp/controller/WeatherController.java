package com.abelatroz.weatherapp.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.abelatroz.weatherapp.exceptions.CityNotFound;
import com.abelatroz.weatherapp.model.ApiRequest;
import com.abelatroz.weatherapp.model.Forecast;
import com.abelatroz.weatherapp.model.WeatherData;
import com.abelatroz.weatherapp.service.UpdateCellsInfo;
import com.abelatroz.weatherapp.service.WeatherService;
import com.abelatroz.weatherapp.view.AppUI;
import com.abelatroz.weatherapp.view.DayCell;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherController {
	private AppUI appUI;
	private WeatherData weatherData;
	private ApiRequest apiRequest; // Instância para acessar o modelo
	
	Locale locale = Locale.getDefault(); // Obtém o idioma do sistema
	
	private List<Forecast> allForecast;
	private Map<String, List<Forecast>> organizedForecasts;
	private WeatherService weatherService = new WeatherService();
	private UpdateCellsInfo updateCellsInfo= new UpdateCellsInfo(this);
	

	// Construtor para inicializar o controlador com a instância de AppUI
	// Esse construtor é necessário para "linkar" as informações da interface com as alterações que faremos com o controlador
    public WeatherController(AppUI appUI) {
        this.appUI = appUI;
    }
    
    // Método para buscar os dados da previsão do tempo
    public void fetchWeather(String city) {        
    	
        // Instanciando o ObjectMapper do JACKSON para mapear o JSON para objetos Java
        ObjectMapper objectMapper = new ObjectMapper();
        try {
        	// Limpa qualquer erro anterior
            appUI.errorValidator(false, "");
            
        	// Fazendo a requisição para obter a previsão do tempo
        	apiRequest = new ApiRequest();
            String apiResponse = apiRequest.fetchWeather(city); // Guarda a resposta em uma String
            System.out.println("Retorno: " + apiResponse); 

            // Desserializa a resposta da API (converte para java)
            weatherData = objectMapper.readValue(apiResponse, WeatherData.class);
            updateCellsInfo.updateCityAndCountryLabel(); // Atualiza o label com a cidade e país pesquisado
            allForecast = weatherData.getList(); // Pega a lista de previsões retornada pela API
            organizedForecasts = weatherService.daysOrganizer(allForecast); // Separamos as previsões por dia
            Forecast todayForecast = organizedForecasts.get("day1").getFirst(); // Pega a primeira previsão referente ao dia de hoje
            updateCellsInfo.updateCurrentDayCell(todayForecast); // Chama o método para atualizar a célula de hoje
            updateCellsInfo.updateHourlyCells(1);
            updateCellsInfo.updateDaySelect();
            appUI.errorValidator(false, "");
        } catch(CityNotFound e) {
        	appUI.errorValidator(true, e.getMessage());
        	return;
        }catch (Exception e) {
            // Capturando qualquer exceção que ocorra e exibindo o erro no console
            e.printStackTrace();
            appUI.errorValidator(true, e.getMessage());
        	return;
        }
    }
    
    public Map<String, List<Forecast>> getOrganizedForecasts() {
		return organizedForecasts;
	}
    
    public void updateHourlyCells(int daySelected) {
    	updateCellsInfo.updateHourlyCells(daySelected);
    	for(DayCell day : appUI.getDays()) {
    		day.getDayCell().getStyleClass().remove("clickedDay");
    		day.getDayCell().getStyleClass().add("days");
    	}
    }
	public AppUI getAppUI() {
		return appUI;
	}

	public WeatherData getWeatherData() {
		return weatherData;
	}
    
	public LocalDateTime timestampConvert(long timestamp) {
		LocalDateTime ldt = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();

		return ldt;
	}
  
}
