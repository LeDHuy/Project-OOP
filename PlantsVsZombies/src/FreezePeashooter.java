import java.awt.event.ActionEvent;
import javax.swing.*;

public class FreezePeashooter extends Plant {
    // A timer for controlling the frequency of shots
    public Timer shootTimer;
    // Constructor to build a freeze peashooter at a given location
    public FreezePeashooter(GamePanel parent,int x,int y) {
        // Call parent Plant class constructor
        super(parent,x,y);
        // Create a timer that shoots every 2000 milliseconds (2 seconds)
        shootTimer = new Timer(2000,(ActionEvent e) -> {
            //System.out.println("SHOOT");
            // Only shoot if there are zombies in the same lane
            if(gp.laneZombies.get(y).size() > 0) {
                // Add a new freeze pea to the lane
                // Position is calculated as 103 + (plant's x position * 100)
                gp.lanePeas.get(y).add(new FreezePea(gp, y, 103 + this.x * 100));
            }
        });
        // Start the shooting timer
        shootTimer.start();
    }
    // Override the stop method from Plant class
    @Override
    public void stop(){
        // Stop the shooting timer
        shootTimer.stop();
    }

}
