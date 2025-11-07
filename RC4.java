package edu.stu.cipher.model;

public class RC4 extends CipherBase {
    private final byte[] S = new byte[256];
    private int i = 0, j = 0;

    public RC4(String key) {
        init(key.getBytes());
    }

    /** Khởi tạo mảng S theo key */
    private void init(byte[] key) {
        for (int k = 0; k < 256; k++) {
            S[k] = (byte) k;    
        }
        j = 0;
        for (int k = 0; k < 256; k++) {
            j = (j + (S[k] & 0xFF) + (key[k % key.length] & 0xFF)) & 0xFF;
            byte temp = S[k];
            S[k] = S[j];
            S[j] = temp;
        }
        i = 0;
        j = 0;
    }

    /** Sinh 1 byte keystream theo công thức RC4+ */
    private byte getKeyStreamByte() {
        i = (i + 1) & 0xFF;
        j = (j + (S[i] & 0xFF)) & 0xFF;

        // Hoán đổi S[i], S[j]
        byte temp = S[i];
        S[i] = S[j];
        S[j] = temp;

        int t1 = S[(S[i] + S[j]) & 0xFF] & 0xFF;
        int t2 = S[(S[t1] + S[i]) & 0xFF] & 0xFF;
        int K = (S[(t1 + t2) & 0xFF] ^ S[(S[t2] + S[t1]) & 0xFF]) & 0xFF;

        return (byte) K;
    }

    /** XOR dữ liệu với keystream */
    public byte[] xorWithKeyStream(byte[] data) {
        byte[] output = new byte[data.length];
        for (int k = 0; k < data.length; k++) {
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