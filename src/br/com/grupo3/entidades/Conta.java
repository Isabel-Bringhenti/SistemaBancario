package br.com.grupo3.entidades;

import br.com.grupo3.exceptions.NumeroInvalidoException;

public abstract class Conta {
	
	protected String cpf;
	protected double saldo;
	protected String codConta; 
	//protected Agencia codAgencia;
	//protected TipoConta tipoConta;
	protected int valorSaque;
	protected int valorDeposito;
	protected int valorTransferencia;
	//TODO: add enums aqui
	
	//TODO: add construtor aqui
	
	public void sacar(double valorInserido) throws NumeroInvalidoException {
		if (valorInserido<=0) {
			throw new NumeroInvalidoException();
		}
		this.saldo-=valorInserido;
		this.saldo-=0.10;
		valorSaque+=0.10;
	}
	
	public void depositar(double valorInserido) throws NumeroInvalidoException {
		if (valorInserido<=0) {
			throw new NumeroInvalidoException();
		}
		this.saldo+=valorInserido;
		this.saldo-=0.10;
		valorDeposito+=0.10;
	}
	
	//public void transferir(double valorInserido,String cpfDestinatario, TipoConta tipoConta ) throws NumeroInvalidoExeption {
	//TODO:metodo transferir	
	//}

}
