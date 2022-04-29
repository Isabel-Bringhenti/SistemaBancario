package br.com.grupo3.enums;

import br.com.grupo3.exceptions.CodigoInvalidoException;

public enum Agencia {
	AGENCIA1(1), AGENCIA2(2), AGENCIA3(3);

	private int codigoAgencia;

	Agencia(int i) {
		this.codigoAgencia = i;
	}

	public int getCodigoAgencia() {
		return codigoAgencia;
	}

	public static Agencia getAgenciaPorCodigo(int i) throws CodigoInvalidoException {
		for (Agencia agenciaAtual : Agencia.values()) {
			if (agenciaAtual.getCodigoAgencia() == i) {
				return agenciaAtual;
			}
		}
		throw new CodigoInvalidoException();
	}
}
