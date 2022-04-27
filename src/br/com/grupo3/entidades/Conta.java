package br.com.grupo3.entidades;

import br.com.grupo3.enums.Agencia;
import br.com.grupo3.enums.TipoConta;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.NumeroInvalidoException;
import br.com.grupo3.validadores.ValidadorCpf;

public abstract class Conta {

	protected String cpf;
	protected double saldo;
	protected String codConta;
	protected Agencia codAgencia;
	protected TipoConta tipoConta;
	protected int valorSaque;
	protected int valorDeposito;
	protected int valorTransferencia;

	public Conta(String cpf, double saldo, String codConta, Agencia codAgencia, TipoConta tipoConta) throws ConstrucaoInvalidaException {
		super();
		this.cpf = ValidadorCpf.validarCpf(cpf);
		this.saldo = saldo;
		this.codConta = codConta;
		this.codAgencia = codAgencia;
		this.tipoConta = tipoConta;
	}

	public void sacar(double valorInserido) throws NumeroInvalidoException {
		if (valorInserido <= 0) {
			throw new NumeroInvalidoException();
		}
		this.saldo -= valorInserido;
		this.saldo -= 0.10;
		valorSaque += 0.10;
	}

	public void depositar(double valorInserido) throws NumeroInvalidoException {
		if (valorInserido <= 0) {
			throw new NumeroInvalidoException();
		}
		this.saldo += valorInserido;
		this.saldo -= 0.10;
		valorDeposito += 0.10;
	}

	public String getCpf() {
		return cpf;
	}

	public double getSaldo() {
		return saldo;
	}

	public String getCodConta() {
		return codConta;
	}

	public Agencia getCodAgencia() {
		return codAgencia;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	// public void transferir(double valorInserido,String cpfDestinatario, TipoConta
	// tipoConta ) throws NumeroInvalidoExeption {
	// TODO PRECISA DE ARQUIVO, NUM DA DOIDAO.
	// }

}
