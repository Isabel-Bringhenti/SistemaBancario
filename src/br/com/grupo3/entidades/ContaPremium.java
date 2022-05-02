package br.com.grupo3.entidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.NumeroInvalidoException;
import br.com.grupo3.exceptions.SaldoInsuficienteException;

public class ContaPremium extends Conta {
	boolean possuiSeguro=false;
	double valorSeguro;
	double gastoSeguroAgora;
	boolean ePremium=true;

	public ContaPremium(String nome, String cpf, double saldo, String codConta, int codAgencia, int tipoConta,boolean possuiSeguro,double valorSeguro)
			throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super(nome, cpf, saldo, codConta, codAgencia, tipoConta);
		this.possuiSeguro=possuiSeguro;
		this.valorSeguro=valorSeguro;
	}
	public  void relatorioTributacaoPremium() {
		LocalDateTime instante=LocalDateTime.now();
		String s = File.separator;
		File caminhoRegistroRepositorio=new File("src" + s + "br" + s + "com" + s + "grupo3");
		File registroRepositorio=new File(caminhoRegistroRepositorio.getAbsolutePath()+s+"registroRepositorio.csv");
		double totalTaxaSaque=0;
		double totalTaxaDeposito=0;
		double totalTaxaTransferencia=0;
		 try (FileReader registroRepositorioReader = new FileReader(registroRepositorio);
	             BufferedReader registroRepositorioReaderBuff = new BufferedReader(registroRepositorioReader)) {

	            String linhaAtual;
	            while (((linhaAtual = registroRepositorioReaderBuff.readLine()) != null)) {
	                String[] itensTemp = linhaAtual.split("¨¨");
	                if (itensTemp[2].equals(this.cpf) && itensTemp[1].equals("3")) {
	                    switch (itensTemp[0]) {
	                        case "saque":
	                            totalTaxaSaque += 0.10;
	                            break;
	                        case "deposito":
	                            totalTaxaDeposito +=0.10;
	                            break;
	                        case "transferencia":
	                            totalTaxaTransferencia += 0.20;
	                            break;
	                    }
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Erro lendo algo, dá uma checada");
	        }
		 System.out.println("Total gasto com taxas do saque: R$"+totalTaxaSaque);
		 System.out.println("Total gasto com taxas do deposito: R$"+totalTaxaDeposito);
		 System.out.println("Total gasto com taxas de transferência: R$"+totalTaxaTransferencia);
		 System.out.println("Valores das taxas:");
		 System.out.println("Saque: R$0.10"
							+ "Deposito: R$0.10"
		 					+ "Transferência: R$ 0.20");
	}
	public void relatorioRendimentoPremium(double valor, int dias) throws NumeroInvalidoException, IOException {
		if (valor <= 0 || dias <= 0|| dias>31) {
			throw new NumeroInvalidoException();
		}
		LocalDateTime instante=LocalDateTime.now();
		String s = File.separator;
		File caminhoRegistroSimulacao=new File("src" + s + "br" + s + "com" + s + "grupo3");
		File registroSimulacao=new File(caminhoRegistroSimulacao.getAbsolutePath()+s+"registroSimulacoes.csv");
		
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
			System.out.println("O rendimento nesse período de "+dias+"dias será de:"+valorRendimento);
		}
		}
	public  void contrataSeguro(double valor) throws SaldoInsuficienteException {
		if(valor>this.saldo) {
			throw new SaldoInsuficienteException();
		}
		this.saldo-=(valor*0.2);
		this.possuiSeguro=true;
		this.valorSeguro=valor;
		this.gastoSeguroAgora+=valor*0.2;
	}
	public boolean isPossuiSeguro() {
		return false;
	}
	public double getValorSeguro() {
		return valorSeguro;
	}
	

}
