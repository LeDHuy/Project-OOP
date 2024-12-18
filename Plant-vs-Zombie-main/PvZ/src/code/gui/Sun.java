package code.gui;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Sun extends JPanel implements MouseListener {
    private int locX;
    private int locY;
    private int endY;
    private Image image;
    private int destruct = 200;
    private GamePanel bg;

    public Sun(int locX, int locY, int endY, GamePanel bg) {
        image = new ImageIcon(this.getClass().getResource("/images/sun.png")).getImage();
        this.locX = locX;
        this.locY = locY;
        this.endY = endY;
        this.bg = bg;
        setSize(80, 80);
        setOpaque(false);
        setLocation(locX, locY);
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void advance() {
        if (locY < endY) {
            locY += 4;
        } else {
            destruct--;
            if (destruct < 0) {
                bg.remove(this);
                bg.getActiveSuns().remove(this);
            }
        }
        setLocation(locX, locY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        bg.setSunScore(bg.getSunScore() + 25);
        bg.remove(this);
        bg.getActiveSuns().remove(this);
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
