package com.forum.view;

import javax.swing.*;

public class PainelUsuario extends JFrame {

    public PainelUsuario(String usuario) {
        setTitle("Bem-vindo " + usuario);
        setSize(360, 180);
        setLocationRelativeTo(null);

        JButton btnPostar = new JButton("Nova Postagem");
        btnPostar.addActionListener(e -> new CadastroPostagem(usuario));

        JButton btnListar = new JButton("Ver Postagens");
        btnListar.addActionListener(e -> new ListarPostagens());

        JPanel p = new JPanel();
        p.add(btnPostar);
        p.add(btnListar);

        add(p);
        setVisible(true);
    }
}
