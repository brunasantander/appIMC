package br.unoeste.fipp.appimc;

import java.io.Serializable;
import java.util.Date;

public class UserData implements Serializable {
    private String nome, condicaoFisica;
    private int peso, altura;
    private Double imc;
    private Date dataCalculo;

    public UserData() {
    }

    public UserData(String nome, String condicaoFisica, int peso, int altura, Double imc, Date dataCalculo) {
        this.nome = nome;
        this.condicaoFisica = condicaoFisica;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
        this.dataCalculo = dataCalculo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCondicaoFisica() {
        return condicaoFisica;
    }

    public void setCondicaoFisica(String condicaoFisica) {
        this.condicaoFisica = condicaoFisica;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    public Date getDataCalculo() {
        return dataCalculo;
    }

    public void setDataCalculo(Date dataCalculo) {
        this.dataCalculo = dataCalculo;
    }
}
