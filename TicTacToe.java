  import javax.swing.*;
  import java.awt.*;
  import java.awt.event.*;

  public class TicTacToe extends JFrame {
      private JButton[][] buttons = new JButton[3][3];
      private char[][] board = new char[3][3];
      private char currentPlayer = 'X';

      public TicTacToe() {
          setTitle("Tic Tac Toe");
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setResizable(false);

          JPanel gamePanel = new JPanel(new GridLayout(3, 3));
          initializeBoard();

          for (int i = 0; i < 3; i++) {
              for (int j = 0; j < 3; j++) {
                  buttons[i][j] = new JButton("");
                  buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                  buttons[i][j].setFocusPainted(false);
                  final int row = i;
                  final int col = j;
                  buttons[i][j].addActionListener(e -> makeMove(row, col));
                  gamePanel.add(buttons[i][j]);
              }
          }

          add(gamePanel);
          pack();
          setSize(400, 400);
          setLocationRelativeTo(null);
      }

      private void initializeBoard() {
          for (int i = 0; i < 3; i++) {
              for (int j = 0; j < 3; j++) {
                  board[i][j] = ' ';
              }
          }
      }

      private void makeMove(int row, int col) {
          if (board[row][col] == ' ') {
              board[row][col] = currentPlayer;
              buttons[row][col].setText(String.valueOf(currentPlayer));
              buttons[row][col].setEnabled(false);

              if (checkWin()) {
                  JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                  resetGame();
              } else if (isBoardFull()) {
                  JOptionPane.showMessageDialog(this, "It's a tie!");
                  resetGame();
              } else {
                  currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
              }
          }
      }

      private void resetGame() {
          initializeBoard();
          for (int i = 0; i < 3; i++) {
              for (int j = 0; j < 3; j++) {
                  buttons[i][j].setText("");
                  buttons[i][j].setEnabled(true);
              }
          }
          currentPlayer = 'X';
      }

      private boolean checkWin() {
          // Check rows
          for (int i = 0; i < 3; i++) {
              if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                  return true;
              }
          }

          // Check columns
          for (int j = 0; j < 3; j++) {
              if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                  return true;
              }
          }

          // Check diagonals
          if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
              return true;
          }
          if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
              return true;
          }

          return false;
      }

      private boolean isBoardFull() {
          for (int i = 0; i < 3; i++) {
              for (int j = 0; j < 3; j++) {
                  if (board[i][j] == ' ') {
                      return false;
                  }
              }
          }
          return true;
      }

      public static void main(String[] args) {
          SwingUtilities.invokeLater(() -> {
              new TicTacToe().setVisible(true);
          });
      }
  }
