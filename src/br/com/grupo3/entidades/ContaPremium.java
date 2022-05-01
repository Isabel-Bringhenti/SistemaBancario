package br.com.grupo3.entidades;

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
		double total = (super.valorSaque) + (super.valorDeposito) + (super.valorTransferencia)+(this.gastoSeguroAgora);
		System.out.println("O total tributado de suas operações foi " + total + ".");
		System.out.println("O banco cobra um valor para cada tipo de operação. Para o saque, é cobrado R$0,10."
				+ " Para o depósito, é cobrado R$0,10. Para a transferencia, é cobrado R$0,20.");
		if(this.possuiSeguro) {
			System.out.println("O valor em seguro contratado é de: R$"+this.valorSeguro);
		}
	}
	public void relatorioRendimentoPremium(double valor, int dias) throws NumeroInvalidoException {
		if (valor <= 0 || dias <= 0) {
			throw new NumeroInvalidoException();
		}
		double valorRendimento = (valor * 0.00016) * dias;
		System.out.println("O rendimento nesse período de " + dias + " dias seria: R$" + valorRendimento);

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
