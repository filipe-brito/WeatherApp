package com.abelatroz.WeatherAppUI;

import java.io.IOException;
import java.io.InputStream; // Importa InputStream para leitura de arquivos

import com.abelatroz.WeatherApp.WeatherApp; // Importa a lógica da classe WeatherApp

import javafx.application.Application; // Importa a classe base para apps JavaFX
import javafx.scene.Scene; // Importa a classe Scene para definir a interface
import javafx.scene.control.Button; // Importa a classe Button
import javafx.scene.control.Label; // Importa a classe Label para textos
import javafx.scene.control.TextField; // Importa a classe TextField para entrada de texto
import javafx.scene.effect.DropShadow; // Importa a classe DropShadow para efeitos visuais
import javafx.scene.layout.VBox; // Importa VBox para layout vertical
import javafx.scene.paint.Color; // Pacote para aplicar cores na fonte
import javafx.scene.text.Font; // Importa a classe Font para customizar fontes
import javafx.scene.text.Text; // Importa a classe Text para textos com fonte customizada
import javafx.stage.Stage; // Importa Stage, que representa a janela principal

public class WeatherAppUI extends Application { // Classe principal da interface gráfica estendendo Application
    
    // Declara os componentes da interface
    private TextField cityInput; // Campo de entrada de texto para cidade
    private Label weatherOutput; // Rótulo para exibir o resultado da previsão
    private boolean buttonOn; // Adiciona variável para controlar o estado do botão
    
    @Override
    public void start(Stage primaryStage) { // Método principal da interface, executado ao iniciar a aplicação
        
        // Inicializa os componentes
        cityInput = new TextField(); // Campo de entrada de texto
        cityInput.setPromptText("Digite o nome da cidade"); // Define o texto de dica no campo
        cityInput.setId("cityInput");
        
        Button getWeatherButton = new Button(); // Botão para buscar a previsão
        Text buttonText = new Text("Obter Previsão"); // Texto do botão
        buttonText.setFill(Color.BLACK);
        getWeatherButton.setGraphic(buttonText); // Aplica o texto no botão
        getWeatherButton.setId("getWeatherButton");
        buttonText.setId("buttonText");
        
        // Inicializa o estado do botão como desligado
        buttonOn = false;
        
        getWeatherButton.setOnAction(e -> toggleWeatherButton(getWeatherButton, buttonText)); // Aplica o método de ligar/desligar botão
        
        weatherOutput = new Label("Previsão será exibida aqui"); // Rótulo para mostrar o resultado da previsão
        weatherOutput.setId("weatherOutput");
        
        // Carregar as fontes 
        InputStream font1 = getClass().getResourceAsStream("/fonts/PixelifySans-Medium.ttf"); // Abre o arquivo da fonte
        Font pixelFont1 = Font.loadFont(font1, 18); // Carrega a fonte com tamanho 18
        InputStream font2 = getClass().getResourceAsStream("/fonts/PixelifySans-Bold.ttf"); // Abre o arquivo da fonte
        Font pixelFont2 = Font.loadFont(font2, 18); // Carrega a fonte com tamanho 18
        
        // Fechar InputStream para evitar vazamento de recursos
        try {
            font1.close(); // Fecha o InputStream após carregar a fonte
            font2.close(); // Fecha o InputStream após carregar a fonte
        } catch (IOException e) {
            e.printStackTrace(); // Imprime o erro se houver falha ao fechar
        }      
        
        // Configura o layout da interface
        VBox root = new VBox(10); // Usando VBox para organizar os componentes verticalmente, com um espaçamento de 10 pixels
        root.setId("root"); // Define um ID para o VBox
        
        // Adiciona os componentes ao VBox
        root.getChildren().addAll(cityInput, getWeatherButton, weatherOutput); // Adiciona os componentes ao layout
        
        // Cria a cena e aplica a folha de estilos CSS
        Scene scene = new Scene(root, 600, 300); // Cria a cena com o layout e define o tamanho
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm()); // Aplica o CSS
        
        // Configura o palco (janela)
        primaryStage.setTitle("WeatherApp"); // Define o título da janela
        primaryStage.setScene(scene); // Define a cena a ser exibida na janela
        primaryStage.show(); // Exibe a janela
    }
      
    // Método para ligar e desligar o botão
    private void toggleWeatherButton(Button getWeatherButton, Text buttonText) {
        if (!buttonOn) {
            // Se o botão estiver desligado, ativa
            buttonOn = true; // Muda o estado para "ligado"
            
            getWeatherButton.setStyle("-fx-background-image: url('images/button-pressed.png');");
            
            buttonText.setFill(Color.ORANGE); // Altera a cor do texto
            buttonText.setStyle("-fx-margin: 10px 0 0 0;");
            
            // Adiciona um efeito de brilho no botão
            fontGlow(buttonText, "orange", 65, 0.85);
            
            // Chama o método de busca de previsão
            fetchWeather();
        } else {
            // Se o botão estiver ligado, desativa
            buttonOn = false; // Muda o estado para "desligado"
            
            getWeatherButton.setStyle("-fx-background-image: url('images/button.png');");
            
            buttonText.setFill(Color.BLACK); // Restaura a cor original
            buttonText.setEffect(null); // Remove o efeito de brilho
            
            weatherOutput.setText("Previsão será exibida aqui"); // Restaura o texto de saída
        }
    }
        
    // Método para aplicar brilho na fonte
    private void fontGlow(Text text, String color, int radius, double spread) {
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web(color));
        glow.setRadius(radius);
        glow.setSpread(spread);
        
        // Aplicar o efeito de brilho
        text.setEffect(glow); // Use setEffect ao invés de setStyle
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
