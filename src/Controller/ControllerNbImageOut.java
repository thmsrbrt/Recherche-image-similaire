package Controller;

import Model.ResearchPicture;
import Vue.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

public class ControllerNbImageOut implements ActionListener {
    private ResearchPicture model;
    private Vue vue;

    /**
     * Constructeur du ControllerNbImageOut
     * @param model model de l'appli
     * @param vue vue de l'appli
     */
    public ControllerNbImageOut(ResearchPicture model, Vue vue) {
        this.model = model;
        this.vue = vue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.setOutPut(model.getOutPut() + Instant.now() + ": Nombre d'image demandé : " + vue.getjTextFieldNumberImage().getText() + " \n");
        if (e.getSource() == vue.getjButtonValideNbImageOut()) {
            model.clearImageMap();
            model.setNbImageOut(Integer.parseInt(vue.getjTextFieldNumberImage().getText()));
            model.similarite(vue.getjTextFieldNameImage().getText());
        }
        vue.setAfficheImageTab(model.getNbImageMap(model.getNbImageOut()));
        vue.setjTextAreaLog(model.getOutPut());
    }
}
