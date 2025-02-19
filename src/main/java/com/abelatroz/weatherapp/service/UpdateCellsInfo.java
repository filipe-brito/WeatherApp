package com.abelatroz.weatherapp.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import com.abelatroz.weatherapp.controller.WeatherController;
import com.abelatroz.weatherapp.model.Forecast;
import com.abelatroz.weatherapp.view.DayCell;
import com.abelatroz.weatherapp.view.HourlyCell;

public class UpdateCellsInfo {
	
	WeatherController controller;
	
	Locale locale = Locale.getDefault(); // Obtém o idioma do sistema
	
	public UpdateCellsInfo(WeatherController controller) {
        this.controller = controller;
    }
	
	public void updateCityAndCountryLabel() {
    	String city = controller.getWeatherData().getCity().getName();
    	String country = controller.getWeatherData().getCity().getCountry();
    	controller.getAppUI().getCityAndCountryLabel().setText(city + ", " + country);;
    }
	
	public void updateCurrentDayCell(Forecast forecast) {
    	LocalDateTime ldt = Instant.ofEpochSecond(forecast.getDt()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    	String month = ldt.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    	String day = Integer.toString(ldt.getDayOfMonth());
    	String icon = forecast.getWeather().get(0).getIcon();
    	controller.getAppUI().getCurrentDayCell().getMonthAndDay().setText(month + " " + day);
    	controller.getAppUI().getCurrentDayCell().getTemp().setText(String.format("%.0f°C", forecast.getMain().getTemp()));
    	controller.getAppUI().getCurrentDayCell().getTodayCell().setStyle("-fx-background-image: url('/images/icons/" + icon + ".png'); -fx-background-repeat: no-repeat; -fx-background-position: center;");
    	controller.getAppUI().getCurrentDayCell().getTodayCell().getStyleClass().add("clickedDay");
    	
    	/* 
    	Converte o timestamp (em segundos desde 1970) para um objeto Instant, representando um ponto no tempo
    	Instant.ofEpochSecond(forecast.getDt())

    	Converte o Instant para um ZonedDateTime, ajustando para o fuso horário local do sistema
    	.atZone(ZoneId.systemDefault())

    	Converte o ZonedDateTime para um LocalDateTime, removendo a informação de fuso horário
    	.toLocalDateTime();
    	*/
    }
	
	public void updateDaySelect() {
    	DayCell[] days = controller.getAppUI().getDays();
    	for(int i = 0; i < 4; i++) {
    		Forecast forecast = controller.getOrganizedForecasts().get("day" + (i+2)).get(1);
    		LocalDateTime ldt = controller.timestampConvert(forecast.getDt());
    		String month = ldt.getMonth().getDisplayName(TextStyle.FULL, locale).toString();
    		int day = ldt.getDayOfMonth();
    		String dayweek = ldt.getDayOfWeek().getDisplayName(TextStyle.FULL, locale).toUpperCase().toString();
    		days[i].getMonthAndDay().setText(month + " " + day);
    		days[i].getWeekday().setText(dayweek);
    	}
    }
	
	public void updateHourlyCells(int daySelected) {
    	List <Forecast> forecasts = controller.getOrganizedForecasts().get("day" + daySelected);
		HourlyCell[] cells = controller.getAppUI().getHourlyCells();
		for(HourlyCell cell : cells) {
			cell.getHourlyCell().setVisible(true);
		}
    	if(daySelected == 1) {
    		for(int i = 0; i < 5; i++) {
    			HourlyCell cell = cells[i];
    			if (i + 1 < forecasts.size()) {
    				Forecast forecast = forecasts.get(i+1);
    				LocalDateTime timestamp = controller.timestampConvert(forecast.getDt());
    				String hour = timestamp.format(DateTimeFormatter.ofPattern("HH:mm"));
    				double temp = forecast.getMain().getTemp();
    				String icon = forecast.getWeather().get(0).getIcon();
    				cell.getHourLabel().setText(hour);
    				cell.getTempLabel().setText(String.format("%.0f°C", temp));
    				cell.getHourlyCell().setStyle("-fx-background-image: url('/images/icons/" + icon + ".png'); -fx-background-repeat: no-repeat; -fx-background-position: center;");
    			} else {
    				cell.getHourlyCell().setVisible(false);
    			}
    		}
    	} else {
    		int cellTime = 0;
    		for(int i = 0; i < forecasts.size(); i++) {
    			Forecast forecast = forecasts.get(i);
    			LocalDateTime timestamp = controller.timestampConvert(forecast.getDt());
    			int hour = Integer.parseInt(timestamp.format(DateTimeFormatter.ofPattern("H")));
    			if (hour == 6 || hour == 9 || hour == 12 || hour == 15 || hour == 18) {
    				HourlyCell cell = cells[cellTime];
    				String hourCell = timestamp.format(DateTimeFormatter.ofPattern("HH:mm"));
    				double tempCell = forecast.getMain().getTemp();
    				String icon = forecast.getWeather().get(0).getIcon();
    				cell.getHourLabel().setText(hourCell);
    				cell.getTempLabel().setText(String.format("%.0f°C", tempCell));
    				cell.getHourlyCell().setStyle("-fx-background-image: url('/images/icons/" + icon + ".png'); -fx-background-repeat: no-repeat; -fx-background-position: center;");
    				cellTime++;
    			}
    		}
    	}
    }
}
