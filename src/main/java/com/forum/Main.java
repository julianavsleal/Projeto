package com.forum;

import javax.swing.SwingUtilities;
import com.forum.view.LoginView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new com.forum.view.LoginView();
        });
    }
}
