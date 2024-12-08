import javax.swing.*;
import java.awt.event.ActionEvent;

public class Sunflower extends Plant {
    // A timer for controlling the amount of sunlight
    Timer sunProduceTimer;
    // Constructor for creating a sunflower at specific coordinates
    public Sunflower(GamePanel parent,int x,int y) {
        // Call parent Plant class constructor
        super(parent, x, y);
        // Create timer that produces sun every 15000 milliseconds (15 seconds)
        sunProduceTimer = new Timer(15000,(ActionEvent e) -> {
            // Create new sun object with calculated positions:
            // X position: 60 + (plant's x position * 100)
            // Start Y: 110 + (plant's y position * 120)
            // End Y: 130 + (plant's y position * 120)
            Sun sta = new Sun(gp,60 + x*100,110 + y*120,130 + y*120);
            // Add sun to active suns list
            gp.activeSuns.add(sta);
            // Add sun to game panel with layer priority 1
            gp.add(sta,new Integer(1));
        });
        // Start the sun production timer
        sunProduceTimer.start();
    }

}
