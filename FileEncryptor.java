package edu.stu.cipher.model;

import java.io.*;
import javax.swing.*;

/**
 * Lớp FileEncryptor: hỗ trợ nhiều thuật toán mã hóa (A5/1, RC4,...)
 */
public class FileEncryptor {
    /**
     * @param inputFile  File nguồn
     * @param outputFile File kết quả
     * @param key        Khóa
     * @param algorithm  Thuật toán ("A5/1" hoặc "RC4")
     */
    public static void processFile(File inputFile, File outputFile, String key, String algorithm) {
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            // Chọn thuật toán mã hóa
            Object cipher;
            cipher = switch (algorithm) {
                case "RC4" -> new RC4(key);
                default -> new A5_1(key);
            };

            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] chunk = new byte[bytesRead];
                System.arraycopy(buffer, 0, chunk, 0, bytesRead);

                byte[] processed;
                if (cipher instanceof A5_1 a5_1)
                    processed = a5_1.process(chunk);
                else
                    processed = ((RC4) cipher).process(chunk);

                fos.write(processed);
            }

            JOptionPane.showMessageDialog(null,
                    "Hoàn tất xử lý file bằng " + algorithm + ":\n" + outputFile.getAbsolutePath(),
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
