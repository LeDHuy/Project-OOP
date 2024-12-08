import javax.swing.*;
import java.awt.*;

public class Pea {
    // The pea's X-coordinate position
    public int posX;
    // The allusion to the primary game panel
    protected GamePanel gp;
    // The pea's lane number as traveling
    public int myLane;
    // Constructor initializes a pea with its parent panel, lane number, and starting X position
    public Pea(GamePanel parent,int lane,int startX){
        this.gp = parent;
        this.myLane = lane;
        posX = startX;
    }
    // Algorithm for managing collision detection and pea movement
    public void advance(){
        // Draw a rectangle to symbolize the hitbox of the pea.
        Rectangle pRect = new Rectangle(posX,130+myLane*120,28,28);
        // Check for collisions with all zombies in the same lane
        for (int i = 0; i < gp.laneZombies.get(myLane).size(); i++) {
            Zombie z = gp.laneZombies.get(myLane).get(i);
             // Create a rectangle representing the zombie's hitbox
            Rectangle zRect = new Rectangle(z.posX,109 + myLane*120,400,120);
            // If pea hits a zombie
            if(pRect.intersects(zRect)){
                // Reduce zombie's health
                z.health -= 300;
                boolean exit = false;
                // If zombie dies
                if(z.health < 0){
                    System.out.println("ZOMBIE DIE");
                    // Clear the lane of zombies
                    gp.laneZombies.get(myLane).remove(i);
                    // Update the current condition of the game
                    GamePanel.setProgress(10);
                    exit = true;
                }
                // Remove the pea after hitting
                gp.lanePeas.get(myLane).remove(this);
                if(exit) break;
            }
        }
        /*if(posX > 2000){
            gp.lanePeas.get(myLane).remove(this);
        }*/
        posX += 15;
    }

}
