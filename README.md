# 🌊 Project Cipher 2 - Stream Ciphers (A5/1 & RC4)

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Algorithm](https://img.shields.io/badge/Algorithm-Stream%20Cipher-blue)
![Security](https://img.shields.io/badge/Security-A5%2F1%20%26%20RC4-red)

**Project Cipher 2** là dự án Java mô phỏng và hiện thực hóa các thuật toán **Mã hóa dòng (Stream Ciphers)** kinh điển. Dự án tập trung vào việc hiểu sâu cơ chế hoạt động của **RC4** (Rivest Cipher 4) và **A5/1** (thuật toán mã hóa trong mạng di động GSM).

Đây là đồ án môn học [Tên Môn Học] tại trường Đại học Công nghệ Sài Gòn (STU).

---

## 🚀 Tính năng & Thuật toán

Dự án hiện thực hóa chi tiết hai thuật toán sau:

### 1. RC4 (Rivest Cipher 4)
RC4 là thuật toán mã hóa dòng được sử dụng rộng rãi trong các giao thức như WEP và TLS (trước đây).
* **Cơ chế:** Sử dụng mảng hoán vị (S-box) và bộ tạo số giả ngẫu nhiên (PRGA).
* **Tính năng:**
    * [x] Nhập Key (Khóa) tùy ý.
    * [x] Mã hóa/Giải mã văn bản (String).
    * [x] Mã hóa/Giải mã tập tin (File byte stream).

### 2. A5/1 (GSM Encryption)
A5/1 là thuật toán mã hóa luồng được sử dụng để đảm bảo quyền riêng tư trong tiêu chuẩn điện thoại di động GSM.
* **Cơ chế:** Sử dụng 3 thanh ghi dịch chuyển phản hồi tuyến tính (LFSR - Linear Feedback Shift Register) với độ dài khác nhau (19, 22, 23 bit).
* **Tính năng:**
    * [x] Mô phỏng hoạt động của 3 thanh ghi R1, R2, R3.
    * [x] Cơ chế Clocking (Majority Vote) để điều khiển bước nhảy.
    * [x] Nhập Session Key (64-bit) và Frame Number (22-bit).

---

## 🛠️ Công nghệ sử dụng

* **Ngôn ngữ:** Java (JDK 17+).
* **Giao diện (GUI):** [Java Swing / JavaFX / Console] *(Bạn điền loại giao diện thực tế vào đây)*.
* **Kỹ thuật lập trình:**
    * Bitwise Operations (Xử lý bit: XOR, Shift, AND/OR) - Cốt lõi của A5/1.
    * File I/O Streams (Đọc ghi file nhị phân).

---

## 📂 Cấu trúc dự án

```text
ProjectCipher2/
│
├── src/
│   ├── algorithms/
│   │   ├── RC4.java          # Logic thuật toán RC4 (KSA, PRGA)
│   │   └── A5_1.java         # Logic thuật toán A5/1 (LFSRs)
│   │
│   ├── ui/                   # Giao diện người dùng
│   │   ├── MainFrame.java    # Cửa sổ chính
│   │   └── Panels/           # Các panel chức năng
│   │
│   ├── utils/                # Các hàm hỗ trợ (Chuyển đổi Hex/Binary)
│   └── Main.java             # Entry point
│
├── bin/                      # File .class
└── README.md
