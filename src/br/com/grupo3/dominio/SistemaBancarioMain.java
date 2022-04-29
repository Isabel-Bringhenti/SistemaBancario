package br.com.grupo3.dominio;

import java.util.Scanner;

import br.com.grupo3.entidades.Conta;
import br.com.grupo3.entidades.ContaCorrente;
import br.com.grupo3.entidades.ContaPoupanca;
import br.com.grupo3.enums.Agencia;
import br.com.grupo3.enums.TipoConta;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.NumeroInvalidoException;
import br.com.grupo3.exceptions.SaldoInsuficienteException;

public class SistemaBancarioMain {

	public static void main(String[] args) {

		try {
			ContaPoupanca c1 = new ContaPoupanca("11122233344", 3000, "1", Agencia.getAgenciaPorCodigo(1),
					TipoConta.getTipoContaPorCodigo(2));
			ContaCorrente c2 = new ContaCorrente("22233344455", 4000, "2", Agencia.getAgenciaPorCodigo(2),
					TipoConta.getTipoContaPorCodigo(1));
			System.out.println(c1);

			handleSaque(c1);
			System.out.println(c1.getSaldo());
			c2.contrataSeguro(3000);
			System.out.println(c2.isPossuiSeguro());

			handleDeposito(c2);
			

			handleDeposito(c1);

		} catch (SaldoInsuficienteException | NumeroInvalidoException | ConstrucaoInvalidaException
				| CodigoInvalidoException e) {

			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Fechando o programa");
		}

	}

	public static void handleSaque(Conta conta) throws NumeroInvalidoException, SaldoInsuficienteException {

		Scanner sc = new Scanner(System.in);
		System.out.println("Olá! quanto deseja sacar?");
		double valor = Double.parseDouble(sc.nextLine());
		conta.sacar(valor);
		System.out.println("Saque realizado com sucesso! O valor foi debitado de sua conta");
		
		
	}

	public static void handleDeposito(Conta conta) throws NumeroInvalidoException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Olá! Bem vindo(a) ao sistema de depósito." + "Quanto deseja depositar?");
		double valor = Double.parseDouble(sc.nextLine());
		conta.depositar(valor);
		System.out.println("Depósito realizado com sucesso");
		
		
	}

}
