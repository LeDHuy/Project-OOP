package code.bullet;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * @author Vu Viet Phong
 */
public class Bullet {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected static int speed = 2;
    protected static int damage = 50;

    protected boolean visible;
    protected Image image;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void loadImage(String imageName) {
        image = new ImageIcon(this.getClass().getResource(imageName)).getImage();
    }

    protected void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static double getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Bullet.speed = speed;
    }

    public static int getDamage() {
        return damage;
    }

    public static void setDamage(int damage) {
        Bullet.damage = damage;
    }    

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void move() {
        x += Bullet.getSpeed();
    }
}
