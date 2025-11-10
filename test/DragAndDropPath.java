import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

public class DragAndDropPath {

    public static void main(String[] args) {

        // 1. Cái Khung (JFrame)
        JFrame frame = new JFrame("Tư Duy Cơ Bản");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. Cái Hộp (JPanel)
        JPanel dropBox = new JPanel();
        dropBox.add(new JLabel("Thả file vào đây")); // Thêm chữ cho dễ hiểu

        // (Chúng ta cũng cần một JLabel để xem kết quả)
        JLabel pathLabel = new JLabel("Đường dẫn sẽ hiện ở đây");

        // 3. Người Gác Cổng (TransferHandler)
        TransferHandler handler = new TransferHandler() {
            
            // Nhiệm vụ 1: Kiểm tra
            @Override
            public boolean canImport(TransferSupport support) {
                // Chỉ gật đầu nếu đó là danh sách file
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            // Nhiệm vụ 2: Chấp nhận
            @Override
            public boolean importData(TransferSupport support) {
                try {
                    // Lấy dữ liệu
                    List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    
                    // Lấy file đầu tiên
                    File file = files.get(0);
                    
                    // Hét lên (cập nhật JLabel)
                    pathLabel.setText(file.getAbsolutePath()); 
                    
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };

        // GÁN "Người Gác Cổng" cho "Cái Hộp"
        dropBox.setTransferHandler(handler);

        // Thêm Cái Hộp và Nhãn vào Khung (dùng layout đơn giản nhất)
        frame.setLayout(new java.awt.GridLayout(2, 1)); // 2 hàng, 1 cột
        frame.add(dropBox);
        frame.add(pathLabel);

        // Hiển thị
        frame.setVisible(true);
    }
}