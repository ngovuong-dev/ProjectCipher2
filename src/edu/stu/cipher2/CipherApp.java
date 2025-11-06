package edu.stu.cipher2;

import edu.stu.cipher2.view.MainFrame;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


// CipherApp project
public class CipherApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}