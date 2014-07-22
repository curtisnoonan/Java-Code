import java.awt.*;

public class Ship extends GameObject implements Shooter<Laser> {

    public Ship(int x, int y, int width, int height) {
        super("Ship", x, y, width, height);
    }

    public Laser fire() {
        // make a new laser object and send it on its way
        return new Laser(x+width/2, y-LASER_HEIGHT);
    }

    public void draw(Graphics g) {
        // set up the coordinates for ship shape
        int[] xs = new int[]{0, 0, width/3, width/2, 2*width/3, width, width};
        int[] ys = new int[]{height, height/2, height/2, 0, height/2, height/2, height};
        int numpoints = xs.length;

        // translate the coordinates to ship location
        for(int i = 0; i < numpoints; ++i) {
            xs[i] += x;
            ys[i] += y;
        }

        g.setColor(Color.GREEN);
        g.fillPolygon(xs, ys, numpoints);
    }
   
}
