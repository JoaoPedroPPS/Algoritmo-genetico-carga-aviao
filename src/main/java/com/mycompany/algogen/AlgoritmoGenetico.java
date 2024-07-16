/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.algogen;
import java.io.*;
import java.util.*;

public class AlgoritmoGenetico {
    private int tamPopulacao;
    private int tamCromossomo = 0;
    private double capacidadePeso;
    private int larguraMaxima;
    private int alturaMaxima;
    private int profundidadeMaxima;
    private int probMutacao;
    private int qtdeCruzamentos;
    private int numeroGeracoes;
    private ArrayList<Produto> produtos = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> populacao = new ArrayList<>();
    private ArrayList<Integer> roletaVirtual = new ArrayList<>();

    public AlgoritmoGenetico(int tamanhoPopulacao, double capacidadePeso,
                             int larguraMaxima, int alturaMaxima, int profundidadeMaxima,
                             int probabilidadeMutacao, int qtdeCruzamentos, int numGeracoes) {
        this.tamPopulacao = tamanhoPopulacao;
        this.capacidadePeso = capacidadePeso;
        this.larguraMaxima = larguraMaxima;
        this.alturaMaxima = alturaMaxima;
        this.profundidadeMaxima = profundidadeMaxima;
        this.probMutacao = probabilidadeMutacao;
        this.qtdeCruzamentos = qtdeCruzamentos;
        this.numeroGeracoes = numGeracoes;
    }

    public void executar() {
        this.criarPopulacao();
        // Executar por gerações
        for (int i = 0; i < this.numeroGeracoes; i++) {
            System.out.println("Geração: " + i);
            mostraPopulacao();
            operadoresGeneticos();
            novoPopulacao();
        }
        mostrarCarga(this.populacao.get(obterMelhor()));
    }

    public void mostraPopulacao() {
        for (int i = 0; i < this.tamPopulacao; i++) {
            System.out.println("Cromossomo " + i + ": " + populacao.get(i));
            System.out.println("Avaliação: " + fitness(populacao.get(i)));
        }
    }

    public void carregaArquivo(String fileName) {
        String csvFile = fileName;
        String line = "";
        String[] produto = null;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                produto = line.split(",");
                Produto novoProduto = new Produto();
                novoProduto.setDescricao(produto[0]);
                novoProduto.setPeso(Double.parseDouble(produto[1]));
                novoProduto.setValor(Double.parseDouble(produto[2]));
                produtos.add(novoProduto);
                System.out.println(novoProduto);
                this.tamCromossomo++;
            }
            System.out.println("Tamanho do cromossomo: " + this.tamCromossomo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> criarCromossomo() {
        ArrayList<Integer> novoCromossomo = new ArrayList<>();
        for (int i = 0; i < this.tamCromossomo; i++) {
            if (Math.random() < 0.6)
                novoCromossomo.add(0);
            else
                novoCromossomo.add(1);
        }
        return novoCromossomo;
    }

    private void criarPopulacao() {
        for (int i = 0; i < this.tamPopulacao; i++)
            this.populacao.add(criarCromossomo());
    }

    private double fitness(ArrayList<Integer> cromossomo) {
        double pesoTotal = 0, larguraTotal = 0, alturaTotal = 0, profundidadeTotal = 0;
        for (int i = 0; i < this.tamCromossomo; i++) {
            if (cromossomo.get(i) == 1) {
                pesoTotal += produtos.get(i).getPeso();
                larguraTotal += produtos.get(i).getLargura();
                alturaTotal += produtos.get(i).getAltura();
                profundidadeTotal += produtos.get(i).getProfundidade();
            }
        }

        // Verifica se excede as dimensões máximas
        if (larguraTotal > larguraMaxima || alturaTotal > alturaMaxima || profundidadeTotal > profundidadeMaxima) {
            return 0; // Penalização por exceder dimensões máximas
        }

        // Verifica se excede a capacidade máxima de peso
        if (pesoTotal > capacidadePeso) {
            return 0; // Penalização por exceder capacidade de peso
        }

        // Calcula o fitness como o valor total dos produtos
        double valorTotal = 0;
        for (int i = 0; i < this.tamCromossomo; i++) {
            if (cromossomo.get(i) == 1) {
                valorTotal += produtos.get(i).getValor();
            }
        }

        return valorTotal;
    }

    private void gerarRoleta() {
        ArrayList<Double> fitnessIndividuos = new ArrayList<>();
        double totalFitness = 0;
        for (int i = 0; i < this.tamPopulacao; i++) {
            fitnessIndividuos.add(fitness(this.populacao.get(i)));
            totalFitness += fitnessIndividuos.get(i);
        }
        System.out.println("Soma total fitness: " + totalFitness);

        for (int i = 0; i < this.tamPopulacao; i++) {
            double qtdPosicoes = (fitnessIndividuos.get(i) / totalFitness) * 1000;
            for (int j = 0; j <= qtdPosicoes; j++)
                roletaVirtual.add(i);
        }
    }

    private int roleta() {
        Random r = new Random();
        int selecionado = r.nextInt(roletaVirtual.size());
        return roletaVirtual.get(selecionado);
    }

    private ArrayList<ArrayList<Integer>> cruzamento() {
        ArrayList<Integer> filho1 = new ArrayList<>();
        ArrayList<Integer> filho2 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> filhos = new ArrayList<>();
        ArrayList<Integer> pai1, pai2;
        int indice_pai1, indice_pai2;
        indice_pai1 = roleta();
        indice_pai2 = roleta();
        pai1 = populacao.get(indice_pai1);
        pai2 = populacao.get(indice_pai2);
        Random r = new Random();
        int pos = r.nextInt(this.tamCromossomo); // Ponto de corte
        for (int i = 0; i <= pos; i++) {
            filho1.add(pai1.get(i));
            filho2.add(pai2.get(i));
        }
        for (int i = pos + 1; i < this.tamCromossomo; i++) {
            filho1.add(pai2.get(i));
            filho2.add(pai1.get(i));
        }
        filhos.add(filho1);
        filhos.add(filho2);
        return filhos;
    }

    private void mutacao(ArrayList<Integer> filho) {
        Random r = new Random();
        int v = r.nextInt(100);
        if (v < this.probMutacao) {
            int ponto = r.nextInt(this.tamCromossomo);
            if (filho.get(ponto) == 1)
                filho.set(ponto, 0);
            else
                filho.set(ponto, 1);

            int ponto2 = r.nextInt(this.tamCromossomo);
            if (filho.get(ponto2) == 1)
                filho.set(ponto2, 0);
            else
                filho.set(ponto2, 1);

            System.out.println("Ocorreu mutação!");
        }
    }

    private void operadoresGeneticos() {
        ArrayList<ArrayList<Integer>> filhos;
        gerarRoleta();
        for (int i = 0; i < this.qtdeCruzamentos; i++) {
            filhos = cruzamento();
            mutacao(filhos.get(0));
            mutacao(filhos.get(1));
            populacao.add(filhos.get(0));
            populacao.add(filhos.get(1));
        }
    }

    protected int obterPior() {
        int indicePior = 0;
        double pior, nota = 0;
        pior = fitness(populacao.get(0));
        for (int i = 1; i < this.tamPopulacao; i++) {
            nota = fitness(populacao.get(i));
            if (nota < pior) {
                pior = nota;
                indicePior = i;
            }
        }
        return indicePior;
    }

    private void novoPopulacao() {
        for (int i = 0; i < this.qtdeCruzamentos; i++) {
            populacao.remove(obterPior());
            populacao.remove(obterPior());
        }
    }

    protected int obterMelhor() {
        int indiceMelhor = 0;
        double melhor, nota = 0;
        melhor = fitness(populacao.get(0));
        for (int i = 1; i < this.tamPopulacao; i++) {
            nota = fitness(populacao.get(i));
            if (nota > melhor) {
                melhor = nota;
                indiceMelhor = i;
            }
        }
        return indiceMelhor;
    }

    public void mostrarCarga(ArrayList<Integer> resultado) {
        System.out.println("Avaliação do Melhor: " + this.fitness(resultado));
        System.out.println("Produtos carregados no avião:");
        for (int i = 0; i < resultado.size(); i++) {
            int carrega = resultado.get(i);
            if (carrega == 1) {
                Produto p = produtos.get(i);
                System.out.println(p.getDescricao() +
                        " Valor:" + p.getValor() + " Peso:" + p.getPeso() +
                        " Largura:" + p.getLargura() + " Altura:" + p.getAltura() +
                        " Profundidade:" + p.getProfundidade());
            }
        }
    }
}
