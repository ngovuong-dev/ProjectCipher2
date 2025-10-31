
package edu.stu.cipher.model;
import java.io.*;
import java.util.Scanner;

public class RC4 {
   /* 
    public static String Encrypt(String input, String key){
        // Tạo một đối tượng mới để lưu kết quả mã hóa
        StringBuilder result = new StringBuilder();
        //  tạo 1  danh sách 
        int S[] = new int[256];
        int T[] = new int[256];
      
        int x=0,j=0,K=0;
      
        //Giai Đoạn 1
        for(int i=0;i<256;i++){
            
            S[i]=i; 
             // Mỗi phần tử T[i] là 1 ký tự của key
            T[i]=key.charAt(i % key.length()) % 256;
            
        }
        //Giai Đoạn 2 
        for(int i=0;i<256;i++){
            //j được cập nhật dựa trên giá trị của S[i] và T[i]
           j = (j + S[i] + T[i]) % 256;
           // Hoán đổi  giá trị S[i] và S[j]
           x=S[i];
            S[i] = S[j];
            S[j] = x;
            
        }
        // Giai Đoạn 3 
        
        for(int n=0;n<input.length();n++){
            int  i = j = 0; // Đặt lại chỉ số i và j về 0
            // Tăng i và j 
            i = (i + 1) % 256;
            j = (j + S[i]) % 256;
            // Hoán đổi  giá trị S[i] và S[j]
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
            
            // Sinh khóa K
            K = S[(S[i] + S[j]) % 256];
            
             // Lấy từng ký tự trong chuỗi đầu vào (input)
            // Thực hiện phép XOR giữa ký tự và khóa K
            char EncryptedChar = (char) (input.charAt(n) ^ K);
             // Thêm ký tự mã hóa vào kết quả
            result.append(EncryptedChar);

        }
        
        
        
        return result.toString();
    }
  */
    
    public static byte[] rc4Encrypt(byte[] data, String key) {
        int[] S = new int[256];
        int[] T = new int[256];
        int i, j = 0;

        // Bước 1: Khởi tạo S và T
        for (i = 0; i < 256; i++) {
            S[i] = i;
            T[i] = key.charAt(i % key.length()) % 256;
        }

        // Bước 2: Hoán vị S (KSA)
        for (i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) % 256;
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }

        // Bước 3: Sinh dòng khóa và XOR dữ liệu (PRGA)
        i = j = 0;
        byte[] result = new byte[data.length];
        for (int n = 0; n < data.length; n++) {
            i = (i + 1) % 256;
            j = (j + S[i]) % 256;
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;

            int K = S[(S[i] + S[j]) % 256];
            result[n] = (byte) (data[n] ^ K);
        }

        return result;
    }

    //  MÃ HÓA FILE (ghi thêm đuôi file vào đầu dữ liệu)
    public static void encryptFile(String inputFile, String outputFile, String key) throws IOException {
        // Lấy đuôi file gốc (.txt, .jpg, .pdf,...)
        String extension = "";
        int dotIndex = inputFile.lastIndexOf('.');
        if (dotIndex != -1) {
            extension = inputFile.substring(dotIndex); // VD: ".txt"
        }

        // Đọc toàn bộ nội dung file gốc
        FileInputStream fis = new FileInputStream(inputFile);
        byte[] fileData = fis.readAllBytes();
        fis.close();

        // Mã hóa nội dung
        byte[] encryptedData = rc4Encrypt(fileData, key);

        // Ghi ra file mới
        FileOutputStream fos = new FileOutputStream(outputFile);

        //  Ghi trước độ dài phần đuôi file (1 byte)
        fos.write(extension.length());

        //  Ghi đuôi file (theo byte)
        fos.write(extension.getBytes());

        //  Ghi dữ liệu mã hóa
        fos.write(encryptedData);
        fos.close();

        System.out.println(" Da ma hoa file : " + inputFile);
        System.out.println(" File duoc luu thanh cong : " + outputFile);
        System.out.println(" duoi file goc duoc ghi : " + extension);
    }

    //  GIẢI MÃ FILE (đọc lại đuôi gốc và khôi phục file) 
    public static void decryptFile(String inputFile, String outputFolder, String key) throws IOException {
        FileInputStream fis = new FileInputStream(inputFile);

        // Đọc byte đầu tiên → độ dài phần đuôi
        int extLength = fis.read();
        if (extLength < 0) {
            throw new IOException("File không hợp lệ hoặc bị hỏng!");
        }

        // Đọc tiếp phần đuôi file
        byte[] extBytes = fis.readNBytes(extLength);
        String extension = new String(extBytes);

        // Đọc phần còn lại (dữ liệu mã hóa)
        byte[] encryptedData = fis.readAllBytes();
        fis.close();

        // Giải mã
        byte[] decryptedData = rc4Encrypt(encryptedData, key);

        // Tạo tên file đầu ra với đuôi gốc
        String outputPath = outputFolder + File.separator + "decoded" + extension;

        // Ghi kết quả ra file
        FileOutputStream fos = new FileOutputStream(outputPath);
        fos.write(decryptedData);
        fos.close();

        System.out.println(" Giải mã thành công!");
        System.out.println(" File được khôi phục: " + outputPath);
    }
    public static void main(String[] args) {
      /*  // Gọi hàm Encrypt để mã hóa chuỗi "HELLO" với khóa "KEY"
        String cipher = Encrypt("HELLO", "KEY");

        // In ra kết quả mã hóa
        System.out.println("Cipher: " + cipher);
        */
      
       Scanner sc = new Scanner(System.in);
        try {
            System.out.println("RC4 FILE ENCRYPTION");
            System.out.print("Nhập chế độ (1 = mã hóa, 2 = giải mã): ");
            int mode = sc.nextInt();
            sc.nextLine(); // bỏ dòng trống

            if (mode == 1) {
                System.out.print("Nhập đường dẫn file cần mã hóa: ");
                String inputPath = sc.nextLine();

                System.out.print("Nhập tên file đầu ra (vd: output.enc): ");
                String outputPath = sc.nextLine();

                System.out.print("Nhập khóa mã hóa: ");
                String key = sc.nextLine();

                encryptFile(inputPath, outputPath, key);
            } 
            else if (mode == 2) {
                System.out.print("Nhập đường dẫn file cần giải mã: ");
                String inputPath = sc.nextLine();

                System.out.print("Nhập thư mục lưu file khôi phục: ");
                String outputFolder = sc.nextLine();

                System.out.print("Nhập khóa giải mã: ");
                String key = sc.nextLine();

                decryptFile(inputPath, outputFolder, key);
            } 
            else {
                System.out.println(" Lựa chọn không hợp lệ!");
            }

        } catch (IOException e) {
            System.err.println(" Lỗi: " + e.getMessage());
        } finally {
            sc.close();
        }
      
    }
    
}
