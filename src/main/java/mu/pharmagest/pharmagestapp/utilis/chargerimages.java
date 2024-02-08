package mu.pharmagest.pharmagestapp.utilis;

import javafx.scene.image.Image;
import mu.pharmagest.pharmagestapp.MainApp;

/**
 * Classe pour obtenir image
 */
public class chargerimages {

    /**
     * Chargement de image
     * @param nomimage :Doit etres avec son extension .png ou jpeg etc
     * @return Image de javafx
     */
    public static Image getimage(String nomimage){
        //chemin vers image
        String chemin = "assets/images/"+nomimage;
        return new Image(MainApp.class.getResourceAsStream(chemin));
    }

}
