package com.abelatroz.WeatherAppUI;

import com.abelatroz.WeatherApp.WeatherApp; // Importa a lógica da classe WeatherApp
import javafx.application.Application; // Importa a classe base para apps JavaFX
import javafx.scene.Scene; // Importa a classe Scene para definir a interface
import javafx.scene.control.Button; // Importa a classe Button
import javafx.scene.control.Label; // Importa a classe Label para textos
import javafx.scene.control.TextField; // Importa a classe TextField para entrada de texto
import javafx.scene.layout.VBox; // Importa VBox para organizar os componentes em coluna
import javafx.stage.Stage; // Importa Stage, que representa a janela principal
import javafx.scene.text.Font; // Importa a classe Font para customizar fontes

import java.io.IOException;
import java.io.InputStream; // Importa InputStream para leitura de arquivos

public class WeatherAppUI extends Application { // Classe principal da interface gráfica estendendo Application
    
    // Declara os componentes da interface
    private TextField cityInput; // Campo de entrada de texto para cidade
    private Label weatherOutput; // Rótulo para exibir o resultado da previsão
    
    @Override
    public void start(Stage primaryStage) { // Método principal da interface, executado ao iniciar a aplicação
        
        // Inicializa os componentes
        cityInput = new TextField(); // Campo de entrada de texto
        cityInput.setPromptText("Digite o nome da cidade"); // Define o texto de dica no campo
        
        Button getWeatherButton = new Button("Obter Previsão"); // Botão para buscar a previsão
        getWeatherButton.setOnAction(e -> fetchWeather()); // Define a ação ao clicar no botão
        getWeatherButton.setId("getWeatherButton");
        
        weatherOutput = new Label("Previsão será exibida aqui"); // Rótulo para mostrar o resultado da previsão
        
        // Carrega a fonte PixelifySans-Regular.ttf
        InputStream fontStream = getClass().getResourceAsStream("/fonts/PixelifySans-Medium.ttf"); // Abre o arquivo da fonte
        Font pixelFont = Font.loadFont(fontStream, 18); // Carrega a fonte com tamanho 18
        //Ao usar o InputStream para carregar a fonte, devemos fecha-lo para evitar vazamento de recurso. E como ele gera uma excessão, precisamos instanciá-lo em um bloco try/catch
        try {
            fontStream.close(); // Fecha o InputStream após carregar a fonte
        } catch (IOException e) {
            e.printStackTrace(); // Imprime o erro se houver falha ao fechar
        }      
        
        // Configura o layout da interface
        VBox root = new VBox(10); // Layout em coluna com espaçamento de 10px
        root.getChildren().addAll(cityInput, getWeatherButton, weatherOutput); // Adiciona os componentes ao layout
        
        // Cria a cena e aplica a folha de estilos CSS
        Scene scene = new Scene(root, 300, 200); // Cria a cena com o layout VBox e define o tamanho
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm()); // Aplica o CSS
        
        // Configura o palco (janela)
        primaryStage.setTitle("WeatherApp"); // Define o título da janela
        primaryStage.setScene(scene); // Define a cena a ser exibida na janela
        primaryStage.show(); // Exibe a janela
    }
    
    // Método que busca a previsão do tempo
    private void fetchWeather() {
        String city = cityInput.getText(); // Pega o nome da cidade digitado
        if (city.isEmpty()) { // Verifica se o campo está vazio
            weatherOutput.setText("Por favor, insira o nome da cidade."); // Exibe mensagem de erro
            return; // Encerra o método se estiver vazio
        }
        
        // Chama o método da classe WeatherApp para buscar a previsão
        String result = WeatherApp.getWeather(city); // Obtém a previsão para a cidade
        weatherOutput.setText(result); // Exibe o resultado no rótulo
    }

    public static void main(String[] args) {
        launch(args); // Inicia a aplicação JavaFX
    }
}

