package logic;

import algorithms.*;
import java.io.IOException;
import java.nio.file.*;

public class FileEncryptManager {

    public static boolean encryptFile(String filePath, String key, String algo) {
        try {
            CipherInterface cipher = new RC4Plus();
            byte[] inputData = Files.readAllBytes(Paths.get(filePath));
            byte[] encrypted = cipher.encrypt(inputData, key);

            String outPath = filePath;
            Files.write(Paths.get(outPath), encrypted);

            System.out.println("✅ File encrypted: " + outPath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean decryptFile(String filePath, String key, String algo) {
        try {
            CipherInterface cipher = new RC4Plus();
            byte[] encryptedData = Files.readAllBytes(Paths.get(filePath));
            byte[] decrypted = cipher.decrypt(encryptedData, key);

            String outPath = filePath;
            Files.write(Paths.get(outPath), decrypted);

            System.out.println("✅ File decrypted: " + outPath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
