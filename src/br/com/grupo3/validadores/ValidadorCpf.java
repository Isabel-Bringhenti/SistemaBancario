package br.com.grupo3.validadores;

import br.com.grupo3.exceptions.ConstrucaoInvalidaException;

public class ValidadorCpf {
	
	public static String validarCpf(String cpf) throws ConstrucaoInvalidaException {
		// Aqui poderia ser feito com cpf.length() != 11
		if (cpf.length()<11||cpf.length()>11) {
			throw new ConstrucaoInvalidaException();
		}
		return cpf;
	}

}
