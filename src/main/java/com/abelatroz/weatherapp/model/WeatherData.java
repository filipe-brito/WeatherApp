package com.abelatroz.weatherapp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Essa classe receberá toda a resposta da api como um objeto único

/*
 * Para cada objeto json, criamos uma classe java, e para cada parâmetro do objeto json terá um parâmetro correspondente na classe java. Exemplo: Cariamos a classe WeatherData para representar toda o retorno json. Nesse objeto tem um array "list". Então criamos o atributo list na classe.
 * Antes de cada atributo, podemos declarar a anotação @JsonProperty("tag_json") para linkar o valor do objeto json ao atributo da classe no java. Tembém podemos fazer a assossiação implícita simplesmente colocando o mesmo no do atributo json no atributo java. Exemplo: json tem o array de objetos "list". Colocando esse mesmo nome no atributo da classe, o Jackson fará o mapeamente por nome.
 * 
*/
@JsonIgnoreProperties(ignoreUnknown = true) // Todos os outros atribubutos json que não colocarmos como atributos dessa classe serão ignorados pelo Jackson
public class WeatherData {
	@JsonProperty("list")
	private List<Forecast> list; 
	@JsonProperty("city")
	private City city;
	
	public List<Forecast> getList() {
		return list;
	}
	public City getCity() {
		return city;
	}
}
