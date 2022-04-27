package br.com.grupo3.entidades;

import br.com.grupo3.enums.Cargo;

public class Gerente extends Funcionario {
	private int codAgencia;
	public Gerente(String cpf, String senha, Cargo cargo,int codAgencia) {
		super(cpf, senha, cargo);
		this.codAgencia=codAgencia;
		
	}
	public int getCodAgencia() {
		return codAgencia;
	}


}
