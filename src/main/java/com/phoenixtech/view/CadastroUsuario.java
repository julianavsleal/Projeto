package com.phoenixtech.view;

import com.phoenixtech.dao.ConexaoBD;
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
        setTitle("Cadastro de Usuario");
        setSize(1000, 700);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new java.awt.BorderLayout());

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new javax.swing.BoxLayout(panelFormulario, javax.swing.BoxLayout.Y_AXIS));
        panelFormulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 300, 30, 300));

        JLabel titulo = new JLabel("Cadastro de Usuario");
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 28));
        titulo.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        panelFormulario.add(titulo);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(30));

        JLabel l1 = new JLabel("Nome:");
        l1.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l1.setPreferredSize(new java.awt.Dimension(120, 35));
        txtNome = new JTextField();
        txtNome.setPreferredSize(new java.awt.Dimension(280, 35));
        txtNome.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        JPanel panelNome = new JPanel();
        panelNome.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelNome.add(l1);
        panelNome.add(txtNome);
        panelNome.setMaximumSize(new java.awt.Dimension(450, 50));
        panelFormulario.add(panelNome);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(15));

        JLabel l2 = new JLabel("Idade:");
        l2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l2.setPreferredSize(new java.awt.Dimension(120, 35));
        txtIdade = new JTextField();
        txtIdade.setPreferredSize(new java.awt.Dimension(280, 35));
        txtIdade.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        JPanel panelIdade = new JPanel();
        panelIdade.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelIdade.add(l2);
        panelIdade.add(txtIdade);
        panelIdade.setMaximumSize(new java.awt.Dimension(450, 50));
        panelFormulario.add(panelIdade);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(15));

        JLabel l3 = new JLabel("Usuario:");
        l3.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l3.setPreferredSize(new java.awt.Dimension(120, 35));
        txtUsuario = new JTextField();
        txtUsuario.setPreferredSize(new java.awt.Dimension(280, 35));
        txtUsuario.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        JPanel panelUsuario = new JPanel();
        panelUsuario.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelUsuario.add(l3);
        panelUsuario.add(txtUsuario);
        panelUsuario.setMaximumSize(new java.awt.Dimension(450, 50));
        panelFormulario.add(panelUsuario);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(15));

        JLabel l4 = new JLabel("Senha:");
        l4.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l4.setPreferredSize(new java.awt.Dimension(120, 35));
        txtSenha = new JPasswordField();
        txtSenha.setPreferredSize(new java.awt.Dimension(280, 35));
        txtSenha.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        JPanel panelSenha = new JPanel();
        panelSenha.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelSenha.add(l4);
        panelSenha.add(txtSenha);
        panelSenha.setMaximumSize(new java.awt.Dimension(450, 50));
        panelFormulario.add(panelSenha);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(15));

        JLabel l5 = new JLabel("Tipo:");
        l5.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l5.setPreferredSize(new java.awt.Dimension(120, 35));
        cboTipo = new JComboBox<>(new String[]{"usuario", "admin"});
        cboTipo.setPreferredSize(new java.awt.Dimension(280, 35));
        cboTipo.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        JPanel panelTipo = new JPanel();
        panelTipo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelTipo.add(l5);
        panelTipo.add(cboTipo);
        panelTipo.setMaximumSize(new java.awt.Dimension(450, 50));
        panelFormulario.add(panelTipo);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(15));

        JLabel l6 = new JLabel("Interesse 1:");
        l6.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l6.setPreferredSize(new java.awt.Dimension(120, 35));
        cboInteresse1 = new JComboBox<>();
        cboInteresse1.setPreferredSize(new java.awt.Dimension(280, 35));
        cboInteresse1.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        JPanel panelInt1 = new JPanel();
        panelInt1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelInt1.add(l6);
        panelInt1.add(cboInteresse1);
        panelInt1.setMaximumSize(new java.awt.Dimension(450, 50));
        panelFormulario.add(panelInt1);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(15));

        JLabel l7 = new JLabel("Interesse 2:");
        l7.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        l7.setPreferredSize(new java.awt.Dimension(120, 35));
        cboInteresse2 = new JComboBox<>();
        cboInteresse2.setPreferredSize(new java.awt.Dimension(280, 35));
        cboInteresse2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        JPanel panelInt2 = new JPanel();
        panelInt2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        panelInt2.add(l7);
        panelInt2.add(cboInteresse2);
        panelInt2.setMaximumSize(new java.awt.Dimension(450, 50));
        panelFormulario.add(panelInt2);
        panelFormulario.add(javax.swing.Box.createVerticalStrut(30));

        carregarInteresses();

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setPreferredSize(new java.awt.Dimension(150, 40));
        btnCadastrar.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        btnCadastrar.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        btnCadastrar.addActionListener(e -> cadastrar());
        panelFormulario.add(btnCadastrar);

        JScrollPane scrollPane = new JScrollPane(panelFormulario);
        add(scrollPane, java.awt.BorderLayout.CENTER);

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
