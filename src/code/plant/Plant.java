package code.plant;

import java.util.List;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

import code.bullet.Bullet;

/**
 * @author Vu Viet Phong
 */
public class Plant {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected static int health = 200;
    private List<Bullet> bullets;

    protected boolean visible;
    protected Image image;
    
    protected Plant(int x, int y) {
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

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
        Plant.health = health;
    }

    public List<Bullet> getBullets() {
        return bullets;
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
}

