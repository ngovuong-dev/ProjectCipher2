package edu.stu.cipher.model;
/**
 * Lớp A5_1 mô phỏng thuật toán mã hóa dòng A5/1.
 * Thuật toán này dùng 3 thanh ghi dịch phản hồi tuyến tính (LFSR)
 * để sinh ra dãy bit khóa và XOR với dữ liệu gốc.
 */
public class A5_1 extends CipherBase{
    // Ba thanh ghi LFSR với độ dài cố định (final = không đổi tham chiếu)
    private final int[] R1 = new int[19];
    private final int[] R2 = new int[22];
    private final int[] R3 = new int[23];
    
    /** 
     * Hàm khởi tạo – nhận vào khóa chuỗi (String key)
     * và chuyển thành bit để khởi tạo ba thanh ghi.
     * @param key
     */
    public A5_1(String key) {
        String keyBits = stringToBits(key); // Chuyển chuỗi sang 64 bit nhị phân

        // Chia 64 bit vào 3 thanh ghi
        for (int i = 0; i < 19; i++) R1[i] = keyBits.charAt(i) - '0';
        for (int i = 0; i < 22; i++) R2[i] = keyBits.charAt(i + 19) - '0';
        for (int i = 0; i < 23; i++) R3[i] = keyBits.charAt(i + 41) - '0';

        warmUp(); // Chạy "làm nóng" ban đầu (giúp trộn đều trạng thái)
    }

    /**
     * Chuyển khóa chuỗi thành 64-bit nhị phân ổn định.
     * Dùng hashCode() * số vàng 2654435761L để sinh giá trị giả ngẫu nhiên.
     */
    private String stringToBits(String input) {
        long hash = input.hashCode() * 2654435761L;
        String bin = String.format("%64s", Long.toBinaryString(hash)).replace(' ', '0');
        return bin.substring(bin.length() - 64);
    }

    /**
     * Làm nóng thanh ghi: gọi sinh bit 100 lần để trộn đều trạng thái.
     */
    private void warmUp() {
        for (int i = 0; i < 100; i++) getKeyStreamBit();
    }

    /**
     * Hàm majority: trả về bit đa số giữa 3 bit điều khiển.
     */
    private int majority(int x, int y, int z) {
        return (x + y + z > 1) ? 1 : 0;
    }

    /**
     * Sinh ra một bit khóa từ 3 thanh ghi:
     * 1. Tính majority của 3 bit điều khiển.
     * 2. Dịch thanh ghi nào có bit điều khiển trùng với majority.
     * 3. Bit khóa = XOR của bit cuối cùng 3 thanh ghi.
     */
    private int getKeyStreamBit() {
        int maj = majority(R1[8], R2[10], R3[10]); // bit điều khiển của mỗi thanh ghi
        if (R1[8] == maj) shiftR1();
        if (R2[10] == maj) shiftR2();
        if (R3[10] == maj) shiftR3();
        return R1[18] ^ R2[21] ^ R3[22]; // XOR 3 bit cuối cùng để sinh keystream bit
    }

    /**
     * Dịch thanh ghi R1 – tap tại các vị trí 13, 16, 17, 18.
     */
    private void shiftR1() {
        int newBit = R1[13] ^ R1[16] ^ R1[17] ^ R1[18];
        System.arraycopy(R1, 0, R1, 1, R1.length - 1); // dịch phải 1
        R1[0] = newBit; // bit mới chèn vào đầu
    }

    /**
     * Dịch thanh ghi R2 – tap tại các vị trí 20, 21.
     */
    private void shiftR2() {
        int newBit = R2[20] ^ R2[21];
        System.arraycopy(R2, 0, R2, 1, R2.length - 1);
        R2[0] = newBit;
    }

    /**
     * Dịch thanh ghi R3 – tap tại các vị trí 7, 20, 21, 22.
     */
    private void shiftR3() {
        int newBit = R3[7] ^ R3[20] ^ R3[21] ^ R3[22];
        System.arraycopy(R3, 0, R3, 1, R3.length - 1);
        R3[0] = newBit;
    }
    
    public byte[] xorWithKeyStream(byte[] data) {
        byte[] output = new byte[data.length];

        for (int i = 0; i < data.length; i++) {
            int ksByte = 0; // sinh 8 bit keystream
            for (int b = 0; b < 8; b++) {
                ksByte = (ksByte << 1) | getKeyStreamBit();
            }
            // XOR từng byte dữ liệu với keystream byte
            output[i] = (byte)(data[i] ^ ksByte);
        }
        return output; // trả về mảng byte đã mã hóa hoặc giải mã
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