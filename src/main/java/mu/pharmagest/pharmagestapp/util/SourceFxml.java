package mu.pharmagest.pharmagestapp.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import mu.pharmagest.pharmagestapp.Launch;

import java.io.IOException;

/**
 * Pour obtenir les views fxml
 */
public class SourceFxml {
    public static FXMLLoader getsrcmain(){
        return new FXMLLoader(Launch.class.getResource("Main.fxml"));
    }

    /**
     * Obtention de fxml
     * @param nom:fichier fxml a charger
     * @return FXMLLoader pour fxml
     */
    public static FXMLLoader getsrcFxml(String nom){
        //Chemin vers dossier fxml
        String chemin = "Views/"+nom+".fxml";
        return new FXMLLoader(Launch.class.getResource(chemin));
    }

    /**
     * Obtention de parent
     * @param nom:fichier fxml a charger
     * @return parent pour fxml
     */
    public static Parent getParentFxml(String nom){

        //Chemin vers dossier fxml
        String chemin = "Views/"+nom+".fxml";
        FXMLLoader loader = new FXMLLoader(Launch.class.getResource(chemin));
        try {
            Parent parent = loader.load();
            return parent;
        }catch (IOException e) {
            // Gérer les erreurs de chargement du fichier FXML
            e.printStackTrace();
            displayErrorAlert("Erreur de chargement parent de l'application");
            return null;
        } catch (Exception e) {
            // Gérer toute autre exception
            e.printStackTrace();
            displayErrorAlert("Une erreur inattendue parent s'est produite");
            return null;
        }

    }

    // Afficher une fenêtre d'alerte en cas d'erreur
    private static void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.showAndWait();
    }


}
