package mu.pharmagest.pharmagestapp.utilis;

import javafx.fxml.FXMLLoader;
import mu.pharmagest.pharmagestapp.MainApp;

/**
 * CLasse qui s'en charge de charger le fichier fxml
 */
public class chargerfxml {

    //Pour charger fxml load
    public static FXMLLoader getFxml(String nomfichier){
        //Chemin vers dossier fxml
        String chemin = "fxml/"+nomfichier+".fxml";
        return new FXMLLoader(MainApp.class.getResource(chemin));
    }

}
