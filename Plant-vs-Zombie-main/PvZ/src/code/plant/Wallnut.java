package code.plant;

/**
 * @author Đinh Bình Thanh Thông
 */
public class Wallnut extends Plant {
    public Wallnut(int x, int y) {
        super(x, y);
        setHealth(500);
        loadImage("/images/plants/wallnut_full_life.gif");
    }
}
