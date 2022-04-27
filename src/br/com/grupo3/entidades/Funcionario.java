package br.com.grupo3.entidades;

import br.com.grupo3.enums.Cargo;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;

public abstract class Funcionario extends Pessoa {
	
	private Cargo cargo;
	
	public Funcionario(String cpf, String senha, Cargo cargo) throws ConstrucaoInvalidaException {
		super(cpf, senha);
		this.cargo=cargo;
	}

	public Cargo getCargo() {
		return cargo;
	}

}
