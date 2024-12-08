import java.awt.*;
import javax.swing.*;

public class Menu extends JPanel {

    Image bgImage;

    public Menu() {
        initComponents(); // Initialize components of the menu
        setSize(1012, 785); // Set the size of the panel
        // Load the background image for the menu
        bgImage = new ImageIcon(this.getClass().getResource("images/menu.jpg")).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null); // Draw the background image on the panel
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Create a JPanel (jPanel1) for additional UI elements or actions
        jPanel1 = new javax.swing.JPanel();

        // Set the preferred size for the main menu panel
        setPreferredSize(new java.awt.Dimension(1012, 785));

        // Make jPanel1 transparent
        jPanel1.setOpaque(false);

        // Add a mouse listener to jPanel1 to handle mouse click events
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt); // Handle the mouse click event
            }
        });

        // Define the layout for jPanel1
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE) // Set horizontal size
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE) // Set vertical size
        );

        // Define the layout for the main menu panel
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(523, Short.MAX_VALUE) // Set spacing before jPanel1
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE) // Add jPanel1 to the layout
                .addGap(102, 102, 102)) // Set spacing after jPanel1
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(122, 122, 122) // Set spacing above jPanel1
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE) // Add jPanel1 to the layout
                .addContainerGap(547, Short.MAX_VALUE)) // Set spacing below jPanel1
        );
    }

    // Handle mouse click event on jPanel1
    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {
        GameWindow.begin(); // Call the begin method in GameWindow to start the game
    }

    // Declare jPanel1 for additional UI interactions
    private javax.swing.JPanel jPanel1;
}
