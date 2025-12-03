package com.phoenixtech.view;

import com.phoenixtech.dao.ConexaoBD;
import com.phoenixtech.dao.PostagemDAO;
import com.phoenixtech.model.Postagem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListarPostagens extends JFrame {

    private DefaultTableModel model;
    private JTable tabela;
    private String usuarioLogado;
    private Integer usuarioLogadoId;
    private PostagemDAO dao;
    private Map<Integer, String> autorMap;

    public ListarPostagens() {
        this(null, null);
    }

    public ListarPostagens(String usuario, Integer usuarioId) {
        this.usuarioLogado = usuario;
        this.usuarioLogadoId = usuarioId;
        this.dao = new PostagemDAO();
        this.autorMap = new HashMap<>();

        setTitle("Postagens");
        setSize(1200, 700);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        String[] col = {"ID", "Autor", "Titulo", "Categoria", "Texto"};
        model = new DefaultTableModel(col, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabela = new JTable(model);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(300);

        carregarPostagens();

        JPanel panelBotoes = new JPanel();
        JButton btnEditar = new JButton("Editar Selecionada");
        btnEditar.addActionListener(e -> editarPostagem());
        JButton btnDeletar = new JButton("Deletar Selecionada");
        btnDeletar.addActionListener(e -> deletarPostagem());

        panelBotoes.add(btnEditar);
        panelBotoes.add(btnDeletar);

        add(new JScrollPane(tabela), "Center");
        add(panelBotoes, "South");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void carregarPostagens() {
        model.setRowCount(0);
        autorMap.clear();
        try {
            List<Postagem> postagens = dao.listarTodas();
            for (Postagem p : postagens) {
                String nomeAutor = obterNomeAutor(p.getAutorId());
                String textoResumo = p.getTexto() != null ? (p.getTexto().length() > 50 ? p.getTexto().substring(0, 50) + "..." : p.getTexto()) : "";
                model.addRow(new Object[]{
                        p.getId(),
                        nomeAutor,
                        p.getTitulo(),
                        p.getCategoria(),
                        textoResumo
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar postagens: " + e.getMessage());
        }
    }

    private String obterNomeAutor(Integer autorId) {
        if (autorId == null || autorId <= 0) return "Anônimo";
        if (autorMap.containsKey(autorId)) return autorMap.get(autorId);
        
        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement ps = conn.prepareStatement("SELECT usuario FROM usuarios WHERE id = ?")) {
            ps.setInt(1, autorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("usuario");
                    autorMap.put(autorId, nome);
                    return nome;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        autorMap.put(autorId, "Desconhecido");
        return "Desconhecido";
    }

    void editarPostagem() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma postagem.");
            return;
        }
        int id = (int) model.getValueAt(linha, 0);
        String nomeAutor = (String) model.getValueAt(linha, 1);
        
        Integer autorId = obterAutorIdPorNome(nomeAutor);

        if (usuarioLogadoId != null && (autorId == null || !usuarioLogadoId.equals(autorId))) {
            JOptionPane.showMessageDialog(this, "Voce so pode editar suas proprias postagens.");
            return;
        }

        try {
            Postagem p = dao.obterPorId(id);
            if (p != null) {
                new CadastroPostagem(usuarioLogado != null ? usuarioLogado : nomeAutor, p, this, p.getAutorId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao editar: " + e.getMessage());
        }
    }

    void deletarPostagem() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma postagem.");
            return;
        }
        int id = (int) model.getValueAt(linha, 0);
        String nomeAutor = (String) model.getValueAt(linha, 1);
        
        Integer autorId = obterAutorIdPorNome(nomeAutor);

        if (usuarioLogadoId != null && (autorId == null || !usuarioLogadoId.equals(autorId))) {
            JOptionPane.showMessageDialog(this, "Voce so pode deletar suas proprias postagens.");
            return;
        }

        int op = JOptionPane.showConfirmDialog(this, "Deseja realmente deletar esta postagem?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op != JOptionPane.YES_OPTION) return;

        try {
            dao.deletar(id);
            JOptionPane.showMessageDialog(this, "Postagem deletada.");
            carregarPostagens();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao deletar: " + e.getMessage());
        }
    }

    private Integer obterAutorIdPorNome(String nomeAutor) {
        for (Map.Entry<Integer, String> entry : autorMap.entrySet()) {
            if (entry.getValue().equals(nomeAutor)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
