package br.com.grupo3.exceptions;

@SuppressWarnings("serial")
public class ValorExistenteException extends Exception {

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Valor inserido já existente.";
	}
	
	

}
