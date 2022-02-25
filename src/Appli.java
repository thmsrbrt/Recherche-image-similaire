import Model.Projet;
import Vue.Vue;

public class Appli {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Projet modele = new Projet();
                Vue vue = new Vue();
                //ControlButton controlButton = new ControlButton(modele, vue);
            }
        });
    }
}
