package com.phoenixtech.view;

import com.phoenixtech.dao.ConexaoBD;
import javax.swing.*;
import java.sql.*;

public class CadastroPostagem extends JFrame {
    JTextField txtAutor, txtTitulo;
    JTextArea txtTexto;
    JComboBox<String> cboCategoria;

    public CadastroPostagem(String autor) {
        setTitle("Nova Postagem");
        setSize(1000, 700);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new java.awt.BorderLayout());

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new javax.swing.BoxLayout(panelFormulario, javax.swing.BoxLayout.Y_AXIS));
        panelFormulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 250, 30, 250));

        JLabel titulo = new JLabel("Nova Postagem");
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 28));
        titulo.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        panelFormulario.add(titulo);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(30));

        JLabel l1 = new JLabel("Autor:");
        l1.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l1.setPreferredSize(new java.awt.Dimension(100, 35));
        txtAutor = new JTextField(autor);
        txtAutor.setPreferredSize(new java.awt.Dimension(350, 35));
        txtAutor.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        txtAutor.setEnabled(false);
        JPanel panelAutor = new JPanel();
        panelAutor.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelAutor.add(l1);
        panelAutor.add(txtAutor);
        panelAutor.setMaximumSize(new java.awt.Dimension(500, 50));
        panelFormulario.add(panelAutor);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(15));

        JLabel l2 = new JLabel("Titulo:");
        l2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l2.setPreferredSize(new java.awt.Dimension(100, 35));
        txtTitulo = new JTextField();
        txtTitulo.setPreferredSize(new java.awt.Dimension(350, 35));
        txtTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelTitulo.add(l2);
        panelTitulo.add(txtTitulo);
        panelTitulo.setMaximumSize(new java.awt.Dimension(500, 50));
        panelFormulario.add(panelTitulo);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(15));

        JLabel l3 = new JLabel("Texto:");
        l3.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l3.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        panelFormulario.add(l3);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(10));
        txtTexto = new JTextArea();
        txtTexto.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        txtTexto.setLineWrap(true);
        txtTexto.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(txtTexto);
        sp.setMaximumSize(new java.awt.Dimension(500, 150));
        panelFormulario.add(sp);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(15));

        JLabel l4 = new JLabel("Categoria:");
        l4.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l4.setPreferredSize(new java.awt.Dimension(100, 35));
        cboCategoria = new JComboBox<>();
        cboCategoria.setPreferredSize(new java.awt.Dimension(350, 35));
        cboCategoria.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        carregarInteresses();
        JPanel panelCategoria = new JPanel();
        panelCategoria.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelCategoria.add(l4);
        panelCategoria.add(cboCategoria);
        panelCategoria.setMaximumSize(new java.awt.Dimension(500, 50));
        panelFormulario.add(panelCategoria);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(30));

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new java.awt.Dimension(150, 40));
        btnSalvar.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        btnSalvar.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        btnSalvar.addActionListener(e -> salvar());
        panelFormulario.add(btnSalvar);

        JScrollPane scrollPane = new JScrollPane(panelFormulario);
        add(scrollPane, java.awt.BorderLayout.CENTER);

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
