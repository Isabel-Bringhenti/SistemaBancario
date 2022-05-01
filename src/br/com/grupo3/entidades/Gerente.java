package br.com.grupo3.entidades;


import br.com.grupo3.enums.Agencia;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;

public class Gerente extends Funcionario {
	private int codAgencia;
	public Gerente(String nome,String cpf, String senha, int codCargo,int codAgencia) throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super(nome,cpf, senha, codCargo);
		this.codAgencia=codAgencia;
		
	}
	public int getCodAgencia() {
		return codAgencia;
	}
	public Agencia getAgenciaPorCodigo() throws CodigoInvalidoException {
		return Agencia.getAgenciaPorCodigo(codAgencia);
	}


}
