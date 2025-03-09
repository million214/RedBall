package RedBall;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer;

public class MainMenu extends JFrame {
    private JLabel totalScoreLabel;
    private Image backgroundImage;
    private Timer colorTimer;
    private int colorIndex = 0;
    private Color[] rainbowColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, new Color(75, 0, 130), new Color(143, 0, 255)};
    
    public MainMenu() {
        setTitle("RedBall - Menu");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Load background image từ thư mục images
        backgroundImage = new ImageIcon(getClass().getResource("/images/redball.jpg")).getImage();
        
        // Tạo panel nền với hình ảnh
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("RedBall Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 35));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        
        // Tạo hiệu ứng đổi màu cầu vồng
        colorTimer = new Timer(300, e -> {
            colorIndex = (colorIndex + 1) % rainbowColors.length;
            titleLabel.setForeground(rainbowColors[colorIndex]);
        });
        colorTimer.start();
        
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 10, 10); // Đẩy chữ sát gần khung trên
        panel.add(titleLabel, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        totalScoreLabel = new JLabel("Tổng điểm: " + RedBall.totalScore, SwingConstants.CENTER);
        totalScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalScoreLabel.setForeground(Color.WHITE);
        panel.add(totalScoreLabel, gbc);
        
        JButton playButton = new JButton("Chơi");
        JButton shopButton = new JButton("Mua Skin");
        JButton authorButton = new JButton("Thông tin tác giả");
        
        playButton.setFont(new Font("Arial", Font.BOLD, 20));
        shopButton.setFont(new Font("Arial", Font.BOLD, 20));
        authorButton.setFont(new Font("Arial", Font.BOLD, 20));
        
        playButton.setBackground(Color.GREEN);
        playButton.setForeground(Color.WHITE);
        playButton.setFocusPainted(false);
        
        shopButton.setBackground(Color.ORANGE);
        shopButton.setForeground(Color.WHITE);
        shopButton.setFocusPainted(false);
        
        authorButton.setBackground(Color.CYAN);
        authorButton.setForeground(Color.BLACK);
        authorButton.setFocusPainted(false);
        
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng menu
                RedBall.main(new String[]{}); // Mở game
            }
        });
        
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SkinShop().setVisible(true);
            }
        });
        
        authorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AuthorInfo().setVisible(true);
            }
        });
        
        gbc.gridy = 2;
        panel.add(playButton, gbc);
        gbc.gridy = 3;
        panel.add(shopButton, gbc);
        gbc.gridy = 4;
        panel.add(authorButton, gbc);
        
        add(panel, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}
