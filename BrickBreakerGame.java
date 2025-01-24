  // BrickBreakerGame.java
  import javax.swing.*;
  import java.awt.*;
  import java.awt.event.*;

  class MapGenerator {
      public int map[][];
      public int brickWidth;
      public int brickHeight;
      
      public MapGenerator(int row, int col) {
          map = new int[row][col];
          for(int i = 0; i < map.length; i++) {
              for(int j = 0; j < map[0].length; j++) {
                  map[i][j] = 1;
              }
          }
          brickWidth = 540/col;
          brickHeight = 150/row;
      }
      
      public void draw(Graphics2D g) {
          for(int i = 0; i < map.length; i++) {
              for(int j = 0; j < map[0].length; j++) {
                  if(map[i][j] > 0) {
                      g.setColor(new Color(65, 105, 225)); // Royal Blue
                      g.fillRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth - 2, brickHeight - 2, 15, 15);
                      
                      g.setStroke(new BasicStroke(2));
                      g.setColor(new Color(176, 196, 222)); // Light Steel Blue
                      g.drawRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth - 2, brickHeight - 2, 15, 15);
                  }
              }
          }
      }
      
      public void setBrickValue(int value, int row, int col) {
          map[row][col] = value;
      }
  }

  public class BrickBreakerGame extends JPanel implements KeyListener, ActionListener {
      private boolean play = false;
      private int score = 0;
      private int totalBricks = 21;
      private Timer timer;
      private int delay = 8;
      private int playerX = 310;
      private int ballposX = 120;
      private int ballposY = 350;
      private int ballXdir = -1;
      private int ballYdir = -2;
      private MapGenerator map;

      public BrickBreakerGame() {
          map = new MapGenerator(3, 7);
          addKeyListener(this);
          setFocusable(true);
          setFocusTraversalKeysEnabled(false);
          timer = new Timer(delay, this);
          timer.start();
      }

      @Override
      protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          
          // background - dark gradient
          Graphics2D g2d = (Graphics2D) g;
          GradientPaint gradient = new GradientPaint(0, 0, new Color(25, 25, 112), 
                                                    getWidth(), getHeight(), new Color(0, 0, 51));
          g2d.setPaint(gradient);
          g2d.fillRect(1, 1, 692, 592);

          // drawing map
          map.draw(g2d);

          // borders
          g2d.setColor(new Color(255, 215, 0, 150)); // Semi-transparent gold
          g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
          g2d.drawRect(0, 0, 691, 591);

          // scores
          g2d.setColor(new Color(240, 248, 255)); // Alice Blue
          g2d.setFont(new Font("Helvetica", Font.BOLD, 25));
          g2d.drawString("Score: " + score, 590, 30);

          // paddle
          GradientPaint paddleGradient = new GradientPaint(playerX, 550, new Color(50, 205, 50),
                                                          playerX + 100, 558, new Color(34, 139, 34));
          g2d.setPaint(paddleGradient);
          g2d.fillRoundRect(playerX, 550, 100, 8, 10, 10);

          // ball
          g2d.setColor(new Color(255, 223, 0)); // Bright yellow
          g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          g2d.fillOval(ballposX, ballposY, 20, 20);

          if(totalBricks <= 0) {
              play = false;
              ballXdir = 0;
              ballYdir = 0;
              g2d.setColor(new Color(255, 69, 0)); // Red-Orange
              g2d.setFont(new Font("Helvetica", Font.BOLD, 30));
              g2d.drawString("You Are The Real Skibidi" + score, 260, 300);
            
              g2d.setFont(new Font("Helvetica", Font.BOLD, 20));
              g2d.drawString("Press Enter to restart the USSR", 230, 350);
          }

          if(ballposY > 570) {
              play = false;
              ballXdir = 0;
              ballYdir = 0;
              g2d.setColor(new Color(255, 69, 0)); // Red-Orange
              g2d.setFont(new Font("Helvetica", Font.BOLD, 30));
              g2d.drawString("Game Over! Score: " + score, 190, 300);
            
              g2d.setFont(new Font("Helvetica", Font.BOLD, 20));
              g2d.drawString("Press Enter to Restart", 230, 350);
          }
      }

      @Override
      public void actionPerformed(ActionEvent e) {
          timer.start();
          if(play) {
              // Ball-Paddle interaction
              Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
              Rectangle paddleRect = new Rectangle(playerX, 550, 100, 8);

              if(ballRect.intersects(paddleRect)) {
                  ballYdir = -ballYdir;
              }

              // Ball-Brick interaction
              A: for(int i = 0; i < map.map.length; i++) {
                  for(int j = 0; j < map.map[0].length; j++) {
                      if(map.map[i][j] > 0) {
                          int brickX = j * map.brickWidth + 80;
                          int brickY = i * map.brickHeight + 50;
                          int brickWidth = map.brickWidth;
                          int brickHeight = map.brickHeight;

                          Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);

                          if(ballRect.intersects(brickRect)) {
                              map.setBrickValue(0, i, j);
                              totalBricks--;
                              score += 5;

                              if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                  ballXdir = -ballXdir;
                              } else {
                                  ballYdir = -ballYdir;
                              }
                              break A;
                          }
                      }
                  }
              }

              ballposX += ballXdir;
              ballposY += ballYdir;
              if(ballposX < 0) {
                  ballXdir = -ballXdir;
              }
              if(ballposY < 0) {
                  ballYdir = -ballYdir;
              }
              if(ballposX > 670) {
                  ballXdir = -ballXdir;
              }
          }
          repaint();
      }

      @Override
      public void keyPressed(KeyEvent e) {
          if(e.getKeyCode() == KeyEvent.VK_D) {
              if(playerX >= 600) {
                  playerX = 600;
              } else {
                  moveRight();
              }
          }
          if(e.getKeyCode() == KeyEvent.VK_A) {
            
              if(playerX < 10) {
                  playerX = 10;
              } else {
                  moveLeft();
              }
          }
          if(e.getKeyCode() == KeyEvent.VK_ENTER) {
              if(!play) {
                  play = true;
                  ballposX = 120;
                  ballposY = 350;
                  ballXdir = -1;
                  ballYdir = -2;
                  score = 0;
                  totalBricks = 21;
                  map = new MapGenerator(3, 7);
                  repaint();
              }
          }
      }

      public void moveRight() {
          play = true;
          playerX += 20;
      }

      public void moveLeft() {
          play = true;
          playerX -= 20;
      }

      @Override
      public void keyReleased(KeyEvent e) {}

      @Override
      public void keyTyped(KeyEvent e) {}

      public static void main(String[] args) {
          JFrame frame = new JFrame();
          BrickBreakerGame gamePlay = new BrickBreakerGame();
          frame.setBounds(10, 10, 700, 600);
          frame.setTitle("Brick Breaker");
          frame.setResizable(false);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.add(gamePlay);
          frame.setVisible(true);
      }
  }