package br.com.grupo3.entidades;

import br.com.grupo3.enums.Cargo;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;

public abstract class Funcionario extends Pessoa {
	
	private Cargo cargo;
	
	public Funcionario(String cpf, String senha, int codCargo) throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super(cpf, senha);
		this.cargo=Cargo.getCargoPorCodigo(codCargo);
	}

	public Cargo getCargo() {
		return cargo;
	}

}
