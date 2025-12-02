package com.phoenixtech.view;

import com.phoenixtech.dao.ConexaoBD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ListarPostagens extends JFrame {

    public ListarPostagens() {
        setTitle("Postagens");
        setSize(1200, 700);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        String[] col = {"Autor", "Titulo", "Categoria"};
        DefaultTableModel model = new DefaultTableModel(col, 0);
        JTable tabela = new JTable(model);

        try (Connection con = ConexaoBD.obterConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT autor, titulo, categoria FROM postagens ORDER BY titulo ASC")) {

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JScrollPane(tabela));
        setVisible(true);
    }
}
