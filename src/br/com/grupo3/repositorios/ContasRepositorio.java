package br.com.grupo3.repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.grupo3.entidades.Conta;
import br.com.grupo3.entidades.ContaCorrente;
import br.com.grupo3.entidades.ContaPoupanca;
import br.com.grupo3.entidades.ContaPremium;
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

			contaArqWriterBuff.append(
					conta.getNome() + "¨¨" + conta.getCpf() + "¨¨" + conta.getSaldo() + "¨¨" + conta.getCodConta()
							+ "¨¨" + conta.getCodAgencia() + "¨¨" + conta.getTipoConta().getCodigoTipoConta() + "¨¨");
			if (conta instanceof ContaCorrente) {
				contaArqWriterBuff.append("1" + "¨¨" + ((ContaCorrente) conta).isPossuiSeguro() + "¨¨"
						+ ((ContaCorrente) conta).getValorSeguro());
			}
			if (conta instanceof ContaPoupanca) {
				contaArqWriterBuff.append("2");
			}
			if (conta instanceof ContaPremium) {
				contaArqWriterBuff.append("3" + "¨¨" + ((ContaPremium) conta).isPossuiSeguro() + "¨¨"
						+ ((ContaPremium) conta).getValorSeguro());
			}
			contaArqWriterBuff.newLine();
			contaArqWriterBuff.flush();

		} catch (IOException e) {
			System.out.println("Erro na escrita dos arquivos");
		}
		System.out.println("Conta adicionada com sucesso!");

	}

	public static List<Conta> getContas() {
		return new ArrayList<Conta>(listaContas.values());

	}

	public static Conta getContaPorCPF(String cpf) throws ValorInexistenteException {
		if (!listaContas.containsKey(cpf)) {
			throw new ValorInexistenteException();
		}
		return listaContas.get(cpf);
	}

	public static List<Conta> getContasPorIdAgencia(int codigo)
			throws ValorInexistenteException, CodigoInvalidoException {
		List<Conta> listaEspecifica = new ArrayList<>();
		for (Conta conta : listaContas.values()) {

			if ((conta.getCodAgencia() == codigo)) {
				listaEspecifica.add(conta);
			}

		}
		return listaEspecifica;
	}

	public static void contaRepositorioLoader() throws IOException {
		String s = File.separator;
		File caminhoContaLD = new File("src" + s + "br" + s + "com" + s + "grupo3");
		File contaLD = new File(caminhoContaLD.getAbsolutePath() + s + "contaRepositorio.csv");

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
				String nomeTemp = linhaTemp[0];
				String cpfTemp = linhaTemp[1];
				double saldoTemp = Double.parseDouble(linhaTemp[2]);
				String codContaTemp = linhaTemp[3];
				int codAgenciaTemp = Integer.parseInt(linhaTemp[4]);
				int tipoContaTemp = Integer.parseInt(linhaTemp[5]);
				String tipoContaAqui = linhaTemp[6];
				Conta contaTemp = null;
				if (tipoContaAqui.equals("1")) {
					boolean possuiSeguroTemp = Boolean.parseBoolean(linhaTemp[7]);
					double valorSeguro = Double.parseDouble(linhaTemp[8]);
					contaTemp = new ContaCorrente(nomeTemp, cpfTemp, saldoTemp, codContaTemp, codAgenciaTemp,
							tipoContaTemp, possuiSeguroTemp, valorSeguro);

				} else if (tipoContaAqui.equals("2")) {
					contaTemp = new ContaPoupanca(nomeTemp, cpfTemp, saldoTemp, codContaTemp, codAgenciaTemp,
							tipoContaTemp);

				}
				if (tipoContaAqui.equals("3")) {
					boolean possuiSeguroTemp = Boolean.parseBoolean(linhaTemp[7]);
					double valorSeguro = Double.parseDouble(linhaTemp[8]);
					contaTemp = new ContaPremium(nomeTemp, cpfTemp, saldoTemp, codContaTemp, codAgenciaTemp,
							tipoContaTemp, possuiSeguroTemp, valorSeguro);

				}
				listaContas.put(contaTemp.getCpf(), contaTemp);

			}
		} catch (ConstrucaoInvalidaException | CodigoInvalidoException e) {
			e.getMessage();
		}
	}
}
