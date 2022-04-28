package br.com.grupo3.entidades;

import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.validadores.ValidadorCpf;
import br.com.grupo3.validadores.ValidadorSenha;

public abstract class Pessoa {
	protected String cpf;
	protected String senha;
	public Pessoa(String cpf, String senha) throws ConstrucaoInvalidaException {
		super();
		this.cpf = ValidadorCpf.validarCpf(cpf);
		this.senha = ValidadorSenha.validarSenha(senha);
	}
	public String getCpf() {
		return cpf;
	}
	
}
