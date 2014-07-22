import javax.swing.*;
import java.util.ArrayList;
/** Curtis Noonan
 * CS 251 4/16/14
 * Used to run the GUI from the InvadersGameFrame.java 
 */
public class SpaceInvaders {   
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(new Runnable() { 
            public void run() { 
                JFrame frame = new InvadersGameFrame(); 
                frame.setVisible(true); 
            } 
        });
    }
    
}
