package RedBall;

import java.awt.*;
import javax.swing.*;

public class AuthorInfo extends JFrame {
    public AuthorInfo() {
        // Cài đặt giao diện cửa sổ thông tin tác giả
        setTitle("Thông tin nhà phát triển");
        setSize(500, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo BackgroundPanel để vẽ nền
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(70, 140, 50, 140)); 
        // Tạo khoảng trống: top, left, bottom, right

        // Ảnh tác giả
        ImageIcon authorImage = new ImageIcon(getClass().getResource("/images/author.jpg"));
        Image scaledImage = authorImage.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thông tin tác giả
        JLabel nameLabel = new JLabel("Tác giả: Bùi Văn Triệu");
        JLabel emailLabel = new JLabel("Email: unlcp001@gmail.com");
        JLabel githubLabel = new JLabel("GitHub: github.com/million214");

        Font textFont = new Font("Arial", Font.BOLD, 18);
        nameLabel.setFont(textFont);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        githubLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Màu sắc văn bản
        nameLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        githubLabel.setForeground(Color.WHITE);

        // Căn giữa các nhãn
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        githubLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tạo nút quay lại
        JButton backButton = new JButton();
        backButton.setPreferredSize(new Dimension(120, 50));
        backButton.setMaximumSize(new Dimension(120, 50));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa

        try {
            ImageIcon exitIcon = new ImageIcon(getClass().getResource("/images/exit.jpg"));
            Image scaledExitImage = exitIcon.getImage().getScaledInstance(120, 50, Image.SCALE_SMOOTH);
            backButton.setIcon(new ImageIcon(scaledExitImage));
            backButton.setBorderPainted(false);
            backButton.setContentAreaFilled(false);
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh exit.jpg");
        }

        // Sự kiện khi nhấn nút quay lại
        backButton.addActionListener(e -> dispose());

        // Sắp xếp nội dung trên giao diện
        backgroundPanel.add(Box.createVerticalStrut(90)); // Khoảng trống phía trên
        backgroundPanel.add(imageLabel);
        backgroundPanel.add(Box.createVerticalStrut(40)); // Khoảng trống giữa ảnh và tên
        backgroundPanel.add(nameLabel);
        backgroundPanel.add(Box.createVerticalStrut(10)); // Khoảng trống giữa các nhãn
        backgroundPanel.add(emailLabel);
        backgroundPanel.add(Box.createVerticalStrut(10)); // Khoảng trống giữa các nhãn
        backgroundPanel.add(githubLabel);
        backgroundPanel.add(Box.createVerticalGlue()); // Đẩy nút xuống đáy
        backgroundPanel.add(backButton);

        setContentPane(backgroundPanel);
    }

    // Lớp BackgroundPanel để vẽ nền cho cửa sổ
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                // Tải ảnh nền
                backgroundImage = new ImageIcon(getClass().getResource("/images/TacGia.png")).getImage();
            } catch (Exception e) {
                System.out.println("Không thể tải ảnh nền TacGia.png");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Phương thức main để chạy ứng dụng
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuthorInfo().setVisible(true));
    }
}
