package code.zombie;

/**
 * @author Đinh Bình Thanh Thông
 */
public class FootballZombie extends Zombie {
    private int speed = 2;

    public FootballZombie(int y) {
        super(y);
        setHealth(500);
        setDamage(75);
        loadImage("/images/zombies/football_zombie.gif");
        getImageDimensions();
    }

    public void move() {
        locX -= speed;
    }
}
