package GamePlayandGUI;

import javax.swing.*;

import entity.plant.CherryBomb;
import entity.plant.FreezePea;
import entity.plant.FreezePeashooter;
import entity.plant.Pea;
import entity.plant.Peashooter;
import entity.plant.Plant;
import entity.plant.Sunflower;
import entity.plant.Wallnut;
import entity.zombie.ConeHeadZombie;
import entity.zombie.NormalZombie;
import entity.zombie.Zombie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

// GamePanel class handles the main gameplay area and logic
public class GamePanel extends JLayeredPane implements MouseMotionListener {

    // Background and object images for the game
    // Background and object images for the game
    Image bgImage; // Background image
    Image lawnMowerImage; // Lawn mower (clears lanes)
    Image peashooterImage, freezePeashooterImage, sunflowerImage; // Plant images
    Image cherryBombImage, wallnutImage, peaImage, freezePeaImage; // Additional plant and projectile images
    Image normalZombieImage, coneHeadZombieImage; // Zombie types
    
    // Data structures for game elements
    public Collider[] colliders; // A grid of colliders for placing plants
    
    public ArrayList<ArrayList<Zombie>> laneZombies;// Zombies present in each lane
    public ArrayList<ArrayList<Pea>> lanePeas; // Projectiles (peas) in each lane
    public ArrayList<Sun> activeSuns; // Suns available to collect
    ArrayList<ArrayList<LawnMower>> laneLawnMowers; // Lawn mowers in each lane

    // Timers to control the flow of the game
    Timer redrawTimer; // Repaints the screen at regular intervals
    Timer advancerTimer; // Updates positions of game objects
    Timer lawnMowerProducer, sunProducer, zombieProducer; // Handles the creation of specific objects
    JLabel sunScoreboard; // Displays the player's current sun score

    GameWindow.PlantType activePlantingBrush = GameWindow.PlantType.None; // Tracks the currently selected plant type
    sound Sound = new sound(); // Handles sound effects and music
    int mouseX, mouseY; // Current mouse cursor position

    private int sunScore; // Tracks the player's available sun currency

    // Getter for the sun score
    public int getSunScore() {
        return sunScore;
    }

    // Setter for the sun score and updates the scoreboard
    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        sunScoreboard.setText(String.valueOf(sunScore)); // Update UI label
    }

    // Constructor for initializing the game panel
    public GamePanel(JLabel sunScoreboard) {
        setSize(1000, 752); // Set the panel dimensions
        setLayout(null); // No layout manager to allow custom placement
        addMouseMotionListener(this); // Enable mouse tracking for gameplay
        this.sunScoreboard = sunScoreboard; // Associate the sun scoreboard
        setSunScore(150); // Initialize the player's sun score with a default value
        playMusic(0); // Start background music (track 0)

        // Load images for all game elements
        bgImage = new ImageIcon(this.getClass().getResource("/images/main/mainBG.png")).getImage();
        lawnMowerImage = new ImageIcon(this.getClass().getResource("/images/lawnmowerIdle.gif")).getImage();
        peashooterImage = new ImageIcon(this.getClass().getResource("/images/plants/peashooter.gif")).getImage();
        freezePeashooterImage = new ImageIcon(this.getClass().getResource("/images/plants/freezepeashooter.gif")).getImage();
        sunflowerImage = new ImageIcon(this.getClass().getResource("/images/plants/sunflower.gif")).getImage();
        cherryBombImage = new ImageIcon(this.getClass().getResource("/images/plants/cherrybomb.gif")).getImage();
        wallnutImage = new ImageIcon(this.getClass().getResource("/images/plants/walnut.gif")).getImage();
        peaImage = new ImageIcon(this.getClass().getResource("/images/pea.png")).getImage();
        freezePeaImage = new ImageIcon(this.getClass().getResource("/images/freezepea.png")).getImage();
        normalZombieImage = new ImageIcon(this.getClass().getResource("/images/zombies/zombie1.png")).getImage();
        coneHeadZombieImage = new ImageIcon(this.getClass().getResource("/images/zombies/coneheadzombie.gif")).getImage();

        // Initialize arrays for zombies, peas, and lawn mowers in each lane
        laneZombies = new ArrayList<>();
        lanePeas = new ArrayList<>();
        laneLawnMowers = new ArrayList<>();

        for (int i = 0; i < 5; i++) { // For each lane
            laneZombies.add(new ArrayList<>()); // Initialize zombie list
            lanePeas.add(new ArrayList<>()); // Initialize pea list
            laneLawnMowers.add(new ArrayList<>()); // Initialize lawn mower list
        }

        // Add lawn mowers to their respective lanes
        for (int i = 0; i < 5; i++) {
            laneLawnMowers.set(i, new LawnMower(GamePanel.this, i)); // Each lane has one lawn mower
        }

        // Initialize the collider grid (5 rows x 9 columns = 45 cells)
        colliders = new Collider[45];
        for (int i = 0; i < 45; i++) {
            Collider a = new Collider(); // Create a collider object
            a.setLocation(44 + (i % 9) * 100, 109 + (i / 9) * 120); // Position based on row and column
            a.setAction(new PlantActionListener((i % 9), (i / 9))); // Set action for planting
            colliders[i] = a; // Store in the collider array
            add(a, new Integer(0)); // Add to the panel
        }

         activeSuns = new ArrayList<>(); // Initialize the active suns list

        // Timer to repaint the panel every 25 milliseconds
        redrawTimer = new Timer(25, (ActionEvent e) -> repaint());
        redrawTimer.start(); // Start the timer

        // Timer to update positions of objects every 60 milliseconds
        advancerTimer = new Timer(60, (ActionEvent e) -> {
            advance();
        });
        advancerTimer.start();

        // Timer to spawn suns every 5 seconds at random positions
        sunProducer = new Timer(5000, (ActionEvent e) -> {
            Random rnd = new Random();
            Sun sta = new Sun(this, rnd.nextInt(800) + 100, 0, rnd.nextInt(300) + 200); // Random x, y coordinates
            activeSuns.add(sta); // Add the new sun to the list
            add(sta, new Integer(1)); // Add to panel with proper z-index
        });
        sunProducer.start(); // Start the timer

        // Timer to spawn zombies every 7 seconds
        zombieProducer = new Timer(7000, this::actionPerformed);
        zombieProducer.start();
    }

    // Add a lawn mower object to the panel
    private void add(LawnMower mower, Integer integer) {}

    // Play background music
    public void playMusic(int i) {
        Sound.setFile(i);
        Sound.play();
        Sound.loop();
    }
    public void stopMusic() {
        Sound.stop();
    }
     // Advance the positions of game objects (zombies, peas, and suns)
     private void advance() {
        for (int i = 0; i < 5; i++) { // For each lane
            for (Zombie z : laneZombies.get(i)) { // Update zombies
                z.advance();
            }
            for (int j = 0; j < lanePeas.get(i).size(); j++) { // Update peas
                Pea p = lanePeas.get(i).get(j);
                p.advance();
            }
        }

        for (int i = 0; i < activeSuns.size(); i++) { // Update suns
            activeSuns.get(i).advance();
        }
    }
    // Paint all game elements on the screen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage,0,0,null); // Draw background

        // Draw plants, zombies, peas, and lawn mowers in their respective lanes
        // Logic to determine positions and images is included here
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if(c.assignedPlant != null){
                Plant p = c.assignedPlant;
                if(p instanceof Peashooter){
                    g.drawImage(peashooterImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof FreezePeashooter){
                    g.drawImage(freezePeashooterImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof Sunflower){
                    g.drawImage(sunflowerImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof CherryBomb){
                    g.drawImage(cherryBombImage, 60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof Wallnut){
                    g.drawImage(wallnutImage, 60 + (i%9)*100,129 + (i/9)*120,null);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            LawnMower mower = (LawnMower) laneLawnMowers.get(i);
            if (mower != null && !mower.isActivated()) {
                g.drawImage(lawnMowerImage, mower.getPosX(), mower.getPosY(), null); // Vẽ máy cắt cỏ chưa được kích hoạt
            }
        }
        for (int i = 0; i < 5 ; i++) {
            for(Zombie z : laneZombies.get(i)){
                if(z instanceof NormalZombie){
                    g.drawImage(normalZombieImage,z.posX,109+(i*120),null);
                }else if(z instanceof ConeHeadZombie){
                    g.drawImage(coneHeadZombieImage,z.posX,109+(i*120),null);
                }
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                if(p instanceof FreezePea){
                    g.drawImage(freezePeaImage, p.posX, 130 + (i * 120), null);
                }else {
                    g.drawImage(peaImage, p.posX, 130 + (i * 120), null);
                }
            }
        
        }

    }
    // Spawn zombies based on level data
    private void actionPerformed(ActionEvent e) {
        Random rnd = new Random();
        // Logic for random zombie spawning based on the current level
        LevelData lvl = new LevelData();
        String[] Level = lvl.Level[Integer.parseInt(lvl.Lvl) - 1];
        int[][] LevelValue = lvl.LevelValue[Integer.parseInt(lvl.Lvl) - 1];
        int l = rnd.nextInt(5);
        int t = rnd.nextInt(100);
        Zombie z = null;
        for (int i = 0; i < LevelValue.length; i++) {
            if (t >= LevelValue[i][0] && t <= LevelValue[i][1]) {
                z = Zombie.getZombie(Level[i], GamePanel.this, l);
            }
        }
        laneZombies.get(l).add(z);
    }
    // Handles user planting actions
    class PlantActionListener implements ActionListener {

        int x,y; // Grid coordinates for the plant

        public PlantActionListener(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(activePlantingBrush == GameWindow.PlantType.Sunflower){
                if(getSunScore()>=50) {
                    colliders[x + y * 9].setPlant(new Sunflower(GamePanel.this, x, y));
                    setSunScore(getSunScore()-50);
                }
            }
            if(activePlantingBrush == GameWindow.PlantType.Peashooter){
                if(getSunScore() >= 100) {
                    colliders[x + y * 9].setPlant(new Peashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore()-100);
                }
            }

            if(activePlantingBrush == GameWindow.PlantType.FreezePeashooter){
                if(getSunScore() >= 175) {
                    colliders[x + y * 9].setPlant(new FreezePeashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore()-175);
                }
            }

            if(activePlantingBrush == GameWindow.PlantType.CherryBomb){
                if (getSunScore() >= 150) {
                    colliders[x + y * 9].setPlant(new CherryBomb(GamePanel.this, x, y));
                    setSunScore(getSunScore()-150);
                }
            }

            if(activePlantingBrush == GameWindow.PlantType.Wallnut){
                if (getSunScore() >= 50) {
                    colliders[x + y * 9].setPlant(new Wallnut(GamePanel.this, x, y));
                    setSunScore(getSunScore()-50);
                }
            }
            activePlantingBrush = GameWindow.PlantType.None;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    static int progress = 0;
    public static void setProgress(int num) {
        progress = progress + num;
        System.out.println(progress);
        if(progress>=150) {
           if("1".equals(LevelData.Lvl)) {
            JOptionPane.showMessageDialog(null,"Level Completed !!!" + '\n' + "Starting next Level");
            GameWindow.gw.dispose();
            LevelData.write("2");
            GameWindow.gw = new GameWindow();
            }  else {
               JOptionPane.showMessageDialog(null,"Level Completed !!!" + '\n' + "More Levels will come soon !!!" + '\n' + "Resetting data");
               LevelData.write("1");
               System.exit(0);
           }
           progress = 0;
        }
    }

}
