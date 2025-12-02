package com.phoenixtech;

import javax.swing.SwingUtilities;
import com.phoenixtech.view.LoginView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new com.phoenixtech.view.LoginView();
        });
    }
}
