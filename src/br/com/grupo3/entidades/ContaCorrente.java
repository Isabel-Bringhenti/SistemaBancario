package br.com.grupo3.entidades;

import br.com.grupo3.enums.Agencia;
import br.com.grupo3.enums.TipoConta;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;

public class ContaCorrente extends Conta {

	public ContaCorrente(String cpf, double saldo, String codConta, Agencia codAgencia, TipoConta tipoConta) throws ConstrucaoInvalidaException {
		super(cpf, saldo, codConta, codAgencia, tipoConta);
	}

	public void relatorioTributacao() {
		double total = (super.valorSaque) + (super.valorDeposito) + (super.valorTransferencia);
		System.out.println("O total tributado de suas operações foi " + total + ".");
		System.out.println("O banco cobra um valor para cada tipo de operação. Para o saque, é cobrado R$0,10."
				+ " Para o depósito, é cobrado R$0,10. Para a transferencia, é cobrado R$0,20.");
	}

}
