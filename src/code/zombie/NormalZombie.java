package code.zombie;

/**
 * @author Trương Đăng Khoa
 */
public class NormalZombie extends Zombie {
    private int speed = 1;
    public NormalZombie(int y) {
        super(y);
        setHealth(300);
        setDamage(50);
        loadImage("/images/zombies/normal_zombie.gif");
        getImageDimensions();
    }

    public void move() {
        locX -= speed;
    }
}
