package br.com.grupo3.entidades;


import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;

public class Gerente extends Funcionario {
	private int codAgencia;
	public Gerente(String cpf, String senha, int codCargo,int codAgencia) throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super(cpf, senha, codCargo);
		this.codAgencia=codAgencia;
		
	}
	public int getCodAgencia() {
		return codAgencia;
	}


}
