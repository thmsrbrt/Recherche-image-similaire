package Controller;

import Model.ResearchPicture;
import Vue.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
        System.out.println("bouton radio");
        if (e.getSource() == vue.getjRadioButtonRGB()) {
            model.setMethodeTypeHisto(true);
        } else if (e.getSource() == vue.getjRadioButtonHSV()) {
            model.setMethodeTypeHisto(false);
        }
        System.out.println(Arrays.toString(model.getNbImageMap(model.getNbImageOut())));    }
}

//[ 005.jpg,  016.jpg,  008.jpg,  015.jpg,  019.jpg,  003.jpg,  023.jpg,  017.jpg,  012.jpg,  059.jpg]