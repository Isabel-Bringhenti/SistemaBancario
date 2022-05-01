package br.com.grupo3.exceptions;

@SuppressWarnings("serial")
public class ValorExistenteException extends Exception {

	@Override
	public String getMessage() {
		return "Valor inserido já existente.";
	}
	
	

}
