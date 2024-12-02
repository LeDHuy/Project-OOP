import javax.swing.*;

public class Wallnut extends Plant {

    public Wallnut(GamePanel parent, int x, int y) {
        super(parent, x, y);
        this.health = 3000;
    }

    @Override
    public void stop() {}
}
