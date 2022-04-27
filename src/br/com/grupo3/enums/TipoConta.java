package br.com.grupo3.enums;

public enum TipoConta {
	CONTACORRENTE(1),
	CONTAPOUPANCA(2);

	private int codigoTipoConta;
	
	TipoConta(int i) {
		this.codigoTipoConta = i;
	}

	public int getCodigoTipoConta() {
		return codigoTipoConta;
	}
	public TipoConta getCodigoTipoConta(int i) {
		for (TipoConta tipocontaAtual : TipoConta.values()) {
			if (tipocontaAtual.getCodigoTipoConta()==i) {
				return tipocontaAtual;
			}
		}
		return TipoConta.CONTAPOUPANCA;
	}
}
