/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 *
 * @author tetzner
 */
public class ProdutoDAO {
    private ArrayList<Produto> produtos;

    public ProdutoDAO() {
        produtos = new ArrayList<>();
    }
    
    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public void adicionaProduto(Produto produto){
        produtos.add(produto);
    }

    public Produto pesquisaProdutoPorNome(String nome){
        int i;
        
        for(i = 0; i < produtos.size(); i++) {
            if(produtos.get(i).getNome().equals(nome)){
                return produtos.get(i);
            }
        }
        return null;
    }
    
    public void excluiProduto(String nome){
        produtos.remove(pesquisaProdutoPorNome(nome));
    }
    
    public void alteraProduto(Produto produto, String novoNome, double novoPreco, int novoQtdEstoque, String novaMarca, String novoModelo, Date novadataValidade){
        produto.setNome(novoNome);
        produto.setPreco(novoPreco);
        produto.setQtdEstoque(novoQtdEstoque);
        
        if(produto instanceof ProdutoEletronico){
            
            ((ProdutoEletronico)produto).setMarca(novaMarca);
            ((ProdutoEletronico)produto).setModelo(novoModelo);
            
        } else if(produto instanceof ProdutoAlimento){
            
            ((ProdutoAlimento)produto).setDataValidade(novadataValidade);
            
        }
    }
     
    public ArrayList<Produto> ordenaProdutosPorNome(){
        ArrayList<Produto> produtosAuxiliar = new ArrayList<>(produtos);
        Collections.sort(produtosAuxiliar);
        return produtosAuxiliar;
    }
    
    
}
