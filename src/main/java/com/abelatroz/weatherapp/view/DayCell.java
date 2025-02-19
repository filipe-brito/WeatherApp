package com.abelatroz.weatherapp.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DayCell {
	Label monthAndDay;
	Label weekday;
	VBox dayCell;
	
	public DayCell() {
		dayCell = new VBox();
		
		monthAndDay = new Label("month and day");
    	monthAndDay.getStyleClass().add("monthAndDay");
    	weekday = new Label("weekday");
    	weekday.getStyleClass().add("weekday");
    	
    	dayCell.getChildren().addAll(weekday, monthAndDay);
    	dayCell.getStyleClass().add("days");
	}

	public Label getMonthAndDay() {
		return monthAndDay;
	}

	public Label getWeekday() {
		return weekday;
	}

	public VBox getDayCell() {
		return dayCell;
	}
}
