# Checkers-AI-UR
This project designs, implements, and evaluates an AI program that plays Checkers game against human or computer opponents
Compilation instruction:
- To compile, either use the IntelliJ IDE or in your terminal, type javac *.java and java Main


Play instruction:
- Interact with the game using the terminal. There are careful instructions along the way.
- Acceptable form of input: for a position, a LOWERCASE character and a number, followed by a hyphen,
and another position. For example: d1-c2, a4-f5, g5-r7
- Invalid move alert is either for invalid input form or a non-capture move when a capture move is
a possibility.

Files included:
1. Main: contains just the start game method
2. Bot: contains the depth first search function, which is used by all implementations of minimax
3. HumanPlayer: contains logic that control human activity in the game
4. State: keep track of the current board. Also contains the heuristic function.
5. Game: contains general game logic
6. Minimax: implementation of the minimax algorithm
7. ABMinimax: implementation of the minimax algorithm using alpha-beta pruning
8. HMinimax: implementation of the minimax algorithm using heuristic function with a depth cutoff from 1-8


For the heuristic function, after trial and error we figured out that our simple but efficient
heuristic works well for this particular game. For a state, we basically count the number of pieces
that each side is having. Utility point is calculated by +1 for a pawn and +4 for a king. A king is
seen as a huge advantage since theoretically it can consumed its whole opponents' pieces.


NOTE: for this project, we don't opt to use the traditional utility function (i.e. utility function
that returns 1 for a MAX win, -1 for a MAX loss and 0 for a non-winning/non-losing state) for our
implementation of minimax. We figured out that such utility function only works for the case of the
4x4 checker board, and for the standard 8x8 one it will throw a StackOverflow error. Hence we have
to use the heuristic function for both the implementation of minimax, abminimax and hminimax.
