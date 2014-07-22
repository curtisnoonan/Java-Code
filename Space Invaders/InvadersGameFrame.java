import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
/** Curtis Noonan
 * CS 251 5/5/14
 * Creates the GUI for the space invaders game and contains 
 * all the methods to run the game.
 */
public class InvadersGameFrame extends JFrame implements GameData{
    /** Sets up the JFrame, JPanel, JLabels, and JButtons
    */
    ArrayList<GameObject> aliens = new ArrayList<>();
    ArrayList<GameObject> lasers = new ArrayList<>();
    ArrayList<GameObject> missiles = new ArrayList<>();
    //initializes the ship
    GameObject ship = new Ship(400,580,20,20);
    public static final int FPS = 30;
    private int frameCounter = 0;
    boolean leftPressed;
    boolean rightPressed;
    boolean firePressed;
    int numAliens = 0;
    int score = 0;
    int lives = 3;
    //for start or paused
    boolean gameState = true;
    //for game over
    boolean gameDone = false;
    /** Sets up the game window and controls the game
    */
    public InvadersGameFrame(){
        JFrame window = new JFrame("Game Window");
        this.getContentPane().setPreferredSize(new Dimension(800, 600));
        this.setTitle("Space Invaders");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);
          
        final JPanel gameboard  = new PaintClass();
        //Put the text inside the gameboard to keep things simple
        gameboard.setLayout(new FlowLayout());
        gameboard.setBackground(Color.BLACK);
        gameboard.setSize(800,600);
        this.add(gameboard);
        
        //final so I can modify it within the inner method
        final JLabel numAliensKilled = new JLabel("Aliens Killed: " + numAliens);
        numAliensKilled.setForeground(Color.WHITE);
        gameboard.add(numAliensKilled);
         
        final JLabel numLivesLeft = new JLabel("Lives Remaining: " + lives);
        numLivesLeft.setForeground(Color.WHITE);
        gameboard.add(numLivesLeft);
        
        final JLabel currentScore = new JLabel("Score: " + score);
        currentScore.setForeground(Color.WHITE);
        gameboard.add(currentScore);
        
        //For game over
        final JPanel blackPanel = new JPanel();
        blackPanel.setBackground(Color.BLACK);
        blackPanel.setSize(800,600);
        blackPanel.setVisible(false);
        this.add(blackPanel);
        
        final JLabel gameOver = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOver.setForeground(Color.WHITE);
        gameOver.setVisible(false);
        blackPanel.add(gameOver);
        
        //Start and pause button
        final JButton start = new JButton("START");
        start.setFocusable(false);
        start.addActionListener(new ActionListener() { 
            /** @param ActionEvent
            * Toggles a JButton between START and PAUSE
            */
            public void actionPerformed(ActionEvent ev) {
                if(start.getText().equals("START")){
                    start.setText("PAUSE");
                    gameState = false;
                }
                else{
                    start.setText("START");
                    gameState = true;
                }
            } 
        });
        ActionListener timeListener = new ActionListener(){
            /** @param ActionEvent
            * Runs all the methods that control the game
            */
            public void actionPerformed(ActionEvent ev){
                if(gameDone == true){
                    gameOver.setVisible(true);
                    gameState = false;
                    gameboard.setVisible(false);
                    blackPanel.setVisible(true);
                }
                if(gameState == true){
                    numAliensKilled.setText ("Aliens Killed: " + numAliens);
                    numLivesLeft.setText ("Lives Remaining: " + lives);
                    currentScore.setText ("Score: " + score);
                    if(++frameCounter % FPS == 0){
                        moveAliens();
                        if(missiles.isEmpty() == true){
                            whichAlien();
                        }
                    }                    
                    moveShip();
                    moveLaser();
                    hitsAlien();
                    moveMissile();
                    nextRound();
                    repaint();
                }
            }
        };
        Timer timer = new Timer(1000/FPS, timeListener);
        timer.start();
        
        addKeyListener(new KeyHandler());
        gameboard.add(start); 
        window.setResizable(false);
        //creates the aliens that begin the game
        initialize();
        this.pack();
        this.setLocationRelativeTo(null);
    }
    /** Class that takes care of drawing the GameObjects
    */
    public class PaintClass extends JPanel{
        /** @param Graphics
        * Draws the aliens, ship, missiles, and lasers
        */
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            for(int counter = 0; counter < aliens.size(); counter++){
                aliens.get(counter).draw(g);
            }
            ship.draw(g);
            if(missiles.size() > 0){
                missiles.get(0).draw(g);
            }
            if(lasers.size() > 0){
                lasers.get(0).draw(g);
            }
        }
    }
    /** Controls all the KeyListeners
    */
    private class KeyHandler extends KeyAdapter {
        /** @param KeyEvent
        * looks for if a key is pressed
        */
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if(lasers.isEmpty()){
                    firePressed = true;
                    fireLaser();
                
                }
            }
        }
        /** @param KeyEvent
        * looks for if a key is released
        */
        public void keyReleased(KeyEvent e) {			
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = false;
            }
        }
        /** @param KeyEvent
        * listens for the escape key
        */
        public void keyTyped(KeyEvent e) {
            // if escape is pressed, then quit the game
            if (e.getKeyChar() == 27) {
                System.exit(0);
            }
        }                       
    }
    /** Creates all the aliens GameObjects at the beginning of the game
    */
    private void initialize() {
        for (int row=0; row < 5; row++){
            for (int x=0; x < 12; x++){
                GameObject alien = new Alien(100+(x*50),(50)+row*30, 25, 25);
                aliens.add(alien);
            }
        }
        lives = 3;
        numAliens = 0;
    }
    //to tell the aliens which direction to move
    boolean rightOrLeft = true;
    /** Moves the aliens left, right, and down
    */
    private void moveAliens(){
        for(int counter = 0; counter < aliens.size(); counter++){
            if(aliens.get(counter).getX() <= 800 && aliens.get(counter).getY() <= 600){
                if(rightOrLeft == true){
                    //moves right
                    for(int counterTwo = 0; counterTwo < aliens.size(); counterTwo++){
                        int alienX = aliens.get(counterTwo).getX();
                        int alienY = aliens.get(counterTwo).getY();
                        aliens.set(counterTwo, new Alien(alienX + 1, alienY, 25, 25));
                    }
                }
                if(rightOrLeft == false){
                    //moves left
                    for(int counterTwo = 0; counterTwo < aliens.size(); counterTwo++){
                        int alienX = aliens.get(counterTwo).getX();
                        int alienY = aliens.get(counterTwo).getY(); 
                        aliens.set(counterTwo, new Alien(alienX - 1, alienY, 25, 25));
                    }
                } 
            }
            if(aliens.get(counter).getX() >= 800){
            rightOrLeft = false;
                //move down
                for(int counterTwo = 0; counterTwo < aliens.size(); counterTwo++){
                    int alienX = aliens.get(counterTwo).getX();
                    int alienY = aliens.get(counterTwo).getY();
                    aliens.set(counterTwo, new Alien(alienX , alienY + 20, 25, 25));
                }   
            }
            if(aliens.get(counter).getX() <= 0){
            rightOrLeft = true;
                //moves down
                for(int counterTwo = 0; counterTwo < aliens.size(); counterTwo++){
                    int alienX = aliens.get(counterTwo).getX();
                    int alienY = aliens.get(counterTwo).getY();
                    aliens.set(counterTwo, new Alien(alienX, alienY + 20, 25, 25));
                }                
            }
            //Indicate the game is over
            if(aliens.get(counter).getY() >= 560){
                gameDone = true;
            }
        }
    }
    /** Moves the ship left and right by incrementing 
    * and decrementing the x-value of the ship.
    */
    private void moveShip(){
        if(leftPressed == true && ship.getX() >=0){
            ship = new Ship(ship.getX()-5, ship.getY(), 20, 20);
        }
        if(rightPressed == true && ship.getX() <= 790){
            ship = new Ship(ship.getX()+5, ship.getY(), 20, 20);
        }
        if(lives == 0){
            gameDone = true;
        }
    }
    /** Creates a new Laser object and adds it to the laser arraylist
    */
    private void fireLaser(){
    GameObject laser = new Laser(ship.getX() + 10, ship.getY());
    lasers.add(laser);
        
    }
    /** Moves the laser upwards, removes the laser if it is out of bounds
    */
    private void moveLaser(){
        if(lasers.size() > 0){
            int laserX = lasers.get(0).getX();
            int laserY = lasers.get(0).getY();
            lasers.set(0, new Laser(laserX + 1, laserY - 8)); 
            if(lasers.get(0).getY() <= 0){
                lasers.remove(0);
            }
        }
    }
    /** Checks to see if any Aliens are hit by a laser,
    * removes the laser and the alien and adds 10 points to the score
    */
    private void hitsAlien(){
        if(lasers.size() > 0){
            for(int counter = 0; counter < aliens.size(); counter++){
                if(lasers.get(0).intersects(aliens.get(counter))){
                    lasers.remove(0);
                    aliens.remove(counter);
                    numAliens++;
                    score+= 10;
                    break;
                }
            }
        }    
    }
    
    /** Generates a random number and uses that number to indicate
    * an alien in the array list to fire
    */ 
    private void whichAlien(){
    //generates a number between 1 and number of aliens
    Random rand = new Random();
    int whoFires = rand.nextInt(aliens.size());
    int whoFiresX = aliens.get(whoFires).getX();
    int whoFiresY = aliens.get(whoFires).getY();
    GameObject missile = new Missile(whoFiresX + 13, whoFiresY + 20);
    missiles.add(missile);
    }
    
    /** Moves the missile downward once an Alien fires it
    * removes the missile if it is out of bounds
    * if the missile hits the ship, it loses a life and creates
    * a new ship object placing it in the center
    */
    private void moveMissile(){
        if(missiles.size() > 0){
            int missileX = missiles.get(0).getX();
            int missileY = missiles.get(0).getY();
            missiles.set(0, new Missile(missileX + 2, missileY + 8));
            if(missiles.isEmpty() != true && missiles.get(0).getY() >= 580){
                missiles.remove(0);
            }
            if(missiles.isEmpty() != true && missiles.get(0).intersects(ship)){
                missiles.remove(0);
                lives--;
                ship = new Ship(400,580,20,20);
            }
        }
    }
    /** If all the aliens are killed,
    * starts another round of aliens
    */
    private void nextRound(){
        if(aliens.size() == 0){
            initialize();
        }
    }
    
}
