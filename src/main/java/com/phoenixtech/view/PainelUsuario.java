package com.phoenixtech.view;

import com.phoenixtech.dao.ConexaoBD;
import javax.swing.*;
import java.sql.*;

public class PainelUsuario extends JFrame {
    private Integer usuarioId;

    public PainelUsuario(String usuario) {
        this.usuarioId = obterUsuarioId(usuario);

        setTitle("Painel do Usuario - " + usuario);
        setSize(1000, 600);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new java.awt.BorderLayout());

        JLabel titulo = new JLabel("Bem-vindo(a), " + usuario + "!");
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 32));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 0, 30, 0));

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new javax.swing.BoxLayout(panelBotoes, javax.swing.BoxLayout.Y_AXIS));
        panelBotoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 300, 50, 300));

        JButton btnPostar = new JButton("Nova Postagem");
        btnPostar.setPreferredSize(new java.awt.Dimension(300, 60));
        btnPostar.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18));
        btnPostar.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        btnPostar.addActionListener(e -> new CadastroPostagem(usuario, usuarioId));

        JButton btnListar = new JButton("Ver Postagens");
        btnListar.setPreferredSize(new java.awt.Dimension(300, 60));
        btnListar.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18));
        btnListar.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        btnListar.addActionListener(e -> new ListarPostagens(usuario, usuarioId));

        panelBotoes.add(btnPostar);
        panelBotoes.add(javax.swing.Box.createVerticalStrut(20));
        panelBotoes.add(btnListar);

        add(titulo, java.awt.BorderLayout.NORTH);
        add(panelBotoes, java.awt.BorderLayout.CENTER);
        setVisible(true);
    }

    private Integer obterUsuarioId(String nomeUsuario) {
        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM usuarios WHERE usuario = ?")) {
            ps.setString(1, nomeUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
