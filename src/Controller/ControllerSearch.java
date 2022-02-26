package Controller;

import Model.ResearchPicture;
import Vue.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
        System.out.println("recherche");
        if (e.getSource() == vue.getjButtonValideRecherche()) {
            model.similarite(vue.getjTextFieldNameImage().getText());
            model.getNbImageMap(model.getNbImageOut());
        }
        System.out.println(Arrays.toString(model.getNbImageMap(model.getNbImageOut())));
        vue.makeJpanelResult();
    }
}
