package com.phoenixtech.view;

import com.phoenixtech.dao.ConexaoBD;
import javax.swing.*;
import java.sql.*;

public class LoginView extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public LoginView() {
        setTitle("Login - PhoenixTech");
        setSize(1000, 600);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new java.awt.BorderLayout());

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new javax.swing.BoxLayout(panelCentral, javax.swing.BoxLayout.Y_AXIS));
        panelCentral.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 300, 50, 300));

        JLabel titulo = new JLabel("Login");
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 32));
        titulo.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        panelCentral.add(javax.swing.Box.createVerticalStrut(50));
        panelCentral.add(titulo);
        panelCentral.add(javax.swing.Box.createVerticalStrut(30));

        JLabel l1 = new JLabel("Usuario:");
        l1.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16));
        l1.setPreferredSize(new java.awt.Dimension(100, 40));
        txtUsuario = new JTextField(20);
        txtUsuario.setPreferredSize(new java.awt.Dimension(250, 40));
        txtUsuario.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        JPanel panelUsuario = new JPanel();
        panelUsuario.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelUsuario.add(l1);
        panelUsuario.add(txtUsuario);
        panelUsuario.setMaximumSize(new java.awt.Dimension(400, 50));
        panelCentral.add(panelUsuario);
        panelCentral.add(javax.swing.Box.createVerticalStrut(15));

        JLabel l2 = new JLabel("Senha:");
        l2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16));
        l2.setPreferredSize(new java.awt.Dimension(100, 40));
        txtSenha = new JPasswordField(20);
        txtSenha.setPreferredSize(new java.awt.Dimension(250, 40));
        txtSenha.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        JPanel panelSenha = new JPanel();
        panelSenha.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelSenha.add(l2);
        panelSenha.add(txtSenha);
        panelSenha.setMaximumSize(new java.awt.Dimension(400, 50));
        panelCentral.add(panelSenha);
        panelCentral.add(javax.swing.Box.createVerticalStrut(30));

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 0));
        JButton btnLogin = new JButton("Entrar");
        btnLogin.setPreferredSize(new java.awt.Dimension(120, 40));
        btnLogin.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        JButton btnCadastro = new JButton("Cadastrar");
        btnCadastro.setPreferredSize(new java.awt.Dimension(120, 40));
        btnCadastro.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        panelBotoes.add(btnLogin);
        panelBotoes.add(btnCadastro);
        panelBotoes.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        panelCentral.add(panelBotoes);

        btnLogin.addActionListener(e -> login());
        btnCadastro.addActionListener(e -> new CadastroUsuario());

        add(panelCentral, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void login() {
        try (Connection con = ConexaoBD.obterConexao()) {
            String sql = "SELECT * FROM usuarios WHERE usuario=? AND senha=? AND ativo=1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtUsuario.getText().trim());
            ps.setString(2, new String(txtSenha.getPassword()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                String usuario = rs.getString("usuario");
                JOptionPane.showMessageDialog(this, "Bem-vindo(a), " + rs.getString("nome") + "!"); 
                if ("admin".equals(tipo)) {
                    new PainelAdmin(usuario);
                } else {
                    new PainelUsuario(usuario);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario ou senha invalidos (ou inativo).");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro de conexão: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> new LoginView());
}
}
