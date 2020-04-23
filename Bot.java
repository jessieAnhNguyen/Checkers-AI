import java.util.ArrayList;
import java.util.Collections;

/**
 * Class Bot: an abstract class that contains methods to get available moves for the bot,
 and helper DFS method used for the implementations of minimax and hminimax
 */
public abstract class Bot {

    public int requiredDepth;
    public int stateVisited = 0;

    // class RandomBot, Minimax, ABMinimax and HMinimax will all implement this method to return a best move given a current state
    public char[][] makeMove(char[][] a, boolean isBlack) {
        return a;
    }

    //This method calculates the utility for a state
    public int DFS(State cur, boolean min, int depth) {
        stateVisited++;
        int curPoint = cur.utility();
        if (curPoint == Integer.MAX_VALUE) {
            return 1;
        } else if (curPoint == Integer.MIN_VALUE) {
            return -1;
        }
        if (depth == 8) { // always cut off search at depth 8
            return 0;
        }

        if (min) {
            cur.utilityPoint = Integer.MAX_VALUE;
        } else {
            cur.utilityPoint = Integer.MIN_VALUE;
        }
        boolean isBot;
        if (min) {
            isBot = false;
        } else {
            isBot = true;
        }
        ArrayList<State> CaptureMoves = availableCaptureMoves(cur.board, isBot);
        ArrayList<State> simpleMoves = availableMoves(cur.board, isBot);
        if (CaptureMoves.size() > 0) {
            for (State next : CaptureMoves) {
                if (min) {
                    cur.utilityPoint = Math.min((DFS(next, false, depth + 1)), cur.utilityPoint);
                } else {
                    cur.utilityPoint = Math.max((DFS(next, true, depth + 1)), cur.utilityPoint);
                }
            }
        } else {
            for (State next : simpleMoves) {
                if (min) {
                    cur.utilityPoint = Math.min((DFS(next, false, depth + 1)), cur.utilityPoint);
                } else {
                    cur.utilityPoint = Math.max((DFS(next, true, depth + 1)), cur.utilityPoint);
                }
            }
        }
        return cur.utilityPoint;
    }


    //This method returns an ArrayList of all available capture moves of bot given a state
    public ArrayList<State> availableCaptureMoves(char[][] a, boolean isBlack) {
        ArrayList<State> AllAvailableCaptureMoves = new ArrayList<>();
        ArrayList<State> CaptureMoves = new ArrayList<>();
        char searchTarget;
        if (isBlack) searchTarget = 'B';
        else searchTarget = 'W';
        for (int i = 1; i < a.length; i++)
            for (int j = 1; j < a.length; j++)
                if (a[i][j] == searchTarget || a[i][j] == searchTarget + 32) {
                    SearchForCapture(new State(a), i, j, AllAvailableCaptureMoves, true, isBlack);
                }
        Collections.sort(AllAvailableCaptureMoves);
        for (State s : AllAvailableCaptureMoves) {
            if(s.numberOfCaptures == AllAvailableCaptureMoves.get(0).numberOfCaptures) CaptureMoves.add(s);
        }
        return CaptureMoves;
    }

    //This method returns an ArrayList of all available simple moves  of bot given a state
    public ArrayList<State> availableMoves(char[][] a, boolean isBlack) {
        ArrayList<State> simpleMoves = new ArrayList<>();
        char searchTarget;
        if (isBlack) searchTarget = 'B';
        else searchTarget = 'W';
        for (int i = 1; i < a.length; i++) {
            for (int j = 1; j < a.length; j++) {
                if (a[i][j] == searchTarget || a[i][j] == searchTarget + 32) {
                    SearchForSimple(new State(a), i, j, simpleMoves, isBlack);
                }
            }
        }
        return simpleMoves;
    }


    //This method searches for applicable capture moves
    public void SearchForCapture(State currentState, int positionX, int positionY, ArrayList<State> moves, boolean first, boolean isBlack) {
        //If the bot is Black
        if (isBlack) {
            if (positionX == currentState.board.length - 1) currentState.board[positionX][positionY] = 'B';
            boolean capturable = false;
            //capture the left under white checker
            if ((positionX + 2 < currentState.board.length && positionY - 2 > 0) && currentState.board[positionX + 2][positionY - 2] == ' '
                    && (currentState.board[positionX + 1][positionY - 1] == 'w' || currentState.board[positionX + 1][positionY - 1] == 'W')) {
                State next = new State(currentState.board);
                next.numberOfCaptures++;
                next.board[positionX + 2][positionY - 2] = currentState.board[positionX][positionY];
                next.board[positionX + 1][positionY - 1] = ' ';
                next.board[positionX][positionY] = ' ';
                capturable = true;
                SearchForCapture(next, positionX + 2, positionY - 2, moves, false, isBlack);
            }
            //capture the right under white checker
            if ((positionX + 2 < currentState.board.length && positionY + 2 < currentState.board.length) && currentState.board[positionX + 2][positionY + 2] == ' '
                    && (currentState.board[positionX + 1][positionY + 1] == 'w' || currentState.board[positionX + 1][positionY + 1] == 'W')) {
                State next = new State(currentState.board);
                next.numberOfCaptures++;
                next.board[positionX + 2][positionY + 2] = currentState.board[positionX][positionY];
                next.board[positionX + 1][positionY + 1] = ' ';
                next.board[positionX][positionY] = ' ';
                capturable = true;
                SearchForCapture(next, positionX + 2, positionY + 2, moves, false, isBlack);
            }
            //if the checker is King: additional capture move
            if (currentState.board[positionX][positionY] == 'B') {
                //capture the left upper white checker
                if ((positionX - 2 > 0 && positionY - 2 > 0) && currentState.board[positionX - 2][positionY - 2] == ' '
                        && (currentState.board[positionX - 1][positionY - 1] == 'w' || currentState.board[positionX - 1][positionY - 1] == 'W')) {
                    State next = new State(currentState.board);
                    next.numberOfCaptures++;
                    next.board[positionX - 2][positionY - 2] = currentState.board[positionX][positionY];
                    next.board[positionX - 1][positionY - 1] = ' ';
                    next.board[positionX][positionY] = ' ';
                    capturable = true;
                    SearchForCapture(next, positionX - 2, positionY - 2, moves, false, isBlack);
                }
                //capture the right upper white checker
                if ((positionX - 2 > 0 && positionY + 2 < currentState.board.length) && currentState.board[positionX - 2][positionY + 2] == ' '
                        && (currentState.board[positionX - 1][positionY + 1] == 'w' || currentState.board[positionX - 1][positionY + 1] == 'W')) {
                    State next = new State(currentState.board);
                    next.numberOfCaptures++;
                    next.board[positionX - 2][positionY + 2] = currentState.board[positionX][positionY];
                    next.board[positionX - 1][positionY + 1] = ' ';
                    next.board[positionX][positionY] = ' ';
                    capturable = true;
                    SearchForCapture(next, positionX - 2, positionY + 2, moves, false, isBlack);
                }
            }
            if (!capturable && !first) moves.add(currentState);
        }
        //If the Bot is White
        else {
            if (positionX == 1) currentState.board[positionX][positionY] = 'W';
            boolean capturable = false;
            //capture the left upper black checker
            if ((positionX - 2 > 0 && positionY - 2 > 0) && currentState.board[positionX - 2][positionY - 2] == ' '
                    && (currentState.board[positionX - 1][positionY - 1] == 'b' || currentState.board[positionX - 1][positionY - 1] == 'B')) {
                State next = new State(currentState.board);
                next.numberOfCaptures++;
                next.board[positionX - 2][positionY - 2] = currentState.board[positionX][positionY];
                next.board[positionX - 1][positionY - 1] = ' ';
                next.board[positionX][positionY] = ' ';
                capturable = true;
                SearchForCapture(next, positionX - 2, positionY - 2, moves, false, isBlack);
            }
            //capture the right upper black checker
            if ((positionX - 2 > 0 && positionY + 2 < currentState.board.length) && currentState.board[positionX - 2][positionY + 2] == ' '
                    && (currentState.board[positionX - 1][positionY + 1] == 'b' || currentState.board[positionX - 1][positionY + 1] == 'B')) {
                State next = new State(currentState.board);
                next.numberOfCaptures++;
                next.board[positionX - 2][positionY + 2] = currentState.board[positionX][positionY];
                next.board[positionX - 1][positionY + 1] = ' ';
                next.board[positionX][positionY] = ' ';
                capturable = true;
                SearchForCapture(next, positionX - 2, positionY + 2, moves, false, isBlack);
            }

            //if the checker is King: additional capture move
            if (currentState.board[positionX][positionY] == 'W') {
                //capture the left under black checker
                if ((positionX + 2 < currentState.board.length && positionY - 2 > 0) && currentState.board[positionX + 2][positionY - 2] == ' '
                        && (currentState.board[positionX + 1][positionY - 1] == 'b' || currentState.board[positionX + 1][positionY - 1] == 'B')) {
                    State next = new State(currentState.board);
                    next.numberOfCaptures++;
                    next.board[positionX + 2][positionY - 2] = currentState.board[positionX][positionY];
                    next.board[positionX + 1][positionY - 1] = ' ';
                    next.board[positionX][positionY] = ' ';
                    capturable = true;
                    SearchForCapture(next, positionX + 2, positionY - 2, moves, false, isBlack);
                }
                //capture the right under black checker
                if ((positionX + 2 < currentState.board.length && positionY + 2 < currentState.board.length) && currentState.board[positionX + 2][positionY + 2] == ' '
                        && (currentState.board[positionX + 1][positionY + 1] == 'b' || currentState.board[positionX + 1][positionY + 1] == 'B')) {
                    State next = new State(currentState.board);
                    next.numberOfCaptures++;
                    next.board[positionX + 2][positionY + 2] = currentState.board[positionX][positionY];
                    next.board[positionX + 1][positionY + 1] = ' ';
                    next.board[positionX][positionY] = ' ';
                    capturable = true;
                    SearchForCapture(next, positionX + 2, positionY + 2, moves, false, isBlack);
                }
            }
            if (!capturable && !first) moves.add(currentState);
        }
    }

    //This method searches for applicable simple moves
    public void SearchForSimple(State currentState, int positionX, int positionY, ArrayList<State> moves, boolean isBot) {
        //if player is bot (black)
        if (isBot) {
            if (positionX == currentState.board.length - 1) currentState.board[positionX][positionY] = 'B';
            //move to the left under space
            if (positionX + 1 < currentState.board.length && positionY - 1 > 0 && currentState.board[positionX + 1][positionY - 1] == ' ') {
                State next = new State(currentState.board);
                next.board[positionX + 1][positionY - 1] = currentState.board[positionX][positionY];
                if (positionX + 1 == currentState.board.length - 1) next.board[positionX + 1][positionY - 1] = 'B';
                next.board[positionX][positionY] = ' ';
                moves.add(next);
            }
            //move to the right under space
            if (positionX + 1 < currentState.board.length && positionY + 1 < currentState.board.length && currentState.board[positionX + 1][positionY + 1] == ' ') {
                State next = new State(currentState.board);
                next.board[positionX + 1][positionY + 1] = currentState.board[positionX][positionY];
                if (positionX + 1 == currentState.board.length - 1) next.board[positionX + 1][positionY + 1] = 'B';
                next.board[positionX][positionY] = ' ';
                moves.add(next);
            }
            //If the checker is King
            if (currentState.board[positionX][positionY] == 'B') {
                //move to the left upper space
                if (positionX - 1 > 0 && positionY - 1 > 0 && currentState.board[positionX - 1][positionY - 1] == ' ') {
                    State next = new State(currentState.board);
                    next.board[positionX - 1][positionY - 1] = currentState.board[positionX][positionY];
                    next.board[positionX][positionY] = ' ';
                    moves.add(next);
                }
                //move to the right upper space
                if (positionX - 1 > 0 && positionY + 1 < currentState.board.length && currentState.board[positionX - 1][positionY + 1] == ' ') {
                    State next = new State(currentState.board);
                    next.board[positionX - 1][positionY + 1] = currentState.board[positionX][positionY];
                    next.board[positionX][positionY] = ' ';
                    moves.add(next);
                }
            }
        }
        //if the player is human (white)
        else {
            if (positionX == 1) currentState.board[positionX][positionY] = 'W';
            //move to the left under space
            if (positionX - 1 > 0 && positionY - 1 > 0 && currentState.board[positionX - 1][positionY - 1] == ' ') {
                State next = new State(currentState.board);
                next.board[positionX - 1][positionY - 1] = currentState.board[positionX][positionY];
                if (positionX - 1 == 1) next.board[positionX + 1][positionY - 1] = 'W';
                next.board[positionX][positionY] = ' ';
                moves.add(next);
            }
            //move to the right upper space
            if (positionX - 1 > 0 && positionY + 1 < currentState.board.length && currentState.board[positionX - 1][positionY + 1] == ' ') {
                State next = new State(currentState.board);
                next.board[positionX - 1][positionY + 1] = currentState.board[positionX][positionY];
                if (positionX - 1 == 1) next.board[positionX - 1][positionY + 1] = 'W';
                next.board[positionX][positionY] = ' ';
                moves.add(next);
            }
            if (currentState.board[positionX][positionY] == 'W') {
                //move to left upper space
                if (positionX + 1 < currentState.board.length && positionY - 1 > 0 && currentState.board[positionX + 1][positionY - 1] == ' ') {
                    State next = new State(currentState.board);
                    next.board[positionX + 1][positionY - 1] = currentState.board[positionX][positionY];
                    next.board[positionX][positionY] = ' ';
                    moves.add(next);
                }
                //move to right upper space
                if (positionX + 1 < currentState.board.length && positionY + 1 < currentState.board.length
                        && currentState.board[positionX + 1][positionY + 1] == ' ') {
                    State next = new State(currentState.board);
                    next.board[positionX + 1][positionY + 1] = currentState.board[positionX][positionY];
                    next.board[positionX][positionY] = ' ';
                    moves.add(next);
                }
            }
        }
    }
}

//the end of bot class
