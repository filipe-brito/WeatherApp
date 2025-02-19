package com.abelatroz.weatherapp.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.abelatroz.weatherapp.exceptions.CityNotFound;

public class ApiRequest {
	
	// Termo	|	O que é												|	Exemplo
	// Variável	|	Espaço para armazenar dados temporários				|	int idade = 25;
	// Objeto	|	Instância de uma classe, com atributos e métodos	|	ApiRequest apiRequest = new ApiRequest();
	// Parâmetro|	Variável na declaração do método					|	public String fetchWeather(String cityName)
	// Atributo	|	Propriedade de uma classe ou objeto					|	private static final String API_URL;
	// Argumento|	Valor enviado ao método para preencher um parâmetro	|	fetchWeather("São Paulo");

    // URL base da API (você pode alterar conforme a API que estiver usando)
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private static final String API_KEY = "365ba3b15a813aa62f189fcc4daa3d84"; // Substitua pela sua chave da API

    // Método para buscar dados do clima para uma cidade
    public String fetchWeather(String cityName) {
        StringBuilder response = new StringBuilder();

        try {
        	// Codifica o nome da cidade em UTF-8
            String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8.toString());
            // Monta a URL com a cidade e a chave da API
            URI uri = new URI(API_URL + "?q=" + encodedCityName + "&appid=" + API_KEY + "&units=metric");
            URL url = uri.toURL();

            // Configura a conexão
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Tempo limite para conexão (5 segundos)
            connection.setReadTimeout(5000); // Tempo limite para leitura (5 segundos)

            // Verifica o código de resposta HTTP
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lê a resposta
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
                // Ele encapsula o InputStreamReader para melhorar o desempenho da leitura. Ele lê os dados em blocos (buffers) ao invés de um caractere por vez, tornando a leitura mais eficiente.
                // O InputStreamReader converte os bytes do fluxo de entrada em caracteres.
                // O método getInputStrem faz a leitura da resposta da API
                String line;
                while ((line = reader.readLine()) != null) {
                	/*reader.readLine() → "linha 1" → atribuído a `line`
                	while ("linha 1" != null) → executa o bloco do laço*/
                    response.append(line);
                }
                reader.close();
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            	throw new CityNotFound("Cidade não encontrada!");
            }else {
                // Lança uma exceção caso a resposta não seja 200 OK
                throw new RuntimeException("HTTP code: " + responseCode);
            }
        } catch (CityNotFound e){
        	throw e; // Repassamos a exceção para ser tratada no controlador
        }catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        // Retorna a resposta como string
        return response.toString();
    }
}