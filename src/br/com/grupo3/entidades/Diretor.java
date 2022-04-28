package br.com.grupo3.entidades;

import br.com.grupo3.enums.Cargo;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;

public class Diretor extends Funcionario {

	public Diretor(String cpf, String senha, Cargo cargo) throws ConstrucaoInvalidaException {
		super(cpf, senha, cargo);
		
	}

}
