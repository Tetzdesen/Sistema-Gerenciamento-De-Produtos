/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author tetzner
 */
public class ProdutoAlimento extends Produto {
    private Date dataValidade;

    public ProdutoAlimento(Date dataValidade, String nome, double preco, int qtdEstoque) {
        super(nome, preco, qtdEstoque);
        this.dataValidade = dataValidade;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }  
}