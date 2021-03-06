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
import br.com.grupo3.entidades.Funcionario;
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
					if (opcao < 0 || opcao > 6) {
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
							if (contaAtual.getTipoConta().getCodigoTipoConta() == 1) {
								((ContaCorrente) contaAtual).relatorioTributacao();
								System.out.println("Relat??rio gerado com sucesso!");
							} else if (contaAtual.getTipoConta().getCodigoTipoConta() == 2) {
								System.out.println("Qual o valor que deseja simular?");
								double valor = sc.nextDouble();
								System.out.println("Qual a quantidade de dias que pretende simular?");
								int dias = sc.nextInt();
								((ContaPoupanca) contaAtual).relatorioRendimentoConsole(valor, dias);
								System.out.println("Simula????o gerada com sucesso!");
							} else if (contaAtual.getTipoConta().getCodigoTipoConta() == 3) {
								((ContaPremium) contaAtual).relatorioTributacaoPremium();
								
								
							}
							break;
						case 3:
							if (contaAtual instanceof ContaPoupanca) {
								throw new OpcaoInvalidaException();
							}
							if (contaAtual instanceof ContaCorrente) {
								System.out.println(contaAtual);
								handleContratarSeguroCorrente(((ContaCorrente) contaAtual));
							}
							if (contaAtual instanceof ContaPremium) {
								System.out.println("Qual o valor que deseja simular?");
								double valor = sc.nextDouble();
								System.out.println("Qual a quantidade de dias que pretende simular?");
								int dias = sc.nextInt();
								((ContaPremium) contaAtual).relatorioRendimentoPremium(valor, dias);
								System.out.println("Simula????o gerada com sucesso!");
							} else {
								break;
							}
							break;
						case 4:
							if (pessoaAtual instanceof Cliente) {
								if (contaAtual instanceof ContaPremium) {
									handleContratarSeguroPremium(((ContaPremium) contaAtual));

								}
							} else if (pessoaAtual instanceof Gerente) {
								System.out.print(
										((Gerente)pessoaAtual).getContasPorIdAgencia(((ContaPremium)contaAtual).getCodAgencia())
												+ "\n");
										
										System.out.println("Relat??rio gerado com sucesso!");
										
							} else if (pessoaAtual instanceof Diretor) {
								System.out.print(ContasRepositorio.getClientesOrdem()+"\n");
								
								
							} else if(pessoaAtual instanceof Presidente) {
								((Presidente)pessoaAtual).gerarRelatorioDeQtdContas(pessoaAtual);
								System.out.print(ContasRepositorio.getContas());
								System.out.println("Relat??rio gerado com sucesso!");
							}
							break;
						case 5:
							if (pessoaAtual instanceof Presidente) {
								handleContratarSeguroPremium((ContaPremium)contaAtual);
							} else if (pessoaAtual instanceof Gerente || pessoaAtual instanceof Diretor) {
								handleContratarSeguroPremium(((ContaPremium) contaAtual));
							} else {
								throw new OpcaoInvalidaException();
							}
							break;
						case 6:
							if (pessoaAtual instanceof Presidente) {
								System.out.print("O total no banco ?? de:"+((Presidente)pessoaAtual).relatorioPresidente());
								System.out.println("Relat??rio gerado com sucesso!");

							} else {
								throw new OpcaoInvalidaException();
							}
							break;

						default:
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
		System.out.println("Bem vindo ao menu!\n" + "1-Saque\n" + "2-Dep??sito\n" + "3-Transfer??ncia\n"
				+ "4-Relat??rios\n" + "0-Sair");
	}

	public static void menuRelatorio(Pessoa pessoa, Conta conta) {
		if (pessoa instanceof Cliente) {
			if (conta instanceof ContaCorrente) {
				System.out.println("Relat??rios dispon??veis\n" + "1-Mostrar saldo\n" + "2-Relat??rio tributa????o\n"
						+ "3-Seguro\n" + "0-Sair");
			} else if (conta instanceof ContaPoupanca) {
				System.out.println(
						"Relat??rios dispon??veis\n" + "1-Mostrar saldo\n" + "2-Relat??rio rendimento\n" + "0-Sair");
			} else if (conta instanceof ContaPremium) {
				System.out.println("Relat??rios dispon??veis\n" + "1-Mostrar saldo\n" + "2-Relat??rio tributa????o\n"
						+ "3-Relat??rio rendimento\n" + "4-Seguro\n" + "0-Sair");

			}
		}
		if (pessoa instanceof Diretor) {
			System.out.println("Relat??rios dispon??veis\n" + "1-Mostrar saldo\n" + "2-Relat??rio tributa????o\n"
					+ "3-Relat??rio rendimento\n" + "4-Relat??rio com todas as contas\n" + "5-Contratar Seguro\n"
					+ "0-Sair");
		}
		if (pessoa instanceof Gerente) {
			System.out.println("Relat??rios dispon??veis\n" + "1-Mostrar saldo\n" + "2-Relat??rio tributa????o\n"
					+ "3-Relat??rio rendimento\n" + "4-Relat??rio com todas as contas de sua ag??ncia\n"
					+ "5-Contratar Seguro\n" + "0-Sair");
		}
		if (pessoa instanceof Presidente) {
			System.out.println("Relat??rios dispon??veis\n" + "1-Mostrar saldo\n" + "2-Relat??rio tributa????o\n"
					+ "3-Relat??rio rendimento\n" + "4-Relat??rio com todas as contas\n" + "5-Contratar Seguro\n"
					+ "6- Relat??rio dinheiro armazenado\n" + "0-Sair");
		}
	}

	public static Pessoa login()
			throws IOException, ValorInexistenteException, SenhaInvalidaException, ConstrucaoInvalidaException {
		Scanner sc = new Scanner(System.in);
		PessoaRepositorio.pessoaRepositorioLoader();

		System.out.println("Ol??, bem vindo ao sistema do banco! Digite seu cpf para logar");
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

	public static void handleSaque(Conta conta) throws NumeroInvalidoException, SaldoInsuficienteException, IOException, ValorInexistenteException, ContaNaoEncontradaException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ol??! quanto deseja sacar?");
		double valor = sc.nextDouble();

		conta.sacar(valor);
		conta.registraTransacao("saque",conta.getTipoConta().getCodigoTipoConta(), conta.getCpf(), valor);
		conta.atualizaSaldo(conta.getCpf());

		System.out.println("Saque realizado com sucesso! O valor foi debitado de sua conta");

	}

	public static void handleDeposito(Conta conta) throws NumeroInvalidoException, IOException, ValorInexistenteException, ContaNaoEncontradaException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ol??! Bem vindo(a) ao sistema de dep??sito." + "Quanto deseja depositar?");
		double valor = sc.nextDouble();
		conta.depositar(valor);
		conta.registraTransacao("deposito",conta.getTipoConta().getCodigoTipoConta(), conta.getCpf(), valor);
		conta.atualizaSaldo(conta.getCpf());
		
		System.out.println("Dep??sito realizado com sucesso");

	}

	public static void handleTransferencia(Conta conta) throws NumeroInvalidoException, IOException,
			ValorInexistenteException, SaldoInsuficienteException, ContaNaoEncontradaException {
		ContasRepositorio.contaRepositorioLoader();
		Scanner sc = new Scanner(System.in);
		System.out.println("Ol??! bem vindo ao menu de transfer??ncia, quanto deseja transferir");
		double valor = sc.nextDouble();
		sc.nextLine();
		System.out.println("Okay, agora o cpf da pessoa para quem voc?? deseja transferir esse valor");
		String cpfDestinatario = sc.nextLine();
		conta.transferir(valor, cpfDestinatario);
		conta.registraTransacao("transferencia",conta.getTipoConta().getCodigoTipoConta(), cpfDestinatario, valor);
		conta.atualizaSaldo(conta.getCpf());
		ContasRepositorio.getContaPorCPF(cpfDestinatario).atualizaSaldo(cpfDestinatario);
		System.out.println("Transfer??ncia realizada com sucesso!");
	}

	public static void mostrarSaldo(Conta conta) {
		System.out.println("Ol??! Seu saldo ?? de:" + conta.getSaldo());
	}

	public static void handleContratarSeguroCorrente(ContaCorrente conta)
			throws SaldoInsuficienteException, ValorNegativoOu0Exception, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ol??! bem vindo ao menu de contrata????o de seguro");
		if (conta.isPossuiSeguro()) {
			System.out.println("Voc?? j?? possui seguro contratado");
			System.out.println("O valor atual ?? de:" + conta.getValorSeguro() + ", se deseja mudar o valor,"
					+ "digite o novo valor,caso contr??rio, digite 0");
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
			System.out.println("Vimos que voc?? n??o tem seguro, qual valor deseja adicionar?");
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
		System.out.println("Ol??! bem vindo ao menu de contrata????o de seguro");
		if (conta.isPossuiSeguro()) {
			System.out.println("Voc?? j?? possui seguro contratado");
			System.out.println("O valor atual ?? de:" + conta.getValorSeguro() + ", se deseja mudar o valor,"
					+ "digite o novo valor,caso contr??rio, digite 0");
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
			System.out.println("Vimos que voc?? n??o tem seguro, qual valor deseja adicionar?");
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

	

}
