package br.com.grupo3.repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import br.com.grupo3.entidades.Conta;
import br.com.grupo3.entidades.ContaCorrente;
import br.com.grupo3.entidades.ContaPoupanca;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.ValorExistenteException;
import br.com.grupo3.exceptions.ValorInexistenteException;

public class ContasRepositorio {

	private static HashMap<String, Conta> listaContas = new HashMap<>();

	public static void adicionarContas(Conta conta) throws ValorExistenteException, IOException {
		if (listaContas.containsKey(conta.getCpf())) {
			throw new ValorExistenteException();
		}
		listaContas.put(conta.getCpf(), conta);
		String s = File.separator;
		File caminhoContaArq = new File("src" + s + "br" + s + "com" + s + "grupo3");
		File contaArq = new File(caminhoContaArq.getAbsolutePath() + s + "contaRepositorio.csv");

		if (!caminhoContaArq.exists()) {
			caminhoContaArq.mkdirs();
		}

		if (!contaArq.exists()) {
			contaArq.createNewFile();
		}

		try (FileWriter contaArqWriter = new FileWriter(contaArq, true);
				BufferedWriter contaArqWriterBuff = new BufferedWriter(contaArqWriter)) {

			contaArqWriterBuff.append(conta.getCpf() + "¨¨" + conta.getSaldo() + "¨¨" + conta.getCodConta() + "¨¨"
					+ conta.getCodAgencia().getCodigoAgencia() + "¨¨" + conta.getTipoConta().getCodigoTipoConta()
					+ "¨¨");
			if (conta instanceof ContaCorrente) {
				contaArqWriterBuff.append("1" + "¨¨" +((ContaCorrente) conta).isPossuiSeguro()  + "¨¨"
						+ ((ContaCorrente) conta).getValorSeguro());
			}
			if (conta instanceof ContaPoupanca) {
				contaArqWriterBuff.append("2");
			}
			contaArqWriterBuff.newLine();
			contaArqWriterBuff.flush();
			contaArqWriterBuff.close();
			contaArqWriter.close();
		} catch (IOException e) {
			System.out.println("Erro na escrita dos arquivos");
		}

	}

	public static Conta getContaPorCPF(String cpf) throws ValorInexistenteException {
		if (!listaContas.containsKey(cpf)) {
			throw new ValorInexistenteException();
		}
		return listaContas.get(cpf);
	}

	public static void contaRepositorioLoader() throws IOException {
		String s = File.separator;
		File caminhoContaLD = new File("src" + s + "br" + s + "com" + s + "grupo3");
		File contaLD = new File(caminhoContaLD.getAbsolutePath() + s + "contaRepositorio.csv");
		System.out.println("até aqui tá safe");

		if (!caminhoContaLD.exists()) {
			caminhoContaLD.mkdirs();
		}

		if (!contaLD.exists()) {
			contaLD.createNewFile();
		}

		try (FileReader contaLDReader = new FileReader(contaLD);
				BufferedReader contaLDReaderBuff = new BufferedReader(contaLDReader)) {
			String linhaContaLDAtual;
			while ((linhaContaLDAtual = contaLDReaderBuff.readLine()) != null) {
				String[] linhaTemp = linhaContaLDAtual.split("¨¨");
				String cpfTemp = linhaTemp[0];
				double saldoTemp = Double.parseDouble(linhaTemp[1]);
				String codContaTemp = linhaTemp[2];
				int codAgenciaTemp = Integer.parseInt(linhaTemp[3]);
				int tipoContaTemp = Integer.parseInt(linhaTemp[4]);
				String tipoContaAqui = linhaTemp[5];
				Conta contaTemp = null;
				if (tipoContaAqui.equals("1")) {
					boolean possuiSeguroTemp = Boolean.parseBoolean(linhaTemp[6]);
					double valorSeguro = Double.parseDouble(linhaTemp[7]);
					if (possuiSeguroTemp) {
						contaTemp = new ContaCorrente(cpfTemp, saldoTemp, codContaTemp, codAgenciaTemp, tipoContaTemp,
								possuiSeguroTemp, valorSeguro);
					} else {
						contaTemp = new ContaCorrente(cpfTemp, saldoTemp, codContaTemp, codAgenciaTemp, tipoContaTemp,
								possuiSeguroTemp);
					}
				} else if (tipoContaAqui.equals("2")) {
					contaTemp = new ContaPoupanca(cpfTemp, saldoTemp, codContaTemp, codAgenciaTemp, tipoContaTemp);
				}
				listaContas.put(contaTemp.getCpf(), contaTemp);
				System.out.println("Rodou");

			}
		} catch (ConstrucaoInvalidaException | CodigoInvalidoException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}
}
