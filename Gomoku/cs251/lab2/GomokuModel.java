/**
 * @author Andree Jacobson (andree@cs.unm.edu)
 * @version 1.0 (Feb 4, 2008)

 * @author Brooke Chenoweth
 * @version 2.0 (Aug 29, 2013)
 * Renamed to Gomoku because I don't speak Swedish.
 * @version 2.1 (Feb 4, 2014) Minor tweaks
 */
package cs251.lab2;

/**
 * This is the interface that describes the methods that must be
 * present in the class you are writing that interfaces with the
 * GUI. The game of Gomoku is very similar to tic-tac-toe, only that
 * you are playing on a larger board, and the goal is to get
 * five-in-a-row. You are allowed to place your marker anywhere
 * there's an empty square on the board.
 */
public interface GomokuModel {

    /**
     * Class to report correct outcome status of the game. One of the
     * methods uses elements of this class to describe the current
     * state of the game. You can easily use the values of this type
     * by just using <code>Outcome.CROSS_WINS</code> as a constant for
     * example.
     */
    public enum Outcome {
        CROSS_WINS, RING_WINS, DRAW, GAME_NOT_OVER;
    }

    /**
     * Class to represent the status of each square on the board.
     */
    public enum Square {
        CROSS('x'), RING('o'), EMPTY(' ');
        
        /** Character representation for the Square. */
        private final char symbol;
        
        private Square(char symbol) {
            this.symbol = symbol;
        }

        /**
         * Gives the character representation of this Square.
         * @return Character representing this square
         */
        public char toChar ( ) {
            return symbol;
        }
    }

    /** Number of horizontal squares on the board */
    public static final int NUM_HSQUARES = 30;

    /** Number of vertical squares on the board */
    public static final int NUM_VSQUARES = 30;

    /** Number of squares in a line required for a win */
    public static final int SQUARES_IN_LINE_FOR_WIN = 5;

    /**
     * This method is called when the user clicks in the board. If the
     * square is already occupied, nothing about the state of the game
     * changes. If however, an empty square is clicked, then it should
     * be filled with a value representing the player currently in
     * turn. On a failure, the turn doesn't change, but that player
     * gets to go again. Called from the GUI.
     * @param row 0-based row that was clicked
     * @param col 0-based column that was clicked
     * @return State of this game after this moved occurred
     */
    Outcome doClick ( int row, int col );

    /**
     * Starts a new game, resets the game board to empty. Pick a
     * random player to go first. In the expected part of the program,
     * you should make it so that the player who won the last game
     * gets to go first in the next round. This method is called by
     * the GUI whenever a new game is supposed to be started, this
     * includes before the first game.
     */
    void newGame ( );

    /**
     * Get a string representation of the board. 
     *
     * The characters are give by the Square.toChar method, so a space
     * in the string is an empty square, an o - the letter 'o' (lower
     * case) - represents the ring, and the 'x' (lowercase x). Each
     * line in the board is separated by a new line character '\n'. An
     * example of a small board might look like this if viewed in a
     * text file:
     *
     * <pre> 
     * ox  x 
     * xoxoo
     * xxoox
     * ooxox
     * xoxoo
     * </pre>
     *
     * In this case, o must have started, and just won the game
     * through a diagonal.
     * @return String representation of the board
     */
    String boardString ( );

    
    /**
     * Configure whether a computer player will be used.
     *
     * At the very least, recognize the following options
     * <ul>
     *    <li>NONE - no computer player (the default)
     *    <li>COMPUTER - one of the players is the computer
     * </ul>
     * It is permissible to have additional options if, for example,
     * there are multiple computer player implementations to choose
     * from.
     * 
     * If the string is not a recognized computer player setting,
     * print a message to the console and use the default no player
     * setting.
     *
     * @param opponent String for computer player type. 
     */
    void setComputerPlayer(String opponent);
}