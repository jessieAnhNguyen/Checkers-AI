
/**
* Class HumanPlayer: contains logic that coordinate human interaction with the game
**/

import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Bot {

    static Scanner scan = new Scanner(System.in);

    // method to compare user's input against the set of available moves to see whether it's valid or not
    public boolean isValid(char[][] a, char[][] b, boolean isBlack) {

        ArrayList<State> captures = availableCaptureMoves(a, isBlack);
        ArrayList<State> moves = availableMoves(a, isBlack);

        // compare the input move (char[][] b) against the set of capture moves generated from state char[][] a
        if(captures.size() > 0) {
            for (State s : captures) {
                if (equalCharArrays(b, s.board)) {
                  return true;
                }
            }
            return false;
        }

        // compare the input move (char[][]  b) against the set of normal moves generated from the state char[][] a
        for (State s : moves) {
            if (equalCharArrays(b, s.board)) {
                return true;
            }
        }
        return false;
    }

    // simple helper method to test whether two 2-dimensional character arrays are equal or not
    public boolean equalCharArrays(char[][] a, char[][] b) {
        for (int i = 1; i < a.length; i++)
            for (int j = 1; j < a.length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        return true;
    }

    // method to check whether the human player has lost
    // "Have lost" means having no pieces, whether pawn or king, on the board
    public static boolean humanLost(char[][] board, char piece) {
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board.length; j++) {
                // piece = pawn, piece + 32 = capitalized letter = king
                if (board[i][j] == piece || board[i][j] == piece + 32) {
                    return false;
                }
            }
        }
        return true;
    }

    // examine human input
    public char[][] humanTurn(char[][] board, char piece) {
        // create a 'buffer' state
        char[][] tmpBoard = new char[board.length][board.length];
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board.length; j++) {
                tmpBoard[i][j] = board[i][j];
            }
        }
        System.out.print("Your move: ");
        String input = scan.nextLine();

        // logic to check whether human input is of valid form and is applicable
        for (int i = 0; i <= input.length() - 5; i += 3) {
            int startingRow = input.charAt(i) - 'a' + 1;
            if (startingRow < 0) {
                i += 32;
            }
            int startingCol = input.charAt(i + 1) - '0';
            int endingRow = input.charAt(i + 3) - 'a' + 1;
            if (endingRow < 0) {
                endingRow += 32;
            }
            int endingCol = input.charAt(i + 4) - '0';
            if (tmpBoard[startingRow][startingCol] != piece && tmpBoard[startingRow][startingCol] != (piece + 32)) {
                System.out.println("Invalid move! Please try another one!");
                return humanTurn(board, piece);
            }
            tmpBoard[endingRow][endingCol] = tmpBoard[startingRow][startingCol];
            tmpBoard[startingRow][startingCol] = ' ';
            if (Math.abs(endingRow - startingRow) > 1) {
                int middleRow = (startingRow + endingRow)/2;
                int middleCol = (startingCol + endingCol)/2;
                tmpBoard[middleRow][middleCol] = ' ';
            }
            if (endingRow == 1 && piece == 'W') {
                tmpBoard[endingRow][endingCol] = 'W';
            }
            if (endingRow == board.length - 1 && piece == 'B') {
                tmpBoard[endingRow][endingCol] = 'B';
            }
        }
        boolean isBlack = piece == 'B';
        if (isValid(board, tmpBoard, isBlack)) {
            return tmpBoard;
        } else {
            System.out.println("Invalid move! Please try another one!");
            return humanTurn(board, piece);
        }
    }
}
