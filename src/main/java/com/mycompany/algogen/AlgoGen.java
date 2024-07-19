/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.algogen;
public class AlgoGen {

    public static void main(String[] args) {
        int populacao = 20;
        double capacidadePeso = 400; 
        int larguraMaxima = 300; 
        int alturaMaxima = 300; 
        int profundidadeMaxima = 400; 
        int probabilidadeMutacao = 5; 
        int qtdeCruzamentos = 5;
        int numeroGeracoes = 10;
        
        AlgoritmoGenetico meuAg = new AlgoritmoGenetico(populacao, capacidadePeso, larguraMaxima,
                                                        alturaMaxima, profundidadeMaxima,
                                                        probabilidadeMutacao, qtdeCruzamentos,
                                                        numeroGeracoes);
        
        meuAg.carregaArquivo("dados.csv"); 
        meuAg.executar(); 
    }
}