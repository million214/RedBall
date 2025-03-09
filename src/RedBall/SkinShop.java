package RedBall;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class SkinShop extends JFrame {
    private JPanel panel;
    private JButton backButton;
    private JLabel scoreLabel;
    private String[] skins = {"redball.png", "blueball.png", "greenball.png"}; // Danh sách skin
    
    public SkinShop() {
        setTitle("Cửa hàng Skin");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(skins.length, 1, 10, 10)); // Hiển thị ảnh theo hàng dọc
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm lề cho bố cục
        
        scoreLabel = new JLabel("Điểm: " + RedBall.totalScore, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel, BorderLayout.NORTH);
        
        for (String skin : skins) {
            URL imgUrl = getClass().getResource("/images/" + skin);
            if (imgUrl == null) {
                System.out.println("Không tìm thấy ảnh: " + skin);
                continue;
            }
            ImageIcon icon = new ImageIcon(imgUrl);
            Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Giảm kích thước ảnh
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);
            JLabel textLabel = new JLabel("Mua với 5 điểm", SwingConstants.CENTER);
            textLabel.setFont(new Font("Arial", Font.BOLD, 12));
            JButton skinButton = new JButton("Mua");
            skinButton.setPreferredSize(new Dimension(100, 30));
            skinButton.addActionListener(e -> buySkin(skin));
            
            JPanel skinPanel = new JPanel();
            skinPanel.setLayout(new BorderLayout());
            skinPanel.add(imageLabel, BorderLayout.CENTER);
            skinPanel.add(textLabel, BorderLayout.SOUTH);
            skinPanel.add(skinButton, BorderLayout.NORTH);
            panel.add(skinPanel);
        }
        
        add(panel, BorderLayout.CENTER);
        
        backButton = new JButton("Quay về");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(e -> dispose());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void buySkin(String skin) {
        if (RedBall.totalScore >= 5) {
            RedBall.totalScore -= 5;
            RedBall.setSelectedSkin(skin);
            JOptionPane.showMessageDialog(this, "Mua thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            scoreLabel.setText("Điểm: " + RedBall.totalScore);
        } else {
            JOptionPane.showMessageDialog(this, "Không đủ điểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        new SkinShop();
    }
}
