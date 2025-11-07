package edu.stu.cipher.model;

import java.awt.*;
import java.io.File;
import javax.swing.*;
public class FrameUI extends javax.swing.JFrame {
    private JTextField fileField, outputDirField, keyField;
    private JButton chooseFileBtn, chooseOutputDirBtn, encryptBtn, decryptBtn;
    private JComboBox<String> algorithmBox;
    public FrameUI() {
        super("File Encryptor - A5/1 & RC4");
        initUI();
    }

      private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Dòng 1: chọn file ---
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("File nguồn:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        fileField = new JTextField();
        add(fileField, gbc);

        gbc.gridx = 2;
        chooseFileBtn = new JButton("Chọn file");
        add(chooseFileBtn, gbc);

        // --- Dòng 2: chọn thư mục đầu ra ---
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Thư mục lưu kết quả:"), gbc);

        gbc.gridx = 1;
        outputDirField = new JTextField();
        add(outputDirField, gbc);

        gbc.gridx = 2;
        chooseOutputDirBtn = new JButton("Chọn thư mục");
        add(chooseOutputDirBtn, gbc);

        // --- Dòng 3: nhập khóa ---
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Khóa:"), gbc);

        gbc.gridx = 1;
        keyField = new JTextField();
        add(keyField, gbc);

        // --- Dòng 4: chọn thuật toán ---
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Thuật toán:"), gbc);

        gbc.gridx = 1;
        algorithmBox = new JComboBox<>(new String[]{"A5/1", "RC4"});
        add(algorithmBox, gbc);

        // --- Dòng 5: nút mã hóa / giải mã ---
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 3;
        JPanel buttonPanel = new JPanel();
        encryptBtn = new JButton("🔒 Mã hóa");
        decryptBtn = new JButton("🔓 Giải mã");
        buttonPanel.add(encryptBtn);
        buttonPanel.add(decryptBtn);
        add(buttonPanel, gbc);

        // === Sự kiện ===
        chooseFileBtn.addActionListener(e -> chooseFile());
        chooseOutputDirBtn.addActionListener(e -> chooseOutputDir());
        encryptBtn.addActionListener(e -> processFile(true));
        decryptBtn.addActionListener(e -> processFile(false));

        // Cấu hình cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 320);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            fileField.setText(chooser.getSelectedFile().getAbsolutePath());
    }

    private void chooseOutputDir() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            outputDirField.setText(chooser.getSelectedFile().getAbsolutePath());
    }

    private void processFile(boolean encrypt) {
        try {
        File inputFile = new File(fileField.getText());
        File outputDir = new File(outputDirField.getText());
        String key = keyField.getText();
        String algorithm = (String) algorithmBox.getSelectedItem();

        if (!inputFile.exists() || !outputDir.isDirectory() || key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "️ Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        String inputName = inputFile.getName();
        String outputName;

        if (encrypt) {
            outputName = inputName + ".enc";
        } else {
            if (inputName.endsWith(".enc")) {
                outputName = inputName.substring(0, inputName.length() - 4) + ".dec";
            } else {
                outputName = inputName + ".dec";
            }
        }

        File outputFile = new File(outputDir, outputName);

        //Gọi đúng phương thức xử lý file
        FileEncryptor.processFile(inputFile, outputFile, key, algorithm, encrypt);

        JOptionPane.showMessageDialog(this, (encrypt ? "Mã hóa" : "Giải mã") + " thành công!\nFile: " + outputFile.getName());

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, " Lỗi: " + ex.getMessage());
        ex.printStackTrace();
    }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 313, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
             SwingUtilities.invokeLater(() -> {new FrameUI().setVisible(true);});
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
