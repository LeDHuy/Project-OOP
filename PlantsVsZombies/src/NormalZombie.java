/**
 * Represents a normal/basic zombie type in the game
 * Inherits from the base Zombie class
 * This is the standard zombie with default health and speed values
 */
public class NormalZombie extends Zombie {
/**
     * Constructor to create a new normal zombie
     * 
     * @param parent The GamePanel that contains this zombie
     * @param lane The lane number where this zombie will move
     */
    public NormalZombie(GamePanel parent,int lane){
        // Apply the parameters that are provided to invoke the constructor of the parent class (Zombie).
        super(parent,lane);
    }

}
