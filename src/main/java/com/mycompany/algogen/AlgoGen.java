/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.algogen;
public class AlgoGen {

    public static void main(String[] args) {
        int populacao = 20;
        double capacidadePeso = 8000; // Capacidade de peso em kg
        int larguraMaxima = 300; // Largura máxima em cm
        int alturaMaxima = 200; // Altura máxima em cm
        int profundidadeMaxima = 150; // Profundidade máxima em cm
        int probabilidadeMutacao = 5; // 5%
        int qtdeCruzamentos = 5;
        int numeroGeracoes = 10;
        
        AlgoritmoGenetico meuAg = new AlgoritmoGenetico(populacao, capacidadePeso, larguraMaxima,
                                                        alturaMaxima, profundidadeMaxima,
                                                        probabilidadeMutacao, qtdeCruzamentos,
                                                        numeroGeracoes);
        
        meuAg.carregaArquivo("dados.csv"); // Carregar os dados do arquivo CSV
        meuAg.executar(); // Executar o algoritmo genético
    }
}