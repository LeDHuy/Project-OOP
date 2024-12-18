package code.zombie;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * @author Vũ Viết Phong
 */
public abstract class Zombie {
    protected int locX = 1295;
    protected int y;
    protected int width;
    protected int height;

    protected static int health;
    protected static int damage;
    
    protected boolean visible;
    protected Image image;

    protected Zombie(int y) {
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
        return locX;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
        Zombie.health = health;
    }

    public static int getDamage() {
        return damage;
    }

    public static void setDamage(int damage) {
        Zombie.damage = damage;
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
        return new Rectangle(locX, y, width, height);
    }

    public abstract void move();
}
