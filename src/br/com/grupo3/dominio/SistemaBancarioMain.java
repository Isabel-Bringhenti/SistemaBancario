package br.com.grupo3.dominio;

import java.io.IOException;
import java.util.Scanner;

import br.com.grupo3.entidades.Conta;
import br.com.grupo3.entidades.ContaCorrente;
import br.com.grupo3.entidades.ContaPoupanca;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.NumeroInvalidoException;
import br.com.grupo3.exceptions.SaldoInsuficienteException;
import br.com.grupo3.exceptions.ValorExistenteException;
import br.com.grupo3.exceptions.ValorInexistenteException;
import br.com.grupo3.repositorios.ContasRepositorio;


public class SistemaBancarioMain {

	public static void main(String[] args) {
		try {
			ContasRepositorio.contaRepositorioLoader();
			
			
			
		
			
			Conta conta1=ContasRepositorio.getContaPorCPF("11122233344");
			conta1.transferir(1000, "11122233355");
			System.out.println(conta1.getSaldo());
			
			
		} catch (IOException e) {
			System.out.println("Deu ruim");
		} catch (ValorInexistenteException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (NumeroInvalidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SaldoInsuficienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
