// A particular kind of zombie called as a "ConeHeadZombie" has a traffic cone on its head.
public class ConeHeadZombie extends Zombie {
    // A constructor in a particular game lane that generates a new ConeHeadZombie
    public ConeHeadZombie(GamePanel parent,int lane){
        // Gets the same parameters for calling the parent (Zombie) class constructor.
        super(parent,lane);
        health = 1800; // Improves this zombie's health to 1800, which should make it stronger than other zombies.
    }
}
