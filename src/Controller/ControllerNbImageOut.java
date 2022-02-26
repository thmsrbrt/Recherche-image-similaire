package Controller;

import Model.ResearchPicture;
import Vue.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
        System.out.println("action nb image out");
        if (e.getSource() == vue.getjButtonValideNbImageOut()) {
            model.setNbImageOut(Integer.parseInt(vue.getjTextFieldNumberImage().getText()));
        }
        System.out.println(Arrays.toString(model.getNbImageMap(model.getNbImageOut())));

    }
}
