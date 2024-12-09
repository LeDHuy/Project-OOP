import javax.swing.*;
import java.util.ArrayList;

/**
 * CherryBomb class representing a plant that explodes and damages zombies.
 */
public class CherryBomb extends Plant {

    // Timer to handle the delay before the explosion occurs
    private Timer explosionTimer;
    
    // Constructor to initialize the CherryBomb and start the countdown for explosion
    public CherryBomb(GamePanel parent, int x, int y) {
        super(parent, x, y); // Initialize the base Plant class with game panel and position
        startExplosionCountdown(); // Start the 2-second countdown for explosion
    }

    /**
     * Start a 2-second countdown before the explosion.
     * This method creates and starts a Timer that triggers the explosion after 2000 ms (2 seconds).
     */
    private void startExplosionCountdown() {
        // Create a timer that triggers after 2000 milliseconds (2 seconds)
        explosionTimer = new Timer(2000, e -> explode());
        explosionTimer.setRepeats(false); // The timer will only fire once
        explosionTimer.start(); // Start the countdown timer
    }

    /**
     * Handle the explosion of the CherryBomb.
     * This method removes zombies in the 3x3 area around the CherryBomb's position.
     */
    private void explode() {
         // Iterate over rows affected by the explosion: current row (y), row above (y - 1), and row below (y + 1)
        for (int row = Math.max(0, y - 1); row <= Math.min(4, y + 1); row++) {
            
            // Get the list of zombies in the current row
            ArrayList<Zombie> zombiesInLane = gp.laneZombies.get(row);
            
            // Remove zombies in the explosion range (3x3 grid) in the affected row
            zombiesInLane.removeIf(zombie -> {

                // Calculate the column of the zombie based on its x position
                int zombieCol = (zombie.posX - 44) / 100;

                // Check if the zombie is in the explosion range (1 column left, center, 1 column right)
                return zombieCol >= x - 1 && zombieCol <= x + 1;
            });
        }

        // Print a message to the console indicating the explosion has occurred at the given position
        System.out.println("Boom! CherryBomb exploded at (" + x + ", " + y + ").");

         // Remove the CherryBomb from the grid by setting its assigned plant to null
        gp.colliders[x + y * 9].assignedPlant = null;

        // Stop the explosion timer since the explosion has occurred
        explosionTimer.stop();
    }

    /**
     * Stop the explosion timer if the CherryBomb is removed before the explosion occurs.
     */
    @Override
    public void stop() {

         // If the timer exists, stop it to prevent the explosion from triggering
        if (explosionTimer != null) {
            explosionTimer.stop();
        }
    }
}
