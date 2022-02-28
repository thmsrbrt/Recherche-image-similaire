package Model;

import fr.unistra.pelican.ByteImage;
import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.io.ImageLoader;


import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;


public class ResearchPicture {
    private TreeMap<Double, String> imageMap;
    private final String racine;
    private final BDD base;
    private int nbImageOut;
    private boolean methodeTypeHisto; //true = RGB ; false = HSV
    private String outPut;

    /**
     * Constructeur ResearchPicture default
     */
    public ResearchPicture() {
        this.racine = "/Users/thomasrobert/Documents_IUT/Semestre4/Image/TD-image/Recherche_img_similaire/Images/";
        this.base = new BDD();
        this.imageMap = new TreeMap<>();
        this.methodeTypeHisto = true;
        this.nbImageOut = 10;
        outPut = "";
    }

    /**
     * Constructeur ResearchPicture by methode
     */
    public ResearchPicture(boolean methodeTypeHisto) {
        this.racine = "/Users/thomasrobert/Documents_IUT/Semestre4/Image/TD-image/Recherche_img_similaire/Images/";
        this.base = new BDD();
        this.imageMap = new TreeMap<>();
        this.methodeTypeHisto = methodeTypeHisto;
        this.nbImageOut = 10;
        outPut = "";
    }

    /**
     * Applique un filtre median à une image
     * @param img image d'entrée
     * @return une image d'ébruité avec le filtre médian
     */
    private Image filtreMedian(Image img) {
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
        outPut += Instant.now() + ": filtre Median Appliqué \n";
        return resultat;
    }

    /**
     * Crée un histogramme RGB à partir d'une image
     * @param image d'entrée
     * @return un tableau de TAILLE [3] [256]
     */
    private double[][] histogrammeRGB(Image image) {
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
        outPut += Instant.now() + ": histogramme RGB créé \n";
        //afficheHisto(histo);
        return histo;
    }

    /**
     * Créer un histogramme à partir d'une image
     * @param image d'entrée
     * @return un tab de TAILLE [3] [256]
     */
    private double[][] histogrammeHSV(Image image) {
        double[][] histo = histogrammeRGB(image);
        for (int i = 0; i < histo.length; i++) {
            double M = Math.max(histo[0][i],Math.max(histo[1][i],histo[2][i]));
            double m = Math.min(histo[0][i],Math.min(histo[1][i],histo[2][i]));
            double V = M/255;
            double S = 0;
            double H = 0;
            if (M > 0)
                S = 1 - m/M;
            else if (M == 0)
                S = 0;

            if (histo[1][i] >= histo[2][i])
                H =  Math.cos((histo[0][i]- 1/2 * histo[1][i] - 1/2 * histo[2][i]) / Math.sqrt(Math.pow(histo[0][i],2)+ Math.pow(histo[1][i], 2) + Math.pow(histo[2][i], 2) - histo[0][i] * histo[1][i] - histo[0][i] * histo[2][i] - histo[1][i] * histo[2][i]));
            else if (histo[1][i] > histo[2][i])
                H = 360 - Math.cos((histo[0][i]- 1/2 * histo[1][i] - 1/2 * histo[2][i]) / Math.sqrt(Math.pow(histo[0][i],2)+ Math.pow(histo[1][i], 2) + Math.pow(histo[2][i], 2) - histo[0][i] * histo[1][i] - histo[0][i] * histo[2][i] - histo[1][i] * histo[2][i]));

            histo[0][i] = H;
            histo[1][i] = S;
            histo[2][i] = V;
        }
        outPut += Instant.now() + ": histogramme HSV créé \n";
        return histo;
    }

        /**
         *
         * @param histo de taille [3][256]
         * @return histogramme de taille [32][3]
         */
    private double[][] discretisationHisto(double[][] histo) {
        outPut += Instant.now() + ": discrétisation effectué \n";
        return discretisationHistoDiv2(discretisationHistoDiv2(discretisationHistoDiv2(histo)));
    }

    /**
     *
     * @param histo de taille [3][X]
     * @return histo de taille [3][X/2]
     */
    private double[][] discretisationHistoDiv2(double[][] histo) {
        int tailleHisto = histo[0].length;
        int nbCanneaux = histo.length;
        double[][] histoDecretisation = new double[nbCanneaux][tailleHisto/2];

        for (int i = 0; i < nbCanneaux; i++)
            for (int j = 0; j < tailleHisto/2; j++)
                histoDecretisation[i][j] = 0;

        int j = 0;
        for (int k = 0; k < nbCanneaux; k++) {
            for (int i = 0; i < tailleHisto-1; i++) {
                histoDecretisation[k][j] += histo[k][i];
                i++;
                histoDecretisation[k][j] += histo[k][i];
                j++;
            }
            j = 0;
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
    private double[][] normalisationHisto(double[][] histo, int nbPixel) {
        for (int i = 0; i < histo.length; i++)
            for (int j = 0; j < histo[i].length; j++)
                histo[i][j] = histo[i][j] / nbPixel * 100;
        outPut += Instant.now() + ": Normalisation histogramme \n";

        return histo;
    }

    /**
     * Insert l'histogramme d'image dans la base de donnée
     * @param name nom de l'image
     * @param histoRGB histogramme de l'image correspondant
     */
    private void insertHistoBDD(String name, double[][] histoRGB, double[][] histoHSV) {
        StringBuilder histoTextRGB = new StringBuilder();
        StringBuilder histoTextHSV = new StringBuilder();

        for (double[] doubles : histoRGB) {
            for (double aDouble : doubles) {
                histoTextRGB.append(aDouble).append(";");
            }
            histoTextRGB.append("\n");
        }
        for (double[] doubles : histoHSV) {
            for (double aDouble : doubles) {
                histoTextHSV.append(aDouble).append(";");
            }
            histoTextHSV.append("\n");
        }
        outPut += Instant.now() + ": image sauvegardé dans la base de donnée \n";
        base.insertToImage(name, histoTextRGB.toString(), histoTextHSV.toString());
    }

    /**
     * Traitement d'image et enregistrement en base de donnée
     * @param dir Le dossier avec les images
     */
    public void init(String dir) {
        String nomImage = "";
        final File[] files = new File(racine + dir).listFiles();
        assert files != null;

        for(File item : files) {
            nomImage = Objects.requireNonNull(item.getName());
            if (!nomImage.contains(".jpg") ) {continue;}
            Image image = ImageLoader.exec(racine + dir + nomImage);
            double[][] valRGB = normalisationHisto(discretisationHisto(histogrammeRGB(filtreMedian(image))), (image.getXDim() * image.getYDim()));
            double[][] valHSV = normalisationHisto(discretisationHisto(histogrammeHSV(filtreMedian(image))), (image.getXDim() * image.getYDim()));

            insertHistoBDD(nomImage, valRGB, valHSV);
        }
        outPut += Instant.now() + ": Traitement d'initialisation terminé \n";
    }

    /**
     * Remplis la map avec les distances entre l'image de référence et les autres images.
     */
    public void similarite(String nomRef) {
        final double[][] histoRef = histoStringToDouble(base.getImageByName(nomRef, methodeTypeHisto));
        if (histoRef == null) {
            outPut += Instant.now() + ": IMAGE INTROUVABLE !\n";
            return;
        }
        String[] allHistosImage = base.getAllImage(methodeTypeHisto);
        String[] allHistosImageName = allHistosImage[0].split(";");
        String[] allHistosImageVal = allHistosImage[1].split("histo");

        for (int i = 0; i < allHistosImage[0].split(";").length; i++) {
            imageMap.put(distanceEntre2Histo(histoRef, histoStringToDouble(allHistosImageVal[i])), allHistosImageName[i]);
        }
        outPut += Instant.now() + ": similarité entre les images sauvegardé \n";
    }

    /**
     * Prend un histogramme en format text et le retourne en tableau de double
     * @param histoString histogramme en String
     * @return histogramme en double[][]
     */
    private double[][] histoStringToDouble(String histoString){
        double[][] histoDouble;
        if (histoString !=  null){
            String[] histos = histoString.split("\n");
            int nbHisto = histos.length;
            int nbBarre = histos[0].split(";").length;
            histoDouble = new double[nbHisto][nbBarre];
            for (int i = 0; i < nbHisto; i++) {
                String[] histo = histos[i].split(";");
                for (int j = 0; j < nbBarre; j++) {
                    //System.out.println(histo[j]);
                    histoDouble[i][j] = Double.parseDouble(histo[j]);
                }
            }
            return histoDouble;
        }
        return null;
    }

    /**
     *
     * @param histoR reference
     * @param histoI à comparer
     * @return distance entre les deux histogrammes
     */
    private double distanceEntre2Histo (double[][] histoR, double[][] histoI) {
        int nbCanal = histoI.length;
        int nbBarre = histoI[0].length;
        double distance = 0;

        for (int i = 0; i < nbCanal; i++) {
            for (int j = 0; j < nbBarre; j++) {
                distance += Math.pow(histoR[i][j] - histoI[i][j], 2);
            }
        }
        return Math.sqrt(distance);
    }

    public String[] getNbImageMap(int nbElement) {
        if (imageMap.isEmpty())
            return new String[0];
        if (nbElement > imageMap.size()) {
            System.err.println("Le nombre d'image similaire demandé est supérieur à la base");
            nbElement = imageMap.size();
        }
        String[] listNomImage = Arrays.toString(imageMap.values().toArray()).replace("[", "").replace("]", "").split(",");
        String[] imagesName = new String[nbElement];
        System.arraycopy(listNomImage, 1, imagesName, 0, nbElement);

        outPut += Instant.now() + ": Image similaire affiché : " + nbElement + " \n";
        outPut += Arrays.toString(imagesName) + "\n";
        return imagesName;
    }

    /**
     * Affichage d'un histogramme
     * @param histo quelconque
     */
    public void afficheHisto(double[][] histo) {
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

    public String getRacine() {
        return this.racine;
    }

    public void setMethodeTypeHisto(boolean methodeTypeHisto) {
        this.methodeTypeHisto = methodeTypeHisto;
    }

    public int getNbImageOut() {
        return nbImageOut;
    }

    public void setNbImageOut(int nbImageOut) {
        this.nbImageOut = nbImageOut;
    }

    public String getOutPut() {
        return outPut;
    }

    public void setOutPut(String outPut) {
        this.outPut =  outPut;
    }

    public void clearImageMap() {
        this.imageMap = new TreeMap<>();
    }
}