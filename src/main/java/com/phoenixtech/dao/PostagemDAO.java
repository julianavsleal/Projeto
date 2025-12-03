package com.phoenixtech.dao;

import com.phoenixtech.model.Postagem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostagemDAO {

    public void inserir(Postagem postagem) throws Exception {
        String sql = "INSERT INTO postagens (autor_id, titulo, texto, categoria) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.obterConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postagem.getAutorId() != null ? postagem.getAutorId() : 0);
            ps.setString(2, postagem.getTitulo());
            ps.setString(3, postagem.getTexto());
            ps.setString(4, postagem.getCategoria());
            ps.executeUpdate();
        }
    }

    public void atualizar(Postagem postagem) throws Exception {
        String sql = "UPDATE postagens SET titulo = ?, texto = ?, categoria = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.obterConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, postagem.getTitulo());
            ps.setString(2, postagem.getTexto());
            ps.setString(3, postagem.getCategoria());
            ps.setInt(4, postagem.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM postagens WHERE id = ?";
        try (Connection conn = ConexaoBD.obterConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Postagem obterPorId(int id) throws Exception {
        String sql = "SELECT id, autor_id, titulo, texto, categoria, criado_em FROM postagens WHERE id = ?";
        try (Connection conn = ConexaoBD.obterConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPostagem(rs);
                }
            }
        }
        return null;
    }

    public List<Postagem> listarTodas() throws Exception {
        List<Postagem> postagens = new ArrayList<>();
        String sql = "SELECT id, autor_id, titulo, texto, categoria, criado_em FROM postagens ORDER BY titulo ASC";
        try (Connection conn = ConexaoBD.obterConexao(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                postagens.add(mapearPostagem(rs));
            }
        }
        return postagens;
    }

    public List<Postagem> listarPorAutor(int autorId) throws Exception {
        List<Postagem> postagens = new ArrayList<>();
        String sql = "SELECT id, autor_id, titulo, texto, categoria, criado_em FROM postagens WHERE autor_id = ? ORDER BY titulo ASC";
        try (Connection conn = ConexaoBD.obterConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, autorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    postagens.add(mapearPostagem(rs));
                }
            }
        }
        return postagens;
    }

    private Postagem mapearPostagem(ResultSet rs) throws SQLException {
        Postagem p = new Postagem();
        p.setId(rs.getInt("id"));
        p.setAutorId(rs.getInt("autor_id"));
        p.setTitulo(rs.getString("titulo"));
        p.setTexto(rs.getString("texto"));
        p.setCategoria(rs.getString("categoria"));
        p.setCriadoEm(rs.getTimestamp("criado_em"));
        return p;
    }
}
