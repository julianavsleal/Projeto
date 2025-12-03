package com.phoenixtech.view;

import com.phoenixtech.dao.ConexaoBD;
import com.phoenixtech.dao.PostagemDAO;
import com.phoenixtech.model.Postagem;
import javax.swing.*;
import java.sql.*;

public class CadastroPostagem extends JFrame {
    JTextField txtAutor, txtTitulo;
    JTextArea txtTexto;
    JComboBox<String> cboCategoria;
    private Postagem postagemEditando;
    private ListarPostagens parentView;
    private Integer usuarioId;

    public CadastroPostagem(String autor, Integer usuarioId) {
        this(autor, null, null, usuarioId);
    }

    public CadastroPostagem(String autor, Postagem postagem, ListarPostagens parentView, Integer usuarioId) {
        this.postagemEditando = postagem;
        this.parentView = parentView;
        this.usuarioId = usuarioId;
        setTitle(postagem != null ? "Editar Postagem" : "Nova Postagem");
        setSize(1000, 700);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new java.awt.BorderLayout());

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new javax.swing.BoxLayout(panelFormulario, javax.swing.BoxLayout.Y_AXIS));
        panelFormulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 250, 30, 250));

        JLabel titulo = new JLabel(postagem != null ? "Editar Postagem" : "Nova Postagem");
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
        if (postagem != null) txtTitulo.setText(postagem.getTitulo());
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
        if (postagem != null) txtTexto.setText(postagem.getTexto());
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
        if (postagem != null) cboCategoria.setSelectedItem(postagem.getCategoria());
        JPanel panelCategoria = new JPanel();
        panelCategoria.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelCategoria.add(l4);
        panelCategoria.add(cboCategoria);
        panelCategoria.setMaximumSize(new java.awt.Dimension(500, 50));
        panelFormulario.add(panelCategoria);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(30));

        JButton btnSalvar = new JButton(postagem != null ? "Atualizar" : "Salvar");
        btnSalvar.setPreferredSize(new java.awt.Dimension(150, 40));
        btnSalvar.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        btnSalvar.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        btnSalvar.addActionListener(e -> salvar());
        panelFormulario.add(btnSalvar);

        JScrollPane scrollPane = new JScrollPane(panelFormulario);
        add(scrollPane, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        try {
            PostagemDAO dao = new PostagemDAO();
            if (postagemEditando != null) {
                postagemEditando.setTitulo(txtTitulo.getText().trim());
                postagemEditando.setTexto(txtTexto.getText().trim());
                postagemEditando.setCategoria(cboCategoria.getSelectedItem().toString());
                dao.atualizar(postagemEditando);
                JOptionPane.showMessageDialog(this, "Postagem atualizada!");
            } else {
                if (usuarioId == null || usuarioId <= 0) {
                    JOptionPane.showMessageDialog(this, "Erro: Usuário não identificado.");
                    return;
                }
                Postagem novaPostagem = new Postagem();
                novaPostagem.setAutorId(usuarioId);
                novaPostagem.setTitulo(txtTitulo.getText().trim());
                novaPostagem.setTexto(txtTexto.getText().trim());
                novaPostagem.setCategoria(cboCategoria.getSelectedItem().toString());
                dao.inserir(novaPostagem);
                JOptionPane.showMessageDialog(this, "Postagem criada!");
            }
            if (parentView != null) {
                parentView.carregarPostagens();
            }
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }
}
