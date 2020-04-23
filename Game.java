import java.util.Scanner;

/**
* Class Game: contains game logic
*/

public class Game {
  static Bot bot;
  static HumanPlayer humanPlayer = new HumanPlayer();
  static Scanner sc = new Scanner(System.in);
  static char[][] board;
  static int dimension;
  static char humanPieces;


  // helper method to print the border of the board
  public static void printBorder(int dimension) {
      if(dimension == 5) {
          System.out.println(" +-+-+-+-+");
      } else {
          System.out.println(" +-+-+-+-+-+-+-+-+");
      }
  }

  // method to print the board
  public static void printboard() {
      if (board.length == 5) {
          System.out.println("  1 2 3 4");
          printBorder(5);
          System.out.println("A|" + board[1][1] + "|" + board[1][2] + "|" + board[1][3] + "|" + board[1][4] + "|");
          printBorder(5);
          System.out.println("B|" + board[2][1] + "|" + board[2][2] + "|" + board[2][3] + "|" + board[2][4] + "|");
          printBorder(5);
          System.out.println("C|" + board[3][1] + "|" + board[3][2] + "|" + board[3][3] + "|" + board[3][4] + "|");
          printBorder(5);
          System.out.println("D|" + board[4][1] + "|" + board[4][2] + "|" + board[4][3] + "|" + board[4][4] + "|");
          printBorder(5);
      } else {
          System.out.println("  1 2 3 4 5 6 7 8");
          printBorder(8);
          System.out.println("A|" + board[1][1] + "|" + board[1][2] + "|" + board[1][3] + "|" + board[1][4] + "|" + board[1][5] + "|" + board[1][6] + "|" + board[1][7] + "|" + board[1][8] + "|");
          printBorder(8);
          System.out.println("B|" + board[2][1] + "|" + board[2][2] + "|" + board[2][3] + "|" + board[2][4] + "|" + board[2][5] + "|" + board[2][6] + "|" + board[2][7] + "|" + board[2][8] + "|");
          printBorder(8);
          System.out.println("C|" + board[3][1] + "|" + board[3][2] + "|" + board[3][3] + "|" + board[3][4] + "|" + board[3][5] + "|" + board[3][6] + "|" + board[3][7] + "|" + board[3][8] + "|");
          printBorder(8);
          System.out.println("D|" + board[4][1] + "|" + board[4][2] + "|" + board[4][3] + "|" + board[4][4] + "|" + board[4][5] + "|" + board[4][6] + "|" + board[4][7] + "|" + board[4][8] + "|");
          printBorder(8);
          System.out.println("E|" + board[5][1] + "|" + board[5][2] + "|" + board[5][3] + "|" + board[5][4] + "|" + board[5][5] + "|" + board[5][6] + "|" + board[5][7] + "|" + board[5][8] + "|");
          printBorder(8);
          System.out.println("F|" + board[6][1] + "|" + board[6][2] + "|" + board[6][3] + "|" + board[6][4] + "|" + board[6][5] + "|" + board[6][6] + "|" + board[6][7] + "|" + board[6][8] + "|");
          printBorder(8);
          System.out.println("G|" + board[7][1] + "|" + board[7][2] + "|" + board[7][3] + "|" + board[7][4] + "|" + board[7][5] + "|" + board[7][6] + "|" + board[7][7] + "|" + board[7][8] + "|");
          printBorder(8);
          System.out.println("H|" + board[8][1] + "|" + board[8][2] + "|" + board[8][3] + "|" + board[8][4] + "|" + board[8][5] + "|" + board[8][6] + "|" + board[8][7] + "|" + board[8][8] + "|");
          printBorder(8);
      }
      System.out.println();
  }

  // method to set up white and black pieces on board
  public static void putCheckersOnBoard(int dimension) {
      board = new char[dimension + 1][dimension + 1];
      for (int i = 1; i <= dimension; i++) {
          for (int j = 1; j <= dimension; j++) {
          board[i][j] = ' ';
          }
      }
      if (dimension == 4) {
          board[1][2] = 'b';
          board[1][4] = 'b';
          board[4][1] = 'w';
          board[4][3] = 'w';
      } else {
          for (int i = 1; i <= 3; i++) {
              for (int j = (i % 2); j <= 8; j += 2) {
               board[i][j] = 'b';
              }
           }
          for (int i = 6; i <= 8; i++) {
              for (int j = (i % 2); j <= 8; j += 2) {
                board[i][j] = 'w';
              }
          }
      }
  }

  // game loop
  public static void gameLoop() {
      while (!humanPlayer.humanLost(board, humanPieces)) {
          System.out.println("White turn!");
          if (humanPieces == 'W') {
              printboard();
              board = humanPlayer.humanTurn(board, 'W');
              printboard();
              System.out.println("Black turn!");
              System.out.println("I'm thinking....");
              char[][] tmp = bot.makeMove(board, true);
              if (tmp == null) {
                  System.out.println("Black lost!");
                  return;
              } else {
                  board = tmp;
              }
              printboard();
              if(humanPlayer.humanLost(board, humanPieces)) {
                  System.out.println("White lost!");
                  return;
              }
          } else {
              System.out.println("I'm thinking....");
              char[][] tmp = bot.makeMove(board, false);
              if (tmp == null) {
                  System.out.println("White lost!");
                  return;
              } else {
                  board = tmp;
              }
              printboard();
              if(humanPlayer.humanLost(board, humanPieces)) {
                  System.out.println("Black lost!");
                  return;
              }
              System.out.println("Black turn!");
              board = humanPlayer.humanTurn(board, 'B');
              printboard();
          }
      }
  }

  // set up bot to play against depending on human's choice
  public static void setUpBot(int choice) {
        switch(choice) {
            case 1:
                bot = new RandomBot();
                break;
            case 2:
                bot = new Minimax();
                break;
            case 3:
                bot = new ABMinimax();
                break;
            case 4:
                bot = new HMinimax();
                System.out.println("Depth limit? (available from 1 to 8)");
                bot.requiredDepth = sc.nextInt();
                break;
            default:
                break;
        }
  }

  public static void startGame() {
      System.out.println("Checkers by Anh Nguyen and Phuong Vu");
      System.out.println("Choose your board: ");
      System.out.println("1. Small 4x4 checkers board");
      System.out.println("2. Standard 8x8 checkers board");
      System.out.print("Your choice: ");
      int choice = sc.nextInt();
      if (choice == 1) {
          dimension = 4;
      }
      else {
          dimension = 8;
      }
      putCheckersOnBoard(dimension);
      System.out.println("Choose an opponent: ");
      System.out.println("1.A bot that plays randomly.");
      System.out.println("2.A bot that plays by Minimax.");
      System.out.println("3.A bot that plays by Minimax with Alpha-Beta pruning.");
      System.out.println("4.A bot that plays by H-Minimax with a fixed depth cut-off.");
      System.out.print("Your choice: 1, 2, 3 or 4:");
      choice = sc.nextInt();
      setUpBot(choice);
      System.out.print("Do you want to play BLACK (press 1) or WHITE (press 2): ");
      choice = sc.nextInt();
          if (choice == 1) {
              humanPieces = 'B';
          } else {
              humanPieces = 'W';
          }
          gameLoop();
  }
}
