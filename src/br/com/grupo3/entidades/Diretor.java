package br.com.grupo3.entidades;


import java.io.IOException;

import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.repositorios.ContasRepositorio;
import br.com.grupo3.repositorios.PessoaRepositorio;

public class Diretor extends Funcionario {

	public Diretor(String nome,String cpf, String senha, int codCargo) throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super(nome,cpf, senha, codCargo);
		
	}
	public static void gerarClientesByAgenciaId(int idAgencia) throws IOException {
		ContasRepositorio.contaRepositorioLoader();
		
	}

}
