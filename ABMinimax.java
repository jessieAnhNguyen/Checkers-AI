/**
* Class ABMinimax: implementation of the minimax algorithm using alpha-beta pruning
*/

import java.util.ArrayList;

public class ABMinimax extends Bot {

    public char[][] makeMove(char[][] a, boolean isBlack) { // boolean isBlack represents the side human player chooses to play
        stateVisited = 0;
        ArrayList<State> captures = availableCaptureMoves(a, isBlack);
        ArrayList<State> moves = availableMoves(a, isBlack);
        // prioritize making capture moves
        // explore the set of all possible capture moves given a state first
        if (captures.size() > 0) {
            long start = System.currentTimeMillis();
            State bestMove = captures.get(0);
            for (State next : captures) { // loop through all capture moves
                DFS(next, true, 1, Integer.MIN_VALUE, Integer.MAX_VALUE); // find the utility of each
                // if next's utility is GREATER than the current utility given that the current player is MAX
                if (next.utilityPoint > bestMove.utilityPoint && isBlack) {
                    bestMove = next;
                }
                // if next's utility is LESS than the current utility given that the current player is MIN
                if (next.utilityPoint < bestMove.utilityPoint && !isBlack) {
                    bestMove = next;
                }
            }
            System.out.println("I have finished searching " + stateVisited + " states.");
            long end = System.currentTimeMillis();
            float timeElapsed = (end - start)/1000F;
            System.out.println("Total time elapsed: " + timeElapsed + " seconds");
            return bestMove.board;

        // then explore the set of all possible "normal" move given a state
        // same logic as for exploring the set of capture moves

        } else if (moves.size() > 0) {
            long start1 = System.currentTimeMillis();
            State bestMove = moves.get(0);
            for (State next : moves) {
                DFS(next, true, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (next.utilityPoint > bestMove.utilityPoint && isBlack) {
                    bestMove = next;
                }
                if (next.utilityPoint < bestMove.utilityPoint && !isBlack) {
                    bestMove = next;
                }
            }
            System.out.println("I have finished searching " + stateVisited + " states.");
            long end1 = System.currentTimeMillis();
            float timeElapsed1 = (end1 - start1)/1000F;
            System.out.println("Total time elapsed " + timeElapsed1 + " seconds");
            return bestMove.board;

        // if there are no available moves left, the bot has lost
        } else {
            System.out.println("I lost. You won the game" );
            System.out.println("Total time elapsed: infinity");
            System.out.println();
            return null;
        }

    }

    // this DFS method is different from the one at the abstract Bot class
    // this is because we do extra bookkeeping with alpha-beta values
    public int DFS(State cur, boolean min, int depth, int alpha, int beta) {
        stateVisited++;
        int curPoint = cur.utility();
        if (curPoint == Integer.MAX_VALUE || curPoint == Integer.MIN_VALUE) return curPoint;
        if (depth == 11) {
            return curPoint;
        }

        if (min) cur.utilityPoint = Integer.MAX_VALUE;
        else cur.utilityPoint = Integer.MIN_VALUE;
        boolean isMachine;
        if (min) isMachine = false;
        else isMachine = true;
        ArrayList<State> captures = availableCaptureMoves(cur.board, isMachine);
        ArrayList<State> moves = availableMoves(cur.board, isMachine);
        if (captures.size() > 0) {
            for (State next : captures) {
                if (min) {
                    int val = DFS(next, false, depth + 1, alpha, beta);
                    beta = Math.min(beta, val);
                    if (beta <= alpha) {
                        cur.utilityPoint = Integer.MIN_VALUE;
                        return cur.utilityPoint;
                    }
                    cur.utilityPoint = Math.min(cur.utilityPoint, beta);
                } else {
                    int val = DFS(next, true, depth + 1, alpha, beta);
                    alpha = Math.max(alpha, val);
                    if (beta <= alpha) {
                        cur.utilityPoint = Integer.MAX_VALUE;
                        return cur.utilityPoint;
                    }
                    cur.utilityPoint = Math.max(cur.utilityPoint, alpha);
                }
            }
        } else {
            for (State next : moves) {
                if (min) {
                    int val = DFS(next, false, depth + 1, alpha, beta);
                    beta = Math.min(beta, val);
                    if (beta <= alpha) {
                        cur.utilityPoint = Integer.MIN_VALUE;
                        return cur.utilityPoint;
                    }
                    cur.utilityPoint = Math.min(cur.utilityPoint, beta);
                } else {
                    int val = DFS(next, true, depth + 1, alpha, beta);
                    alpha = Math.max(alpha, val);
                    if (beta <= alpha){
                        cur.utilityPoint = Integer.MAX_VALUE;
                        return cur.utilityPoint;
                    }
                    cur.utilityPoint = Math.max(cur.utilityPoint, alpha);
                }
            }
        }
        return cur.utilityPoint;
    }
}
