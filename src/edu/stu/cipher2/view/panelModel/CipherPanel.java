package edu.stu.cipher2.view.panelModel;

import edu.stu.cipher2.model.A5;
import edu.stu.cipher2.model.RC4;
import edu.stu.cipher2.model.CipherBase;
import edu.stu.cipher2.view.MainFrame;
import java.awt.Desktop;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;



public class CipherPanel extends javax.swing.JPanel {
    private MainFrame mainFrame;
    private boolean typeSwitchED = true;
    private boolean typeCipher = true;
    private String cipherName = "A5";
    private String key;
    
    private File inputFile;
    private File outputFile = new File("output");
    
    public CipherPanel(MainFrame mainFrame) {
        initComponents();
        this.mainFrame = mainFrame;
        syncCipher();
        syncTitleSwitchED();
        dragAndDropPath();
    }

    public void syncTitleSwitchED() {
        if (typeSwitchED)
            lblTitleED.setText("Encrypt");
        else
            lblTitleED.setText("Decrypt");
    }

    public void syncCipher() {
        cbcA5.setSelected(typeCipher);
        cbcRC4.setSelected(!typeCipher);
        if (typeCipher)
            cipherName = ("A5");
        else
            cipherName = ("RC4");
    }
    
    public void dragAndDropPath() { // Kéo thả dữ liệu
        // Login nhận file hoặc folder     
        TransferHandler handler;
        handler = new TransferHandler() {
            
            @Override
            public boolean canImport(TransferSupport support) { // kiểm tra
                // Chỉ gật đầu nếu đó là danh sách file
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) { // Nhận dữ liệu
                try {
                    // Lấy dữ liệu
                    List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    // Lấy file đầu tiên
                    File file = files.get(0);

                    // Hét lên (cập nhật JLabel)
                    inputFile = file;
                    lblPath.setText(file.getAbsolutePath());
                    
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
        // Set khung
        pnpDragAndDrop.setTransferHandler(handler);
    }
    
    /**
    * Xử lý mã hóa/giải mã với đường dẫn lưu do người dùng chọn
    * - Tạo thư mục mới theo thời gian
    * - Giữ cấu trúc folder
    * - Tạo key.txt
    */
    public void processPathWithOutputDir(
            File inputPathP,           // File hoặc folder nguồn
            File outputDirP,           // Thư mục đích (do người dùng chọn)
            String keyP,               // Khóa
            String algorithmP,         // RC4 hoặc A5
            boolean encryptP           // true = mã hóa
    ) {
        if (inputPathP == null || !inputPathP.exists()) {
            JOptionPane.showMessageDialog(null, "Nguồn không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (outputDirP == null || !outputDirP.isDirectory()) {
            JOptionPane.showMessageDialog(null, "Thư mục đích không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (keyP == null || keyP.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Khóa không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);;
            return;
        }
        if (algorithmP == null || algorithmP.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thuật toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            CipherBase cipher = "RC4".equalsIgnoreCase(algorithmP) ? new RC4(keyP) : new A5(keyP);

            // === TẠO THƯ MỤC MỚI TRONG outputDirP ===
            String prefix = encryptP ? "Encrypted_" : "Decrypted_";
            String newFolderName = prefix + inputPathP.getName() + "_";

            File outputRoot = new File(outputDirP, newFolderName);
            if (!outputRoot.mkdirs()) {
                JOptionPane.showMessageDialog(null, "Không thể tạo thư mục đích!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // === XỬ LÝ FILE HOẶC FOLDER ===
            if (inputPathP.isDirectory()) {
                encryptFolder(inputPathP, outputRoot, cipher, keyP, encryptP);
            } else {
                String ext = encryptP ? ".enc" : ".dec";
                File destFile = new File(outputRoot, inputPathP.getName() + ext);
                processSingleFile(inputPathP, destFile, cipher);
            }


            // === THÔNG BÁO THÀNH CÔNG ===
            JOptionPane.showMessageDialog(
                null,
                (encryptP ? "Mã hóa" : "Giải mã") + " thành công!\n"
                + "Lưu tại: " + outputRoot.getAbsolutePath(),
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void encryptFolder(File srcDir, File destDir, CipherBase cipher, String keyP, boolean encryptP) throws IOException {
        File[] files = srcDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                File subDest = new File(destDir, file.getName());
                subDest.mkdir();
                encryptFolder(file, subDest, cipher, keyP, encryptP);
            } else {
                String ext = encryptP ? ".enc" : ".dec";
                File destFile = new File(destDir, file.getName() + ext);
                processSingleFile(file, destFile, cipher);
            }
        }
    }
    
    private void processSingleFile(File src, File dest, CipherBase cipher) throws IOException {
        try (FileInputStream fis = new FileInputStream(src);
             FileOutputStream fos = new FileOutputStream(dest)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] chunk = new byte[bytesRead];
                System.arraycopy(buffer, 0, chunk, 0, bytesRead);
                byte[] processed = cipher.encrypt(chunk); // RC4/A5: encrypt = decrypt
                fos.write(processed);
            }
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optionSwitchED = new javax.swing.JOptionPane();
        bgbA5CR4 = new javax.swing.ButtonGroup();
        pnpTitle0 = new javax.swing.JPanel();
        pnpTitle1 = new javax.swing.JPanel();
        btnExit = new javax.swing.JButton();
        lblTitleED = new javax.swing.JLabel();
        btnSwitchED = new javax.swing.JButton();
        pnpSplit = new javax.swing.JPanel();
        pnpMain = new javax.swing.JPanel();
        btnRunning = new javax.swing.JButton();
        lblTypeCipher = new javax.swing.JLabel();
        cbcA5 = new javax.swing.JCheckBox();
        cbcRC4 = new javax.swing.JCheckBox();
        pnpDragAndDrop = new javax.swing.JPanel();
        lblDragAndDrop = new javax.swing.JLabel();
        btnUpload = new javax.swing.JButton();
        btnSelect = new javax.swing.JButton();
        lblTitlePath = new javax.swing.JLabel();
        lblPath = new javax.swing.JLabel();
        lblKey = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaKey = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(204, 204, 204));

        pnpTitle0.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout pnpTitle0Layout = new javax.swing.GroupLayout(pnpTitle0);
        pnpTitle0.setLayout(pnpTitle0Layout);
        pnpTitle0Layout.setHorizontalGroup(
            pnpTitle0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );
        pnpTitle0Layout.setVerticalGroup(
            pnpTitle0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnpTitle1.setBackground(new java.awt.Color(51, 51, 51));

        btnExit.setBackground(new java.awt.Color(255, 0, 51));
        btnExit.setFont(new java.awt.Font("Droid Sans", 1, 24)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.setFocusPainted(false);
        btnExit.setFocusable(false);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        lblTitleED.setFont(new java.awt.Font("Droid Sans", 1, 40)); // NOI18N
        lblTitleED.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleED.setText("Title");

        btnSwitchED.setBackground(new java.awt.Color(153, 0, 255));
        btnSwitchED.setFont(new java.awt.Font("Droid Sans", 1, 24)); // NOI18N
        btnSwitchED.setForeground(new java.awt.Color(255, 255, 255));
        btnSwitchED.setText("Switch");
        btnSwitchED.setFocusPainted(false);
        btnSwitchED.setFocusable(false);
        btnSwitchED.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSwitchEDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnpTitle1Layout = new javax.swing.GroupLayout(pnpTitle1);
        pnpTitle1.setLayout(pnpTitle1Layout);
        pnpTitle1Layout.setHorizontalGroup(
            pnpTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnpTitle1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitleED)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnpTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSwitchED, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnpTitle1Layout.setVerticalGroup(
            pnpTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnpTitle1Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(btnSwitchED, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pnpTitle1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblTitleED)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnpSplit.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout pnpSplitLayout = new javax.swing.GroupLayout(pnpSplit);
        pnpSplit.setLayout(pnpSplitLayout);
        pnpSplitLayout.setHorizontalGroup(
            pnpSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnpSplitLayout.setVerticalGroup(
            pnpSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        pnpMain.setBackground(new java.awt.Color(255, 255, 255));
        pnpMain.setPreferredSize(new java.awt.Dimension(850, 370));

        btnRunning.setBackground(new java.awt.Color(0, 204, 51));
        btnRunning.setFont(new java.awt.Font("Droid Sans", 1, 24)); // NOI18N
        btnRunning.setForeground(new java.awt.Color(255, 255, 255));
        btnRunning.setText("Run");
        btnRunning.setFocusPainted(false);
        btnRunning.setFocusable(false);
        btnRunning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunningActionPerformed(evt);
            }
        });

        lblTypeCipher.setFont(new java.awt.Font("Droid Sans", 1, 24)); // NOI18N
        lblTypeCipher.setForeground(new java.awt.Color(51, 51, 51));
        lblTypeCipher.setText("Type Cipher:");

        cbcA5.setBackground(new java.awt.Color(255, 255, 255));
        bgbA5CR4.add(cbcA5);
        cbcA5.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        cbcA5.setForeground(new java.awt.Color(0, 0, 0));
        cbcA5.setSelected(true);
        cbcA5.setText(" A5");
        cbcA5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbcA5.setFocusPainted(false);
        cbcA5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbcA5ActionPerformed(evt);
            }
        });

        cbcRC4.setBackground(new java.awt.Color(255, 255, 255));
        bgbA5CR4.add(cbcRC4);
        cbcRC4.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        cbcRC4.setForeground(new java.awt.Color(0, 0, 0));
        cbcRC4.setText("RC4");
        cbcRC4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbcRC4.setFocusPainted(false);
        cbcRC4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbcRC4ActionPerformed(evt);
            }
        });

        pnpDragAndDrop.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1))));

        lblDragAndDrop.setFont(new java.awt.Font("Droid Sans", 2, 18)); // NOI18N
        lblDragAndDrop.setForeground(new java.awt.Color(102, 102, 102));
        lblDragAndDrop.setText("Drag and Drop here !");

        btnUpload.setBackground(new java.awt.Color(51, 51, 255));
        btnUpload.setFont(new java.awt.Font("Droid Sans", 1, 17)); // NOI18N
        btnUpload.setForeground(new java.awt.Color(255, 255, 255));
        btnUpload.setText("Upload");
        btnUpload.setFocusPainted(false);
        btnUpload.setFocusable(false);
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        btnSelect.setBackground(new java.awt.Color(0, 0, 153));
        btnSelect.setFont(new java.awt.Font("Droid Sans", 1, 17)); // NOI18N
        btnSelect.setForeground(new java.awt.Color(255, 255, 255));
        btnSelect.setText("Select");
        btnSelect.setFocusPainted(false);
        btnSelect.setFocusable(false);
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnpDragAndDropLayout = new javax.swing.GroupLayout(pnpDragAndDrop);
        pnpDragAndDrop.setLayout(pnpDragAndDropLayout);
        pnpDragAndDropLayout.setHorizontalGroup(
            pnpDragAndDropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnpDragAndDropLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnpDragAndDropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 111, 111)
                .addComponent(lblDragAndDrop)
                .addContainerGap(219, Short.MAX_VALUE))
        );
        pnpDragAndDropLayout.setVerticalGroup(
            pnpDragAndDropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnpDragAndDropLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSelect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnpDragAndDropLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDragAndDrop)
                .addGap(30, 30, 30))
        );

        lblTitlePath.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        lblTitlePath.setForeground(new java.awt.Color(0, 0, 0));
        lblTitlePath.setText("Path:");

        lblPath.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        lblPath.setForeground(new java.awt.Color(0, 0, 0));
        lblPath.setText("path/~");

        lblKey.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        lblKey.setForeground(new java.awt.Color(0, 0, 0));
        lblKey.setText("Key:");

        textAreaKey.setColumns(20);
        textAreaKey.setRows(3);
        textAreaKey.setWrapStyleWord(true);
        jScrollPane1.setViewportView(textAreaKey);

        javax.swing.GroupLayout pnpMainLayout = new javax.swing.GroupLayout(pnpMain);
        pnpMain.setLayout(pnpMainLayout);
        pnpMainLayout.setHorizontalGroup(
            pnpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnpMainLayout.createSequentialGroup()
                .addGroup(pnpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnpMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnRunning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnpMainLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(pnpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblKey)
                            .addComponent(pnpDragAndDrop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1))
                        .addGap(0, 102, Short.MAX_VALUE))
                    .addGroup(pnpMainLayout.createSequentialGroup()
                        .addGroup(pnpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnpMainLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(lblTypeCipher)
                                .addGap(43, 43, 43)
                                .addComponent(cbcA5)
                                .addGap(110, 110, 110)
                                .addComponent(cbcRC4))
                            .addGroup(pnpMainLayout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(lblTitlePath)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPath)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnpMainLayout.setVerticalGroup(
            pnpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnpMainLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitlePath)
                    .addComponent(lblPath))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnpDragAndDrop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblKey)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(pnpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTypeCipher)
                    .addComponent(cbcA5)
                    .addComponent(cbcRC4))
                .addGap(18, 18, 18)
                .addComponent(btnRunning, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnpTitle0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnpTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnpSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(pnpMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnpTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnpTitle0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnpSplit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnpMain, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(this, 
        "Do you want leave?",
        "Yes", 
        javax.swing.JOptionPane.YES_NO_OPTION);
        
        if (result == javax.swing.JOptionPane.YES_OPTION) {
            System.exit(0);
        }
        
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnRunningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunningActionPerformed
        // TODO add your handling code here:
        if (inputFile == null )
            if (key == null || key.isEmpty())
                return; // Không thông báo gì, chỉ bỏ qua nếu chưa chọn
        
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (folderChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        outputFile = folderChooser.getSelectedFile();
        key = textAreaKey.getText();
        
        this.processPathWithOutputDir(
            inputFile,           // File hoặc folder nguồn
            outputFile,           // Thư mục đích (do người dùng chọn)
            key,               // Khóa
            cipherName,         // RC4 hoặc A5
            typeSwitchED           // true = mã hóa
        );
    }//GEN-LAST:event_btnRunningActionPerformed

    private void btnSwitchEDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSwitchEDActionPerformed
        // TODO add your handling code here:
        // Button Switch
        int result = optionSwitchED.showConfirmDialog(this, 
        "Do you want switch?",
        "Yes", 
        javax.swing.JOptionPane.YES_NO_OPTION);
        
        if (result == javax.swing.JOptionPane.YES_OPTION) {
            typeSwitchED = !typeSwitchED;
        }
        syncTitleSwitchED();
    }//GEN-LAST:event_btnSwitchEDActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        // TODO add your handling code here:
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (folderChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            inputFile = folderChooser.getSelectedFile();
            lblPath.setText(inputFile.getAbsolutePath());
        }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            inputFile = fileChooser.getSelectedFile();
            lblPath.setText(inputFile.getAbsolutePath());
        }
    }//GEN-LAST:event_btnSelectActionPerformed

    private void cbcA5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbcA5ActionPerformed
        // TODO add your handling code here:
        typeCipher = true;
        syncCipher();
    }//GEN-LAST:event_cbcA5ActionPerformed

    private void cbcRC4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbcRC4ActionPerformed
        // TODO add your handling code here:
        typeCipher = false;
        syncCipher();
    }//GEN-LAST:event_cbcRC4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgbA5CR4;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnRunning;
    private javax.swing.JButton btnSelect;
    private javax.swing.JButton btnSwitchED;
    private javax.swing.JButton btnUpload;
    private javax.swing.JCheckBox cbcA5;
    private javax.swing.JCheckBox cbcRC4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDragAndDrop;
    private javax.swing.JLabel lblKey;
    private javax.swing.JLabel lblPath;
    private javax.swing.JLabel lblTitleED;
    private javax.swing.JLabel lblTitlePath;
    private javax.swing.JLabel lblTypeCipher;
    private javax.swing.JOptionPane optionSwitchED;
    private javax.swing.JPanel pnpDragAndDrop;
    private javax.swing.JPanel pnpMain;
    private javax.swing.JPanel pnpSplit;
    private javax.swing.JPanel pnpTitle0;
    private javax.swing.JPanel pnpTitle1;
    private javax.swing.JTextArea textAreaKey;
    // End of variables declaration//GEN-END:variables
}
