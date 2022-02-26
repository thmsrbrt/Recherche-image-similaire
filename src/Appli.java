import Controller.ControllerNbImageOut;
import Controller.ControllerSearch;
import Controller.ControllerTypeHisto;
import Model.ResearchPicture;
import Vue.Vue;

public class Appli {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ResearchPicture model = new ResearchPicture();
                Vue vue = new Vue(model);
                ControllerSearch controllerSearch = new ControllerSearch(model, vue);
                ControllerTypeHisto controllerTypeHisto = new ControllerTypeHisto(model, vue);
                ControllerNbImageOut controllerNbImageOut = new ControllerNbImageOut(model, vue);
            }
        });
    }
}
