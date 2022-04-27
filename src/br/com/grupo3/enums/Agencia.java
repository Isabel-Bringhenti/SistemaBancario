package br.com.grupo3.enums;

public enum Agencia {
	AGENCIA1(1),
	AGENCIA2(2),
	AGENCIA3(3);
	
	private int codigoAgencia;

	 Agencia(int i) {
		this.codigoAgencia = i;
	}

	public int getCodigoAgencia() {
		return codigoAgencia;
	}
	public Agencia getAgenciaPorCodigo(int i) {
		for (Agencia agenciaAtual : Agencia.values()) {
			if (agenciaAtual.getCodigoAgencia()==i) {
				return agenciaAtual;
			}
		}
		return Agencia.AGENCIA2;
	}
}
