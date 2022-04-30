package br.com.grupo3.exceptions;


@SuppressWarnings("serial")
public class SaldoInsuficienteException extends Exception {

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Saldo insuficiente";
	}

}
