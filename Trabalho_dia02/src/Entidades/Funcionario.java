package Entidades;

public abstract class Funcionario extends Pessoa {
	
	private Cargo cargo;
	
	public Funcionario(String cpf, String senha, Cargo cargo) {
		super(cpf, senha);
		this.cargo=cargo;
	}

}
