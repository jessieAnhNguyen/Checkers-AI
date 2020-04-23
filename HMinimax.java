import java.util.ArrayList;

/**
* Class HMinimax: contains the implementation of heuristic minimax
**/

public class HMinimax extends Bot {


    public char[][] makeMove(char[][] a, boolean isBlack) { // boolean isBlack represents the side the human player chooses to play
        stateVisited = 0;
        ArrayList<State> captures = availableCaptureMoves(a, isBlack);
        ArrayList<State> normal = availableMoves(a, isBlack);
        // prioritize making capture moves
        // explore the set of all possible capture moves given a state first
        if (captures.size() > 0) {
            long start = System.currentTimeMillis(); // start the time counter
            State bestMove = captures.get(0);
            for (State next : captures) { // loop through all the capture moves
                DFS(next, true, 1); // find the utility of each.
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
            float timeElapsed = (end-start)/1000F; // calculate time elapsed
            System.out.println("Total time elapsed: " + timeElapsed + " seconds");
            return bestMove.board;

        // then explore the set of all possible "normal" move given a state
        // same logic as for exploring the set of capture moves
        } else if (normal.size() > 0) {
            long start1 = System.currentTimeMillis();
            State bestMove = normal.get(0);
            for (State next : normal) {
                DFS(next, true, 1);
                if (next.utilityPoint > bestMove.utilityPoint && isBlack) {
                    bestMove = next;
                }
                if (next.utilityPoint < bestMove.utilityPoint && !isBlack) {
                    bestMove = next;
                }
            }
            System.out.println("I have finished searching " + stateVisited + " states.");
            long end1 = System.currentTimeMillis();
            float timeElapsed1 = (end1-start1)/1000F;
            System.out.println("Total time elapsed: " + timeElapsed1 + " seconds");
            return bestMove.board;

        // if there are no available moves left, the bot has lost
        } else {
            System.out.println(" I have lost. You won the game." );
            System.out.println("Total time elapsed: infinity");
            return null;
        }
    }
}
