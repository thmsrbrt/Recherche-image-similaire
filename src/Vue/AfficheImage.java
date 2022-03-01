package Vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Composant qui affiche une image
 */
public class AfficheImage extends JPanel {
    private BufferedImage image;

    public AfficheImage(String nomFichier) {
        System.out.println("je lis une image");
        try {
            image = ImageIO.read(new File(nomFichier));
            this.setPreferredSize(new Dimension(image.getWidth(),
                    image.getHeight()));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void changerImage(String nomFichier) {
        System.out.println("je change l'image");
        try {
            image = ImageIO.read(new File(nomFichier));
            this.setPreferredSize(new Dimension(image.getWidth(),
                    image.getHeight()));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
