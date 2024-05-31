package mu.pharmagest.pharmagestapp.util.layout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import mu.pharmagest.pharmagestapp.util.SourceFxml;

import java.io.IOException;

/**
 * Class pour le haut d'application
 */
public class TopApp extends HBox {


    // Afficher une fenêtre d'alerte en cas d'erreur
    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    public TopApp() {

        try {
            // Charger le fichier FXML principal
            FXMLLoader fxmlLoader = SourceFxml.getsrcFxml("Layout/topapp");
            fxmlLoader.setRoot(this);

            fxmlLoader.setController(this);

            fxmlLoader.load();

        }catch (IOException e) {
            // Gérer les erreurs de chargement du fichier FXML
            e.printStackTrace();
            displayErrorAlert("Erreur de chargement ici de l'application");
        } catch (Exception e) {
            // Gérer toute autre exception
            e.printStackTrace();
            displayErrorAlert("Une erreur inattendue ici s'est produite");
        }


    }

    @FXML
    void closebtn(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.close();
    }

    @FXML
    void minimize(MouseEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setIconified(true);
    }


}
