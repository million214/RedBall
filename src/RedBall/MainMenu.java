package RedBall;

import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame {
    private JLabel totalScoreLabel;
    private Image backgroundImage;
    private Timer colorTimer;
    private int colorIndex = 0;
    private Color[] rainbowColors = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, new Color(75, 0, 130), new Color(143, 0, 255) };

    public MainMenu() {
        // Cài đặt giao diện chính của menu
        setTitle("RedBall - Menu");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Phát nhạc nền khi vào MainMenu
        SoundManager.playBackgroundMusic("background_music.wav");

        // Tải ảnh nền
        backgroundImage = new ImageIcon(getClass().getResource("/images/menu.png")).getImage();

        // Tạo JPanel để vẽ ảnh nền
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề của menu
        JLabel titleLabel = new JLabel("RedBall Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        colorTimer = new Timer(300, e -> {
            colorIndex = (colorIndex + 1) % rainbowColors.length;
            titleLabel.setForeground(rainbowColors[colorIndex]);
        });
        colorTimer.start();

        gbc.gridy = 0;
        gbc.insets = new Insets(30, 10, 30, 10);  // Điều chỉnh khoảng cách cho tiêu đề
        panel.add(titleLabel, gbc);

        // Hiển thị tổng điểm
        totalScoreLabel = new JLabel("Tổng điểm: " + RedBall.totalScore, SwingConstants.CENTER);
        totalScoreLabel.setFont(new Font("Arial", Font.BOLD, 22));
        totalScoreLabel.setForeground(Color.WHITE);
        JPanel scorePanel = new JPanel();
        scorePanel.setOpaque(false);
        scorePanel.setLayout(new BorderLayout());
        scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scorePanel.add(totalScoreLabel, BorderLayout.CENTER);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 40, 20, 40);  // Điều chỉnh khoảng cách quanh điểm
        panel.add(wrapRoundedPanel(scorePanel, new Color(0, 0, 0, 180)), gbc);

        // Tạo các nút chức năng
        gbc.insets = new Insets(20, 40, 20, 40);  // Tăng khoảng cách giữa các nút
        gbc.gridy = 2;
        panel.add(createRoundedButton("Chơi", Color.GREEN), gbc);

        gbc.gridy = 3;
        panel.add(createRoundedButton("Mua Skin", Color.ORANGE), gbc);

        gbc.gridy = 4;
        panel.add(createRoundedButton("Thông tin tác giả", Color.CYAN), gbc);

        add(panel, BorderLayout.CENTER);
    }

    // Hàm tạo một panel bo tròn
    private JPanel wrapRoundedPanel(JComponent component, Color bgColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    // Hàm tạo nút bo tròn
    private JPanel createRoundedButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);

        JPanel roundedPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        roundedPanel.setOpaque(false);
        roundedPanel.setLayout(new BorderLayout());
        roundedPanel.add(button, BorderLayout.CENTER);

        // Xử lý sự kiện cho các nút
        if (text.equals("Chơi")) {
            button.addActionListener(e -> {
                dispose(); // Đóng cửa menu
                RedBall.main(new String[]{});  // Bắt đầu trò chơi
                SoundManager.stopBackgroundMusic(); // Dừng nhạc nền
            });
        } else if (text.equals("Mua Skin")) {
            button.addActionListener(e -> {
                SkinShop skinShop = new SkinShop();
                skinShop.setVisible(true);
                skinShop.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        SoundManager.playBackgroundMusic("background_music.wav"); // Phát lại nhạc nền khi đóng cửa sổ
                    }
                });
                SoundManager.stopBackgroundMusic(); // Dừng nhạc nền
            });
        } else {
            button.addActionListener(e -> {
                AuthorInfo authorInfo = new AuthorInfo();
                authorInfo.setVisible(true);
                authorInfo.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        SoundManager.playBackgroundMusic("background_music.wav"); // Phát lại nhạc nền khi đóng cửa sổ
                    }
                });
                SoundManager.stopBackgroundMusic(); // Dừng nhạc nền
            });
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(roundedPanel, BorderLayout.CENTER);
        return wrapper;
    }

    // Main method để chạy ứng dụng
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
