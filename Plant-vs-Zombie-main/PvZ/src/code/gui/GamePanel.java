package code.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import code.plant.*;
import code.zombie.*;

/**
 * @author Vu Viet Phong
 */
public class GamePanel extends JLayeredPane implements ActionListener {
    private static final int BG_WIDTH = 1300;
    private static final int BG_HEIGHT = 769;
    private final int DELAY = 25;

    private Image bgImg;
    private Image lmImg;
    private int[] rowlm = {150, 270, 390, 510, 630}; 

    private Image sunflowerImg;
    private Image peashooterImg;
    private Image torchwoodImg;
    private Image walnutImg;

    private Plant[][] plants;
    private ArrayList<Zombie> zombies;

    private Timer timer;
    private Timer sunProducer;
    private Timer zombieProducer;
    
    GamePanel(JLabel sunScoreboard) {
        this.sunScoreboard = sunScoreboard;
        setSunScore(150);
        setSize(BG_WIDTH, BG_HEIGHT);
        setLayout(null);
        setFocusable(true);

        bgImg = new ImageIcon(this.getClass().getResource("/images/background.jpg")).getImage();
        lmImg = new ImageIcon(this.getClass().getResource("/images/items/Lawn_Mower.png")).getImage();

        sunflowerImg = new ImageIcon(this.getClass().getResource("images/plants/sun_flower.gif")).getImage();
        peashooterImg = new ImageIcon(this.getClass().getResource("images/plants/pea_shooter.gif")).getImage();
        torchwoodImg = new ImageIcon(this.getClass().getResource("images/plants/torchwood.gif")).getImage();
        walnutImg = new ImageIcon(this.getClass().getResource("images/plants/walnut_full_life.gif")).getImage();

        timer = new Timer(DELAY, this);
        timer.start();

        // SUNS
        activeSuns = new ArrayList<>();
        sunProducer = new Timer(5000, (ActionEvent e) -> {
            Random rd = new Random();
            Sun sta = new Sun(rd.nextInt(BG_WIDTH - 300) + 240, 0, rd.nextInt(BG_HEIGHT - 300) + 100, this);
            activeSuns.add(sta);
            add(sta, new Integer(1));
        });
        sunProducer.start();

        // PLANTS
        plants = new Plant[5][9];
        setRowsCoordinates();
        setColumnsCoordinates();

        ControlMouse click = new ControlMouse();
        addMouseListener(click);
        initPlant();

        // ZOMBIES
        zombieProducer = new Timer(7000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateZombies();
            }
        });
        zombieProducer.start();

        initZombies(5);
    }

    private void initPlant() {
        int plantRow = returnGridRowPosition(xMouse);
        int plantCol = returnGridColumnPosition(yMouse);
        int x = (plantRow - 90) / 120;
        int y = (plantCol - 350) / 100;

        if (selectedButton == 0) {
            plants[y][x] = new SunFlower(plantRow, plantCol);
            setSunScore(getSunScore() - 50);
        }
        
        if (selectedButton == 1) {
            plants[y][x] = new PeaShooter(plantRow, plantCol);
            setSunScore(getSunScore() - 100);
        }

        if (selectedButton == 2) {
            plants[y][x] = new PeaShooter2(plantRow, plantCol);
            setSunScore(getSunScore() - 200);
        }

        if (selectedButton == 3) {
            plants[y][x] = new Torchwood(plantRow, plantCol);
            setSunScore(getSunScore() - 175);
        }

        if (selectedButton == 4) {
            plants[y][x] = new Wallnut(plantRow, plantCol);
            setSunScore(getSunScore() - 50);
        }

        selectedButton = -1;
        planted = false;
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
        
        // Draw background
        g.drawImage(bgImg, 0, 0, this.getWidth(), this.getHeight(), this);

        // Draw lawnmover
        for (int i = 0; i < 5; i++) {
            g.drawImage(lmImg, 240, rowlm[i], 100, 80, this);
        }

        // Draw plants
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (plants[i][j] instanceof SunFlower) {
                    g.drawImage(sunflowerImg, plants[i][j].getX(), plants[i][j].getY(), 75, 75, this);
                } 
                
                if (plants[i][j] instanceof PeaShooter) {
                    g.drawImage(peashooterImg, plants[i][j].getX(), plants[i][j].getY(), 75, 75, this);
                }
                
                if (plants[i][j] instanceof PeaShooter2) {
                    g.drawImage(peashooterImg, plants[i][j].getX(), plants[i][j].getY(), 75, 75, this);
                } 
                
                if (plants[i][j] instanceof Torchwood) {
                    g.drawImage(torchwoodImg, plants[i][j].getX(), plants[i][j].getY(), 100, 100, this);
                } 

                if (plants[i][j] instanceof Wallnut) {
                    g.drawImage(walnutImg, plants[i][j].getX(), plants[i][j].getY(), 75, 75, this);
                }
            }
        }

        // Draw zombies
        for (Zombie zombie : zombies) {
            if (zombie.isVisible()) {
                g.drawImage(zombie.getImage(), zombie.getX(), zombie.getY(), null);
            }
        }
    }

    /* Make a sun scoreboard */
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

    private List<Sun> activeSuns;

    public List<Sun> getActiveSuns() {
        return activeSuns;
    }

    public void setActiveSuns(List<Sun> activeSuns) {
        this.activeSuns = activeSuns;
    }

    /* PLANTS */
    private int[] rows;
    private int[] columns;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        updateSuns();
        updateZombies();
        repaint();
    }

    private void updateSuns() {
        for (int i = 0; i < activeSuns.size(); i++) {
            activeSuns.get(i).advance();
        }
    }

    private void updateZombies() {
        for (int i = 0; i < zombies.size(); i++) {
            Zombie z = zombies.get(i);

            if (z.isVisible()) {
                z.move();
            } // else {
              //  zombies.remove(i);
            // }
        }
    }

    private Play.PlantType active = Play.PlantType.None;

    public Play.PlantType getActivePlantingBrush() {
        return active;
    }

    public void setActivePlantingBrush(Play.PlantType active) {
        this.active = active;
    }

    private int xMouse;
    private int yMouse;
    private int selectedButton = -1;
    private Boolean planted = false;

    private class ControlMouse implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            xMouse = e.getX();
            yMouse = e.getY();

            // Card position
            if (xMouse < 155 && xMouse >= 0) {
                if (yMouse <= 200 && yMouse >= 100 && sunScore >= 50) {
                    selectedButton = 0;
                }
                if (yMouse <= 300 && yMouse >= 200 && sunScore >= 100) {
                    selectedButton = 1;
                }
                if (yMouse <= 400 && yMouse >= 300 && sunScore >= 200) {
                    selectedButton = 2;
                }
                if (yMouse <= 500 && yMouse >= 400 && sunScore >= 175) {
                    selectedButton = 3;
                }
                if (yMouse <= 600 && yMouse >= 500 && sunScore >= 50) {
                    selectedButton = 4;
                }
            }

            // Plantable location
            if (xMouse <= 1200 && xMouse >= 300 && yMouse <= 630 && yMouse > 30 && selectedButton != -1) {
                planted = true;
            }
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
    }
}
