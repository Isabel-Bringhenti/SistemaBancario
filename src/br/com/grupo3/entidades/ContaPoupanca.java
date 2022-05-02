package br.com.grupo3.entidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.NumeroInvalidoException;

public class ContaPoupanca extends Conta {

	public ContaPoupanca(String nome,String cpf, double saldo, String codConta, int codAgencia, int tipoConta) throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super(nome,cpf, saldo, codConta, codAgencia, tipoConta);
	}

	public void relatorioRendimento(double valor, int dias) throws NumeroInvalidoException, IOException {
		if (valor <= 0 || dias <= 0|| dias>31) {
			throw new NumeroInvalidoException();
		}
		LocalDateTime instante=LocalDateTime.now();
		String s = File.separator;
		File caminhoRegistroSimulacao=new File("src" + s + "br" + s + "com" + s + "grupo3");
		File registroSimulacao=new File(caminhoRegistroSimulacao.getAbsolutePath()+s+"registroSimulacoes.txt");
		
		if(!caminhoRegistroSimulacao.exists()) {
			caminhoRegistroSimulacao.mkdirs();
		}
		if(!registroSimulacao.exists()) {
			registroSimulacao.createNewFile();
		}
		try(FileWriter registroSimulacaoContaWriter = new FileWriter(registroSimulacao);
	             BufferedWriter registroSimulacaoContaWriterBuff = new BufferedWriter(registroSimulacaoContaWriter)){
			registroSimulacaoContaWriterBuff.append("Relatório de simulação de poupança de "+this.nome+" gerado em:"+instante);
			registroSimulacaoContaWriterBuff.newLine();
			double valorRendimento = (valor * 0.00016) * dias;
			registroSimulacaoContaWriterBuff.append("O rendimento nesse período de " + dias + " dias seria: R$" + valorRendimento);
		}
		

	}
	public void relatorioRendimentoConsole(double valor, int dias) {
		double valorRendimento=(valor*0.00016)*dias;
		System.out.println("O rendimento nesse período de "+dias+"dias será de:"+valorRendimento);
		
	}

}
