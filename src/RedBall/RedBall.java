package RedBall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class RedBall extends JComponent implements ActionListener, MouseMotionListener, KeyListener, MouseListener {

    // Quản lý vị trí và tốc độ quả bóng
    private int ballx = 150; 
    private int bally = 30;  
    private int ballxSpeed = 5; 
    private int ballySpeed = 7; 

    // Quản lý vị trí và tốc độ của paddle (bàn đạp)
    private int paddlex = 350; 
    private int paddleSpeed = 10; 

    private static final int BALL_SIZE = 50; // Kích thước của quả bóng

    // Biến quản lý điểm số và trạng thái trò chơi
    public int score = 0; 
    public boolean gameOver; 

    private boolean moveLeft = false, moveRight = false; // Cờ điều khiển di chuyển paddle

    private JButton backButton; // Nút quay về màn hình chính
    public static int totalScore = 0; 
    public static String selectedSkin = "redball.png"; // Skin quả bóng được chọn
    private Image ballImage; 

    private SoundManager soundManager; // Quản lý âm thanh

    // Hàm main - Khởi tạo cửa sổ và game
    public static void main(String[] args) {
        JFrame wind = new JFrame("RedBall/GamePinfo");
        RedBall g = new RedBall();

        wind.setFocusable(true); // Đảm
        wind.add(g); // Thêm game component vào cửa sổ
        wind.pack(); // Tự động điều chỉnh kích thước của cửa sổ
        wind.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Đóng cửa khi thoát
        wind.setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
        wind.setVisible(true); // Hiển thị cửa sổ

        // Lắng nghe sự kiện chuột và bàn phím
        wind.addMouseMotionListener(g); 
        wind.addKeyListener(g); 
        wind.addMouseListener(g);

        // Khởi tạo nút quay về
        g.initBackButton(wind);

        // Cập nhật game mỗi 17 ms (khoảng 60 FPS)
        Timer tt = new Timer(17, g); 
        tt.start();
    }

    public RedBall() {
        soundManager = new SoundManager(); // Khởi tạo đối tượng quản lý âm thanh
        loadBallImage(); // Tải hình ảnh quả bóng
    }

    // Tải hình ảnh quả bóng từ file
    public void loadBallImage() {
        String imagePath = "/images/" + selectedSkin; // Đường dẫn tới ảnh
        try {
            ballImage = new ImageIcon(getClass().getResource(imagePath)).getImage(); // Tải ảnh
            if (ballImage == null) throw new Exception("Ảnh null");
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh từ: " + imagePath);
            e.printStackTrace();
            ballImage = null;
        }
    }

    // Cập nhật skin của quả bóng
    public static void setSelectedSkin(String newSkin) {
        selectedSkin = newSkin;
        new RedBall().loadBallImage(); // Tải lại hình ảnh mới
    }

    // Khởi tạo nút quay về màn hình chính
    private void initBackButton(JFrame wind) {
        backButton = new JButton("Quay về");
        backButton.setBounds(10, 10, 100, 30); // Đặt vị trí và kích thước của nút
        backButton.addActionListener(e -> {
            new MainMenu().setVisible(true); // Hiển thị màn hình chính
            wind.dispose(); // Đóng cửa sổ game
        });
        wind.setLayout(null); // Thiết lập layout của cửa sổ
        wind.add(backButton); // Thêm nút vào cửa sổ
    }

    // Trả về kích thước của component (màn hình game)
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600); // Kích thước màn hình game
    }

    // Vẽ lại màn hình game mỗi khi có thay đổi
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ nền
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 800, 600);

        // Vẽ mặt đất
        g.setColor(Color.GREEN);
        g.fillRect(0, 550, 800, 100);

        // Vẽ paddle
        g.setColor(Color.black);
        g.fillRect(paddlex, 500, 100, 20);

        // Vẽ quả bóng
        if (ballImage != null) {
            g.drawImage(ballImage, ballx, bally, BALL_SIZE, BALL_SIZE, this); // Nếu có hình ảnh, vẽ quả bóng
        } else {
            g.setColor(Color.RED);
            g.fillOval(ballx, bally, BALL_SIZE, BALL_SIZE); // Nếu không có ảnh, vẽ quả bóng đỏ
        }

        // Vẽ điểm số
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString(String.valueOf(score), 30, 80);

        // Nếu game over, hiển thị thông báo
        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over!", 300, 200);
            g.drawString("Điểm số của bạn: " + score, 300, 250);
            g.drawString("Nhấn R để chơi lại", 300, 300);
        }
    }

    // Cập nhật trạng thái trò chơi mỗi khi có sự kiện
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            // Cập nhật vị trí quả bóng
            ballx += ballxSpeed;
            bally += ballySpeed;

            // Điều khiển paddle di chuyển
            if (moveLeft) paddlex = Math.max(0, paddlex - paddleSpeed);
            if (moveRight) paddlex = Math.min(700, paddlex + paddleSpeed);

            // Kiểm tra va chạm giữa quả bóng và paddle
            if (ballx + 30 >= paddlex && ballx <= paddlex + 100 && bally + 30 >= 500 && bally + 30 <= 510) {
                ballySpeed = -7; // Đảo chiều dọc của quả bóng
                score++; // Tăng điểm
                soundManager.playSound("jump.wav"); // Phát âm thanh khi quả bóng nhảy lên

                // Tăng tốc quả bóng sau mỗi 5 điểm
                if (score % 5 == 0) {
                    if (ballxSpeed > 0) ballxSpeed += 2;
                    else ballxSpeed -= 2;

                    if (ballySpeed < 0) ballySpeed -= 2;
                    else ballySpeed += 2;

                    if (Math.abs(ballxSpeed) > 25) ballxSpeed = ballxSpeed > 0 ? 25 : -25;
                    if (Math.abs(ballySpeed) > 25) ballySpeed = ballySpeed > 0 ? 25 : -25;
                }
            }

            // Kiểm tra điều kiện game over (quả bóng rơi xuống dưới)
            if (bally >= 550) {
                gameOver = true;
                totalScore += score; // Cập nhật điểm tổng
                ((Timer) e.getSource()).stop(); // Dừng timer
                JOptionPane.showMessageDialog(this, "Game Over! Điểm số của bạn: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                new MainMenu().setVisible(true); // Quay về menu chính
                JFrame frame = (JFrame) this.getParent().getParent().getParent().getParent();
                if (frame != null) frame.dispose(); // Đóng cửa sổ game
            }

            // Kiểm tra va chạm với tường và thay đổi hướng bóng
            if (bally <= 0) ballySpeed = 7;
            if (ballx >= 775) ballxSpeed = -5;
            if (ballx <= 0) ballxSpeed = 5;

            repaint(); // Vẽ lại màn hình
        }
    }

    // Reset trò chơi
    private void resetGame() {
        ballx = 150; // Vị trí quả bóng
        bally = 30;
        ballxSpeed = 5; // Tốc độ quả bóng
        ballySpeed = 7;
        paddlex = 350; // Vị trí paddle
        score = 0; // Điểm số
        gameOver = false; // Trạng thái trò chơi
        moveLeft = false; // Dừng di chuyển paddle
        moveRight = false;

        Timer tt = new Timer(17, this); // Khởi tạo lại timer
        tt.start();
        repaint(); // Vẽ lại màn hình
    }

    // Điều khiển di chuyển paddle bằng chuột
    @Override
    public void mouseMoved(MouseEvent e) {
        paddlex = e.getX() - 50; // Đặt paddle theo vị trí chuột
        repaint();
    }

    // Các phương thức không dùng đến, bỏ qua
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    // Các phương thức để xử lý sự kiện bàn phím
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame(); // Chơi lại khi nhấn R
        } else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft = true; // Di chuyển paddle sang trái
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight = true; // Di chuyển paddle sang phải
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft = false; // Dừng di chuyển paddle sang trái
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight = false; // Dừng di chuyển paddle sang phải
        }
    }

    // Các phương thức không dùng đến, bỏ qua
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
