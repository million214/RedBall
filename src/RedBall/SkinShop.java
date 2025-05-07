package RedBall;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class SkinShop extends JFrame {
    private JLabel scoreLabel;
    private String[] skins = {"saphia.png", "yellow.png", "greenball.png"};

    public SkinShop() {
        setTitle("Cửa hàng Skin");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/images/SkinShop.png");
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Nhãn điểm số
        scoreLabel = new JLabel("Điểm: " + RedBall.totalScore, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(Color.BLACK);
        backgroundPanel.add(scoreLabel, BorderLayout.NORTH);

        JPanel woodPanel = new JPanel();
        woodPanel.setOpaque(false);
        woodPanel.setLayout(new BoxLayout(woodPanel, BoxLayout.Y_AXIS));
        woodPanel.setBorder(BorderFactory.createEmptyBorder(80, 30, 30, 30)); // Cách trên 80px
        woodPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        woodPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        backgroundPanel.add(woodPanel, BorderLayout.CENTER);

        // Thêm khoảng cách lớn hơn phía trên skin
        woodPanel.add(Box.createVerticalStrut(80)); 

        JPanel skinsPanel = new JPanel();
        skinsPanel.setOpaque(false);
        skinsPanel.setLayout(new BoxLayout(skinsPanel, BoxLayout.Y_AXIS));
        for (String skin : skins) {
            skinsPanel.add(createSkinBox(skin));
        }
        woodPanel.add(skinsPanel);

        // Nút "Quay về"
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/images/exit.jpg"));
        Image scaledBackImage = backIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH); 
        JButton backButton = new JButton(new ImageIcon(scaledBackImage));
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        // Sự kiện cho nút "Quay về"
        backButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createSkinBox(String skin) {
        URL imgUrl = getClass().getResource("/images/" + skin);
        if (imgUrl == null) {
            System.out.println("Không tìm thấy ảnh: " + skin);
            return new JPanel();
        }

        ImageIcon icon = new ImageIcon(imgUrl);
        Image scaledImage = icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nút "Mua với 5 điểm"
        JButton skinButton = new JButton("Mua với 5 điểm");
        skinButton.setFont(new Font("Arial", Font.BOLD, 14));
        skinButton.setPreferredSize(new Dimension(120, 40));
        skinButton.setBackground(new Color(50, 150, 255));
        skinButton.setForeground(Color.WHITE);
        skinButton.setFocusPainted(false);
        skinButton.setBorder(BorderFactory.createEmptyBorder());
        skinButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        skinButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hiệu ứng bo tròn và chuyển màu nền
        skinButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(50, 150, 255), 0, c.getHeight(), new Color(70, 180, 255));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                super.paint(g, c);
            }
        });

        // Thêm hiệu ứng hover
        skinButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                skinButton.setBackground(new Color(70, 180, 255)); // Sáng hơn khi hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                skinButton.setBackground(new Color(50, 150, 255)); // Quay lại màu ban đầu
            }
        });

        // Xử lý sự kiện nút mua skin
        skinButton.addActionListener(e -> buySkin(skin));

        JPanel skinBox = new JPanel();
        skinBox.setLayout(new BoxLayout(skinBox, BoxLayout.Y_AXIS));
        skinBox.setOpaque(false);
        skinBox.add(imageLabel);
        skinBox.add(Box.createVerticalStrut(10));
        skinBox.add(skinButton);

        return skinBox;
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

    // Lớp panel nền
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            URL imageURL = getClass().getResource(imagePath);
            if (imageURL != null) {
                backgroundImage = new ImageIcon(imageURL).getImage();
            } else {
                System.out.println("Không tìm thấy ảnh nền: " + imagePath);
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

    public static void main(String[] args) {
        new SkinShop();
    }
}
