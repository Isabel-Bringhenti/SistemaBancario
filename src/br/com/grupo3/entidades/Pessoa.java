package br.com.grupo3.entidades;

import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.SenhaInvalidaException;
import br.com.grupo3.validadores.ValidadorCpf;
import br.com.grupo3.validadores.ValidadorSenha;

public abstract class Pessoa implements Comparable<Pessoa> {
	protected String nome;
	protected String cpf;
	protected String senha;
	
	@Override
	public  int compareTo(Pessoa o) {
		if(this.getNome().compareTo(o.getNome())<0) {
			return 0;
		}
		if(this.getNome().compareTo(o.getNome())>0) {
			return 1;	
		}
		return -1;
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
