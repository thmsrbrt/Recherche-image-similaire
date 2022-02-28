package Test;

import Model.BDD;
import Model.ResearchPicture;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        ResearchPicture researchPicture = new ResearchPicture();
        BDD base = new BDD();
        //base.dropImageTable();
        //base.creatImageTable();
        //researchPicture.init("motos/");
        //base.getImageByName("002.jpg");
        researchPicture.similarite("002.jpg");
        researchPicture.setMethodeTypeHisto(false);
        researchPicture.similarite("002.jpg");
        //
        //[ 008.jpg,  003.jpg,  064.jpg,  007.jpg,  016.jpg,  005.jpg,  019.jpg,  006.jpg,  267.jpg,  260.jpg]
        System.out.println(Arrays.toString(researchPicture.getNbImageMap(10)));
    }
}
