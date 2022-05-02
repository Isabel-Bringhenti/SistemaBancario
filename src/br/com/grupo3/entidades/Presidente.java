package br.com.grupo3.entidades;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.ValorInexistenteException;
import br.com.grupo3.interfaces.GeradorRelatório;
import br.com.grupo3.repositorios.ContasRepositorio;

public class Presidente extends Funcionario implements GeradorRelatório {

	public Presidente(String nome,String cpf, String senha, int codCargo) throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super(nome,cpf, senha, codCargo);
	}
	public void relatorioContas(){
	System.out.println("O número total de contas é de:"+ContasRepositorio.getContas().size());
}
	@Override
	public void gerarRelatorioDeQtdContas(Pessoa pessoa) throws ValorInexistenteException, CodigoInvalidoException {
		int quantidadeContas;
		
		
		quantidadeContas=ContasRepositorio.getContas().size();
	System.out.println("O número total de contas nas agências selecionadas é de:"+quantidadeContas);
		
	}
	@Override
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
      FileWriter relatorioQtdContasWriter = new FileWriter(relatorioQtdContas,true);
      BufferedWriter relatorioQtdContasWriterBuff = new BufferedWriter(relatorioQtdContasWriter); 
    	  relatorioQtdContasWriterBuff.append("Relatório de quantidade de contas,feito por"+ this.nome+" aconteceu em"+ instante);
    	  relatorioQtdContasWriterBuff.newLine();
    	  relatorioQtdContasWriterBuff.append("O número total de contas no banco é de:"+ContasRepositorio.getContas().size());
    	  relatorioQtdContasWriterBuff.newLine();
    	  relatorioQtdContasWriterBuff.close();
    	  relatorioQtdContasWriter.close();
    	  
     
      }
	public void registraRelatorioTotalNoBanco() throws IOException {
		LocalDateTime instante=LocalDateTime.now();
		String s = File.separator;
		File caminhoRelatorioTotalBanco= new File("src" + s + "br" + s + "com" + s + "grupo3");
		File relatorioTotalBanco= new File(caminhoRelatorioTotalBanco.getAbsolutePath()+s+"relatorioRepositorio.csv");
		
		  if(!caminhoRelatorioTotalBanco.exists()) {
	          caminhoRelatorioTotalBanco.mkdirs();
	      }

	      if(!relatorioTotalBanco.exists()) {
	          relatorioTotalBanco.createNewFile();
	      }
	      FileWriter relatorioTotalBancoWriter = new FileWriter(relatorioTotalBanco,true);
	      BufferedWriter relatorioTotalBancoWriterBuff = new BufferedWriter(relatorioTotalBancoWriter); 
	    	  relatorioTotalBancoWriterBuff.append("Relatório de total de contas gerado por: "+this.nome+"em:"+instante);
	    	  relatorioTotalBancoWriterBuff.newLine();
	    	  relatorioTotalBancoWriterBuff.append("O valor total do cofre no banco é de:" +relatorioPresidente());
	    	  relatorioTotalBancoWriterBuff.newLine();
	    	  relatorioTotalBancoWriterBuff.close();
	    	  relatorioTotalBancoWriter.close();
	    	  
	}
	public double relatorioPresidente() {
		List<Conta> listaContas = ContasRepositorio.getContas();
		double totalBanco = 0;
		for (Conta conta : listaContas) {
			totalBanco += conta.getSaldo();

		}
		return totalBanco;
	}
}
