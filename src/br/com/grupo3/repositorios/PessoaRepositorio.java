package br.com.grupo3.repositorios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import br.com.grupo3.entidades.Cliente;
import br.com.grupo3.entidades.Diretor;
import br.com.grupo3.entidades.Gerente;
import br.com.grupo3.entidades.Pessoa;
import br.com.grupo3.entidades.Presidente;
import br.com.grupo3.exceptions.ValorExistenteException;

public class PessoaRepositorio {
	
	private static HashMap<String, Pessoa> listaPessoas = new HashMap<>();
	
	public static void adicionarPessoa(Pessoa pessoa) throws ValorExistenteException, IOException {
		if (listaPessoas.containsKey(pessoa.getCpf())) {
			throw new ValorExistenteException();
		} 
		listaPessoas.put(pessoa.getCpf(), pessoa);
		
		String s = File.separator;
		File caminhoPessoaArq = new File("src"+s+"br"+s+"com"+s+"grupo3");
		File pessoaArq = new File(caminhoPessoaArq.getAbsolutePath()+s+"pessoaRepositorio.csv");
		
		if(!caminhoPessoaArq.exists()) {
			caminhoPessoaArq.mkdirs();
		}
		
		if(!pessoaArq.exists()) {
			pessoaArq.createNewFile();
		}
		
		try(FileWriter pessoaArqWriter = new FileWriter(pessoaArq,true);
			BufferedWriter pessoaArqWriterBuff = new BufferedWriter(pessoaArqWriter)){
			
			pessoaArqWriterBuff.append(pessoa.getCpf()+"|"+pessoa.getSenha()+"|");
			
			if(pessoa instanceof Cliente) {
				pessoaArqWriterBuff.append("0");
			} else if (pessoa instanceof Diretor) {
				pessoaArqWriterBuff.append("1");
			} else if (pessoa instanceof Gerente) {
				pessoaArqWriterBuff.append("2"+"|"+((Gerente)pessoa).getCodAgencia());
			} else if (pessoa instanceof Presidente) {
				pessoaArqWriterBuff.append("3");
			}
			
			pessoaArqWriterBuff.newLine();
			
		} catch (IOException e) {
			System.out.println("Houve um problema ao escrever o arquivo.");
		}
		
	}

}
