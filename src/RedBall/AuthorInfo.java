package RedBall;

import java.awt.*;
import javax.swing.*;

public class AuthorInfo extends JFrame {
    public AuthorInfo() {
        setTitle("Thông tin nhà phát triển");
        setSize(400, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Khoảng cách viền ngoài

        // Thêm ảnh tác giả, phóng to ảnh và căn giữa
        ImageIcon authorImage = new ImageIcon(getClass().getResource("/images/author.jpg"));
        Image scaledImage = authorImage.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        // Thông tin tác giả (tên, email, GitHub gần với ảnh)
        JLabel nameLabel = new JLabel("Tác giả: Bùi Văn Triệu", SwingConstants.CENTER);
        JLabel emailLabel = new JLabel("Email: unlcp001@gmail.com", SwingConstants.CENTER);
        JLabel githubLabel = new JLabel("GitHub: github.com/million214", SwingConstants.CENTER);

        // Định dạng font chữ
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        githubLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Căn chỉnh các thành phần
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        githubLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Gộp ảnh + thông tin vào panel chung để giữ bố cục gọn gàng
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(imageLabel);
        infoPanel.add(Box.createVerticalStrut(10)); // Khoảng cách giữa ảnh và tên
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5)); // Khoảng cách giữa tên và email
        infoPanel.add(emailLabel);
        infoPanel.add(Box.createVerticalStrut(5)); // Khoảng cách giữa email và GitHub
        infoPanel.add(githubLabel);

        // Nút quay lại
        JButton backButton = new JButton("Quay lại");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(140, 40));
        backButton.setMaximumSize(new Dimension(140, 40)); // Giữ kích thước cố định
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> dispose());

        // Thêm các thành phần vào panel chính
        panel.add(infoPanel);
        panel.add(Box.createVerticalStrut(20)); // Khoảng cách trước nút bấm
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuthorInfo().setVisible(true));
    }
}
