package Model;

import fr.unistra.pelican.Image;
import fr.unistra.pelican.IntegerImage;
import fr.unistra.pelican.algorithms.io.ImageLoader;
import fr.unistra.pelican.algorithms.segmentation.labels.DrawFrontiersOnImage;
import fr.unistra.pelican.algorithms.segmentation.labels.FrontiersFromSegmentation;
import fr.unistra.pelican.algorithms.segmentation.labels.LabelsToRandomColors;
import fr.unistra.pelican.algorithms.visualisation.Viewer2D;

public class SegmentationTools {


    /**
     * Seuillage naif d'une image en niveaux de gris
     * @param img en niveaux de gris
     * @param threshold entier
     * @return result
     */
    public static Image thresholding(Image img, int threshold){

        //Connaitre la hauteur et la largeur d'une image
        int largeur = img.getXDim();
        int hauteur = img.getYDim();

        //DÃ©clarer une nouvelle image à 1 canal pour stocker resultat
        IntegerImage result = new IntegerImage(largeur, hauteur, 1, 1, 1);

        //Parcourir l'ensemble des pixels d'une image et seuiller la valeur courante
        for(int x=0; x<img.getXDim();x++){
            for(int y=0; y<img.getYDim();y++){
                if(img.getPixelXYBByte(x, y, 0)<=threshold)
                    result.setPixelXYBInt(x, y, 0, 0);
                else
                    result.setPixelXYBInt(x, y, 0, 255);
            }
        }
        return result;

    }

    public static void main(String[] args) {

        //Charger une image en memoire
        Image test= ImageLoader.exec("/home/eee/Bureau/foie.jpg");

        Image segmentation=thresholding(test,128);

        Image segmentationLabels = LabelsToRandomColors.exec(segmentation);
        Image frontiers = DrawFrontiersOnImage.exec(test, FrontiersFromSegmentation.exec(segmentation));

        //Afficher une image de segmentation
        Viewer2D.exec(segmentationLabels);
        Viewer2D.exec(frontiers);
    }

}