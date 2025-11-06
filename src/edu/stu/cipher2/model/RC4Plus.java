package algorithms;

/**
 *
 * @author trant
 */
public class RC4Plus implements CipherInterface {
    
    //Sinh luồng khóa và XOR với dữ liệu (PRGA)
    @Override
    public byte[] encrypt(byte[] data, String key) {
        byte[] S = init(key);
        byte[] output = new byte[data.length];
        int i = 0, j = 0;

        for (int n = 0; n < data.length; n++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;

            // Hoán vị
            byte tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;

            // Tính toán các chỉ số t và K theo RC4+
            int t = (S[i] + S[j]) & 0xFF;

            // Công thức keystream RC4+ (cải tiến so với RC4)
            int K = ((S[(S[i] + S[j]) & 0xFF] + S[(S[j] + S[t]) & 0xFF]) & 0xFF)
                    ^ S[(S[i] + S[j] + S[t]) & 0xFF];

            // Mã hóa byte dữ liệu
            output[n] = (byte) (data[n] ^ K);
        }

        return output;
    }

    @Override
    public byte[] decrypt(byte[] data, String key) {
        // RC4 là đối xứng: giải mã = mã hóa
        return encrypt(data, key);
    }

    //Tạo mảng hoán vị dựa vào khóa (KSA)
    private byte[] init(String key) {
        byte[] S = new byte[256];
        byte[] K = key.getBytes();

        // Khởi tạo mảng S = [0, 1, 2, ..., 255]
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
        }

        int j = 0;
        // Trộn mảng S dựa theo khóa K (Key Scheduling Algorithm)
        for (int i = 0; i < 256; i++) {
            j = (j + (S[i] & 0xFF) + (K[i % K.length] & 0xFF)) & 0xFF;
            byte tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;
        }

        return S;
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}