package br.com.grupo3.entidades;

import br.com.grupo3.enums.Agencia;
import br.com.grupo3.enums.TipoConta;
import br.com.grupo3.exceptions.NumeroInvalidoException;

public class ContaPoupanca extends Conta {

	public ContaPoupanca(String cpf, double saldo, String codConta, Agencia codAgencia, TipoConta tipoConta) {
		super(cpf, saldo, codConta, codAgencia, tipoConta);
	}

	public void relatorioRendimento(double valor, int dias) throws NumeroInvalidoException {
		if (valor <= 0 || dias <= 0) {
			throw new NumeroInvalidoException();
		}
		double valorRendimento = (valor * 0.00016) * dias;
		System.out.println("O rendimento nesse período de " + dias + " dias seria: R$" + valorRendimento);

	}

}
