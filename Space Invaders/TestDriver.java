import java.util.*;

/**
 * Driver for testing out space invaders objects.
 */
public class TestDriver {

    public static void main(String[] args) {

        // Make some game objects and test out their methods.
        List<Object2D> objects = new ArrayList<>();

        // Make a ship and fire its laser.
        Ship ship = new Ship(200, 155, 50, 40);
        objects.add(ship);
        Laser shot = ship.fire();
        objects.add(shot);

        // Make some aliens and fire missiles
        for(int x = -10; x < 300; x+= 65) {
            for(int y = -5; y < 200; y += 30) {
                Alien alien = new Alien(x, y, 55, 25);
                objects.add(alien);
                Missile missile = alien.fire();
                objects.add(missile);
            }
        }

        // Print out a bunch of stuff about the objects
        for(Object2D object : objects) {

            // Make sure toString was properly overridden.
            System.out.println(object);

            // Check Object2D methods
            System.out.format("  Vals = %d %d %d %d%n",
                              object.getX(), object.getY(),
                              object.getWidth(), object.getHeight());            
            System.out.println("  Bounds = " + object.getBoundingRectangle());
            System.out.println("  Intersects ship? " + object.intersects(ship));
            System.out.println("  Out of bounds? " + object.isOutOfBounds());

            // This better be true for all
            System.out.println("  Is GameObject? " + (object instanceof GameObject));

            // Should be true for Ship and Alien
            System.out.println("  Is Shooter? " + (object instanceof Shooter));
        }
    }

}
