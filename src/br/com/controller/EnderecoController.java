
package br.com.controller;

import br.com.modelo.Endereco;
import br.com.persistencia.EnderecoDAOJDBC;
import br.com.persistencia.dao.EnderecoDAO;

public class EnderecoController {
      
    public int salvar(Endereco e){
        EnderecoDAO dao = new EnderecoDAOJDBC();
        return dao.salvar(e);
    }
}
