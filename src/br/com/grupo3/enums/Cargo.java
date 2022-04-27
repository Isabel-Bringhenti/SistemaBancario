package br.com.grupo3.enums;

public enum Cargo {
	DIRETOR(1),
	GERENTE(2),
	PRESIDENTE(3);
	
	private int codigoCargo;

	 Cargo(int i) {
		this.codigoCargo = i;
	}

	public int getCodigoCargo() {
		return codigoCargo;
	}
	public Cargo getCargoPorCodigo(int i) {
		for (Cargo cargoAtual : Cargo.values()) {
			if (cargoAtual.getCodigoCargo()==i) {
				return cargoAtual;
			}
		}
		return Cargo.DIRETOR;
	}
}



