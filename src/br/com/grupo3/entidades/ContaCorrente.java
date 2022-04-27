package br.com.grupo3.entidades;

public class ContaCorrente extends Conta{
	
	public void relatorioTributacao() {
		double total = (super.valorSaque)+(super.valorDeposito)+(super.valorTransferencia);
		System.out.println("O total tributado de suas operações foi " + total + ".");
		System.out.println("O banco cobra um valor para cada tipo de operação. Para o saque, é cobrado R$0,10."
				+ " Para o depósito, é cobrado R$0,10. Para a transferencia, é cobrado R$0,20.");
	}

}
