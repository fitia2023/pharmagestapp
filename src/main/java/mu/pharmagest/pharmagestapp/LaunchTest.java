package mu.pharmagest.pharmagestapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mu.pharmagest.pharmagestapp.Controller.MainController;
import mu.pharmagest.pharmagestapp.Modele.Utilisateur;
import mu.pharmagest.pharmagestapp.util.SourceFxml;
import mu.pharmagest.pharmagestapp.util.SourceImage;

import java.io.IOException;
import java.util.Date;

/**
 * Pour tester les affichages
 */
public class LaunchTest extends Application {

//    //    Fenêtre main test dashboard global
    @Override
    public void start(Stage firststage) {

        try {
            // Charger le fichier FXML principal
            FXMLLoader fxmlLoader = SourceFxml.getsrcmain();
            Parent parent = fxmlLoader.load();
            MainController mainController = fxmlLoader.getController();

            mainController.setUtilisateur(
                    new Utilisateur(
                            1,
                            "Doe",
                            "John",
                            new Date(), // Date actuelle
                            "123 Rue de la Rue",
                            123456789,
                            "john.doe",
                            "password",
                            Utilisateur.Role.pharmacien, // Supposons que Role est une classe avec un constructeur prenant un rôle en paramètre
                            true,
                            false
                    )
            );
            Scene scene = new Scene(parent,1100,750);

            // Rendre la fenêtre transparente
            firststage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);

            //Logo de application
            String logo = "logo.png";
            firststage.getIcons().add(SourceImage.getsrcImage(logo));

            // Définir la scène principale et afficher la fenêtre
            firststage.setScene(scene);
            firststage.setTitle("test");
            firststage.show();
        } catch (IOException e) {
            // Gérer les erreurs de chargement du fichier FXML
            e.printStackTrace();
            displayErrorAlert("Erreur de chargement de l'application");
        } catch (Exception e) {
            // Gérer toute autre exception
            e.printStackTrace();
            displayErrorAlert("Une erreur inattendue s'est produite");
        }
    }

    //    Fenêtre test affichage
//    @Override
//    public void start(Stage firststage) {
//
//        try {
//            // Charger le fichier FXML principal
//            FXMLLoader fxmlLoader = SourceFxml.getsrcFxml("Dashboard");
//            Parent parent = fxmlLoader.load();
//            Scene scene = new Scene(parent);
//
//            // Rendre la fenêtre transparente
//            firststage.initStyle(StageStyle.TRANSPARENT);
//            scene.setFill(Color.TRANSPARENT);
//
//            //Logo de application
//            String logo = "logo.png";
//            firststage.getIcons().add(SourceImage.getsrcImage(logo));
//
//            // Définir la scène principale et afficher la fenêtre
//            firststage.setScene(scene);
//            firststage.setTitle("test");
//            firststage.show();
//        } catch (IOException e) {
//            // Gérer les erreurs de chargement du fichier FXML
//            e.printStackTrace();
//            displayErrorAlert("Erreur de chargement de l'application");
//        } catch (Exception e) {
//            // Gérer toute autre exception
//            e.printStackTrace();
//            displayErrorAlert("Une erreur inattendue s'est produite");
//        }
//    }


    public static void main(String[] args) {
        launch(args);
    }

    // Afficher une fenêtre d'alerte en cas d'erreur
    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
