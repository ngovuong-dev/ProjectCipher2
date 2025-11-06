package edu.stu.cipher2.view;


import edu.stu.cipher2.view.panelModel.CipherPanel;
import javax.swing.*;
import java.awt.*;

// Main UI
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel currentPanel;
    
    public MainFrame() { // MainFrame Function
        initComponents();
    }

    private void initComponents() {
        // ---- Init Frame ----
        this.setTitle("Cipher Version 2");
        this.setSize(880, 590);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        // ---- Init Panel ----
        cardLayout = new CardLayout(); // Init cardLayout
        currentPanel = new JPanel(cardLayout); // Set mainPanel
        // Add panel
        JPanel cipherPanel = new CipherPanel(this);
        
        // -- Init show --
        currentPanel.add(cipherPanel, "cipherPanel");
        add(currentPanel);
        cardLayout.show(currentPanel, "cipherPanel");
    }
    
    public void switchTo(String name) {
        cardLayout.show(currentPanel, name);
    }
}
