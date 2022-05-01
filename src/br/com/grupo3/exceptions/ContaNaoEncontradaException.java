package br.com.grupo3.exceptions;

@SuppressWarnings("serial")
public class ContaNaoEncontradaException extends Exception {

	@Override
	public String getMessage() {
		return "Conta não encontrada no sistema";
	}
	
}
