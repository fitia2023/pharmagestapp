package mu.pharmagest.pharmagestapp.Controller.Authentification;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import mu.pharmagest.pharmagestapp.Controller.MainController;
import mu.pharmagest.pharmagestapp.LienBD.DAO.UtilisateurDAO;
import mu.pharmagest.pharmagestapp.util.SourceFxml;
import mu.pharmagest.pharmagestapp.util.SourceImage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Responsable de la connexion log
 */
public class LoginController implements Initializable {
    @FXML
    public TextField InputIdentifiant;
    @FXML
    public PasswordField InputMdp;
    @FXML
    public ProgressBar progression;
    @FXML
    public VBox champ_connexion;
    @FXML
    public VBox champ_inscription;
    @FXML
    public Label btn_con;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    @FXML
    void se_connecter(ActionEvent event){
        //Verifie si les champs sont bien correcte
        if (!champvide()){
            //verification que c'est correct
            if (UtilisateurDAO.sAuthentifier(InputIdentifiant.getText(),InputMdp.getText())){
                UtilisateurDAO.enregistrerConnexion(InputIdentifiant.getText(),true);
                //System.out.print("Correcte");
                champ_connexion.setVisible(false);
                //animation progressbar
                progressAndChange();

            }else {
                UtilisateurDAO.enregistrerConnexion(InputIdentifiant.getText(),false);

                showErrorMessage("Identifiant ou mot de passe incorrecte");
                cleanInput();
            }
        }else {
            showErrorMessage("Veuillez remplir tous les champs");
            cleanInput();
        }

    }

    // Test si les champs input sont vides
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
        Stage stage = (Stage) champ_connexion.getScene().getWindow();
        progression.setVisible(true);
        progression.setProgress(0);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(progression.progressProperty(), 1))
        );
        timeline.play();
        //une fois termine
        timeline.setOnFinished(
                event->{
                    stage.close();
                    loadaccueil();
                }
        );
    }

//    Pour afficher le principal accueil
    private void loadaccueil(){
        Stage accueil = new Stage();
        try {
            // Charger le fichier FXML principal
            FXMLLoader fxmlLoader = SourceFxml.getsrcmain();
            Parent parent =fxmlLoader.load();
            MainController mainController = fxmlLoader.getController();
            mainController.setUtilisateur(UtilisateurDAO.getUtilisateurConnecter(InputIdentifiant.getText(),InputMdp.getText()));
            Scene scene = new Scene(parent,1100,750);

            // Rendre la fenêtre transparente
            accueil.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);

            //Logo de application
            String logo = "logo.png";
            accueil.getIcons().add(SourceImage.getsrcImage(logo));

            // Définir la scène principale et afficher la fenêtre
            accueil.setResizable(false);
            accueil.centerOnScreen();
            accueil.setScene(scene);
            accueil.setTitle("pharmagest");
           Platform.runLater(accueil::show);

        }catch (IOException e) {
            // Gérer les erreurs de chargement du fichier FXML
            e.printStackTrace();
            showErrorMessage("Erreur de chargement de l'application");
        } catch (Exception e) {
            // Gérer toute autre exception
            e.printStackTrace();
            showErrorMessage("Une erreur inattendue s'est produite");
        }

    }

    @FXML
    public void inscription(MouseEvent event) {
        champ_connexion.setVisible(false);
        champ_inscription.setVisible(true);
        btn_con.setVisible(true);
    }
    @FXML
    public void con(MouseEvent event) {
        champ_inscription.setVisible(false);
        btn_con.setVisible(false);
        champ_connexion.setVisible(true);

    }

    //Pour alert erreur :
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }

}
