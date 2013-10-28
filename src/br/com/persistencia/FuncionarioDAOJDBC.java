package br.com.persistencia;

import br.com.modelo.Endereco;
import br.com.modelo.Funcionario;
import br.com.persistencia.dao.FuncionarioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class FuncionarioDAOJDBC implements FuncionarioDAO {

    private static final String INSERT = "insert into funcionario (nome, idade, sexo, data_nascimento, rg, cpf, telefone, "
            + "endereco_codigo, salario, cargo, login, senha) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String LIST = "select * from funcionario;";
    private static final String REMOVE = "delete from funcionario where codigo = ?;";
    private static final String UPDATE = "update funcionario set nome = ?, idade = ?, sexo = ?, data_nascimento = ?, rg = ?,  cpf = ?, telefone = ?, salario = ?, cargo = ?, login = ?, senha = ? where codigo = ?;";
    private static final String LISTBYID = "select * from funcionario,endereco where funcionario.endereco_codigo = endereco.codigo and funcionario.codigo = ?;";
    private static final String LISTBYNOME = "select * from funcionario where nome like ?;";
    private static final String VALIDALOGIN = "select login, senha from funcionario where login = ? and senha = ?;";

    public int salve(Funcionario f) {
        if (f.getCodigo() == 0) {
            return insert(f);
        } else {
            return update(f);
        }
    }

    private int insert(Funcionario f) {
        Connection con = null;
        PreparedStatement pstm = null;
        int retorno = -1;
        try {
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);


            pstm.setString(1, f.getNome());
            pstm.setInt(2, f.getIdade());
            pstm.setString(3, f.getSexo());
            pstm.setDate(4, new java.sql.Date(f.getDataNascimento().getTime()));
            pstm.setString(5, f.getRg());
            pstm.setString(6, f.getCpf());
            pstm.setString(7, f.getTelefone());
            pstm.setInt(8, f.getEndereco().getCodigo());
            pstm.setDouble(9, f.getSalario());
            pstm.setString(10, f.getCargo());
            pstm.setString(11, f.getLogin());
            pstm.setString(12, f.getSenha());

            pstm.execute();

            try (ResultSet rs =
                    pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    retorno = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir: " + e);
        } finally {
            try {
                ConnectionFactory.closeConnection(con, pstm);
            } catch (SQLException ex) {
            }
            return retorno;
        }
    }

    public boolean remove(int codigo) {

        boolean status = false;
        Connection con = null;
        PreparedStatement pstm = null;

        try {
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(REMOVE);
            pstm.setInt(1, codigo);
            pstm.execute();
            status = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir Funcionario: " + e.getMessage());
        } finally {
            try {
                ConnectionFactory.closeConnection(con, pstm);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return status;
    }

    public List<Funcionario> listAll() {

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(LIST);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Funcionario f = new Funcionario();

                f.setCodigo(rs.getInt("codigo"));
                f.setNome(rs.getString("nome"));
                f.setCpf(rs.getString("cpf"));
                f.setIdade(rs.getInt("idade"));
                f.setCargo(rs.getString("cargo"));

                funcionarios.add(f);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar os funcionarios: " + e.getMessage());
        } finally {
            try {
                ConnectionFactory.closeConnection(con, pstm, rs);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return funcionarios;
    }

    public Funcionario listById(int codigo) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Funcionario f = new Funcionario();

        try {
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(LISTBYID);
            pstm.setInt(1, codigo);
            rs = pstm.executeQuery();

            while (rs.next()) {

                f.setCodigo(rs.getInt("codigo"));
                f.setNome(rs.getString("nome"));
                f.setIdade(rs.getInt("idade"));
                f.setSexo(rs.getString("sexo"));
                f.setDataNascimento(rs.getDate("data_nascimento"));
                f.setRg(rs.getString("rg"));
                f.setCpf(rs.getString("cpf"));
                f.setTelefone(rs.getString("telefone"));

                Endereco e = new Endereco();
                e.setEstado(rs.getString("endereco.estado"));
                e.setCidade(rs.getString("endereco.cidade"));
                e.setBairro(rs.getString("endereco.bairro"));
                e.setRua(rs.getString("endereco.rua"));
                e.setComplemento(rs.getString("endereco.complemento"));
                e.setNumero(rs.getInt("endereco.numero"));
                e.setCodigo(rs.getInt("endereco.codigo"));
                f.setEndereco(e);

                f.setSalario(rs.getDouble("salario"));
                f.setCargo(rs.getString("cargo"));
                f.setLogin(rs.getString("login"));
                f.setSenha(rs.getString("senha"));

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar funcionário: " + ex.getMessage());
        } finally {
            try {
                ConnectionFactory.closeConnection(con, pstm, rs);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + ex.getMessage());
            }
        }

        return f;
    }

    public int update(Funcionario f) {
        Connection con = null;
        PreparedStatement pstm = null;
        int retorno = -1;
        try {
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(UPDATE);

            pstm.setString(1, f.getNome());
            pstm.setInt(2, f.getIdade());
            pstm.setString(3, f.getSexo());
            pstm.setDate(4, new java.sql.Date(f.getDataNascimento().getTime()));
            pstm.setString(5, f.getRg());
            pstm.setString(6, f.getCpf());
            pstm.setString(7, f.getTelefone());
            pstm.setDouble(8, f.getSalario());
            pstm.setString(9, f.getCargo());
            pstm.setString(10, f.getLogin());
            pstm.setString(11, f.getSenha());
            pstm.setInt(12, f.getCodigo());

            pstm.execute();
            retorno = f.getCodigo();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar os dados do funcionario: " + ex.getMessage());
        } finally {
            try {
                ConnectionFactory.closeConnection(con, pstm);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + ex.getMessage());
            }
        }
        return retorno;
    }

    public List<Funcionario> listByNome(String nome) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(LISTBYNOME);
            pstm.setString(1, "%" + nome + "%");
            rs = pstm.executeQuery();

            while (rs.next()) {
                Funcionario f = new Funcionario();

                f.setCodigo(rs.getInt("codigo"));
                f.setNome(rs.getString("nome"));
                //f.setIdade(rs.getInt("idade"));
               // f.setSexo(rs.getString("sexo"));
                //f.setDataNascimento(rs.getDate("data_nascimento"));
                //f.setRg(rs.getString("rg"));
                f.setCpf(rs.getString("cpf"));
                //f.setTelefone(rs.getString("telefone"));

                /*Endereco e = new Endereco();
                e.setEstado(rs.getString("endereco.estado"));
                e.setCidade(rs.getString("endereco.cidade"));
                e.setBairro(rs.getString("endereco.bairro"));
                e.setRua(rs.getString("endereco.rua"));
                e.setComplemento(rs.getString("endereco.complemento"));
                e.setNumero(rs.getInt("endereco.numero"));
                f.setEndereco(e);*/

                //f.setSalario(rs.getDouble("salario"));
                f.setCargo(rs.getString("cargo"));
                //f.setLogin(rs.getString("login"));
                //f.setSenha(rs.getString("senha"));

                funcionarios.add(f);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquinar funcionario: " + ex.getMessage());
        } finally {
            try {
                ConnectionFactory.closeConnection(con, pstm, rs);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + ex.getMessage());
            }
        }
        return funcionarios;
    }

    public boolean autenticarLogin(String login, String senha) {

        boolean valida = false;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            con = ConnectionFactory.getConnection();
            pstm = con.prepareStatement(VALIDALOGIN);

            pstm.setString(1, login);
            pstm.setString(2, senha);

            rs = pstm.executeQuery();

            while (rs.next()) {
                return true;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao autentificar login: " + ex.getMessage());
        } finally {
            try {
                ConnectionFactory.closeConnection(con, pstm, rs);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: ");
            }
        }
        return valida;
    }
}
