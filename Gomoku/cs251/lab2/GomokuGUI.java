/**
 * @author Andree Jacobson (andree@cs.unm.edu)
 * @version 1.0 (Feb 4, 2008)
 * @version 1.1 (Feb 5, 2008)
 * 
 * @author Brooke Chenoweth
 * @version 2.0 (Aug 29, 2013)
 * Renamed to Gomoku because I don't speak Swedish.
 * @version 2.1 (Feb 4, 2014)
 * Some style tweaks. Try to fit coding standard better.
 * Make static method to start GUI in thread-safe manner.
 */
package cs251.lab2;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * This is the graphical user interface for the Gomoku game.
 */
public class GomokuGUI {

    /**
     * Class used to display the Gomoku game board
     */
    private class BoardPanel extends JPanel {

        /** Used to shut up the compiler */
        private static final long serialVersionUID = 1L;

        /**
         * Draws the x's and o's on the game board in the right places.
         * @param g Graphics context for the component
         */
        public void drawBoard ( Graphics2D g ) {

            // Read the string from the model so I know what to draw,
            // convert to lowercase just in case.
            String s = model.boardString().toLowerCase();
                        
            // Split board into rows
            String[] rowStrings = s.split("\\n");

            // Figure out number of rows and columns
            int numRows = rowStrings.length;
            int numCols = rowStrings[0].length();

            // Use a somewhat wide pen based on size of box
            int offset = SQUARE_SIZE / 5;
            int x2offset = 2 * offset;
            g.setStroke ( new BasicStroke ( offset ) );

            // Draw the signs on the board (within the squares)
            for ( int row = 0; row < numRows; row++ ) {
                for ( int col = 0; col < numCols; col++ ) {

                    // Choose what to do depending on the character in the string I got
                    switch ( rowStrings[row].charAt(col) ) {

                    case 'o': // Draw a ring
                        g.setColor ( Color.BLUE );
                        g.drawOval ( col * SQUARE_SIZE + offset,
                                     row * SQUARE_SIZE + offset,
                                     SQUARE_SIZE - x2offset,
                                     SQUARE_SIZE - x2offset );
                        break;

                    case 'x': // Draw an X
                        g.setColor ( Color.RED );
                        g.drawLine ( col * SQUARE_SIZE + offset,
                                     row * SQUARE_SIZE + offset,
                                     ( col + 1 ) * SQUARE_SIZE - offset,
                                     ( row + 1 ) * SQUARE_SIZE - offset );
                        g.drawLine ( ( col + 1 ) * SQUARE_SIZE - offset,
                                     row * SQUARE_SIZE + offset,
                                     col * SQUARE_SIZE + offset,
                                     ( row + 1 ) * SQUARE_SIZE - offset );
                        break;
                    }
                }
            }
        }

        /**
         * Mainly used to redraw the grid on the board, so that it's always there.
         */
        @Override
        public void paintComponent ( Graphics g ) {
            g.setColor ( Color.BLACK );
            int width = getWidth ( );
            int height = getHeight ( );
            for ( int row = 0; row <= GomokuModel.NUM_VSQUARES; row++ ) {
                int line = row * SQUARE_SIZE;

                // Draw the vertical lines
                g.drawLine ( 0, line, width, line );

                // Draw the horizontal lines
                g.drawLine ( line, 0, line, height );
            }

            // Then draw the contents
            drawBoard ( (Graphics2D) g );
        }
    }

    /** 
     * Handles a mouse click on the board.
     * @param x X position of the click
     * @param y Y position of the click
     */ 
    private void doMouseClick(int x, int y) {
        int row = y / SQUARE_SIZE;
        int col = x / SQUARE_SIZE;

        boolean gameOver = true;

        // Check current status and decide what to do
        GomokuModel.Outcome outcome = model.doClick ( row, col );

        // Better repaint so that the latest move shows up
        boardFrame.repaint ( );

        // If necessary present a dialog box
        switch ( outcome ) {
        case DRAW:
            JOptionPane.showMessageDialog ( null, "Draw Game!", "Game Over",
                                            JOptionPane.INFORMATION_MESSAGE );
            break;
        case CROSS_WINS:
            JOptionPane.showMessageDialog ( null, "Cross Wins!", "Game Over",
                                            JOptionPane.INFORMATION_MESSAGE );
            break;
        case RING_WINS:
            JOptionPane.showMessageDialog ( null, "Ring Wins!", "Game Over",
                                            JOptionPane.INFORMATION_MESSAGE );
            break;
        default:
            // No action to take. Either game is over or someone clicked on the wrong spot.
            gameOver = false;
            break;
        }

        if ( gameOver ) {
            // Question user about another game, and either quit or restart
            int choice = JOptionPane.showConfirmDialog ( null, "Play again?", "Play again?",
                                                         JOptionPane.YES_NO_OPTION );
            if ( choice == JOptionPane.NO_OPTION ) {
                System.exit ( 0 );
            } else {
                model.newGame();
            }
        }

        // Repaint after user choose a new game as otherwise we'll still see the old
        // contents of the board, and that's no fun.
        boardFrame.repaint();
    }

    /** Size in pixels of each square */
    public static final int SQUARE_SIZE = 20;

    /** Reference to the model class */
    private final GomokuModel model;

    /** Reference to my board */
    private final JFrame boardFrame;

    /**
     * Constructor for the GUI.
     * @param gomokuModel Reference to a model that correctly
     * implements the interface, so that I know that all methods are
     * there.
     */
    private GomokuGUI ( GomokuModel gomokuModel ) {
        model = gomokuModel;
        model.newGame ( );

        // Create the frame
        boardFrame = new JFrame ( );
        boardFrame.setTitle ( "Gomoku" );

        // Create the board
        BoardPanel boardPanel = new BoardPanel ( );
        boardPanel.setPreferredSize ( new Dimension (
                                        GomokuModel.NUM_HSQUARES * SQUARE_SIZE + 1,
                                        GomokuModel.NUM_VSQUARES * SQUARE_SIZE + 1 ) );
        boardPanel.addMouseListener ( new MouseAdapter() {
    
                @Override
                public void mouseClicked ( MouseEvent e ) {

                    int x = e.getX ( );
                    int y = e.getY ( );
                    doMouseClick(x, y);
                }
            });

        // Create status panel for new game and quit buttons
        JPanel statusPanel = new JPanel ( new FlowLayout ( ) );
        statusPanel.setPreferredSize ( new Dimension (
                                         GomokuModel.NUM_HSQUARES * SQUARE_SIZE, 50 ) );

        // Create a new New Game Button
        JButton newGameButton = new JButton ( "New Game" );
        newGameButton.addActionListener ( new ActionListener ( ) {
                public void actionPerformed ( ActionEvent e ) {
                    model.newGame ( );
                    boardFrame.repaint ( );
                }
            } );
        statusPanel.add ( newGameButton );

        // Create a nice quit button
        JButton quitButton = new JButton ( "Quit" );
        quitButton.addActionListener ( new ActionListener ( ) {
                public void actionPerformed ( ActionEvent e ) {
                    System.exit ( 0 );
                }
            } );
        statusPanel.add ( quitButton );

        // Add the panels to the frame
        boardFrame.add ( statusPanel, BorderLayout.NORTH );
        boardFrame.add ( boardPanel, BorderLayout.CENTER );

        // Make sure it looks good
        boardFrame.pack ( );
        boardFrame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        boardFrame.setLocationRelativeTo ( null );
        boardFrame.setResizable ( false );
        boardFrame.setVisible ( true );
    }

    /**
     * Construct and display GUI for a Gomoku game
     * @param model Reference to a model that correctly
     * implements the interface, so that I know that all methods are
     * there.
     */
    public static void showGUI(final GomokuModel model) {
        // For thread safety, invoke GUI code on event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new GomokuGUI(model);
                }
            });
    }
}