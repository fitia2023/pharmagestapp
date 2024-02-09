package mu.pharmagest.pharmagestapp.authentification.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import mu.pharmagest.pharmagestapp.authentification.modele.UtilisateurDAO;

import mu.pharmagest.pharmagestapp.utilis.chargerfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controleur pour l'interface de connexion au lancement de application
 */
public class authentificationControleur implements Initializable {

    @FXML
    public TextField InputIdentifiant;
    @FXML
    public PasswordField InputMdp;
    @FXML
    public ProgressBar progression;
    @FXML
    public VBox champ;


    //Utilisateurdao pour pouvoir recevoir vers base de donnee
    private UtilisateurDAO utilisateurDAO;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utilisateurDAO = new UtilisateurDAO();
    }


    @FXML
    void se_connecter(ActionEvent event){
        //Verifie si les champs sont bien correcte
        if (!champvide()){
            //verification que c'est correcte
            if (utilisateurDAO.sAuthentifier(InputIdentifiant.getText(),InputMdp.getText())){
//                System.out.print("Correcte");

                champ.setVisible(false);
                //animation progressbar
                progressAndChange();
            }else {
                showErrorMessage("Identifiant ou mot de passe incorrecte");
                cleanInput();
            }
        }else {
            showErrorMessage("Veuillez remplir tous les champs");
            cleanInput();
        }

    }

    // Test si les champs input sont vide
    private Boolean champvide() {
        return InputIdentifiant.getText().isBlank() || InputMdp.getText().isBlank();
    }

    //Efface contenu de input
    private void cleanInput(){
        InputIdentifiant.clear();
        InputMdp.clear();
    }
    //Demarrage du progessbar
    private void progressAndChange(){
        progression.setVisible(true);
        progression.setProgress(0);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(progression.progressProperty(), 1))
        );
        timeline.play();
        //une fois termine
        timeline.setOnFinished(event -> changestage());
    }
    //Pour change de fenetre vers dashboard
    private void changestage(){
        Platform.runLater(()->{
            Stage stage =(Stage) champ.getScene().getWindow();
            try {
                Scene scene = new Scene(chargerfxml.getFxml("dashboard").load());
                stage.setScene(scene);
                //Centrage de ecan
                // Obtenir les dimensions de l'écran
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();

                // Calculer les coordonnées pour centrer le stage
                double centerX = bounds.getMinX() + bounds.getWidth() / 2;
                double centerY = bounds.getMinY() + bounds.getHeight() / 2;

                // Définir les propriétés du stage
                stage.setScene(scene);
                stage.setX(centerX - (stage.getWidth() / 2));
                stage.setY(centerY - (stage.getHeight() / 2));

                // Afficher le stage
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //Pour alert erreur:
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }
}
