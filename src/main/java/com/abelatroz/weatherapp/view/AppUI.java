package com.abelatroz.weatherapp.view;

import com.abelatroz.weatherapp.controller.WeatherController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppUI extends Application {
	// Como as informações das interfaces serão alteradas constantemente, o ideal é tornar esses objetos atributos da classe
	SearchBar searchBar;
	
	Label cityAndCountryLabel;
	
	TodayCell currentDayCell;
	// Array com as células do seletor de dia
	DayCell[] days;
	// Array com as células por hora
	HourlyCell[] hourlyCells;
	
	Label message;
	
	
	VBox cityResearched;
	
	HBox weatherDisplay;
	
	VBox errorBox;
	
	VBox resultBox;
	
    // Inicializar atributos no construtor é uma boa prática porque garante que os objetos estejam prontos para uso assim que a instância da classe for criada.
    public AppUI() {
    	searchBar = new SearchBar();
    	cityAndCountryLabel = new Label();
    	currentDayCell = new TodayCell();
    	days = new DayCell[4];
    	hourlyCells = new HourlyCell[5];
    	
    	cityResearched = new VBox();
    	weatherDisplay = new HBox(10);
    	errorBox = new VBox(10);
    	resultBox = new VBox(10);
    	
    	message = new Label();
    }

    @Override
    public void start(Stage primaryStage) {
    	
    	WeatherController controller = new WeatherController(this);
    	
    	// CARREGA AS FONTES PERSONALIZADAS
    	FontLoader.loadFonts(18);
    	
        // TÍTULO DA INTERFACE
        Label title = new Label("Previsão do Tempo");
        title.getStyleClass().add("label-title");
        VBox titleBox = new VBox(title);
        titleBox.getStyleClass().add("titleBox");
        
        // PAINEL COM OS ELEMENTOS PARA BUSCAR A PREVISÃO
        searchBar.getFetchButton().setOnAction(event ->{
        	String city = searchBar.getCityInput().getText().trim();
        	if (city.isEmpty()) {
                String message = "O campo de pesquisa está vazio. Por favor, informe uma cidade.";
                errorValidator(true, message);
                return;
            } 
        	controller.fetchWeather(city);
        });
        
        VBox searchSection = new VBox(searchBar.getSearchSection());
        searchSection.getStyleClass().add("searchSection");
        
        // PEQUENO PAINEL PARA MOSTRAR O PAÍS E A CIDADE PESQUISADA
        cityAndCountryLabel.setText("City, Country");
        cityAndCountryLabel.getStyleClass().add("cityAndCountryLabel");
        cityResearched.getStyleClass().add("cityResearched");
        cityResearched.getChildren().add(cityAndCountryLabel);
        
        // CÉLULA COM A PREVISÃO ATUAL
        currentDayCell.getTodayCell().setOnMouseClicked(event -> {controller.updateHourlyCells(1); 
        currentDayCell.getTodayCell().getStyleClass().add("clickedDay");
        });

        // SELETOR DE DIA
        for(int i = 0; i < 4; i++) {
        	final int index = i;
        	days[i] = new DayCell();
        	days[i].getDayCell().setOnMouseClicked(event -> {
            	controller.updateHourlyCells(index+2);
            	styleDaySelected(index);
            }); 
        }
        HBox daySelector = new HBox(15);
        for(int i = 0; i < 4; i++) {
        	daySelector.getChildren().add(days[i].getDayCell());
        }
        daySelector.getStyleClass().add("daySelector");  
        
        // CÉLULAS DOS HORÁRIOS
        for (int i = 0; i < 5; i++) {
            hourlyCells[i] = new HourlyCell();
        }
        HBox hourlyPanel = new HBox(5);
        for(int i = 0; i < 5; i++) {
        	hourlyPanel.getChildren().add(hourlyCells[i].getHourlyCell());
        }
        hourlyPanel.getStyleClass().add("hourlyPanel");
                
        VBox otherCells = new VBox(10, daySelector, hourlyPanel);
        otherCells.getStyleClass().add("otherCells");
        
        // HBOX QUE ORGANIZA HORIZONTALMENTE AS CÉLULAS DE HOJE E POR HORÁRIO, E O SELETOR DE DIA
        weatherDisplay.getChildren().addAll(currentDayCell.getTodayCell(), otherCells);
        weatherDisplay.getStyleClass().add("weatherDisplay");
        
        // VBOX PADRÃO PARA MENSAGENS DE ERRO
        message.setText("Digite o nome da cidade");
        message.setWrapText(true); // Permite que o texto quebre automaticamente
        message.getStyleClass().add("message");
        errorBox.getChildren().add(message);
        errorBox.getStyleClass().add("errorBox");
        
        // VBOX QUE ENVOLVE TODOS OS ITENS QUE REPRESENTAM O RETORNO DA CONSULTA DE PREVISÃO, SEJA PARA APRESENTAR A TELA DE PREVISÃO OU A TELA DE ERRO
        resultBox.getChildren().add(errorBox);
        resultBox.getStyleClass().add("resultBox");
        
        // VBOX COMO TODOS OS ELEMENTOS QUE SERÃO EXIBIDOS NA CENA
        VBox root = new VBox(15, titleBox, searchSection, resultBox);
        
        // CENA COMPLETA E ESTILO
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // CONFIGURAÇÃO DA JANELA PRINCIPAL
        primaryStage.setTitle("WeatherApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    // MÉTODO PARA APLICAR O ESTILO QUANDO O DIA É CLICADO
    public void styleDaySelected(int clickedBox) {
    	currentDayCell.getTodayCell().getStyleClass().remove("clickedDay");
		currentDayCell.getTodayCell().getStyleClass().add("currentDayCell");
    	for(int i = 0; i < 4; i++) {
    		if(i == clickedBox) {
    			days[i].getDayCell().getStyleClass().add("clickedDay");
    		} else {
    			days[i].getDayCell().getStyleClass().remove("clickedDay");
    			days[i].getDayCell().getStyleClass().add("days");
    		}
    	}
    }
    
    // MÉTODO PARA ALTERNAR O BOX DE PREVISÃO E DE ERRO
    public void errorValidator (boolean error, String errorMessage) {
    	if(!error) {
    		resultBox.getChildren().setAll(cityResearched, weatherDisplay);
    	} else {
    		resultBox.getChildren().setAll(errorBox);
    		message.setText("ERRO!\n" + errorMessage);
    	}
    }

	//GETTERS
    public TodayCell getCurrentDayCell() {
		return currentDayCell;
	}
    
    public DayCell[] getDays() {
		return days;
	}
    
    public HourlyCell[] getHourlyCells() {
		return hourlyCells;
	}

	public Label getCityAndCountryLabel() {
		return cityAndCountryLabel;
	}
}