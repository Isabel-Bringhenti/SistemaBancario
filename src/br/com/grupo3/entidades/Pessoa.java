package br.com.grupo3.entidades;

public abstract class Pessoa {
	protected String cpf;
	protected String senha;
	public Pessoa(String cpf, String senha) {
		super();
		this.cpf = cpf;
		this.senha = senha;
	}
	public String getCpf() {
		return cpf;
	}
	
}
