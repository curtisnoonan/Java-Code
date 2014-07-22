/**
 * Interface for objects that can shoot something.
 * T The type of "bullet" that is fired.
 */
public interface Shooter<T extends GameObject> {

    /**
     * Create a new "bullet" and return it.
     * @return Fired object.
     */
    T fire();

}
