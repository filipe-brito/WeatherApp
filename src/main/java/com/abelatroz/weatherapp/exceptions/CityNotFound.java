package com.abelatroz.weatherapp.exceptions;

//Classe de exceção personalizada para quando a cidade não é encontrada na API
public class CityNotFound extends RuntimeException {
	// Construtor que recebe uma mensagem personalizada
    public CityNotFound(String message) {
        super(message);
    }
}
