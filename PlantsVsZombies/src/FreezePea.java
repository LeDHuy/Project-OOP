import java.awt.*;

public class FreezePea extends Pea {
    // Constructor initializes a freeze pea with same parameters as regular pea
    public FreezePea(GamePanel parent,int lane,int startX){
        super(parent,lane,startX);
    }
    // Override the advance method from the parent Pea class
    @Override
    public void advance(){
        // Construct the freeze pea's hitbox.
        Rectangle pRect = new Rectangle(posX,130+myLane*120,28,28);
        // Check for collisions with zombies in the same lane
        for (int i = 0; i < gp.laneZombies.get(myLane).size(); i++) {
            Zombie z = gp.laneZombies.get(myLane).get(i);
            // Make a zombie hitbox
            Rectangle zRect = new Rectangle(z.posX,109 + myLane*120,400,120);
            // If freeze pea hits zombie
            if(pRect.intersects(zRect)){
                z.health -= 300; // Inflict damage on the zombie
                z.slow(); // Particular to FreezePea: reduce the zombie's speed
                boolean exit = false;
                // Handle zombie death
                if(z.health < 0){
                    System.out.println("ZOMBIE DIE");
                    GamePanel.setProgress(10);
                    gp.laneZombies.get(myLane).remove(i);
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
