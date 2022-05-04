package br.com.grupo3.entidades;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import br.com.grupo3.exceptions.CodigoInvalidoException;
import br.com.grupo3.exceptions.ConstrucaoInvalidaException;
import br.com.grupo3.exceptions.ValorInexistenteException;
import br.com.grupo3.interfaces.GeradorRelatório;
import br.com.grupo3.repositorios.ContasRepositorio;

// Esse arquivo ficou um pouco bagunçado, seria bom passar um ctrl+shift+f para formatar o código
public class Diretor extends Funcionario implements GeradorRelatório {

    public Diretor(String nome, String cpf, String senha, int codCargo) throws ConstrucaoInvalidaException, CodigoInvalidoException {
        super(nome, cpf, senha, codCargo);

    }

    public void relatorioContas() {
        System.out.println("O número total de contas é de:" + ContasRepositorio.getContas().size());
    }

    // Essa função ficou repetida, talvez seria melhor se fosse implementada em funcionário
    @Override
    public void gerarRelatorioDeQtdContas(Pessoa pessoa) throws ValorInexistenteException, CodigoInvalidoException {
        int quantidadeContas;


        quantidadeContas = ContasRepositorio.getContas().size();
        System.out.println("O número total de contas nas agências selecionadas é de:" + quantidadeContas);

    }

    // Essa função ficou repetida, talvez seria melhor se fosse implementada em funcionário
    public void registraRelatorio() throws IOException {
        LocalDateTime instante = LocalDateTime.now();
        String s = File.separator;
        File caminhoRelatorioQtdContas = new File("src" + s + "br" + s + "com" + s + "grupo3");
        File relatorioQtdContas = new File(caminhoRelatorioQtdContas.getAbsolutePath() + s + "relatorioRepositorio.csv");

        if (!caminhoRelatorioQtdContas.exists()) {
            caminhoRelatorioQtdContas.mkdirs();
        }

        if (!relatorioQtdContas.exists()) {
            relatorioQtdContas.createNewFile();
        }
        FileWriter relatorioQtdContasWriter = new FileWriter(relatorioQtdContas);
        BufferedWriter relatorioQtdContasWriterBuff = new BufferedWriter(relatorioQtdContasWriter);
        relatorioQtdContasWriterBuff.append("Relatório de quantidade de contas feito por, " + this.nome + " aconteceu em" + instante);
        relatorioQtdContasWriterBuff.newLine();
        relatorioQtdContasWriterBuff.append("O número total de contas no banco é de:" + ContasRepositorio.getContas().size());
        relatorioQtdContasWriterBuff.close();
        relatorioQtdContasWriter.close();


    }

}
