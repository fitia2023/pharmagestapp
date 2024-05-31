package mu.pharmagest.pharmagestapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mu.pharmagest.pharmagestapp.util.SourceFxml;
import mu.pharmagest.pharmagestapp.util.SourceImage;

import java.io.IOException;

/**
 * Classe pour point de lancement de l'application
 */
public class Launch extends Application {



    public static void main(String[] args) {
        launch(args);
    }

//    Fenêtre principale
    @Override
    public void start(Stage firststage){

        try {
            // Charger le fichier FXML principal
            FXMLLoader fxmlLoader = SourceFxml.getsrcFxml("login");
            Scene scene = new Scene(fxmlLoader.load(),600,400);

            // Rendre la fenêtre transparente
            firststage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);

            //Logo de application
            String logo = "logo.png";
            firststage.getIcons().add(SourceImage.getsrcImage(logo));

            // Définir la scène principale et afficher la fenêtre
            firststage.setScene(scene);
            firststage.setTitle("Login");
            firststage.show();
        }catch (IOException e) {
            // Gérer les erreurs de chargement du fichier FXML
            e.printStackTrace();
            displayErrorAlert("Erreur de chargement de l'application");
        } catch (Exception e) {
            // Gérer toute autre exception
            e.printStackTrace();
            displayErrorAlert("Une erreur inattendue s'est produite");
        }
    }

    // Afficher une fenêtre d'alerte en cas d'erreur
    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}