# Sistema Gerenciamento De Produtos

Este é um sistema de gerenciamento de produtos desenvolvido em Java, utilizando conceitos de Programação Orientada a Objetos (POO), como herança, polimorfismo e interfaces. O sistema permite o cadastro, pesquisa, exclusão, alteração e listagem de produtos, com interface gráfica construída com a biblioteca Swing, seguindo o padrão de arquitetura MVC.

## Autores

 - Maik Ramos Maifredo
 - Gabriel Tetzner Menegueti

## Funcionalidades

- **Cadastro de Produtos:** Permite cadastrar produtos do tipo Alimento ou Eletrônico. Produtos Alimento possuem data de validade, e produtos Eletrônico possuem marca e modelo.
- **Pesquisa de Produtos:** Pesquisa produtos pelo nome e exibe as informações.
- **Exclusão de Produtos:** Remove um produto existente da lista.
- **Alteração de Produtos:** Modifica os atributos de produtos já cadastrados.
- **Listagem de Produtos:** Exibe a lista de produtos cadastrados, com opção de ordenação por nome (implementada com a interface `Comparable<Produto>`).
- **Interface Gráfica (GUI):** Desenvolvida com Swing, a interface exibe os dados em um `JTextArea` e recebe entradas através de `JTextField` e `JComboBox`.

## Estrutura do Projeto

O projeto segue o padrão MVC (Model-View-Controller), dividido em pacotes:

### 1. `model`
- **Classe Abstrata Produto:** Classe base com atributos e métodos comuns.
- **ProdutoEletronico e ProdutoAlimento:** Classes que herdam de Produto, com atributos e comportamentos específicos.
- **ProdutoDAO:** Classe responsável por gerenciar os produtos em memória.

### 2. `view`
- **ProdutoView:** Interface gráfica com componentes Swing (`JTextField`, `JTextArea`, `JButton`).

### 3. `controller`
- **ProdutoController:** Controla as ações do usuário, como cadastro, pesquisa, exclusão e alteração.

### 4. `main`
- **Main:** Classe principal que inicializa o sistema.

## Requisitos

- **Java 8 ou superior**
- **Biblioteca Swing**

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/Tetzdesen/Sistema-Gerenciamento-De-Estoque.git
   ```
2. Compile o código:
   ```bash
   javac -d bin src/**/*.java
   ```
3. Execute o sistema:
   ```bash
   java -cp bin main.Main
   ```

## Tratamento de Exceções

O sistema realiza tratamento de exceções para evitar erros durante a manipulação de dados. Exceções comuns, como `NumberFormatException` ao inserir dados inválidos em campos de preço ou quantidade, são tratadas com mensagens de erro claras para o usuário.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

