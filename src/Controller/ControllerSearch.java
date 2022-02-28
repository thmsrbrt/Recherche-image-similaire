package Controller;

import Model.ResearchPicture;
import Vue.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

public class ControllerSearch implements ActionListener {
    private ResearchPicture model;
    private Vue vue;

    /**
     * Constructeur du ControllerSearch
     * @param model model de l'appli
     * @param vue vue de l'appli
     */
    public ControllerSearch(ResearchPicture model, Vue vue) {
        this.model = model;
        this.vue = vue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.setOutPut(model.getOutPut() + Instant.now() + ": Recherche en cours \n");
        if (e.getSource() == vue.getjButtonValideRecherche()) {
            model.clearImageMap();
            model.similarite(vue.getjTextFieldNameImage().getText());
        }
        vue.setjLabelNomImageRef("Nom de l'image : ");
        vue.setImage("motos/");
        vue.setAfficheImageTab(model.getNbImageMap(model.getNbImageOut()));
        vue.setjTextAreaLog(model.getOutPut());
    }
}

