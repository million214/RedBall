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

    private int ballx = 150;
    private int bally = 30;
    private int ballxSpeed = 5;
    private int ballySpeed = 7;
    
    private int paddlex = 350;
    private int paddleSpeed = 10;
    
    private static final int BALL_SIZE = 50; // Kích thước mới của bóng

    
    public int score = 0;
    public boolean gameOver;
    
    private boolean moveLeft = false, moveRight = false;
    
    private JButton backButton;
    public static int totalScore = 0;
    public static String selectedSkin = "redball.png"; // Mặc định là bóng đỏ
    private Image ballImage;

    public static void main(String[] args) {
        JFrame wind = new JFrame("RedBall/GamePinfo");
        RedBall g = new RedBall();
        
        wind.setFocusable(true);
        wind.requestFocus();
        
        wind.add(g);
        wind.pack();
        wind.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        wind.setLocationRelativeTo(null);
        wind.setVisible(true);
        wind.addMouseMotionListener(g);
        wind.addKeyListener(g);
        wind.addMouseListener(g);

        g.initBackButton(wind);
        
        Timer tt = new Timer(17, g);
        tt.start();
    }

    public RedBall() {
        loadBallImage();
    }

    public void loadBallImage() {
        String imagePath = "/images/" + selectedSkin;
        try {
            ballImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            if (ballImage == null) throw new Exception("Ảnh null");
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh từ: " + imagePath);
            e.printStackTrace();
            ballImage = null;
        }
    }


    public static void setSelectedSkin(String newSkin) {
        selectedSkin = newSkin;
        new RedBall().loadBallImage(); // Load lại ảnh ngay khi đổi skin
    }


    private void initBackButton(JFrame wind) {
        backButton = new JButton("Quay về");
        backButton.setBounds(10, 10, 100, 30);
        backButton.addActionListener(e -> {
            new MainMenu().setVisible(true);
            wind.dispose();
        });
        wind.setLayout(null);
        wind.add(backButton);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.cyan);
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.GREEN);
        g.fillRect(0, 550, 800, 100);

        g.setColor(Color.black);
        g.fillRect(paddlex, 500, 100, 20);

        if (ballImage != null) {
            g.drawImage(ballImage, ballx, bally, 50, 50, this);
        } else {
            g.setColor(Color.RED);
            g.fillOval(ballx, bally, 50, 50);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString(String.valueOf(score), 30, 80);

        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over!", 300, 200);
            g.drawString("Điểm số của bạn: " + score, 300, 250);
            g.drawString("Nhấn R để chơi lại", 300, 300);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            ballx += ballxSpeed;
            bally += ballySpeed;

            if (moveLeft) {
                paddlex = Math.max(0, paddlex - paddleSpeed);
            }
            if (moveRight) {
                paddlex = Math.min(700, paddlex + paddleSpeed);
            }

            if (ballx + 30 >= paddlex && ballx <= paddlex + 100 && bally + 30 >= 500 && bally + 30 <= 510) {
                ballySpeed = -7;
                score++;
            }

            if (bally >= 550) {
                gameOver = true;
                totalScore += score;
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(this, "Game Over! Điểm số của bạn: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                new MainMenu().setVisible(true);
                JFrame frame = (JFrame) this.getParent().getParent().getParent().getParent();
                if (frame != null) {
                    frame.dispose();
                }
            }

            if (bally <= 0) {
                ballySpeed = 7;
            }
            if (ballx >= 775) {
                ballxSpeed = -5;
            }
            if (ballx <= 0) {
                ballxSpeed = 5;
            }

            repaint();
        }
    }

    private void resetGame() {
        ballx = 150;
        bally = 30;
        ballxSpeed = 5;
        ballySpeed = 7;
        paddlex = 350;
        score = 0;
        gameOver = false;
        moveLeft = false;
        moveRight = false;
        
        Timer tt = new Timer(17, this);
        tt.start();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        paddlex = e.getX() - 50;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        } 
        else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft = true;
        } 
        else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
