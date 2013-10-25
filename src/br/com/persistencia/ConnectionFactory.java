package br.com.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


    public class ConnectionFactory {
        //String de conexão do mySQL

        private static final String STR_CONEXAO = "jdbc:mysql://localhost:3306/";
        //Nome da base de dados criada no MySQL
        private static final String DATABASE = "lojamusica";
        //Usuário do MySQL
        private static final String USER = "root";
        //Senha do MySQL
        private static final String PASSWORD = "";

        //Método que retorna a conexão
        public static Connection getConnection()
                throws SQLException {
            try {
                return DriverManager.getConnection(STR_CONEXAO + DATABASE, USER, PASSWORD);
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        //Método que fecha a conexão

        public static void closeConnection(Connection con)
                throws SQLException {
            if (con != null) {
                con.close();
            }
        }

        //Método que fecha o Statement
        public static void closeConnection(Connection con, Statement stmt)
                throws SQLException {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection(con);
        }

        //Método que fecha o ResultSet
        public static void closeConnection(Connection con, Statement stmt,
                ResultSet rs)
                throws SQLException {
            if (rs != null) {
                rs.close();
            }
            closeConnection(con, stmt);
        }
    }

