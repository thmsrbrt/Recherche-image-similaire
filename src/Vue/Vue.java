package Vue;

import Controller.ControllerNbImageOut;
import Controller.ControllerSearch;
import Controller.ControllerTypeHisto;
import Model.ResearchPicture;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Vue extends JFrame {
    private final ResearchPicture model;
    private ControllerNbImageOut controllerNbImageOut;
    private ControllerTypeHisto controllerTypeHisto;
    private ControllerSearch controllerSearch;

    private final int size = 1000;
    private final String dossier;

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
    private JLabel jLabelNomImageRef;

    private AfficheImage afficheImageRef;
    private AfficheImageTab afficheImageTab;

    public Vue(ResearchPicture model) {
        this.model = model;
        this.dossier = "motos" + "/";
        //this.model.init(this.dossier); // décommenter une fois la base de donnée enregistrée
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
        jLabelNomImageRef = new JLabel("Nom de l'image : ");

        jTextFieldNameImage = new JTextField("100.jpg");
        jTextFieldNumberImage = new JTextField("10");


        jRadioButtonRGB = new JRadioButton("RGB", true);
        jRadioButtonHSV = new JRadioButton("HSV");

        afficheImageRef = new AfficheImage(model.getRacine() + dossier + getjTextFieldNameImage().getText().replace(" ", ""));
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

        jTextFieldNumberImage.setPreferredSize(new Dimension(50, 30));
        jTextFieldNumberImage.setHorizontalAlignment(JTextField.CENTER);

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
        JScrollPane scrollLog = new JScrollPane(jTextAreaLog);
        scrollLog.setPreferredSize(new Dimension(this.size/2,this.size/4));
        jPanelLog.add(scrollLog);
    }

    public void makeJPanelImageRef() {
        jPanelImageRef = new JPanel();
        jPanelImageRef.setSize(this.size/2, this.size/2);
        jPanelImageRef.setLayout(new BoxLayout(jPanelImageRef, BoxLayout.Y_AXIS));
        String label = "Nom de l'image : ";
        jLabelNomImageRef.setText(label + getjTextFieldNameImage().getText());
        jLabelNomImageRef.setHorizontalAlignment(JLabel.CENTER);
        jPanelImageRef.add(jLabelNomImageRef);
        jPanelImageRef.add(afficheImageRef);
    }

    public void makeJpanelResult() {
        jPanelOutputImage = new JPanel();
        jPanelOutputImage.setSize(this.size, this.size/2);
        jPanelOutputImage.setLayout(new BoxLayout(jPanelOutputImage, BoxLayout.X_AXIS));
        String[] imgs = model.getNbImageMap(model.getNbImageOut());
        if (model.getNbImageOut() > 0) {
            afficheImageTab = new AfficheImageTab(model.getNbImageOut(),(model.getRacine() + dossier), imgs );
        }
        JScrollPane scrollImage = new JScrollPane(afficheImageTab, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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

    public void setjTextAreaLog(String jTextAreaLog) {
        this.jTextAreaLog.setText(jTextAreaLog);
    }

    public void setjLabelNomImageRef(String e) {
        this.jLabelNomImageRef.setText(e + this.getjTextFieldNameImage().getText());
    }

    public void setImage(String s) {
        this.afficheImageRef.changerImage(model.getRacine() + s + this.getjTextFieldNameImage().getText());
    }

    public void setAfficheImageTab(String[] imageTab) {
        System.out.println(Arrays.toString(imageTab));
        System.out.println(model.getNbImageOut());
        this.afficheImageTab.removeAll();
        if (model.getNbImageOut() > 0)
            this.afficheImageTab.changerImageTab(model.getNbImageOut(),(model.getRacine() + dossier), imageTab);
    }
}
