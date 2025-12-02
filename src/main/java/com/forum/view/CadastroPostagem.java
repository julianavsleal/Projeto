package com.forum.view;

import com.forum.dao.ConexaoBD;
import javax.swing.*;
import java.sql.*;

public class CadastroPostagem extends JFrame {
    JTextField txtAutor, txtTitulo;
    JTextArea txtTexto;
    JComboBox<String> cboCategoria;

    public CadastroPostagem(String autor) {
        setTitle("Nova Postagem");
        setSize(480, 420);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel l1 = new JLabel("Autor:");
        l1.setBounds(20, 20, 100, 20);
        txtAutor = new JTextField(autor);
        txtAutor.setBounds(140, 20, 300, 20);
        txtAutor.setEnabled(false);

        JLabel l2 = new JLabel("Titulo:");
        l2.setBounds(20, 60, 100, 20);
        txtTitulo = new JTextField();
        txtTitulo.setBounds(140, 60, 300, 20);

        JLabel l3 = new JLabel("Texto:");
        l3.setBounds(20, 100, 100, 20);
        txtTexto = new JTextArea();
        JScrollPane sp = new JScrollPane(txtTexto);
        sp.setBounds(140, 100, 300, 180);

        JLabel l4 = new JLabel("Categoria:");
        l4.setBounds(20, 300, 100, 20);
        cboCategoria = new JComboBox<>();
        cboCategoria.setBounds(140, 300, 300, 20);
        carregarInteresses();

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(200, 340, 100, 25);
        btnSalvar.addActionListener(e -> salvar());

        add(l1); add(txtAutor);
        add(l2); add(txtTitulo);
        add(l3); add(sp);
        add(l4); add(cboCategoria);
        add(btnSalvar);

        setVisible(true);
    }

    void carregarInteresses() {
        try (Connection con = ConexaoBD.obterConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT nome FROM interesses")) {

            while (rs.next()) cboCategoria.addItem(rs.getString(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void salvar() {
        try (Connection con = ConexaoBD.obterConexao()) {
            String sql = "INSERT INTO postagens (autor, titulo, texto, categoria) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtAutor.getText().trim());
            ps.setString(2, txtTitulo.getText().trim());
            ps.setString(3, txtTexto.getText().trim());
            ps.setString(4, cboCategoria.getSelectedItem().toString());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Postagem criada!");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }
}
