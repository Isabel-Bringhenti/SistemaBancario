package br.com.grupo3.entidades;

import br.com.grupo3.enums.Agencia;
import br.com.grupo3.enums.TipoConta;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.SaldoInsuficienteException;

public class ContaCorrente extends Conta {
	private boolean possuiSeguro=false;
	private double valorSeguro;

	public ContaCorrente(String cpf, double saldo, String codConta, Agencia codAgencia, TipoConta tipoConta) throws ConstrucaoInvalidaException {
		super(cpf, saldo, codConta, codAgencia, tipoConta);
	}

	public void relatorioTributacao() {
		double total = (super.valorSaque) + (super.valorDeposito) + (super.valorTransferencia);
		System.out.println("O total tributado de suas operações foi " + total + ".");
		System.out.println("O banco cobra um valor para cada tipo de operação. Para o saque, é cobrado R$0,10."
				+ " Para o depósito, é cobrado R$0,10. Para a transferencia, é cobrado R$0,20.");
		if(this.possuiSeguro) {
			System.out.println("O valor em seguro contratado é de: R$"+this.valorSeguro);
		}
	}
	public  void contrataSeguro(double valor) throws SaldoInsuficienteException {
		if(valor>this.saldo) {
			throw new SaldoInsuficienteException();
		}
		this.saldo-=(valor*0.2);
		this.possuiSeguro=true;
		this.valorSeguro=valor;
	}

	public boolean isPossuiSeguro() {
		return possuiSeguro;
	}

	public double getValorSeguro() {
		return valorSeguro;
	}
	

}
