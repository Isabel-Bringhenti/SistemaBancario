package br.com.grupo3.entidades;

import br.com.grupo3.enums.Cargo;

public abstract class Funcionario extends Pessoa {
	
	private Cargo cargo;
	
	public Funcionario(String cpf, String senha, Cargo cargo) {
		super(cpf, senha);
		this.cargo=cargo;
	}

}
