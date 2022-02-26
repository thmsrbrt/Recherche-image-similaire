package Vue;

import Controller.ControllerNbImageOut;
import Controller.ControllerSearch;
import Controller.ControllerTypeHisto;
import Model.ResearchPicture;

import javax.swing.*;
import java.awt.*;

public class Vue extends JFrame {
    private final ResearchPicture model;
    private ControllerNbImageOut controllerNbImageOut;
    private ControllerTypeHisto controllerTypeHisto;
    private ControllerSearch controllerSearch;

    private final int size = 1000;

    private JPanel jPanelOutputImage;
    private JPanel jPanelImageRef;
    private JPanel jPanelOption;
    private JPanel jPanelLog;
    private JPanel jPanelInput;

    private JTextArea jTextAreaLog;
    private JTextField jTextFieldNameImage;
    private JTextField jTextFieldNumberImage;

    private JButton jButtonValideNbImageOut;
    private JButton jButtonValideRecherche;

    private JRadioButton jRadioButtonRGB;
    private JRadioButton jRadioButtonHSV;

    private JLabel jLabelNomRadio;
    private JLabel jLabelNbImageOut;
    private JLabel jLabelNomImage;


    public Vue(ResearchPicture model) {
        this.model = model;
        this.setResizable(true);
        this.setTitle("Recherche d'image similaire");
        this.initAttribut();

        this.affichage();
        this.setVisible(true);
        this.setSize(this.size, this.size);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public void initAttribut() {
        controllerSearch = new ControllerSearch(model, this);
        controllerNbImageOut = new ControllerNbImageOut(model, this);
        controllerTypeHisto = new ControllerTypeHisto(model, this);


        jButtonValideRecherche = new JButton("Valider");
        jButtonValideNbImageOut = new JButton("Valider");

        jLabelNomImage = new JLabel("Nom image : ");
        jLabelNomRadio = new JLabel("Type de histogramme : ");
        jLabelNbImageOut = new JLabel("Nombre d'image similaire souhaité : ");

        jTextFieldNameImage = new JTextField("002.jpg");
        jTextFieldNumberImage = new JTextField("10");


        jRadioButtonRGB = new JRadioButton("RGB", true);
        jRadioButtonHSV = new JRadioButton("HSV");



    }

    public void affichage() {
        JPanel jPanelGeneral = new JPanel();
        jPanelGeneral.setLayout(new BoxLayout(jPanelGeneral, BoxLayout.Y_AXIS));

        JPanel jPanelTop = new JPanel();
        jPanelTop.setLayout(new BoxLayout(jPanelTop, BoxLayout.X_AXIS));
        jPanelTop.setSize(this.size, this.size/2);

        JPanel jPanelResult = new JPanel();
        jPanelResult.setLayout(new BoxLayout(jPanelResult, BoxLayout.X_AXIS));
        jPanelResult.setSize(this.size/2, this.size);

        makeJPanelImageRef();
        makeJPanelOption();
        jPanelTop.add(jPanelImageRef);
        jPanelTop.add(jPanelOption);

        makeJpanelResult();
        jPanelResult.add(jPanelOutputImage);

        jPanelGeneral.add(jPanelTop);
        jPanelGeneral.add(new JSeparator());
        jPanelGeneral.add(jPanelResult);
        this.setContentPane(jPanelGeneral);
    }

    private void makeJPanelOption() {
        jPanelOption = new JPanel();
        jPanelOption.setLayout(new BoxLayout(jPanelOption, BoxLayout.Y_AXIS));
        jPanelOption.setSize(this.size/2, this.size/2);

        makeJPanelInput();
        makeJPanelLog();

        jPanelOption.add(jPanelInput);
        jPanelOption.add(new JSeparator());
        jPanelOption.add(jPanelLog);
    }

    private void makeJPanelInput() {
        jPanelInput = new JPanel();
        jPanelInput.setLayout(new BoxLayout(jPanelInput, BoxLayout.Y_AXIS));

        JPanel jPanelName = new JPanel();
        jTextFieldNameImage.setPreferredSize(new Dimension(350, 30));
        jButtonValideRecherche.addActionListener(controllerSearch);

        jPanelName.add(jLabelNomImage);
        jPanelName.add(jTextFieldNameImage);
        jPanelName.add(jButtonValideRecherche);

        JPanel jPanelType = new JPanel();
        jPanelType.add(jLabelNomRadio);

        ButtonGroup groupeRadioButton = new ButtonGroup();

        groupeRadioButton.add(jRadioButtonRGB);
        jPanelType.add(jRadioButtonRGB);
        jRadioButtonRGB.addActionListener(controllerTypeHisto);

        groupeRadioButton.add(jRadioButtonHSV);
        jPanelType.add(jRadioButtonHSV);
        jRadioButtonHSV.addActionListener(controllerTypeHisto);

        JPanel jPanelNumber = new JPanel();
        jButtonValideNbImageOut.addActionListener(controllerNbImageOut);

        jPanelNumber.add(jLabelNbImageOut);
        jPanelNumber.add(jTextFieldNumberImage);
        jPanelNumber.add(jButtonValideNbImageOut);

        jPanelInput.add(jPanelName);
        jPanelInput.add(jPanelType);
        jPanelInput.add(jPanelNumber);
    }

    private void makeJPanelLog() {
        jPanelLog = new JPanel();
        jTextAreaLog = new JTextArea();

        //this.jTextAreaLog = ; TODO : mettre le vrai output
        this.jTextAreaLog.append("eirujzidnk jfsdnck sdcvzfsdfsdfs dfsfd sfsdf\nsd\nffdfsf\nsdfsdf\nsdfsdf\nsfsdf\nsdfsdf\ndf\ndfs");
        JScrollPane scrollLog = new JScrollPane(jTextAreaLog);
        scrollLog.setPreferredSize(new Dimension(this.size/2,this.size/4));

        jPanelLog.add(scrollLog);
    }

    private void makeJPanelImageRef() {
        jPanelImageRef = new JPanel();
        jPanelImageRef.setSize(this.size/2, this.size/2);
        jPanelImageRef.setLayout(new BoxLayout(jPanelImageRef, BoxLayout.Y_AXIS));
        //TODO : modifier la taille de limage
        AfficheImage image = new AfficheImage(model.getRacine() + "img/eiffel.jpg");
        Dimension dimension = new Dimension(this.size/2, this.size/2);
        image.setPreferredSize(dimension);
        //TODO : centrer ce TITRE!!
        String label = "Nom de l'image : ";
        JLabel jLabelNomImageRef = new JLabel(label + "img/eiffel.jpg");
        jPanelImageRef.add(jLabelNomImageRef);
        jPanelImageRef.add(image);
    }

    public void makeJpanelResult() {
        jPanelOutputImage = new JPanel();
        jPanelOutputImage.setSize(this.size, this.size/2);
        jPanelOutputImage.setLayout(new BoxLayout(jPanelOutputImage, BoxLayout.X_AXIS));

        //TODO : afficher le résultat , mettre les images dans un srcoll bordel

        String[] imgs = model.getNbImageMap(model.getNbImageOut());
        System.out.println(imgs.length);
        JPanel a = new JPanel();
        if (imgs.length > 0) {
            for (int i = 0; i < model.getNbImageOut(); i++) {
                a= (new AfficheImage(model.getRacine() + "motos/" + imgs[i].replace(" ", "")));
            }
        }

        JScrollPane scrollImage = new JScrollPane(a);
        scrollImage.setPreferredSize(new Dimension(this.size,this.size/2));

        jPanelOutputImage.add(scrollImage);
    }


    public JTextField getjTextFieldNumberImage() {
        return jTextFieldNumberImage;
    }

    public JButton getjButtonValideNbImageOut() {
        return jButtonValideNbImageOut;
    }

    public JRadioButton getjRadioButtonRGB() {
        return jRadioButtonRGB;
    }

    public JRadioButton getjRadioButtonHSV() {
        return jRadioButtonHSV;
    }

    public JTextField getjTextFieldNameImage() {
        return jTextFieldNameImage;
    }

    public JButton getjButtonValideRecherche() {
        return jButtonValideRecherche;
    }

}
