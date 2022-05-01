package br.com.grupo3.dominio;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import br.com.grupo3.entidades.Cliente;
import br.com.grupo3.entidades.Conta;
import br.com.grupo3.entidades.ContaCorrente;
import br.com.grupo3.entidades.ContaPoupanca;
import br.com.grupo3.entidades.ContaPremium;
import br.com.grupo3.entidades.Diretor;
import br.com.grupo3.entidades.Gerente;
import br.com.grupo3.entidades.Pessoa;
import br.com.grupo3.entidades.Presidente;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.ContaNaoEncontradaException;
import br.com.grupo3.exceptions.NumeroInvalidoException;
import br.com.grupo3.exceptions.OpcaoInvalidaException;
import br.com.grupo3.exceptions.SaldoInsuficienteException;
import br.com.grupo3.exceptions.SenhaInvalidaException;
import br.com.grupo3.exceptions.ValorExistenteException;
import br.com.grupo3.exceptions.ValorInexistenteException;
import br.com.grupo3.exceptions.ValorNegativoOu0Exception;
import br.com.grupo3.repositorios.ContasRepositorio;
import br.com.grupo3.repositorios.PessoaRepositorio;
import br.com.grupo3.validadores.ValidadorCpf;
import br.com.grupo3.validadores.ValidadorSenha;

public class SistemaBancarioMain {

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			ContasRepositorio.contaRepositorioLoader();
			PessoaRepositorio.pessoaRepositorioLoader();
		
			Pessoa pessoaAtual = login();
			Conta contaAtual = loginConta(pessoaAtual);
			System.out.println(contaAtual);
			while (true) {
				menuInicial();
				int opcaoEscolhida = sc.nextInt();
				switch (opcaoEscolhida) {
				case 0:
					System.out.println("Saindo do programa!");
					System.exit(0);
					break;
				case 1:
					handleSaque(contaAtual);
					break;
				case 2:
					handleDeposito(contaAtual);
					break;
				case 3:
					handleTransferencia(contaAtual);
					break;
				case 4:
					menuRelatorio(pessoaAtual, contaAtual);
					int opcao = sc.nextInt();
					if (opcao <= 0 || opcao > 5) {
						throw new OpcaoInvalidaException();
					} else

						switch (opcao) {
						case 0:
							System.out.println("Saindo do programa!");
							System.exit(0);
							break;
						case 1:
							mostrarSaldo(contaAtual);
							break;
						case 2:
							if (contaAtual.getTipoConta().getCodigoTipoConta()==1 ) {
								((ContaCorrente) contaAtual).relatorioTributacao();
							} else if (contaAtual.getTipoConta().getCodigoTipoConta()==2) {
								System.out.println("Qual o valor que deseja simular?");
								double valor = sc.nextDouble();
								System.out.println("Qual a quantidade de dias que pretende simular?");
								int dias = sc.nextInt();
								((ContaPoupanca) contaAtual).relatorioRendimento(valor, dias);
							} else if (contaAtual.getTipoConta().getCodigoTipoConta()==3) {
								((ContaPremium) contaAtual).relatorioTributacaoPremium();
							}
							break;
						case 3:
							if (contaAtual instanceof ContaPoupanca) {
								throw new OpcaoInvalidaException();
							} if (contaAtual instanceof ContaCorrente) {
								System.out.println(contaAtual);
								handleContratarSeguroCorrente(((ContaCorrente) contaAtual));
							} if (contaAtual instanceof ContaPremium) {
								System.out.println("Qual o valor que deseja simular?");
								double valor = sc.nextDouble();
								System.out.println("Qual a quantidade de dias que pretende simular?");
								int dias = sc.nextInt();
								((ContaPremium) contaAtual).relatorioRendimentoPremium(valor, dias);
							}else {
								break;
							}
							break;
						case 4:
							if (pessoaAtual instanceof Cliente) {
								if(contaAtual instanceof ContaPremium) {
									handleContratarSeguroPremium(((ContaPremium)contaAtual));
											
								}
							} else if (pessoaAtual instanceof Gerente) {
								System.out.print(
										ContasRepositorio.getContasPorIdAgencia(((Gerente) pessoaAtual).getCodAgencia())
												+ "\n");
							} else {
								System.out.print(ContasRepositorio.getContas() + "\n");
							}
							break;
						case 5:
							if (pessoaAtual instanceof Presidente) {
								System.out.println("O valor total no cofre do banco é de:"
										+relatorioPresidente());
							} else if (pessoaAtual instanceof Gerente || pessoaAtual instanceof Diretor) {
								handleContratarSeguroPremium(((ContaPremium) contaAtual));
							} else
								throw new OpcaoInvalidaException();
							break;
						case 6:
							if (pessoaAtual instanceof Presidente) {
								handleContratarSeguroPremium(((ContaPremium) contaAtual));
							} else
								throw new OpcaoInvalidaException();
							break;

						}
				default:
					break;
				}
			}

		} catch (IOException e) {

		} catch (ValorInexistenteException | ConstrucaoInvalidaException | SenhaInvalidaException
				| SaldoInsuficienteException | NumeroInvalidoException | ContaNaoEncontradaException
				| OpcaoInvalidaException | CodigoInvalidoException | ValorNegativoOu0Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Fechando o programa");
		}

	}

	public static void menuInicial() {
		System.out.println("Bem vindo ao menu!\n" + "1-Saque\n" + "2-Depósito\n" + "3-Transferência\n"
				+ "4-Relatórios\n" + "0-Sair");
	}

	public static void menuRelatorio(Pessoa pessoa, Conta conta) {
		if (pessoa instanceof Cliente) {
			if (conta instanceof ContaCorrente) {
				System.out.println(
						"Relatórios disponíveis\n" + "1-Mostrar saldo\n" + "2-Relatório tributação\n" + "3-Seguro\n"+"0-Sair");
			} else if (conta instanceof ContaPoupanca) {
				System.out.println("Relatórios disponíveis\n" + "1-Mostrar saldo\n" + "2-Relatório rendimento\n"+"0-Sair");
			} else if(conta instanceof ContaPremium) {
				System.out.println(
						"Relatórios disponíveis\n" + "1-Mostrar saldo\n" + "2-Relatório tributação\n" + "3-Relatório rendimento\n"+"4-Seguro\n"+"0-Sair");
				
			}
		}
		if (pessoa instanceof Diretor) {
			System.out.println("Relatórios disponíveis\n" + "1-Mostrar saldo\n" + "2-Relatório tributação\n"
					+ "3-Relatório rendimento\n" + "4-Relatório com todas as contas\n" + "5-Contratar Seguro\n"+"0-Sair");
		}
		if (pessoa instanceof Gerente) {
			System.out.println("Relatórios disponíveis\n" + "1-Mostrar saldo\n" + "2-Relatório tributação\n"
					+ "3-Relatório rendimento\n" + "4-Relatório com todas as contas de sua agência\n"
					+ "5-Contratar Seguro\n"+"0-Sair");
		}
		if (pessoa instanceof Presidente) {
			System.out.println("Relatórios disponíveis\n" + "1-Mostrar saldo\n" + "2-Relatório tributação\n"
					+ "3-Relatório rendimento\n" + "4-Relatório com todas as contas\n"
					+ "5-Contratar Seguro\n"+"6- Relatório dinheiro armazenado\n"+"0-Sair");
		}
	}

	public static Pessoa login()
			throws IOException, ValorInexistenteException, SenhaInvalidaException, ConstrucaoInvalidaException {
		Scanner sc = new Scanner(System.in);
		PessoaRepositorio.pessoaRepositorioLoader();

		System.out.println("Olá, bem vindo ao sistema do banco! Digite seu cpf para logar");
		String cpf = sc.nextLine();

		ValidadorCpf.validarCpf(cpf);

		Pessoa pessoaAtual = PessoaRepositorio.getPessoaPorCPF(cpf);

		System.out.println("Okay, agora sua senha");
		String senhaTentativa = sc.nextLine();

		ValidadorSenha.validarSenha(senhaTentativa);

		if (!senhaTentativa.equals(pessoaAtual.getSenha())) {
			throw new SenhaInvalidaException();

		}
		System.out.println("Bem vindo ao sistema!");
		return pessoaAtual;

	}

	public static void handleSaque(Conta conta) throws NumeroInvalidoException, SaldoInsuficienteException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Olá! quanto deseja sacar?");
		double valor = sc.nextDouble();

		conta.sacar(valor);

		System.out.println("Saque realizado com sucesso! O valor foi debitado de sua conta");

	}

	public static void handleDeposito(Conta conta) throws NumeroInvalidoException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Olá! Bem vindo(a) ao sistema de depósito." + "Quanto deseja depositar?");
		double valor = sc.nextDouble();
		conta.depositar(valor);
		System.out.println("Depósito realizado com sucesso");

	}

	public static void handleTransferencia(Conta conta)
			throws NumeroInvalidoException, IOException, ValorInexistenteException, SaldoInsuficienteException {
		ContasRepositorio.contaRepositorioLoader();
		Scanner sc = new Scanner(System.in);
		System.out.println("Olá! bem vindo ao menu de transferência, quanto deseja transferir");
		double valor = sc.nextDouble();
		sc.nextLine();
		System.out.println("Okay, agora o cpf da pessoa para quem você deseja transferir esse valor");
		String cpfDestinatario = sc.nextLine();
		sc.nextLine();
		conta.transferir(valor, cpfDestinatario);
		System.out.println("Transferência realizada com sucesso!");
	}

	public static void mostrarSaldo(Conta conta) {
		System.out.println("Olá! Seu saldo é de:" + conta.getSaldo());
	}

	public static void handleContratarSeguroCorrente(ContaCorrente conta)
			throws SaldoInsuficienteException, ValorNegativoOu0Exception, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Olá! bem vindo ao menu de contratação de seguro");
		if (conta.isPossuiSeguro()) {
			System.out.println("Você já possui seguro contratado");
			System.out.println("O valor atual é de:" + conta.getValorSeguro() + ", se deseja mudar o valor,"
					+ "digite o novo valor,caso contrário, digite 0");
			double valor = sc.nextDouble();
			if (valor == 0) {
				System.exit(0);
			} else if (valor > conta.getSaldo()) {
				throw new SaldoInsuficienteException();
			} else {
				conta.contrataSeguro(valor);
				System.out.println("Seguro contratado com sucesso!");
			}
		} else {
			System.out.println("Vimos que você não tem seguro, qual valor deseja adicionar?");
			double valor = sc.nextDouble();
			if (valor <= 0) {
				throw new ValorNegativoOu0Exception();
			} else if (valor > conta.getSaldo()) {
				throw new SaldoInsuficienteException();
			} else {
				conta.contrataSeguro(valor);
				System.out.println("Seguro contratado com sucesso!");
			}

		}

	}

	public static void handleContratarSeguroPremium(ContaPremium conta)
			throws SaldoInsuficienteException, ValorNegativoOu0Exception, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Olá! bem vindo ao menu de contratação de seguro");
		if (conta.isPossuiSeguro()) {
			System.out.println("Você já possui seguro contratado");
			System.out.println("O valor atual é de:" + conta.getValorSeguro() + ", se deseja mudar o valor,"
					+ "digite o novo valor,caso contrário, digite 0");
			double valor = sc.nextDouble();
			if (valor == 0) {
				System.exit(0);
			} else if (valor > conta.getSaldo()) {
				throw new SaldoInsuficienteException();
			} else {
				conta.contrataSeguro(valor);
				System.out.println("Seguro contratado com sucesso!");
			}
		} else {
			System.out.println("Vimos que você não tem seguro, qual valor deseja adicionar?");
			double valor = sc.nextDouble();
			if (valor <= 0) {
				throw new ValorNegativoOu0Exception();
			} else if (valor > conta.getSaldo()) {
				throw new SaldoInsuficienteException();
			} else {
				conta.contrataSeguro(valor);
				System.out.println("Seguro contratado com sucesso!");
			}

		}

	}

	public static Conta loginConta(Pessoa pessoa) throws IOException, ContaNaoEncontradaException {
		ContasRepositorio.contaRepositorioLoader();
		List<Conta> listaContas = ContasRepositorio.getContas();
		for (Conta conta : listaContas) {
			if (conta.getCpf().equals(pessoa.getCpf())) {
				return conta;
			}
		}
		throw new ContaNaoEncontradaException();

	}
	public static double relatorioPresidente() {
		List<Conta> listaContas= ContasRepositorio.getContas();
		double totalBanco=0;
		for (Conta conta : listaContas) {
			totalBanco+=conta.getSaldo();
			
		}
		return totalBanco;
	}

}
