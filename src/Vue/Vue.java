package Vue;

import Controller.ControlMenu;
import Model.Projet;

import javax.swing.*;
import java.awt.*;

public class Vue extends JFrame {
    //Model
    private Projet projet;
    //Controleur
    ControlMenu controlMenu;
    private final int size = 1000;

    private JMenuItem jMenuExit;

    private JPanel jPanelOutputImage;
    private JPanel jPanelImageRef;
    private JPanel jPanelOption;
    private JPanel jPanelLog;
    private JPanel jPanelInput;

    private JFrame jFrameOuputImage;
    private ImageIcon imageIconRef;
    private JTextArea jTextAreaLog;


    public Vue() {
        this.setResizable(true);
        this.setTitle("Recherche d'image similaire");
        this.affichage();

        this.setVisible(true);
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
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
        JLabel jLabelNomImage = new JLabel("Nom image : ");
        JTextField jTextFieldNameImage = new JTextField("test.jpg");
        jTextFieldNameImage.setPreferredSize(new Dimension(350, 30));
        JButton jButtonValideRecherche = new JButton("Valider");
        jPanelName.add(jLabelNomImage);
        jPanelName.add(jTextFieldNameImage);
        jPanelName.add(jButtonValideRecherche);

        JPanel jPanelType = new JPanel();
        ButtonGroup groupeRadioButton = new ButtonGroup();

        JLabel jLabelNomRadio = new JLabel("Type de histogramme : ");
        jPanelType.add(jLabelNomRadio);

        JRadioButton jRadioButtonRGB = new JRadioButton("RGB", true);
        groupeRadioButton.add(jRadioButtonRGB);
        jPanelType.add(jRadioButtonRGB);

        JRadioButton jRadioButtonHSV = new JRadioButton("HSV");
        groupeRadioButton.add(jRadioButtonHSV);
        jPanelType.add(jRadioButtonHSV);

        JPanel jPanelNumber = new JPanel();
        JLabel jLabelNbImageOut = new JLabel("Nombre d'image similaire souhaité : ");
        JTextField jTextFieldNumberImage = new JTextField("10");
        jPanelNumber.add(jLabelNbImageOut);
        jPanelNumber.add(jTextFieldNumberImage);

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
        String nom = "/Users/thomasrobert/Documents_IUT/Semestre4/Image/TD-image/Recherche_img_similaire/Images/img/eiffel.jpg";
        AfficheImage image = new AfficheImage(nom);
        Dimension dimension = new Dimension(this.size/2, this.size/2);
        image.setPreferredSize(dimension);
        String label = "Nom de l'image : ";
        JLabel jLabelNomImageRef = new JLabel(label + "nom");
        jPanelImageRef.add(jLabelNomImageRef);
        jPanelImageRef.add(image);
    }

    private void makeJpanelResult() {
        jPanelOutputImage = new JPanel();
        jPanelOutputImage.setSize(this.size, this.size/2);
        jPanelOutputImage.setLayout(new BoxLayout(jPanelOutputImage, BoxLayout.X_AXIS));

        JScrollPane scrollLog = new JScrollPane();
        scrollLog.setPreferredSize(new Dimension(this.size,this.size/2));

        ImageIcon imageIcon = null;
        jFrameOuputImage = new JFrame();

        for (int i = 0; i < 12; i++) {
            //Image image = Toolkit.getDefaultToolkit().getImage(""); //modele.getRacine() + model.getNom(i)
            //imageIcon.setImage(image);
        }

        jPanelOutputImage.add(scrollLog);
    }



}
