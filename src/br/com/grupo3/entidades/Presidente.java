package br.com.grupo3.entidades;


import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;

public class Presidente extends Funcionario {

	public Presidente(String cpf, String senha, int codCargo) throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super(cpf, senha, codCargo);
	}

}
