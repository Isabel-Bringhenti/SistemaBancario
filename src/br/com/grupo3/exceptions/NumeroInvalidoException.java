package br.com.grupo3.exceptions;


@SuppressWarnings("serial")
public class NumeroInvalidoException extends Exception {



	@Override
	public String getMessage() {
		return "Numero inválido.";
	}
	
	

}
