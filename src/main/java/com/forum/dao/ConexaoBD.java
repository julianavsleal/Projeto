package com.forum.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados MySQL. Contém
 * as configurações de host, porta, banco, usuário e senha. Utiliza o driver
 * JDBC para estabelecer a conexão.
 */
public class ConexaoBD {

    // Endereço do servidor de banco de dados (localhost para servidor local)
    private static String host = "localhost";
    // Porta padrão do MySQL
    private static String porta = "3306";
    // Nome do banco de dados a ser utilizado
    private static String db = "phoenixtech";
    // Nome do usuário do banco de dados
    private static String usuario = "root";
    // Senha do usuário do banco de dados
    private static String senha = "root";

    // Bloco estático para carregar o driver JDBC do MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do MySQL não encontrado!");
            e.printStackTrace();
        }
    }

    /**
     * Método estático para obter uma conexão com o banco de dados MySQL. Lança
     * exceção SQLException em caso de falha na conexão.
     *
     * Parâmetros da URL: - useSSL=false: Desabilita SSL para conexões locais -
     * allowPublicKeyRetrieval=true: Permite recuperação de chave pública (MySQL
     * 8+) - serverTimezone=UTC: Define fuso horário UTC para evitar problemas
     * de timestamp
     *
     * @return Connection - objeto de conexão com o banco de dados
     * @throws SQLException - se houver erro ao conectar ao banco
     */
    public static Connection obterConexao() throws SQLException {
        // Tenta carregar o driver MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Se não encontrar, tenta a versão legada
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.err.println("Erro: Driver MySQL não encontrado!");
                System.err.println("Certifique-se de que mysql-connector-java-*.jar está no classpath");
                throw new SQLException("Driver MySQL não disponível", ex);
            }
        }
        
        // Monta a URL de conexão JDBC com os parâmetros necessários
        String url = "jdbc:mysql://127.0.0.1:3306/forumdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        // Retorna a conexão estabelecida com o banco de dados
        return DriverManager.getConnection(url, usuario, senha);
    }
}