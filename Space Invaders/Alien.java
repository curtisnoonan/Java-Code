import java.awt.*;
/**
* Curtis Noonan
* CS 251
* 4/9/2014
* Implements the Shooter interface and creates Alien objects for space invaders.
*/
public class Alien extends GameObject implements Shooter<Missile> {

    private static final int POSITIONS = 2;
    private int animationPosition = 0;

    public Alien(int x, int y, int width, int height) {
        super("Alien", x, y, width, height);
    }

    public void updateAnimation() {
        // Advance to next animation position
        animationPosition++;
        animationPosition %= POSITIONS;
    }

    public Missile fire() {
        return new Missile(x+width/2, y+height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        
        int xCenter = x + width/2;
        int yCenter = y + height/2;
        if(animationPosition == 0) {
            g.drawLine(xCenter, yCenter, x, y);
            g.drawLine(xCenter, yCenter, x+width, y);
        } else {
            g.drawLine(xCenter, yCenter, x+width/4, y);
            g.drawLine(xCenter, yCenter, x+3*width/4, y);
        }

        g.fillOval(x+width/4, y+height/4, width/2, 3*height/4);
    }
}
