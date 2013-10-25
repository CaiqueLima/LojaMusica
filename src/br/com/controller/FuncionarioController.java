package br.com.controller;

import br.com.modelo.Funcionario;
import br.com.persistencia.FuncionarioDAOJDBC;
import br.com.persistencia.dao.FuncionarioDAO;
import java.util.List;

public class FuncionarioController {

    public int salvar(Funcionario f) {
        FuncionarioDAO dao = new FuncionarioDAOJDBC();
        return dao.salve(f);
    }

    public List<Funcionario> listarTodos() {
        FuncionarioDAO dao = new FuncionarioDAOJDBC();
        return dao.listAll();
    }

    public List<Funcionario> listByNome(String nome) {
        FuncionarioDAO dao = new FuncionarioDAOJDBC();
        return dao.listByNome(nome);
    }

    public Funcionario listById(int codigo) {
        FuncionarioDAO dao = new FuncionarioDAOJDBC();
        return dao.listById(codigo);
    }

    public boolean remove(int id) {
        FuncionarioDAO dao = new FuncionarioDAOJDBC();
        return dao.remove(id);
    }
    
    public boolean autenticarLogin(String login, String senha){
        FuncionarioDAO dao = new FuncionarioDAOJDBC();
        return dao.autenticarLogin(login, senha);
    }
}
