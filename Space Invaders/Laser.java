import java.awt.*;
/**
* Curtis Noonan
* CS 251
* 4/9/2014
* Creates a new Laser object.
*/
public class Laser extends GameObject {

    public Laser(int x, int y) {
        super("Laser", x-LASER_WIDTH/2, y, LASER_WIDTH, LASER_HEIGHT);
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }
} 
