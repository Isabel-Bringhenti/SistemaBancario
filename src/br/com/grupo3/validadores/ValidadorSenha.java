package br.com.grupo3.validadores;

import br.com.grupo3.exceptions.ConstrucaoInvalidaException;

public class ValidadorSenha {
	
	public static String validarSenha(String senha) throws ConstrucaoInvalidaException {
		if (senha.length()<8) {
			throw new ConstrucaoInvalidaException();
		}
		return senha;
	}

}
