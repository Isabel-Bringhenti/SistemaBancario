package br.com.grupo3.exceptions;

@SuppressWarnings("serial")
public class SenhaInvalidaException extends Exception {

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Senha inválida!";
	}

}
