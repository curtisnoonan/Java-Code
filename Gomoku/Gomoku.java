import cs251.lab2.*;
/** Curtis Noonan
This class is used to play Gomoku, get 5 X's or 5 0's in a row to win!*/
public class Gomoku implements GomokuModel{

    public static void main( String[] args ) { 
        Gomoku game = new Gomoku(); 
        if(args.length > 0) { 
            game.setComputerPlayer(args[0]); 
        } 
        GomokuGUI.showGUI(game); 
    }
    
    Square[][] gameBoardTiles = new Square[NUM_VSQUARES][NUM_HSQUARES]; //2D array for coordinates
    boolean computerEnabled = false; //to determine who is playing
    
    /* clears the game board with empty squares */
    public void newGame(){
        for (int row = 0; row < NUM_VSQUARES; row++) { // Iterate over each row.
            for (int col = 0; col < NUM_HSQUARES; col++) { // Iterate over each column.
                gameBoardTiles[row][col] = Square.EMPTY;
            }
        }
    }
    
    /* returns a string representation of the Gomoku board */
    public String boardString ( ){
        String boardInString = "";
        for (int row = 0; row < NUM_VSQUARES; row++) { // Iterate over each row.
            for (int col = 0; col < NUM_HSQUARES; col++) { // Iterate over each column.
                boardInString += gameBoardTiles[row][col].toChar();
            }
            boardInString += "\n";
        }
        return boardInString;   
    }
    /* returns an Outcome and takes the coordinates of a click from the mouse
        currently does not work as I can not get the computer to draw rings, but
        it is close*/
    public Outcome doClick ( int row, int col ){
            int counter = 0;
            if(counter == 0){
                counter++;
                gameBoardTiles[row][col] = Square.CROSS;
                if(checkForCrossWin()){
                    return Outcome.CROSS_WINS;
                }
                else if (checkForDraw()){
                    return Outcome.DRAW;
                }
                else{
                    return Outcome.GAME_NOT_OVER;
                }
            }
            if(computerEnabled == true){
                if(counter == 1){
                    counter--;
                        if(gameBoardTiles[row][col-1] == Square.EMPTY){ //places a O to the left if it can
                            gameBoardTiles[row][col-1] = Square.RING;
                        }
                        else if(gameBoardTiles[row][col+1] == Square.EMPTY){ //places a O to the right if it can
                            gameBoardTiles[row][col+1] = Square.RING;
                        }
                        else if(gameBoardTiles[row+1][col] == Square.EMPTY){ 
                            gameBoardTiles[row+1][col] = Square.RING;
                        }
                        else if(gameBoardTiles[row-1][col] == Square.EMPTY){
                            gameBoardTiles[row-1][col] = Square.RING;
                        }
                        else if(gameBoardTiles[row+1][col+1] == Square.EMPTY){
                            gameBoardTiles[row+1][col+1] = Square.RING;
                        }
                        else if(gameBoardTiles[row+1][col-1] == Square.EMPTY){
                            gameBoardTiles[row+1][col-1] = Square.RING;
                        }
                        else if(gameBoardTiles[row-1][col+1] == Square.EMPTY){
                            gameBoardTiles[row-1][col+1] = Square.RING;
                        }
                        else if(gameBoardTiles[row+1][col-1] == Square.EMPTY){
                            gameBoardTiles[row+1][col-1] = Square.RING;
                        }
                        else{
                            for (int row1 = 0; row1 < NUM_VSQUARES; row1++) { //if it can place from above chooses first available
                                for (int col1 = 0; col1 < NUM_HSQUARES; col1++) { 
                                    if(gameBoardTiles[row1][col1] != Square.EMPTY){
                                        gameBoardTiles[row1][col1] = Square.RING;
                                    }
                                }
                            }
                        }
                    if(checkForRingWin()){
                        return Outcome.RING_WINS;
                    }
                    else if (checkForDraw()){
                        return Outcome.DRAW;
                    }
                    else{
                        return Outcome.GAME_NOT_OVER;
                    }
                }
            }
            else{
                gameBoardTiles[row][col] = Square.RING;
                if(checkForRingWin()){
                    return Outcome.RING_WINS;
                }
                else if (checkForDraw()){
                    return Outcome.DRAW;
                }
                else{
                    return Outcome.GAME_NOT_OVER;
                }
            }
        return Outcome.GAME_NOT_OVER;
    }
    
    /* returns a boolean if 5 X are in a row*/
    public boolean checkForCrossWin(){
        for(int col = 0; col < NUM_HSQUARES; col++){ 
            for(int row = 0; row < NUM_VSQUARES; row++){ 
                if(gameBoardTiles[row][col] == Square.CROSS){
                    int numCrossLeft = 0; //probably an easier way, but this works
                    int numCrossRight = 0;
                    int numCrossUp = 0;
                    int numCrossDown = 0;
                    int numCrossRightHoriz = 0;
                    int numCrossLeftHoriz = 0;
                    int numCrossBotLeftHoriz = 0;
                    int numCrossBotRightHoriz = 0;
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row][col+counter+1] == Square.CROSS){ //right side
                            numCrossRight++;
                            if (numCrossRight == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    } 
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row][col-counter-1] == Square.CROSS){ //left side
                            numCrossLeft++;
                            if (numCrossLeft == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row+counter+1][col] == Square.CROSS){ //vertical 
                            numCrossUp++;
                            if (numCrossUp == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row-counter-1][col] == Square.CROSS){ //down
                            numCrossDown++;
                            if (numCrossDown == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row+counter+1][col+counter+1] == Square.CROSS){ //right horiz
                            numCrossRightHoriz++;
                            if (numCrossRightHoriz == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row+counter+1][col-counter-1] == Square.CROSS){ //left horiz
                            numCrossLeftHoriz++;
                            if (numCrossLeftHoriz == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row-counter-1][col-counter-1] == Square.CROSS){ //bot left horiz
                            numCrossBotLeftHoriz++;
                            if (numCrossBotLeftHoriz == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row-counter-1][col+counter+1] == Square.CROSS){ //bot right horiz
                            numCrossBotRightHoriz++;
                            if (numCrossBotRightHoriz == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /* returns a boolean if 5 O are in a row */
    public boolean checkForRingWin(){
        for(int col = 0; col < NUM_HSQUARES; col++){ 
            for(int row = 0; row < NUM_VSQUARES; row++){ 
                if(gameBoardTiles[row][col] == Square.RING){
                    int numRingLeft = 0;
                    int numRingRight = 0;
                    int numRingUp = 0;
                    int numRingDown = 0;
                    int numRingRightHoriz = 0;
                    int numRingLeftHoriz = 0;
                    int numRingBotLeftHoriz = 0;
                    int numRingBotRightHoriz = 0;
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row][col+counter+1] == Square.RING){ //right side
                            numRingRight++;
                            if (numRingRight == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    } 
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row][col-counter-1] == Square.RING){ //left side
                            numRingLeft++;
                            if (numRingLeft == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row+counter+1][col] == Square.RING){ //vertical 
                            numRingUp++;
                            if (numRingUp == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row-counter-1][col] == Square.RING){ //down
                            numRingDown++;
                            if (numRingDown == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row+counter+1][col+counter+1] == Square.RING){ //right horiz
                            numRingRightHoriz++;
                            if (numRingRightHoriz == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row+counter+1][col-counter-1] == Square.RING){ //left horiz
                            numRingLeftHoriz++;
                            if (numRingLeftHoriz == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row-counter-1][col-counter-1] == Square.RING){ //bot left horiz
                            numRingBotLeftHoriz++;
                            if (numRingBotLeftHoriz == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                    for(int counter = 0; counter < SQUARES_IN_LINE_FOR_WIN-1; counter++){
                        if(gameBoardTiles[row-counter-1][col+counter+1] == Square.RING){ //bot right horiz
                            numRingBotRightHoriz++;
                            if (numRingBotRightHoriz == SQUARES_IN_LINE_FOR_WIN-1){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /* checks if there is a draw*/
    public boolean checkForDraw(){
        int numPlayed = 0; 
        for (int row = 0; row < NUM_VSQUARES; row++) { 
            for (int col = 0; col < NUM_HSQUARES; col++) {
                if (gameBoardTiles[row][col] == Square.CROSS || gameBoardTiles[row][col] == Square.RING){ //check if not empty
                    numPlayed++;
                }
            }
        }
        if(numPlayed == NUM_VSQUARES*NUM_HSQUARES){ //checks to see if the board is full
            return true;
        }
        else{
            return false;
        }
    }
    /* checks if there is an opponent or if you are playing against a computer takes a string arg*/
    public void setComputerPlayer(String opponent){
        if(opponent.equals("COMPUTER")){ //checking command line arg
            computerEnabled = true; //enables computer
        }
    }
}
