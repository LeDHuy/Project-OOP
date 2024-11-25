package Plants;

public class WallNut extends Plant {
    public WallNut(int x, int y) {
        super(x, y);
        setHealth(500);
        loadImage("/images/plants/wallnut_full_life.gif");
    }
}
