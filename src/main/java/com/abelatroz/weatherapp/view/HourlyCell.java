package com.abelatroz.weatherapp.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HourlyCell {
	Label hourLabel;
	Label tempLabel;
	VBox hourlyCell;
	
	public HourlyCell() {
		hourlyCell = new VBox();
		
    	hourLabel = new Label("hour");
    	hourLabel.getStyleClass().add("hourLabel");
    	tempLabel = new Label("temp°C");
    	tempLabel.getStyleClass().add("tempLabel");
    	
    	hourlyCell.getChildren().addAll(hourLabel, tempLabel);
    	hourlyCell.getStyleClass().add("hourlyCell");
	}

	public Label getHourLabel() {
		return hourLabel;
	}

	public Label getTempLabel() {
		return tempLabel;
	}

	public VBox getHourlyCell() {
		return hourlyCell;
	}
}
