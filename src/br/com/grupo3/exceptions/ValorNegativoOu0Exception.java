package br.com.grupo3.exceptions;

@SuppressWarnings("serial")
public class ValorNegativoOu0Exception extends Exception {

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Valor inserido é negativo ou igual a 0 num local onde não é permitido!";
	}
	

}
