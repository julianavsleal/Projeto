package com.phoenixtech.view;

import javax.swing.*;

public class PainelUsuario extends JFrame {

    public PainelUsuario(String usuario) {
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
        btnPostar.addActionListener(e -> new CadastroPostagem(usuario));

        JButton btnListar = new JButton("Ver Postagens");
        btnListar.setPreferredSize(new java.awt.Dimension(300, 60));
        btnListar.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18));
        btnListar.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        btnListar.addActionListener(e -> new ListarPostagens());

        panelBotoes.add(btnPostar);
        panelBotoes.add(javax.swing.Box.createVerticalStrut(20));
        panelBotoes.add(btnListar);

        add(titulo, java.awt.BorderLayout.NORTH);
        add(panelBotoes, java.awt.BorderLayout.CENTER);
        setVisible(true);
    }
}
