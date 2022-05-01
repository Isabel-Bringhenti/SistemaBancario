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

import br.com.grupo3.entidades.Cliente;
import br.com.grupo3.entidades.Diretor;
import br.com.grupo3.entidades.Gerente;
import br.com.grupo3.entidades.Pessoa;
import br.com.grupo3.entidades.Presidente;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.ValorExistenteException;
import br.com.grupo3.exceptions.ValorInexistenteException;

public class PessoaRepositorio {

	private static HashMap<String, Pessoa> listaPessoas = new HashMap<>();

	public static void adicionarPessoa(Pessoa pessoa) throws ValorExistenteException, IOException {
		if (listaPessoas.containsKey(pessoa.getCpf())) {
			throw new ValorExistenteException();
		}
		listaPessoas.put(pessoa.getCpf(), pessoa);

		String s = File.separator;
		File caminhoPessoaArq = new File("src" + s + "br" + s + "com" + s + "grupo3");
		File pessoaArq = new File(caminhoPessoaArq.getAbsolutePath() + s + "pessoaRepositorio.csv");

		if (!caminhoPessoaArq.exists()) {
			caminhoPessoaArq.mkdirs();
		}

		if (!pessoaArq.exists()) {
			pessoaArq.createNewFile();
		}

		try (FileWriter pessoaArqWriter = new FileWriter(pessoaArq, true);
				BufferedWriter pessoaArqWriterBuff = new BufferedWriter(pessoaArqWriter)) {

			pessoaArqWriterBuff.append(pessoa.getNome()+"¨¨"+pessoa.getCpf() + "¨¨" + pessoa.getSenha() + "¨¨");

			if (pessoa instanceof Cliente) {
				pessoaArqWriterBuff.append("0");
			} else if (pessoa instanceof Diretor) {
				pessoaArqWriterBuff.append("1" + "¨¨" + ((Diretor) pessoa).getCargo().getCodigoCargo());
			} else if (pessoa instanceof Gerente) {

				pessoaArqWriterBuff.append("2" + "¨¨" + ((Gerente) pessoa).getCargo().getCodigoCargo() + "¨¨"
											+ ((Gerente) pessoa).getCodAgencia());
			} else if (pessoa instanceof Presidente) {
				pessoaArqWriterBuff.append("3" + "¨¨" + ((Presidente) pessoa).getCargo().getCodigoCargo());
			}

			pessoaArqWriterBuff.newLine();
			pessoaArqWriterBuff.flush();
			
		} catch (IOException e) {
			System.out.println("Houve um problema ao escrever o arquivo.");
		}
		System.out.println("Pessoa adicionada com sucesso!");

	}

	public static Pessoa getPessoaPorCPF(String cpf) throws ValorInexistenteException {
		if (!listaPessoas.containsKey(cpf)) {
			throw new ValorInexistenteException();
		}
		return listaPessoas.get(cpf);
	}

	public static List<Pessoa>  getPessoas() {
		return  new ArrayList<Pessoa>(listaPessoas.values());
		
	}

	public static void pessoaRepositorioLoader() throws IOException {
		String s = File.separator;
		File caminhoPessoaLD = new File("src" + s + "br" + s + "com" + s + "grupo3");
		File pessoaLD = new File(caminhoPessoaLD.getAbsolutePath() + s + "pessoaRepositorio.csv");

		if (!caminhoPessoaLD.exists()) {
			caminhoPessoaLD.mkdirs();
		}

		if (!pessoaLD.exists()) {
			pessoaLD.createNewFile();
		}

		try (FileReader pessoaLDReader = new FileReader(pessoaLD);
				BufferedReader pessoaLDReaderBuff = new BufferedReader(pessoaLDReader)) {
			String linhaPessoaLDAtual;
			while ((linhaPessoaLDAtual = pessoaLDReaderBuff.readLine()) != null) {
				String[] linhaTemporaria = linhaPessoaLDAtual.split("¨¨");
				String nomeTemporario=linhaTemporaria[0];
				String cpfTemporario = linhaTemporaria[1];
				String senhaTemporario = linhaTemporaria[2];
				String tipoPessoaTemp = linhaTemporaria[3];
				Pessoa pessoaTemp = null;
				if (tipoPessoaTemp.equals("0")) {
					pessoaTemp = new Cliente(nomeTemporario,cpfTemporario, senhaTemporario);
				} else {
					int codigoCargoPessoaTemp = Integer.parseInt(linhaTemporaria[3]);
					if (tipoPessoaTemp.equals("1")) {

						pessoaTemp = new Diretor(nomeTemporario,cpfTemporario, senhaTemporario, codigoCargoPessoaTemp);
					} else if (tipoPessoaTemp.equals("2")) {
						int codigoAgenciaTemp = Integer.parseInt(linhaTemporaria[4]);
						pessoaTemp = new Gerente(nomeTemporario,cpfTemporario, senhaTemporario, codigoCargoPessoaTemp,
								codigoAgenciaTemp);
					} else if (tipoPessoaTemp.equals("3")) {
						pessoaTemp = new Presidente(nomeTemporario,cpfTemporario, senhaTemporario, codigoCargoPessoaTemp);
					}
				}
				listaPessoas.put(pessoaTemp.getCpf(), pessoaTemp);

				
			}
		} catch (ConstrucaoInvalidaException | CodigoInvalidoException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

	}

}
