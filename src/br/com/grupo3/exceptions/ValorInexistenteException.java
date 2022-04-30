package br.com.grupo3.exceptions;

@SuppressWarnings("serial")
public class ValorInexistenteException extends Exception {

	@Override
	public String getMessage() {
		return "Valor inserido não existente";
	}

}
