package com.mycompany.algogen;
import java.io.*;
import java.util.*;
public class Produto {
    private String descricao;
    private double peso;
    private double valor;
    private int largura;
    private int altura;
    private int profundidade;

    public Produto() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "descricao='" + descricao + '\'' +
                ", peso=" + peso +
                ", valor=" + valor +
                ", largura=" + largura +
                ", altura=" + altura +
                ", profundidade=" + profundidade +
                '}';
    }
}