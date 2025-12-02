package com.forum.view;

import com.forum.dao.ConexaoBD;
import javax.swing.*;
import java.sql.*;

public class LoginView extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public LoginView() {
        setTitle("Login");
        setSize(360, 200);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel l1 = new JLabel("Usuario:");
        l1.setBounds(20, 20, 100, 20);
        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 20, 200, 20);

        JLabel l2 = new JLabel("Senha:");
        l2.setBounds(20, 60, 100, 20);
        txtSenha = new JPasswordField();
        txtSenha.setBounds(120, 60, 200, 20);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBounds(120, 100, 100, 25);

        JButton btnCadastro = new JButton("Cadastrar");
        btnCadastro.setBounds(230, 100, 90, 25);

        btnLogin.addActionListener(e -> login());
        btnCadastro.addActionListener(e -> new CadastroUsuario());

        add(l1); add(txtUsuario);
        add(l2); add(txtSenha);
        add(btnLogin); add(btnCadastro);

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
                JOptionPane.showMessageDialog(this, "Bem-vindo, " + rs.getString("nome") + "!"); 
                if ("admin".equals(tipo)) {
                    new PainelAdmin(usuario);
                } else {
                    new PainelUsuario(usuario);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos (ou inativo).");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro de conexão: " + ex.getMessage());
        }
    }
}
