package com.forum.view;

import com.forum.dao.ConexaoBD;
import javax.swing.*;
import java.sql.*;

public class CadastroUsuario extends JFrame {
    JTextField txtNome, txtIdade, txtUsuario;
    JPasswordField txtSenha;
    JComboBox<String> cboTipo, cboInteresse1, cboInteresse2;
    PainelAdmin parent;

    public CadastroUsuario() {
        this(null);
    }

    public CadastroUsuario(PainelAdmin parent) {
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setTitle("Cadastro");
        setSize(420, 380);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel l1 = new JLabel("Nome:");
        l1.setBounds(20, 20, 100, 20);
        txtNome = new JTextField();
        txtNome.setBounds(140, 20, 220, 20);

        JLabel l2 = new JLabel("Idade:");
        l2.setBounds(20, 60, 100, 20);
        txtIdade = new JTextField();
        txtIdade.setBounds(140, 60, 220, 20);

        JLabel l3 = new JLabel("Usuario:");
        l3.setBounds(20, 100, 100, 20);
        txtUsuario = new JTextField();
        txtUsuario.setBounds(140, 100, 220, 20);

        JLabel l4 = new JLabel("Senha:");
        l4.setBounds(20, 140, 100, 20);
        txtSenha = new JPasswordField();
        txtSenha.setBounds(140, 140, 220, 20);

        JLabel l5 = new JLabel("Tipo:");
        l5.setBounds(20, 180, 100, 20);
        cboTipo = new JComboBox<>(new String[]{"usuario", "admin"});
        cboTipo.setBounds(140, 180, 220, 20);

        JLabel l6 = new JLabel("Interesse 1:");
        l6.setBounds(20, 220, 100, 20);
        cboInteresse1 = new JComboBox<>();
        cboInteresse1.setBounds(140, 220, 220, 20);

        JLabel l7 = new JLabel("Interesse 2:");
        l7.setBounds(20, 260, 100, 20);
        cboInteresse2 = new JComboBox<>();
        cboInteresse2.setBounds(140, 260, 220, 20);

        carregarInteresses();

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(160, 300, 120, 25);
        btnCadastrar.addActionListener(e -> cadastrar());

        add(l1); add(txtNome);
        add(l2); add(txtIdade);
        add(l3); add(txtUsuario);
        add(l4); add(txtSenha);
        add(l5); add(cboTipo);
        add(l6); add(cboInteresse1);
        add(l7); add(cboInteresse2);
        add(btnCadastrar);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    void carregarInteresses() {
        try (Connection con = ConexaoBD.obterConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT nome FROM interesses")) {

            while (rs.next()) {
                String nome = rs.getString(1);
                cboInteresse1.addItem(nome);
                cboInteresse2.addItem(nome);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar interesses: " + e.getMessage());
        }
    }

    void cadastrar() {
        try (Connection con = ConexaoBD.obterConexao()) {
            // Validações
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome é obrigatório.");
                return;
            }
            if (txtIdade.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Idade é obrigatória.");
                return;
            }
            if (txtUsuario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Usuário é obrigatório.");
                return;
            }
            if (new String(txtSenha.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Senha é obrigatória.");
                return;
            }
            
            String sql = "INSERT INTO usuarios (nome, idade, usuario, senha, tipo, interesse1, interesse2) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtNome.getText().trim());
            ps.setInt(2, Integer.parseInt(txtIdade.getText().trim()));
            ps.setString(3, txtUsuario.getText().trim());
            ps.setString(4, new String(txtSenha.getPassword()));
            ps.setString(5, cboTipo.getSelectedItem().toString());
            ps.setString(6, cboInteresse1.getSelectedItem().toString());
            ps.setString(7, cboInteresse2.getSelectedItem().toString());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cadastrado com sucesso!");
            // Se invocado a partir do PainelAdmin, atualiza a lista
            if (parent != null) {
                try {
                    parent.carregarUsuarios();
                } catch (Exception ignored) {}
            }
            dispose();
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Nome de usuário já existe.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número válido.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
        }
    }
}
