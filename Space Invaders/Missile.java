import java.awt.*;
/**
* Curtis Noonan
* CS 251
* 4/9/2014
* Creates a new Missile object.
*/
public class Missile extends GameObject {

    public Missile(int x, int y) {
        super("Missile", x - MISSILE_WIDTH/2, y, MISSILE_WIDTH, MISSILE_HEIGHT);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillArc(x, y, width, height, 180, 180);
    }
}
