package com.phoenixtech.dao;


import com.phoenixtech.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO {

    public boolean existe(Usuario usuario) throws Exception {
        String sql = "SELECT 1 FROM usuario WHERE nome = ? AND senha = ? LIMIT 1";

        try (Connection conn = ConexaoBD.obterConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getSenha());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}