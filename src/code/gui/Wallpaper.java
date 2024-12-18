package code.gui;

import java.awt.GraphicsEnvironment; 
import java.awt.GraphicsDevice;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

public class Wallpaper extends JFrame implements ActionListener {    
    static GraphicsDevice device = GraphicsEnvironment
        .getLocalGraphicsEnvironment().getScreenDevices()[0];    
    public Wallpaper() {
        initUI();
    }
                       
    private void initUI() {
        JPanel jPanel1 = new JPanel();
        JLabel logoImg = new JLabel();
        JButton startImg = new JButton();
        JLabel wallpaperImg = new JLabel();
 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());

        jPanel1.setToolTipText("");
        jPanel1.setOpaque(false);

        logoImg.setIcon(new ImageIcon(getClass().getResource("/images/logo.png")));
        logoImg.setHorizontalTextPosition(SwingConstants.CENTER);
        logoImg.setVerticalTextPosition(SwingConstants.TOP);

        startImg.setIcon(new ImageIcon(getClass().getResource("/images/button.png")));
        startImg.setHorizontalTextPosition(SwingConstants.CENTER);
        startImg.addActionListener(this);

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(258, 258, 258)
                        .addComponent(logoImg))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(818, 818, 818)
                        .addComponent(startImg, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)))
                        .addGap(252, 252, 252))
        );

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addComponent(logoImg)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 386, Short.MAX_VALUE)
                .addComponent(startImg, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
        );

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        getContentPane().add(jPanel1, gridBagConstraints);

        wallpaperImg.setIcon(new ImageIcon(getClass().getResource("/images/wallpaper.jpg")));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        getContentPane().add(wallpaperImg, gridBagConstraints);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        Play display = new Play();
        device.setFullScreenWindow(display);
    }
}
