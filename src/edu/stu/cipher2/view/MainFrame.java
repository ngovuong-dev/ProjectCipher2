package edu.stu.cipher2.view;

import javax.swing.*;

// Main UI
public class MainFrame extends JFrame {
    private JPanel currentPanel;

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("CipherApp");
        setSize(830, 570);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
    }
}
