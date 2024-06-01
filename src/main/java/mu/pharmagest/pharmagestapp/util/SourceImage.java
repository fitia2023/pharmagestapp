package mu.pharmagest.pharmagestapp.util;

import javafx.scene.image.Image;
import mu.pharmagest.pharmagestapp.Launch;

/**
 * Pour obtenir les images
 */
public class SourceImage {

    /**
     * Methode pour obtenir image javafx
     * @param nom :fichier .jpeg ou etc
     * @return Image de javafx ou null
     */
    public static Image getsrcImage(String nom){
        try{
            String chemin = "assets/images/"+nom;
            return new Image(Launch.class.getResourceAsStream(chemin));
        }catch (Exception e){
            System.out.println("Erreur de chargement de image");
            e.printStackTrace();
            return null; // Retourne null en cas d'erreur
        }
    }

    public static String getPathImage(String nom){
        return "src/main/resources/mu/pharmagest/pharmagestapp/assets/images/"+nom;
    }
}
