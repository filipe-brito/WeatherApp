package com.abelatroz.weatherapp.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SearchBar {
	TextField cityInput;
	Button fetchButton;
	VBox searchSection;
	
	public SearchBar() {
		// PAINEL COM OS ELEMENTOS PARA BUSCAR A PREVISÃO
        // Campo de texto para cidade
        cityInput = new TextField();
        cityInput.setPromptText("Digite o nome da cidade");
        cityInput.getStyleClass().add("cityInput");

        // Botão para buscar previsão
        fetchButton = new Button("Buscar Previsão");
        fetchButton.getStyleClass().add("fetchButton");
        
        searchSection = new VBox(15, cityInput, fetchButton);
        searchSection.getStyleClass().add("searchSection");
	}

	public TextField getCityInput() {
		return cityInput;
	}

	public Button getFetchButton() {
		return fetchButton;
	}

	public VBox getSearchSection() {
		return searchSection;
	}
}
