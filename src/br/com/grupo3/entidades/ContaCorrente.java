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
import br.com.grupo3.exceptions.SaldoInsuficienteException;

public class ContaCorrente extends Conta {
	private boolean possuiSeguro=false;
	private double valorSeguro;
	private double gastoSeguroAgora;
	

	public ContaCorrente(String nome,String cpf, double saldo, String codConta, int codAgencia, int tipoConta,boolean possuiSeguro,double valorSeguro ) throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super(nome,cpf, saldo, codConta, codAgencia, tipoConta);
		this.possuiSeguro=possuiSeguro;
		this.valorSeguro=valorSeguro;
	}

	public  void relatorioTributacao() {
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
		return possuiSeguro;
	}

	public double getValorSeguro() {
		return valorSeguro;
	}
	

}
