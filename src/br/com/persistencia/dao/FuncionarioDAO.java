package br.com.persistencia.dao;

import br.com.modelo.Funcionario;
import java.util.List;

public interface FuncionarioDAO {

        int salve(Funcionario f);
        boolean remove(int codigo);
        List<Funcionario> listAll();
        Funcionario listById(int codigo);
        List<Funcionario> listByNome(String nome);
        boolean autenticarLogin(String login, String senha);
}
