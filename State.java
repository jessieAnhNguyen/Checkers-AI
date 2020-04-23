/**
* Class State: contains the implementation of the checker board as well as the heuristic function
**/

public class State implements Comparable<State> {

    public char board[][];
    public int utilityPoint;
    public int numberOfCaptures = 0;

    public State(char[][] temp) {
        board = new char[temp.length][temp.length];
        for (int i = 1; i < temp.length; i++)
            for (int j = 1; j < temp.length; j++)
                board[i][j] = temp[i][j];
    }

    // heuristic function
    // evaluate the state based on number of checkers on the board for each side
    public int utility() {
        int blackUtility = 0;
        int whiteUtility = 0;
        for (int i = 1; i < board.length; i++)
            for (int j = 1; j < board.length; j++) {
                if (board[i][j] == 'b') { // +1 utility point for a black pawn
                    blackUtility += 1;
                }
                if (board[i][j] == 'B') { // +4 utility point for a black king
                    blackUtility += 4;
                }
                if (board[i][j] == 'w') { // +1 utility point for a white pawn
                    whiteUtility += 1;
                }
                if (board[i][j] == 'W') { // +4 utility point for a white king
                    whiteUtility += 4;
                }
            }
        if(blackUtility == 0) {
            return -Integer.MIN_VALUE;
        } else if(whiteUtility == 0) {
            return Integer.MAX_VALUE;
        } else {
            return blackUtility - whiteUtility; // assuming that black is the max player
        }
    }

    // method to compare 2 states based on number of captures
    public int compareTo(State s) {
        if (this.numberOfCaptures > s.numberOfCaptures)  {
            return 1;
        } else if (this.numberOfCaptures < s.numberOfCaptures) {
            return -1;
        }
        return 0;
    }
}
