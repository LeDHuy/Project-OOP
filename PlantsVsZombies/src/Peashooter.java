import java.awt.event.ActionEvent;
import javax.swing.*;

public class Peashooter extends Plant {
    public Timer shootTimer;
    // A clock to manage the frequency of shots
    public Peashooter(GamePanel parent,int x,int y) {
        // Call parent Plant class constructor
        super(parent,x,y);
        // Set up a timer to go off every two seconds, or 2000 milliseconds.
        shootTimer = new Timer(2000,(ActionEvent e) -> {
            //System.out.println("SHOOT");
            // Only shoot if there are zombies in the same lane
            if(gp.laneZombies.get(y).size() > 0) {
                // Add a new pea to the lane
                // Position is calculated as 103 + (plant's x position * 100)
                gp.lanePeas.get(y).add(new Pea(gp, y, 103 + this.x * 100));
            }
        });
        // Set the timer for the shot.
        shootTimer.start();
    }
    // Override the stop method from Plant class
    @Override
    
    public void stop(){
        // Stop the shooting timer
        shootTimer.stop();
    }

}
