package br.com.grupo3.enums;

import br.com.grupo3.exceptions.CodigoInvalidoException;

public enum Cargo {
	DIRETOR(1), GERENTE(2), PRESIDENTE(3);

	private int codigoCargo;

	Cargo(int i) {
		this.codigoCargo = i;
	}

	public int getCodigoCargo() {
		return codigoCargo;
	}

	public Cargo getCargoPorCodigo(int i) throws CodigoInvalidoException {
		for (Cargo cargoAtual : Cargo.values()) {
			if (cargoAtual.getCodigoCargo() == i) {
				return cargoAtual;
			}
		}
		throw new CodigoInvalidoException();
	}
}
