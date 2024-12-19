package entity.plant;
import GamePlayandGUI.GamePanel;

public class Wallnut extends Plant {

    public Wallnut(GamePanel parent, int x, int y) {
        super(parent, x, y);
        this.health = 2000;
    }

    @Override
    public void stop() {}
}
