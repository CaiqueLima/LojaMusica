
package br.com.persistencia;

import br.com.modelo.Endereco;
import br.com.persistencia.dao.EnderecoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class EnderecoDAOJDBC implements EnderecoDAO{

    public static final String INSERT = "insert into endereco (estado , cidade, bairro, rua, complemento, numero) values (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "update endereco set estado = ?, cidade = ?, bairro = ?, rua = ?, complemento = ?,  numero = ? where codigo = ?;";
   
   
    public int salvar(Endereco e) {
        if(e.getCodigo() == 0){
            return inserir(e);
        }else{
            return update(e);
        }  
    }
    
    private int inserir (Endereco e){
        int status = -1;
        Connection con = null;
        PreparedStatement pstm = null;
        
        try{
           con = ConnectionFactory.getConnection();
           pstm = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
           
           pstm.setString(1, e.getEstado());
           pstm.setString(2, e.getCidade());
           pstm.setString(3, e.getBairro());
           pstm.setString(4, e.getRua());
           pstm.setString(5, e.getComplemento());
           pstm.setInt(6, e.getNumero());
           pstm.execute();
           
           try(ResultSet rs = pstm.getGeneratedKeys()){
               rs.next();
               status = rs.getInt(1);
           }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Erro ao inserir endereço: " + ex.getMessage());
        } finally{
            try{
                ConnectionFactory.closeConnection(con, pstm);
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + ex.getMessage());
            }
        }
        
        return status;
    }
    
     public int update(Endereco e) {
        Connection con = null;
        PreparedStatement pstm = null;
        int retorno = -1;
        
        try {
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(UPDATE);

            pstm.setString(1, e.getEstado());
            pstm.setString(2, e.getCidade());
            pstm.setString(3, e.getBairro());
            pstm.setString(4, e.getRua());
            pstm.setString(5, e.getComplemento());
            pstm.setInt(6, e.getNumero());
            pstm.setInt(7, e.getCodigo());

            pstm.execute();
            retorno = e.getCodigo();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar endereço do funcionario: " + ex.getMessage());
        } finally {
            try {
                ConnectionFactory.closeConnection(con, pstm);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + ex.getMessage());
            }
        }
        return retorno;
    }
}
