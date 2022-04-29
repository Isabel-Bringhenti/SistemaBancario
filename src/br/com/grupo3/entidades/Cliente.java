package br.com.grupo3.entidades;

import br.com.grupo3.exceptions.ConstrucaoInvalidaException;

public class Cliente extends Pessoa {

	public Cliente(String cpf, String senha) throws ConstrucaoInvalidaException {
		super(cpf, senha);

	}
	
}
