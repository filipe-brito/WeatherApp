package com.abelatroz.weatherapp.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TodayCell {
	Label today;
	Label monthAndDay;
	Label temp;
	VBox todayCell;
	
	public TodayCell() {
        // CÉLULA COM A PREVISÃO ATUAL
		today = new Label("HOJE");
		today.getStyleClass().add("todayLabel");
        monthAndDay = new Label("Current Day");
        monthAndDay.getStyleClass().add("currentDayLabel");
        temp = new Label("Current Temp°");
        temp.getStyleClass().add("currentTempLabel");
        todayCell = new VBox(today, monthAndDay, temp);
        todayCell.getStyleClass().add("currentDayCell");
	}

	public Label getMonthAndDay() {
		return monthAndDay;
	}

	public Label getTemp() {
		return temp;
	}

	public VBox getTodayCell() {
		return todayCell;
	}
	
	
}
