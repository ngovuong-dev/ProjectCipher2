import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutionException;

public class EncryptorApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private EncryptPanel encryptPanel;
    private HomePanel homePanel;

    public EncryptorApp() {
        setTitle("File Encryptor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        homePanel = new HomePanel(this);
        encryptPanel = new EncryptPanel(this);

        mainPanel.add(homePanel, "home");
        mainPanel.add(encryptPanel, "encrypt");

        add(mainPanel);
        cardLayout.show(mainPanel, "home");
    }

    public void switchTo(String name) {
        cardLayout.show(mainPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EncryptorApp().setVisible(true));
    }
}

// ---------------- HOME PANEL -----------------
class HomePanel extends JPanel {
    public HomePanel(EncryptorApp app) {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Trang chính", SwingConstants.CENTER);
        JButton btnEncrypt = new JButton("Mã hóa file");

        btnEncrypt.addActionListener(e -> app.switchTo("encrypt"));
        add(title, BorderLayout.CENTER);
        add(btnEncrypt, BorderLayout.SOUTH);
    }
}

// ---------------- ENCRYPT PANEL -----------------
class EncryptPanel extends JPanel {
    private JButton btnStart, btnBack;
    private JLayeredPane layeredPane;
    private OverlayPanel overlayPanel;
    private EncryptTask worker;
    private EncryptorApp app;

    public EncryptPanel(EncryptorApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        // --- Panel chính ---
        JPanel mainContent = new JPanel(new FlowLayout());
        btnStart = new JButton("Bắt đầu mã hóa");
        btnBack = new JButton("Trở lại menu");
        mainContent.add(btnStart);
        mainContent.add(btnBack);

        // --- Overlay panel ---
        overlayPanel = new OverlayPanel();

        // --- LayeredPane chứa cả 2 lớp ---
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(mainContent, JLayeredPane.DEFAULT_LAYER);

        add(layeredPane, BorderLayout.CENTER);

        overlayPanel.setVisible(false);

        // --- Event ---
        btnStart.addActionListener(e -> startEncrypt());
        btnBack.addActionListener(e -> app.switchTo("home"));
    }

    private void startEncrypt() {
        // Hiện overlay
        overlayPanel.setVisible(true);
        overlayPanel.startProgress();

        worker = new EncryptTask(overlayPanel);
        worker.execute();

        overlayPanel.setCancelAction(() -> {
            if (worker != null && !worker.isDone()) {
                worker.cancel(true);
            }
            overlayPanel.stopProgress();
            app.switchTo("home");
        });
    }

    // --- Worker thực hiện mã hóa giả lập ---
    private static class EncryptTask extends SwingWorker<Void, Integer> {
        private OverlayPanel overlay;

        public EncryptTask(OverlayPanel overlay) {
            this.overlay = overlay;
        }

        @Override
        protected Void doInBackground() throws Exception {
            for (int i = 0; i <= 100; i++) {
                if (isCancelled()) break;
                Thread.sleep(50); // giả lập mã hóa
                setProgress(i);
                overlay.updateProgress(i);
            }
            return null;
        }

        @Override
        protected void done() {
            if (!isCancelled()) {
                overlay.showComplete();
            } else {
                overlay.showCancelled();
            }
        }
    }
}

// ---------------- OVERLAY PANEL -----------------
class OverlayPanel extends JPanel {
    private JProgressBar progressBar;
    private JButton btnCancel;
    private JLabel labelStatus;
    private Runnable cancelAction;

    public OverlayPanel() {
        setOpaque(true);
        setBackground(new Color(0, 0, 0, 170)); // nền mờ

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        labelStatus = new JLabel("Đang mã hóa...", SwingConstants.CENTER);
        labelStatus.setForeground(Color.WHITE);
        labelStatus.setFont(labelStatus.getFont().deriveFont(Font.BOLD, 16));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        btnCancel = new JButton("Hủy và trở về menu");
        btnCancel.addActionListener(e -> {
            if (cancelAction != null) cancelAction.run();
        });

        gbc.gridy = 0;
        add(labelStatus, gbc);
        gbc.gridy = 1;
        add(progressBar, gbc);
        gbc.gridy = 2;
        add(btnCancel, gbc);
    }

    public void setCancelAction(Runnable r) {
        this.cancelAction = r;
    }

    public void updateProgress(int value) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(value));
    }

    public void showComplete() {
        labelStatus.setText("Hoàn tất mã hóa!");
        btnCancel.setText("Quay lại menu");
    }

    public void showCancelled() {
        labelStatus.setText("Đã hủy tiến trình.");
    }

    public void startProgress() {
        progressBar.setValue(0);
        labelStatus.setText("Đang mã hóa...");
        btnCancel.setText("Hủy và trở về menu");
    }

    public void stopProgress() {
        setVisible(false);
    }
}
