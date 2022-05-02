package br.com.grupo3.interfaces;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import br.com.grupo3.entidades.Pessoa;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ValorInexistenteException;
import br.com.grupo3.repositorios.ContasRepositorio;

public interface GeradorRelatório {
	void gerarRelatorioDeQtdContas(Pessoa pessoa) throws ValorInexistenteException, CodigoInvalidoException;
		
	void registraRelatorio() throws IOException;
	
}

