package br.com.grupo3.entidades;


import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.repositorios.ContasRepositorio;

public class Presidente extends Funcionario {

	public Presidente(String nome,String cpf, String senha, int codCargo) throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super(nome,cpf, senha, codCargo);
	}
	public void relatorioContas(){
	System.out.println("O número total de contas é de:"+ContasRepositorio.getContas().size());
}
}
