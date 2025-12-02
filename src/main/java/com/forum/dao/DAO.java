package com.forum.dao;


import com.forum.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Classe DAO (Data Access Object) responsável por acessar o banco de dados e
 * realizar operações relacionadas ao Usuário. Segue o padrão DAO para separar a
 * lógica de acesso a dados da lógica de negócio.
 */
public class DAO {

    /**
     * Verifica se um usuário existe no banco de dados com o nome e senha
     * fornecidos. Utiliza PreparedStatement para evitar SQL Injection.
     *
     * @param usuario - objeto Usuario contendo nome e senha a serem verificados
     * @return boolean - true se o usuário existe, false caso contrário
     * @throws Exception - se houver erro de conexão ou execução da query
     */
    public boolean existe(Usuario usuario) throws Exception {
        // Query SQL otimizada: retorna apenas 1 se encontrar correspondência (mais eficiente que SELECT *)
        String sql = "SELECT 1 FROM usuario WHERE nome = ? AND senha = ? LIMIT 1";

        // Try-with-resources: fecha automaticamente Connection e PreparedStatement
        try (Connection conn = ConexaoBD.obterConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Define os parâmetros da query (substitui os "?" pelos valores reais)
            ps.setString(1, usuario.getNome());  // Primeiro parâmetro: nome do usuário
            ps.setString(2, usuario.getSenha()); // Segundo parâmetro: senha do usuário

            // Executa a query e obtém o resultado
            try (ResultSet rs = ps.executeQuery()) {
                // rs.next() retorna true se encontrou pelo menos um registro, false caso contrário
                return rs.next();
            }
        }
    }
}