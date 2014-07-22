import java.awt.Rectangle;

public interface Object2D {

    /** @return x coordinate of upper left corner of object. */
    int getX();

    /** @return y coordinate of upper left corner of object. */
    int getY();

    /** @return object width. */
    int getWidth();

    /** @return object height. */
    int getHeight();

    /**
     * Get the bounding rectangle for the object.
     * @return Bounding rectangle.
     */
    Rectangle getBoundingRectangle();

    /**
     * Does this object intersect another? (Checking if the bounding
     * rectangles intersect will generally suffice.)
     * @param other The other object to check.
     * @return True if objects intersect.
     */
    boolean intersects(Object2D other);

    /** 
     * Is any part of the object outside of the game board?
     * @return True if part of object is out of bounds.
     */
    boolean isOutOfBounds();
}
