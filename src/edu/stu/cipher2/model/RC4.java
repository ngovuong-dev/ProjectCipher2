
package edu.stu.cipher2.model;

/**
 * Lớp RC4 mô phỏng thuật toán mã hóa dòng RC4
 * dùng chung cho mã hóa và giải mã
 */
public class RC4 extends CipherBase {
    private final byte[] S = new byte[256];
    private int i = 0, j = 0;

    public RC4(String key) {
        init(key.getBytes());
    }

    // Khởi tạo mảng hoán vị S bằng khóa
    private void init(byte[] key) {
        // 1. Khởi tạo S = [0, 1, 2, ..., 255]
       for (int k = 0; k < 256; k++) {
            S[k] = (byte) k;
        }
        j = 0;
        for (int k = 0; k < 256; k++) {
            // Cập nhật j dựa vào giá trị của S[k] và byte khóa tương ứng
            j = (j + S[k] + key[k % key.length]) & 0xFF; //& 0xFF ép kiểu về giá trị không dấu âm 
             // Hoán đổi S[k] và S[j]
            byte temp = S[k];
            S[k] = S[j];
            S[j] = temp;
        }
    }

    // Sinh 1 byte keystream
    private byte getKeyStreamByte() {
        // Tăng i và tính lại j
        i = (i + 1) & 0xFF;         // tương đương (i + 1) mod 256
        j = (j + S[i]) & 0xFF;      // tương đương (j + S[i]) mod 256
        byte temp = S[i];
        S[i] = S[j];
        S[j] = temp;
        // Tính chỉ số truy cập ngẫu nhiên và trả về byte khóa  
        return S[(S[i] + S[j]) & 0xFF];
    }

    // Mã hóa hoặc giải mã mảng byte
    public byte[] xorWithKeyStream(byte[] data) {
        byte[] output = new byte[data.length];
        for (int k = 0; k < data.length; k++) {
            // Lấy 1 byte keystream và XOR với byte dữ liệu
            output[k] = (byte) (data[k] ^ getKeyStreamByte());
        }
        return output;
    }
    @Override
     public byte[] encrypt(byte[] data) {
        return xorWithKeyStream(data);
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return xorWithKeyStream(data);
    }
}