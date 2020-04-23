/**
* Class RandomBot: contains the implementation of a bot that makes random move
**/

import java.util.ArrayList;
import java.util.Random;

public class RandomBot extends Bot {

    // the boolean is the side you choose to play
    public char[][] makeMove(char[][] a, boolean isBlack) {

        ArrayList<State> captures = availableCaptureMoves(a, isBlack);
        ArrayList<State> normal = availableMoves(a, isBlack);
        Random rand = new Random();

        // still prioritize making capture moves
        if (captures.size() > 0) {
            long start = System.currentTimeMillis();
            int i = rand.nextInt(captures.size());
            System.out.println(" I have made a random move ");
            long end = System.currentTimeMillis();
            long timeElapsed = end - start;
            System.out.println("Total time elapsed: " + timeElapsed + " miliseconds");
            return captures.get(i).board;

        // then normal moves
        } else if (normal.size() > 0) {
            long start1 = System.currentTimeMillis();
            int j = rand.nextInt(normal.size());
            System.out.println(" I have made a random move");
            long end1 = System.currentTimeMillis();
            long timeElapsed1 = end1 - start1;
            System.out.println("Total time elapsed: " + timeElapsed1 + " miliseconds");
            return normal.get(j).board;

        // no available moves, so the bot loses
        } else {
            System.out.println("I lost. You won the game");
            System.out.println("Total time elapsed: infinity");
            return null;
        }
    }
}
