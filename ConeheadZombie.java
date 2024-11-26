package Zoombie;
public class ConeheadZombie extends Zombie {
    private int speed = 1;

    public ConeheadZombie(int y) {
        super(y);
        setHealth(400);
        setDamage(60);
        loadImage("/images/zombies/conehead_zombie.gif");
        getImageDimensions();
    }

    public void move() {
        locX -= speed;
    }
}

