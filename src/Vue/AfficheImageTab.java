package Vue;

import javax.swing.*;

public class AfficheImageTab extends JPanel {

    public AfficheImageTab(int nbImages, String racine, String[] nomImage) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JPanel[] images = new JPanel[nbImages];
        try {
            for(int i = 0; i < images.length; i++) {
                images[i] = new JPanel();
                images[i].add(new AfficheImage(racine + nomImage[i].replace(" ", "")));
                add(images[i]);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void changerImageTab(int nbImages, String racine, String[] nomImage) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JPanel[] images = new JPanel[nbImages];
        try {
            for(int i = 0; i < images.length; i++) {
                images[i] = new JPanel();
                images[i].add(new AfficheImage(racine + nomImage[i].replace(" ", "")));
                add(images[i]);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }


}
