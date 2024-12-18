package code.gui;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.Font;
import javax.swing.Timer;
import javax.swing.SwingConstants;

import code.plant.*;
import code.zombie.*;

/**
 * @author Vu Viet Phong
 */
public class Background extends JLayeredPane implements MouseListener, MouseMotionListener {
    private final int BG_WIDTH = 1300;
    private final int BG_HEIGHT = 769;

    private Image bgImg;

    private Image lmImg;
    private Lawnmower lm;
    private int[] rowlm = {150, 270, 390, 510, 630}; 

    // private Timer timer;
    private Timer sunProducer;
    private Timer redrawTimer;
    private Timer advancerTimer;
    private Timer zombieProducer;

    private boolean ingame;
    
    private int[] rows;
    private int[] columns;

    private Collider[][] plants;
    private ArrayList<Zombie> zombies;
    
    /* Sun */
    private int sunScore;
    private JLabel sunScoreboard;

    public int getSunScore() {
        return sunScore;
    }

    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        sunScoreboard.setText(String.valueOf(sunScore));
        sunScoreboard.setFont(new Font("Open Sans", 1, 30));
        sunScoreboard.setHorizontalTextPosition(SwingConstants.CENTER);
    }

    private ArrayList<Sun> activeSuns;

    public ArrayList<Sun> getActiveSuns() {
        return activeSuns;
    }

    public void setActiveSuns(ArrayList<Sun> activeSuns) {
        this.activeSuns = activeSuns;
    }

    public Background(JLabel sunScoreboard) {
        this.sunScoreboard = sunScoreboard;
        setSunScore(150);
        setSize(BG_WIDTH, BG_HEIGHT);
        setLayout(null);
        setRowsCoordinates();
        setColumnsCoordinates();

        ingame = true;        
        bgImg = new ImageIcon(this.getClass().getResource("/images/background.jpg")).getImage();
        lmImg = new ImageIcon(this.getClass().getResource("/images/items/Lawn_Mower.png")).getImage();

        addMouseListener(this);

        int plantRow = returnGridRowPosition(xMouse);
        int plantCol = returnGridColumnPosition(yMouse);

        int x = (plantRow - 90) / 120;
        int y = (plantCol - 350) / 100;

        plants = new Collider[5][9];
        for (int i = 0; i < 5; i++) {  
            for (int j = 0; j < 9; j++) {
                
            }
        }

        activeSuns = new ArrayList<>();
        redrawTimer = new Timer(25, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        advancerTimer = new Timer(60, (ActionEvent e) -> advance());
        advancerTimer.start();

        zombieProducer = new Timer(7000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateZombies();
            }
        });
        zombieProducer.start();

        initZombies(5);
    }

    private void advance() {
        updateZombies();

        for (int i = 0; i < activeSuns.size(); i++) {
            activeSuns.get(i).advance();
        }
    }

    public void initZombies(int n) {
        Random rd = new Random();

        zombies = new ArrayList<>();
        int[] listZombies = new int[n];
        
        for (int i = 0; i < n; i++) {
            int num = rd.nextInt(5);
            listZombies[i] = rows[num];
        }
        
        for (int i = 0; i < n; i++) {
            int type = rd.nextInt(3) + 1;

            switch (type) {
                case 1:
                    zombies.add(new NormalZombie(listZombies[i]));
                    break;
                case 2:
                    zombies.add(new ConeheadZombie(listZombies[i]));
                    break;
                case 3:
                    zombies.add(new FootballZombie(listZombies[i]));
                    break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {
            doDrawing(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        g.drawImage(bgImg, 0, 0, this.getWidth(), this.getHeight(), this);

        // Draw lawnmover
        for (int i = 0; i < 5; i++) {
            g.drawImage(lmImg, 240, rowlm[i], 100, 80, this);
        }
        
        //Draw plants
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Collider c = plants[i][j];
                if (c.assignedPlant != null) {
                    Plant plant = c.assignedPlant;
                    if (plant instanceof SunFlower) {
                        g.drawImage(plant.getImage(), plant.getX(), plant.getY(), 75, 75, this);
                    } else if (plant instanceof PeaShooter) {
                        g.drawImage(plant.getImage(), plant.getX(), plant.getY(), 75, 75, this);
                    } else if (plant instanceof Torchwood) {
                        g.drawImage(plant.getImage(), plant.getX(), plant.getY(), 100, 100, this);
                    } else {
                        g.drawImage(plant.getImage(), plant.getX(), plant.getY(), 75, 75, this);
                    }
                }
            }
        }

        //Draw zombies
        for (Zombie zombie : zombies) {
            if (zombie.isVisible()) {
                g.drawImage(zombie.getImage(), zombie.getX(), zombie.getY(), null);
            }
        }
    }

    // Possible Row Cordinates
    private void setRowsCoordinates() {
        rows = new int[5];
        rows[0] = 90;
        rows[1] = 210;
        rows[2] = 330;
        rows[3] = 450;
        rows[4] = 570;
    }

    // Possible Column Cordinates
    private void setColumnsCoordinates() {
        columns = new int[9];
        columns[0] = 350;
        columns[1] = 450;
        columns[2] = 550;
        columns[3] = 650;
        columns[4] = 750;
        columns[5] = 850;
        columns[6] = 950;
        columns[7] = 1050;
        columns[8] = 1150;
    }

    // Ensures that the user can't randomly place plants in the world but only in the grid.
    public int returnGridRowPosition(int y) {
        int row;
        int[] rowGrid = {30, 150, 270, 390, 510, 630};

        for (row = 0; row < 5; row++) {
            if (y > rowGrid[row] && y < rowGrid[row + 1]) {
                return rows[row];
            }
        }

        return -1;
    }

    // Ensures that the user can't randomly place plants in the world but only in the grid.
    public int returnGridColumnPosition(int x) {
        int column;
        int[] columnGrid = {300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200};

        for (column = 0; column < 9; column++) {
            if(x > columnGrid[column] && x < columnGrid[column + 1]) {
                return columns[column];
            }
        }

        return -1;
    }

    private void inGame() {
        if (!ingame) {
        //    timer.stop();
        }
    }

    /*
    private void updateBullets() {
        List<Bullet> bs = plant.getBullets();
        for (int i = 0; i < bs.size(); i++) {
            Bullet b = bs.get(i);
            if (b.isVisible()) {
                b.move();
            } else {
                bs.remove(i);
            }
        }
    }
    */

    private void updateZombies() {
        if (zombies.isEmpty()) {
            ingame = false;
            return;
        }
        
        for (int i = 0; i < zombies.size(); i++) {
            Zombie z = zombies.get(i);

            if (z.isVisible()) {
                z.move();
            } // else {
              //  zombies.remove(i);
            // }
        }
    }

    public void checkCollisions() {
        /*
        List<Bullet> bs = plant.getBullets();
        for (Bullet b : bs) {
            Rectangle r1 = b.getBounds();
            for (Zombie zombie : zombies) {
                Rectangle r2 = zombie.getBounds();
                if (r1.intersects(r2)) {
                    b.setVisible(false);
                    if (zombie.getHealth() == 0) {
                        zombie.setVisible(false);
                    } else {
                        int hp = zombie.getHealth() - b.getDamage();
                        Zombie.setHealth(hp);
                    }
                }
            }
        }
        Rectangle r3 = plant.getBounds();
        for (Zombie zombie : zombies) {
            Rectangle r2 = zombie.getBounds();
            if (r3.intersects(r2)) {
                if (plant.getHealth() == 0) {
                    plant.setVisible(false);
                } else {
                    int hp = plant.getHealth() - zombie.getDamage();
                    plant.setHealth(hp);
                }
            }
        }
        */

        for (int i = 0; i < 5; i++) {
            Rectangle r4 = lm.getBounds();
            for (Zombie zombie : zombies) {
                Rectangle r2 = zombie.getBounds();
                if (r4.intersects(r2)) {
                    Lawnmower lms = new Lawnmower(330, rowlm[i]);

                    lm.move();
                    if (lm.getX() == (BG_WIDTH - 1)) {
                        lm.setVisible(false);
                    }
                    zombie.setVisible(false);
                }
            }
        }
    }

    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int xMouse = 0;
    private volatile int yMouse = 0;

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        screenX = e.getXOnScreen();
        screenY = e.getYOnScreen();

        xMouse = getX();
        yMouse = getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int deltaX = e.getXOnScreen() - screenX;
        int deltaY = e.getYOnScreen() - screenY;

        setLocation(xMouse + deltaX, yMouse + deltaY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    private Play.PlantType active = Play.PlantType.None;

    public Play.PlantType getActivePlantingBrush() {
        return active;
    }

    public void setActivePlantingBrush(Play.PlantType active) {
        this.active = active;
    }
}
    
    