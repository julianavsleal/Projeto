package com.phoenixtech.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoBD {

    private static String host = "localhost";
    private static String porta = "3306";
    private static String db = "phoenixtech";
    private static String usuario = "root";
    private static String senha = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do MySQL não encontrado!");
            e.printStackTrace();
        }
    }

    public static Connection obterConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.err.println("Erro: Driver MySQL não encontrado!");
                System.err.println("Certifique-se de que mysql-connector-java-*.jar está no classpath");
                throw new SQLException("Driver MySQL não disponível", ex);
            }
        }

        String url = "jdbc:mysql://127.0.0.1:3306/phoenixtech?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        return DriverManager.getConnection(url, usuario, senha);
    }
}