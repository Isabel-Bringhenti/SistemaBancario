package br.com.grupo3.enums;

import br.com.grupo3.exceptions.CodigoInvalidoException;

public enum TipoConta {
	CONTACORRENTE(1), CONTAPOUPANCA(2), CONTAPREMIUM(3);

	private int codigoTipoConta;

	TipoConta(int i) {
		this.codigoTipoConta = i;
	}

	public int getCodigoTipoConta() {
		return codigoTipoConta;
	}

	public static TipoConta getTipoContaPorCodigo(int i) throws CodigoInvalidoException {
		for (TipoConta tipocontaAtual : TipoConta.values()) {
			if (tipocontaAtual.getCodigoTipoConta() == i) {
				return tipocontaAtual;
			}
		}
		throw new CodigoInvalidoException();
	}
}
