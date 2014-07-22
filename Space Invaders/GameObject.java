import java.awt.*;
/**
* Curtis Noonan
* CS 251
* 4/9/2014
* Implements the Object 2D interface and creates the objects for space invaders.
*/
public abstract class GameObject implements Object2D, GameData {

    protected final String name;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public GameObject(String name,
                      int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public boolean isOutOfBounds() {
        return !getBoardRectangle().contains(getBoundingRectangle());
    }

    public Rectangle getBoardRectangle() {
        return new Rectangle(0, 0, GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT);
    }

    public boolean intersects(Object2D other) {
        return getBoundingRectangle().intersects(other.getBoundingRectangle());
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public String toString() {
        return name + " at (" + x + ", " + y + ")";
    }

    public abstract void draw(Graphics g);
    
}
