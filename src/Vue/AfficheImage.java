package Vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Composant qui affiche une image
 */
public class AfficheImage extends Panel {

    private static final long serialVersionUID = 1L;
    private BufferedImage image;

    public AfficheImage(String nomFichier) {

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
