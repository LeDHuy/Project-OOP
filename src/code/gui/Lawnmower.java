package code.gui;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author Vu Viet Phong
 */
public class Lawnmower extends JPanel {
    protected int locX;
    protected int locY;
    protected int widthL;
    protected int heightL;
    private static int speed = 20;
    protected boolean visible;
    private Image img;

    public Lawnmower(int locX, int locY) {
        this.locX = locX;
        this.locY = locY;
        setSize(82, 70);
        visible = true;
    }
    
    protected void loadImage(String imageName) {
        img = new ImageIcon(this.getClass().getResource(imageName)).getImage();
    }

    protected void getImageDimensions() {
        widthL = img.getWidth(null);
        heightL = img.getHeight(null);
    }

    public int getLocX() {
        return locX;
    }

    public void move() {
        locX += Lawnmower.getSpeed();
    }

    public static int getSpeed() {
        return speed;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Image getImage() {
        return img;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(locX, locY, widthL, heightL);
    }
}
