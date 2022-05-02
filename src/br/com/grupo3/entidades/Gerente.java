package br.com.grupo3.entidades;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.grupo3.enums.Agencia;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.ValorInexistenteException;
import br.com.grupo3.interfaces.GeradorRelatório;
import br.com.grupo3.repositorios.ContasRepositorio;

public class Gerente extends Funcionario implements GeradorRelatório {
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
	public List<Conta> getContasPorIdAgencia(int codigo)
			throws ValorInexistenteException, CodigoInvalidoException {
		List<Conta> listaEspecifica = new ArrayList<>();
		for (Conta conta : ContasRepositorio.getContas()) {

			if ((conta.getCodAgencia() == codigo)) {
				listaEspecifica.add(conta);
			}

		}
		return listaEspecifica;
	}
	@Override
	public void gerarRelatorioDeQtdContas(Pessoa pessoa) throws ValorInexistenteException, CodigoInvalidoException {
		int quantidadeContas;
		
		
		quantidadeContas=ContasRepositorio.getContas().size();
	System.out.println("O número total de contas nas agências selecionadas é de:"+quantidadeContas);
		
	}
	public void registraRelatorio() throws IOException {
		LocalDateTime instante=LocalDateTime.now();
		String s = File.separator;
		File caminhoRelatorioQtdContas= new File("src" + s + "br" + s + "com" + s + "grupo3");
		File relatorioQtdContas= new File(caminhoRelatorioQtdContas.getAbsolutePath()+s+"relatorioRepositorio.csv");
		
		  if(!caminhoRelatorioQtdContas.exists()) {
	          caminhoRelatorioQtdContas.mkdirs();
	      }

	      if(!relatorioQtdContas.exists()) {
	          relatorioQtdContas.createNewFile();
	      }
	      FileWriter relatorioQtdContasWriter = new FileWriter(relatorioQtdContas);
	      BufferedWriter relatorioQtdContasWriterBuff = new BufferedWriter(relatorioQtdContasWriter); 
	    	  relatorioQtdContasWriterBuff.append("Relatório de quantidade de contas, aconteceu em"+ instante);
	    	  relatorioQtdContasWriterBuff.newLine();
	    	  try {
				relatorioQtdContasWriterBuff.append("O número total de contas na agência selecionada é de:"+this.getContasPorIdAgencia(this.getCodAgencia()));
				relatorioQtdContasWriterBuff.close();
				relatorioQtdContasWriter.close();
			} catch (IOException | ValorInexistenteException | CodigoInvalidoException e) {
				System.out.println(e.getMessage());
			}
	    	  
	     
	      }
		


}
