package br.com.grupo3.entidades;


import java.util.ArrayList;
import java.util.List;

import br.com.grupo3.enums.Agencia;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.ValorInexistenteException;
import br.com.grupo3.repositorios.ContasRepositorio;

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
	public int getContasPorIdAgencia(int codigo)
			throws ValorInexistenteException, CodigoInvalidoException {
		List<Conta> listaEspecifica = new ArrayList<>();
		for (Conta conta : ContasRepositorio.getContas()) {

			if ((conta.getCodAgencia() == codigo)) {
				listaEspecifica.add(conta);
			}

		}
		return listaEspecifica.size();
	}
		


}
