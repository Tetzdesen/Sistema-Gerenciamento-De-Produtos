/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import model.*;
import view.*;

/**
 *
 * @author maik
 */
public class ProdutoController{
    
    private ProdutoDAO dao;
    private ProdutoView view;
   
    public ProdutoController(){

        view = new ProdutoView();
        dao = new ProdutoDAO();
        
        JButton buttonAdicionarProduto = view.getButtonAdicionarProduto();
        JButton buttonRemoverProduto = view.getButtonRemoverProduto();
        JButton buttonEditarProduto = view.getButtonEditarProduto();
        JButton buttonListarProdutosOrdenados = view.getButtonListarProdutosOrdenados();
        JButton buttonListarProdutosNaoOrdenados = view.getButtonListarProdutosNaoOrdenados();
        
        //adicionando ouvintes aos botoes
        buttonAdicionarProduto.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                realizarAdicionarProduto(e);
            }
        });
        buttonRemoverProduto.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                realizarRemoverProduto(e);
            }
        });
        buttonEditarProduto.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                realizarEditarProduto(e);
            }
        });
        buttonListarProdutosOrdenados.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                realizarListarProdutosOrdenados(e);
            }
        });
        buttonListarProdutosNaoOrdenados.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                realizarListarProdutosNaoOrdenados(e);
            }
        });
    }
    
    private void realizarAdicionarProduto(ActionEvent e){
        try{
        //obtendo as informações do textField
        String tipo = view.getComboBoxOpcaoTipoDeProduto().getSelectedItem().toString();
        String nome = view.getTextFieldNome().getText();
        double preco = Double.parseDouble(view.getTextFieldPreco().getText());
        int quantidade = Integer.parseInt(view.getTextFieldQuantidade().getText());
        
        //verificando os casos especiais
        //campo nome vazio
        if(nome.isEmpty()) throw new IllegalArgumentException("Favor preencher o campo 'Nome'!");
        
        //permitir apenas 1 cadastro por nome
        if(dao.pesquisaProdutoPorNome(nome) != null) throw new IllegalArgumentException("Produto já cadastrado no sistema! Favor adicionar outro ou utilizar 'Editar Produto'!");
        
        //não permitir valorer menores ou iguais a zero
        if(preco <= 0.0) throw new IllegalArgumentException("Favor informar preço maior que zero!");
        
        //não permitir quantidade negativa
        if(quantidade<0) throw new IllegalArgumentException("Favor informar quantidade positiva!");
        
        //verificando qual tipo de produto e inserindo o correto
        if (tipo.equals("Alimento")){
            //se for alimento, usa data de validade
            Date validade = (new SimpleDateFormat("dd/MM/yyyy")).parse(view.getTextFieldDataDeValidade().getText());
            
            //cria o objeto Alimento e adiciona no estoque
            dao.adicionaProduto(new ProdutoAlimento(validade,nome,preco,quantidade));
        }else{
            //se não for alimento, será eletrônico. Usar marca e modelo
            String marca = view.getTextFieldMarca().getText();
            String modelo = view.getTextFieldModelo().getText();
            
            //verifica se são valores válidos
            if(marca.isEmpty()) throw new IllegalArgumentException("Favor preencher o campo 'Marca'!");
            if(modelo.isEmpty()) throw new IllegalArgumentException("Favor preencher o campo 'Modelo'!");
            
            //cria o objeto Eletronico e adiciona no estoque
            dao.adicionaProduto(new ProdutoEletronico(marca,modelo,nome,preco,quantidade));
        }
        //limpa os campos pra ficar mais bonito
        limparCampos();
        
        //exibe mensagem de sucesso
        JOptionPane.showMessageDialog(view, "Produto adicionado com sucesso!");
        }
        catch(ParseException | NumberFormatException erroDeDados){
            JOptionPane.showMessageDialog(view, "Favor informar dados válidos!");
        }
        catch(IllegalArgumentException erro){
            JOptionPane.showMessageDialog(view,erro.getMessage());
        }
    }
    
    private void realizarRemoverProduto(ActionEvent e){
        try{
        String nome = view.getTextFieldNome().getText();
        
        //verifica se foi escrito alguma coisa no campo nome
        if(nome.isEmpty()) throw new IllegalArgumentException("Favor preencher o campo 'Nome'!");
        
        //verificar questão de produto não cadastrado = se pode utilizar dentro do pesquisaProdutoPorNome
        if(dao.pesquisaProdutoPorNome(nome) == null) throw new IllegalArgumentException("Produto não cadastrado!");
        dao.excluiProduto(nome);
        
        //limpa os campos pra ficar bonito
        limparCampos();
        //exibe mensagem de sucesso ao remover, se remover com sucesso
        JOptionPane.showMessageDialog(view, "Produto removido com sucesso!");
        }
        catch(NumberFormatException erroDeData){
            JOptionPane.showMessageDialog(view, "Favor informar nome válido!");
        }
        catch(IllegalArgumentException erroNaoCadastrado){
            JOptionPane.showMessageDialog(view,erroNaoCadastrado.getMessage());
        }
    }
    
    private void realizarEditarProduto(ActionEvent e){
        //variavies 
        Produto produto = dao.pesquisaProdutoPorNome(view.getTextFieldNome().getText());
        String novoNome;
        double novoPreco;
        int novaQuantidade;
        String novaMarca;
        String novoModelo;
        Date novaValidade;
        
        try{
            //verifica se o campo nome foi preenchido
            if(view.getTextFieldNome().getText().isEmpty()) throw new IllegalArgumentException("Favor preencher o campo 'Nome'!");
            
            novoNome = JOptionPane.showInputDialog("Novo nome: ",produto.getNome());
            
            //verifica se o novo nome tá preenchido e se existe algum produto com esse nome já cadastrado
            if(novoNome.isEmpty()) throw new IllegalArgumentException("Favor preencher o campo 'Nome'!");
            if(!produto.getNome().equals(novoNome) && dao.pesquisaProdutoPorNome(novoNome) != null) throw new IllegalArgumentException("Já existe produto cadastrado com esse nome, favor utilizar outro!");
            
            novoPreco = Double.parseDouble(JOptionPane.showInputDialog("Novo preço: ",produto.getPreco()));
            
            //verifica se o novo preço é positivo
            if(novoPreco <= 0.0) throw new IllegalArgumentException("Favor informar preço maior que zero!");
            
            novaQuantidade = Integer.parseInt(JOptionPane.showInputDialog("Nova quantidade: ",produto.getQtdEstoque()));
            
            //verifica se a nova quantidade é negativa
            if(novaQuantidade<0) throw new IllegalArgumentException("Favor informar quantidade positiva!");
            
            if (produto instanceof ProdutoAlimento){
                
                //se for alimento, usa data de validade
                novaValidade = (new SimpleDateFormat("dd/MM/yyyy")).parse(JOptionPane.showInputDialog("Nova data: ",(new SimpleDateFormat("dd/MM/yyyy")).format(((ProdutoAlimento) produto).getDataValidade())));
                dao.alteraProduto(produto, novoNome, novoPreco, novaQuantidade, null, null, novaValidade);
            }else{
                
                //se não for alimento, será eletrônico. Usar marca e modelo
                novaMarca = JOptionPane.showInputDialog("Nova marca: ",((ProdutoEletronico) produto).getMarca());
                novoModelo = JOptionPane.showInputDialog("Novo modelo: ",((ProdutoEletronico) produto).getModelo());
                dao.alteraProduto(produto, novoNome, novoPreco, novaQuantidade, novaMarca, novoModelo, null);
            }
            //limpa os campos pra ficar bonito
            limparCampos();
            
            //exibe mensagem de sucesso
            JOptionPane.showMessageDialog(view,"Produto alterado com sucesso!");
        }
        catch(ParseException | NumberFormatException erroDeDados){
            JOptionPane.showMessageDialog(view, "Favor informar dados válidos!");
        }
        catch(NullPointerException erroNaoCadastrado){
            JOptionPane.showMessageDialog(view,"Produto não cadastrado!");
        }
        catch(IllegalArgumentException erroDuplicata){
            JOptionPane.showMessageDialog(view,erroDuplicata.getMessage());
        }
    }
    
    private void realizarListarProdutosOrdenados(ActionEvent e){
        listarProdutos(dao.ordenaProdutosPorNome());
    }
    
    private void realizarListarProdutosNaoOrdenados(ActionEvent e){
        listarProdutos(dao.getProdutos());
    }
    
    private void listarProdutos(ArrayList<Produto> produtos){
        JTextArea telinha = view.getTextAreaSaida();
        telinha.setText("");
        try{
            if (produtos.isEmpty()) throw new IllegalArgumentException("Não existem produtos cadastrados no momento!");
            
            //perfumarias pra forçar uma impressão bonita no jTextArea
            int i=1;
            telinha.append(String.format("%-3s %-10s %-10s %-11s %-7s %-9s %-9s %-10s\n","Nº:","Nome:","Tipo:","Quantidade:","Preço:","Marca:","Modelo:","Data de Validade:"));
            for (Produto produto : produtos) {
                telinha.append(String.format("%03d %-10s ",i++,produto.getNome()));
                if(produto instanceof ProdutoAlimento){
                    telinha.append(String.format("%-10s %-11d %-7.2f %-9s %-9s ","Alimento",produto.getQtdEstoque(),produto.getPreco(),"",""));
                    telinha.append((new SimpleDateFormat("dd/MM/yyyy")).format(((ProdutoAlimento) produto).getDataValidade()) + "\n");
                }else{
                    telinha.append(String.format("%-10s %-11d %-7.2f ","Eletronico",produto.getQtdEstoque(),produto.getPreco()));
                    telinha.append(String.format("%-9s %-9s\n",((ProdutoEletronico) produto).getMarca(),((ProdutoEletronico) produto).getModelo()));
                }
            } 
        }catch(IllegalArgumentException erroArrayVazio){
            JOptionPane.showMessageDialog(view,erroArrayVazio.getMessage());
        }
    }
    
    //metodo auxiliar pra evitar redundancia na perfumaria do Adicionar, Remover e Editar
    private void limparCampos(){
        view.getTextFieldNome().setText("");
        view.getTextFieldPreco().setText("");
        view.getTextFieldQuantidade().setText("");
        view.getTextFieldMarca().setText("");
        view.getTextFieldModelo().setText("");
        view.getTextFieldDataDeValidade().setText("");
    }
}

