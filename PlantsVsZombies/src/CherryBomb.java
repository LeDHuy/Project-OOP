import javax.swing.*;
import java.util.ArrayList;

/**
 * CherryBomb class representing a plant that explodes and damages zombies.
 */
public class CherryBomb extends Plant {

    private Timer explosionTimer;

    public CherryBomb(GamePanel parent, int x, int y) {
        super(parent, x, y);
        startExplosionCountdown();
    }

    private void startExplosionCountdown() {
        explosionTimer = new Timer(2000, e -> explode());
        explosionTimer.setRepeats(false);
        explosionTimer.start();
    }

    private void explode() {
        for (int row = Math.max(0, y - 1); row <= Math.min(4, y + 1); row++) {
            ArrayList<Zombie> zombiesInLane = gp.laneZombies.get(row);
            zombiesInLane.removeIf(zombie -> {
                int zombieCol = (zombie.posX - 44) / 100;
                return zombieCol >= x - 1 && zombieCol <= x + 1;
            });
        }

        System.out.println("Boom! CherryBomb exploded at (" + x + ", " + y + ").");

        gp.colliders[x + y * 9].assignedPlant = null;
        explosionTimer.stop();
    }

    @Override
    public void stop() {
        if (explosionTimer != null) {
            explosionTimer.stop();
        }
    }
}
