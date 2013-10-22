
package br.com.modelo;

public class EntradaSaida_produtos extends Estoque {
    
    private int qtde;
    private int id_produto;
    private String tipo;

    public EntradaSaida_produtos() {
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
