package br.com.grupo3.entidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.grupo3.enums.TipoConta;
import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.ContaNaoEncontradaException;
import br.com.grupo3.exceptions.NumeroInvalidoException;
import br.com.grupo3.exceptions.SaldoInsuficienteException;
import br.com.grupo3.exceptions.ValorInexistenteException;
import br.com.grupo3.repositorios.ContasRepositorio;
import br.com.grupo3.validadores.ValidadorCpf;

public abstract class Conta implements Comparable<Conta> {

	protected String nome;
	protected String cpf;
	protected double saldo;
	protected String codConta;
	protected int codAgencia;
	protected TipoConta tipoConta;
	protected  int valorSaque;
	protected  int valorDeposito;
	protected  int valorTransferencia;
	@Override
	public  int compareTo(Conta c) {
		if(this.getNome().compareTo(c.getNome())<0) {
			return 0;
		}
		if(this.getNome().compareTo(c.getNome())>0) {
			return 1;	
		}
		return -1;
	}

	public Conta(String nome,String cpf, double saldo, String codConta, int codAgencia, int tipoConta) throws ConstrucaoInvalidaException, CodigoInvalidoException {
		super();
		this.nome=nome;
		this.cpf = ValidadorCpf.validarCpf(cpf);
		this.saldo = saldo;
		this.codConta = codConta;
		this.codAgencia = codAgencia;
		this.tipoConta = TipoConta.getTipoContaPorCodigo(tipoConta);
	}

	public void sacar(double valorInserido) throws NumeroInvalidoException, SaldoInsuficienteException {
		if (valorInserido <= 0) {
			throw new NumeroInvalidoException();
		}if(valorInserido+0.1 > this.saldo) {
			throw new SaldoInsuficienteException();
		}
		this.saldo -= (valorInserido+0.10);
		valorSaque += 0.10;
	}

	public void depositar(double valorInserido) throws NumeroInvalidoException {
		if (valorInserido <= 0) {
			throw new NumeroInvalidoException();
		}
		this.saldo += valorInserido;
		this.saldo -= 0.10;
		valorDeposito += 0.10;
	}
	public void transferir(double valorInserido,String cpfDestinatario)
							  throws NumeroInvalidoException, IOException, ValorInexistenteException, SaldoInsuficienteException {
		ContasRepositorio.contaRepositorioLoader();
		if (valorInserido <= 0) {
			throw new NumeroInvalidoException();
		}if(valorInserido+0.2 > this.saldo) {
			throw new SaldoInsuficienteException();
		}if(ContasRepositorio.getContaPorCPF(cpfDestinatario).equals(null)) {
			throw new ValorInexistenteException();
		}else {
		ContasRepositorio.getContaPorCPF(cpfDestinatario).saldo+=valorInserido;
		String cpf=this.cpf;
		ContasRepositorio.getContaPorCPF(cpf).saldo-=(valorInserido+0.2);
		valorTransferencia+=0.2;
		this.saldo-=(valorInserido+0.2);
		}	
	}
		
	public void registraTransacao (String transacao,int tipo,String cpfDestinatario,double valor) throws IOException, ValorInexistenteException {
		String s = File.separator;
		File caminhoRegistroArq = new File("src" + s + "br" + s + "com" + s + "grupo3");
		File registroArq = new File(caminhoRegistroArq.getAbsolutePath() + s + "registroRepositorio.csv");

		if (!caminhoRegistroArq.exists()) {
			caminhoRegistroArq.mkdirs();
		}

		if (!registroArq.exists()) {
			registroArq.createNewFile();
		}

		try (FileWriter registroArqWriter = new FileWriter(registroArq, true);
				BufferedWriter registroArqWriterBuff = new BufferedWriter(registroArqWriter)){
			registroArqWriterBuff.append(transacao+"¨¨"+tipo+"¨¨"+this.cpf+"¨¨"+valor+"¨¨");
			if(ContasRepositorio.getContaPorCPF(cpfDestinatario) instanceof ContaCorrente) {
				registroArqWriterBuff.append("1");
			} else if(ContasRepositorio.getContaPorCPF(cpfDestinatario) instanceof ContaPoupanca) {
				registroArqWriterBuff.append("2");
			} else if(ContasRepositorio.getContaPorCPF(cpfDestinatario) instanceof ContaPremium) {
				registroArqWriterBuff.append("3");
			}
			
			if(!cpfDestinatario.equals(this.cpf)) {
				registroArqWriterBuff.append("¨¨"+cpfDestinatario);
			}
			registroArqWriterBuff.newLine();
		}
	}
		
	public void atualizaSaldo(String cpf) throws  ValorInexistenteException, ContaNaoEncontradaException, IOException {
		String s = File.separator;
		File caminhoAtualizaArq = new File("src" + s + "br" + s + "com" + s + "grupo3");
		File atualizaArq = new File(caminhoAtualizaArq.getAbsolutePath() + s + "contaRepositorio.csv");
		//List<Conta> listaAtualizada= new ArrayList();
		System.out.println(atualizaArq.length());
		if (!caminhoAtualizaArq.exists()) {
			caminhoAtualizaArq.mkdirs();
		}

		if (!atualizaArq.exists()) {
			atualizaArq.createNewFile();
		}
		StringBuilder SB= new StringBuilder();
		try (FileReader atualizaArqReader = new FileReader(atualizaArq);
				BufferedReader atualizaArqReaderBuff = new BufferedReader(atualizaArqReader)){
			if (!this.cpf.equals(cpf)) {
				throw new ContaNaoEncontradaException();
			}
			String saldoAtual=String.valueOf(this.saldo);
			String linhaTemporaria="";
			while((linhaTemporaria=atualizaArqReaderBuff.readLine())!= null) {
				//String saldoAntigo=String.valueOf(ContasRepositorio.getContaPorCPF(cpf).getSaldo());
				String[] separador=linhaTemporaria.split("¨¨");
				if(separador[1].equals(cpf)) {
					linhaTemporaria = linhaTemporaria.replace(separador[2],String.valueOf(ContasRepositorio.getContaPorCPF(cpf).getSaldo())) ;
				}
	
				
				SB.append(linhaTemporaria+"\n");
			}
			
			
			

		}catch (IOException e) {
			System.out.println("Deu erro ai mano");
		}
		try (FileWriter atualizaArqWriter = new FileWriter(atualizaArq);
				BufferedWriter atualizaArqWriterBuff = new BufferedWriter(atualizaArqWriter)){
	              atualizaArqWriterBuff.append(SB);
		}
	          
	}
		
	

	public String getCpf() {
		return cpf;
	}

	public double getSaldo() {
		return saldo;
	}

	public String getCodConta() {
		return codConta;
	}

	public int getCodAgencia() {
		return codAgencia;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	@Override
	public String toString() {
		return "Conta [nome="+ nome +", cpf=" + cpf + ", saldo=" + saldo + ", codConta=" + codConta + ", codAgencia=" + codAgencia
				+ ", tipoConta=" + tipoConta + "]";
	}

	public  int getValorSaque() {
		return valorSaque;
	}

	public  int getValorDeposito() {
		return valorDeposito;
	}

	public  int getValorTransferencia() {
		return valorTransferencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	


}
