package br.com.grupo3.exceptions;


@SuppressWarnings("serial")
public class CodigoInvalidoException extends Exception {

	@Override
	public String getMessage() {

		return "Codigo inserido inválido";
	}

}
