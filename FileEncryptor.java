package edu.stu.cipher.model;

import java.io.*;
import javax.swing.*;

public class FileEncryptor {
    public static void processFile(File inputFile, File outputFile, String key, String algorithm, boolean encrypt) {
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            // tạo cipher bằng switch truyền thống (tương thích Java 8)
            CipherBase cipher;
            if ("RC4".equalsIgnoreCase(algorithm)) {
                cipher = new RC4(key);
            } else {
                cipher = new A5_1(key);
            }

            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] chunk = new byte[bytesRead];
                System.arraycopy(buffer, 0, chunk, 0, bytesRead);

                byte[] processed = encrypt ? cipher.encrypt(chunk) : cipher.decrypt(chunk);
                fos.write(processed);
            }

            JOptionPane.showMessageDialog(null,
                    (encrypt ? "Mã hóa" : "Giải mã") + " hoàn tất bằng " + algorithm + ":\n" + outputFile.getAbsolutePath(),
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}