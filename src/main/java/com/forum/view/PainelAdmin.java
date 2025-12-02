package com.forum.view;

import com.forum.dao.ConexaoBD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class PainelAdmin extends JFrame {

    JTable tabela;
    DefaultTableModel model;
    String usuarioLogado;

    public PainelAdmin(String usuario) {
        this.usuarioLogado = usuario;
        setTitle("Admin - Gestao de Usuarios");
        setSize(700, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID","Usuario","Nome","Ativo"}, 0);
        tabela = new JTable(model);

        carregarUsuarios();

        JButton btnInativar = new JButton("Inativar");
        btnInativar.addActionListener(e -> inativar());

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluir());

        JButton btnNovo = new JButton("Novo Usuario");
        btnNovo.addActionListener(e -> new CadastroUsuario(this));

        // Botões para gerenciar postagens (mesma funcionalidade do PainelUsuario)
        JButton btnPostar = new JButton("Nova Postagem");
        btnPostar.addActionListener(e -> new CadastroPostagem(usuarioLogado));

        JButton btnListar = new JButton("Ver Postagens");
        btnListar.addActionListener(e -> new ListarPostagens());

        JPanel p = new JPanel();
        p.add(btnInativar);
        p.add(btnExcluir);
        p.add(btnNovo);
        p.add(btnPostar);
        p.add(btnListar);

        add(new JScrollPane(tabela), "Center");
        add(p, "South");

        setVisible(true);
    }

    void carregarUsuarios() {
        model.setRowCount(0);
        try (Connection con = ConexaoBD.obterConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, usuario, nome, ativo FROM usuarios")) {

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void inativar() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário.");
            return;
        }
        int id = (int) tabela.getValueAt(linha, 0);
        try (Connection con = ConexaoBD.obterConexao()) {
            PreparedStatement ps = con.prepareStatement("UPDATE usuarios SET ativo=0 WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Usuário inativado.");
            carregarUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    void excluir() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário.");
            return;
        }
        int id = (int) tabela.getValueAt(linha, 0);
        int op = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o usuário?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op != JOptionPane.YES_OPTION) return;
        try (Connection con = ConexaoBD.obterConexao()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM usuarios WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Usuário excluído.");
            carregarUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
