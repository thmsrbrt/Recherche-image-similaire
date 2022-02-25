package Model;

import fr.unistra.pelican.ByteImage;
import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.io.ImageLoader;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Projet {
    private static TreeMap<Double, String> imageMap = new TreeMap<>();
    private static String racine = "/Users/thomasrobert/Documents_IUT/Semestre4/Image/TD-image/Recherche img similaire/Images/";
    private static BDD base;
    private boolean methodeTypeHisto; //true = RGB ; false = HSV

    public Projet(){

    }

    /**
     * Applique un filtre median à une image
     * @param img image d'entrée
     * @return une image d'ébruité avec le filtre médian
     */
    public static Image filtreMedian(Image img) {
        int largeur = img.getXDim();
        int hauteur = img.getYDim();
        int nbDime = img.getBDim();
        ByteImage resultat = new ByteImage(img);
        ArrayList<Integer> mediane = new ArrayList<Integer>();

        for (int x = 1; x < largeur -1; x++) {
            for (int y = 1; y < hauteur -1; y++) {
                for (int z = 0; z < nbDime; z++) {
                    mediane.add(img.getPixelXYBByte(x, y, z));
                    mediane.add(img.getPixelXYBByte(x +1, y, z));
                    mediane.add(img.getPixelXYBByte(x +1, y +1, z));
                    mediane.add(img.getPixelXYBByte(x - 1, y -1, z));
                    mediane.add(img.getPixelXYBByte(x  -1, y, z));
                    mediane.add(img.getPixelXYBByte(x, y -1, z));
                    mediane.add(img.getPixelXYBByte(x -1, y +1, z));
                    mediane.add(img.getPixelXYBByte(x + 1, y - 1, z));
                    mediane.add(img.getPixelXYBByte(x, y + 1, z));
                    Collections.sort(mediane);
                    int color;
                    if (mediane.size() % 2 == 1) {
                        color = mediane.get(mediane.size()/2);
                    } else {
                        color = (mediane.get(3) + mediane.get(4) + mediane.get(5)) / 3;
                    }
                    resultat.setPixelXYBByte(x, y, z, color);
                    mediane.clear();
                }
            }
        }
        return resultat;
    }

    /**
     * Crée un histogramme RGB à partir d'une image
     * @param image d'entrée
     * @return un tableau de TAILLE [3] [256]
     */
    public static double[][] histogrammeRGB(Image image) {
        int largeur = image.getXDim();
        int hauteur = image.getYDim();
        int nbDime = image.getBDim();

        double[][] histo = new double[nbDime][256];

        for (int j = 0; j < nbDime-1; j++) {
            for (int i = 0; i < histo.length; i++) {
            histo[j][i] = 0;
            }
        }

        for (int z = 0; z < nbDime; z++) {
            for(int x = 0; x < largeur; x++) {
                for (int y = 0; y < hauteur; y++) {
                    histo[z][image.getPixelXYBByte(x, y, z)]++;
                }
            }
        }
        //afficheHisto(histo);
        return histo;
    }

    /**
     * TODO : faire diviser par 2 au lieux de 10
     * @param histo de taille [3][256]
     * @return histograme de taille [255][3]
     */
    public static double[][] discretisationHisto(double[][] histo) {
        int tailleHisto = histo[0].length;
        int nbCanneaux = histo.length;
        double[][] histoDecretisation = new double[nbCanneaux][tailleHisto/10];

        for (int i = 0; i < nbCanneaux; i++)
            for (int j = 0; j < tailleHisto/10; j++)
                histoDecretisation[i][j] = 0;

        int j = 0;
        for (int i = 0; i < tailleHisto; i++) {
            for (int k = 0; k < nbCanneaux; k++) {
                if (!(i == 0) && i % 10 == 0 && k == 0)
                    j++;
                if (i >= 250)
                    break;
                //System.out.println(j + "teste" + " |i :" + i + " |j : " + k);
                histoDecretisation[k][j] += histo[k][i];
            }
        }
        //afficheHisto(histoDecretisation);

        return histoDecretisation;
    }

    /**
     * Normaliser un histogramme
     * @param histo discretisé
     * @param nbPixel nombre pixel de l'image
     * @return un histogramme normalisé
     */
    public static double[][] normalisationHisto(double[][] histo, int nbPixel) {
        for (int i = 0; i < histo.length; i++)
            for (int j = 0; j < histo[i].length; j++)
                histo[i][j] = histo[i][j] / nbPixel * 100;
        //afficheHisto(histo);
        return histo;
    }

    /**
     * Affichage d'un histogramme
     * @param histo quelconque
     */
    public static void afficheHisto(double[][] histo) {
        if (histo.length == 3) {
            System.out.println("Affichage histogramme couleur");
            try {
                HistogramTools.plotHistogram(histo[0], " R");
                HistogramTools.plotHistogram(histo[1], " G");
                HistogramTools.plotHistogram(histo[2], " B");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (histo.length == 1) {
            System.out.println("Affichage histogramme noir et blanc");
            try {
                HistogramTools.plotHistogram(histo[0], "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("taille d'histogramme non pris en charge");
    }

    /**
     * Remplis la map avec les distances entre l'image de référence et les autres images.
     * @param imageRef reference
     */
    public static void Similarite(Image imageRef, String dir) {
        final double[][] normalisationImageRefToHisto = normalisationImageToHisto(imageRef, null);
        final File[] files = new File(racine + dir).listFiles();
        assert files != null;
        for(File item : files) {
            String nomImage = Objects.requireNonNull(item.getName());
            if (!nomImage.contains(".jpg") ) {continue;}
            Image imageCompar = ImageLoader.exec(racine + dir + nomImage);
            imageMap.put(similariteHisto(normalisationImageRefToHisto, normalisationImageToHisto(imageCompar, nomImage)), nomImage);
        }
    }

    /**
     *
     * @param image quelconque en entrée
     * @return un double[][] avec un histonormalisé
     */
    private static double[][] normalisationImageToHisto(Image image, String nomImage) {
        double[][] val = normalisationHisto(discretisationHisto(histogrammeRGB(image)), (image.getXDim() * image.getYDim()));
        insertHistoBDD(nomImage, val);
        return  val;
    }

    /**
     * Insert l'histogramme d'image dans la base de donnée
     * @param name nom de l'image
     * @param histo histogramme de l'image correspondant
     */
    private static void insertHistoBDD(String name, double[][] histo) {
        StringBuilder histoText = new StringBuilder();

        for (double[] doubles : histo) {
            for (double aDouble : doubles) {
                histoText.append(aDouble).append(";");
            }
            histoText.append("\n");
        }

        System.out.println(histoText);
        System.out.println(name);
        base.insertToImageMoto(name, histoText.toString());
    }

    /**
     *
     * @param histoR reference
     * @param histoI à comparer
     * @return distance entre les deux histos
     */
    private static double similariteHisto(double[][] histoR, double[][] histoI) {
        int nbCanal = histoI.length;
        int nbBarre = histoI[0].length;
        double distance = 0;

        for (int i = 0; i < nbCanal; i++) {
            for (int j = 0; j < nbBarre; j++) {
                distance += Math.pow(histoR[i][j] - histoI[i][j], 2);
            }
            //System.out.println("distance entre chaque canal " + distance);
        }
        return Math.sqrt(distance);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        base = new BDD();
        base.dropImageMotoTable();
        base.creatImageMotoTable();
        Image imageEiffel = ImageLoader.exec(racine + "img/eiffel.jpg");
        //d'ébruiter l'image
        imageEiffel = filtreMedian(imageEiffel);
        //Viewer2D.exec(imageEiffel);

        //double[][] histoRGB = histogrammeImage(imageEiffel);
        //double[][] histRBGDiscretisation = discretisationHisto(histoRGB);
        //double[][] histoNormalise = normalisationHisto(histRBGDiscretisation, (imageEiffel.getXDim()* imageEiffel.getYDim()));

        //test comparaison 2 images
        Image motoref = ImageLoader.exec(racine + "motos/001.jpg");
        //double[][]  histoRef = normalisationImageToHisto(motoref);
        //System.out.println("Teste 1 : "+similariteHisto(histoRef, normalisationImageToHisto(motore1)));
        Similarite(motoref, "motos/");
        //System.out.println(imageMap);
        Image broad = ImageLoader.exec(racine + "broad/0001.png");



        /*
        imageMap.clear();
        System.out.println(imageMap);
        Similarite(broad, "broad/");
        System.out.println(imageMap);

         */

    }
    /*
    private static void insertBDD() {
        base.dropImageMotoTable();
        base.creatImageMotoTable();
        Set<Double> keys = imageMap.keySet();
        for(Double key: keys){
            //base.insertTable(imageMap.get(key), key);
        }
    }
     */

/*
    private static ArrayList[][] getImgRessemblance() throws SQLException, ClassNotFoundException {
        return base.resemblance(10);
    }

 */



    public static String getRacine() {
        return racine;
    }

}
