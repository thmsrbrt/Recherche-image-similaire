package Controller;

import Model.ResearchPicture;
import Vue.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

public class ControllerTypeHisto implements ActionListener {
    private ResearchPicture model;
    private Vue vue;

    /**
     * Constructeur du ControllerTypeHisto
     * @param model model de l'appli
     * @param vue vue de l'appli
     */
    public ControllerTypeHisto(ResearchPicture model, Vue vue) {
        this.model = model;
        this.vue = vue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.setOutPut(model.getOutPut() + Instant.now() + ": Changement de méthode de recherche \n");
        if (e.getSource() == vue.getjRadioButtonRGB()) {
            model.clearImageMap();
            model.setMethodeTypeHisto(true);
            model.similarite(vue.getjTextFieldNameImage().getText());
            vue.makeJpanelResult();
        } else if (e.getSource() == vue.getjRadioButtonHSV()) {
            model.clearImageMap();
            model.setMethodeTypeHisto(false);
            model.similarite(vue.getjTextFieldNameImage().getText());
        }
        vue.setAfficheImageTab(model.getNbImageMap(model.getNbImageOut()));
        vue.setjTextAreaLog(model.getOutPut());
    }
}

//[ 005.jpg,  016.jpg,  008.jpg,  015.jpg,  019.jpg,  003.jpg,  023.jpg,  017.jpg,  012.jpg,  059.jpg]