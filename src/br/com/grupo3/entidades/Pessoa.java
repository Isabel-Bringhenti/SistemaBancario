package br.com.grupo3.entidades;

import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.SenhaInvalidaException;
import br.com.grupo3.validadores.ValidadorCpf;
import br.com.grupo3.validadores.ValidadorSenha;

public abstract class Pessoa implements Comparable<Pessoa> {
	protected String nome;
	protected String cpf;
	protected String senha;


	// Aqui poderia ser algo assim
	@Override
	public  int compareTo(Pessoa o) {
		return this.getNome().compareTo(o.getNome());
	}
	

	public Pessoa(String nome,String cpf, String senha) throws ConstrucaoInvalidaException {
		this.nome=nome;
		this.cpf = ValidadorCpf.validarCpf(cpf);
		this.senha = ValidadorSenha.validarSenha(senha);
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public String getSenha() {
		return senha;
	}

	public String getNome() {
		return nome;
	}
	
	
	
}
