package br.com.modelo;

import java.util.Date;

public class Estoque {

    private int codigo;
    private String nome_produto;
    private String marca;
    private String validade;
    private String qtde_estoque;
    private Date data_cadastro;

    public Estoque() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    
    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    
    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    
    public String getQtde_estoque() {
        return qtde_estoque;
    }

    public void setQtde_estoque(String qtde_estoque) {
        this.qtde_estoque = qtde_estoque;
    }

    
    public Date getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }
}
