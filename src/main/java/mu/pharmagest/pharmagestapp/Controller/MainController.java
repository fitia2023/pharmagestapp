package mu.pharmagest.pharmagestapp.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SpotLight;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import mu.pharmagest.pharmagestapp.Controller.Authentification.LoginController;
import mu.pharmagest.pharmagestapp.Controller.Dashboard.DashboardController;
import mu.pharmagest.pharmagestapp.Controller.Dashboard.NavController;
import mu.pharmagest.pharmagestapp.Launch;
import mu.pharmagest.pharmagestapp.Modele.Utilisateur;
import mu.pharmagest.pharmagestapp.util.SourceFxml;
import mu.pharmagest.pharmagestapp.util.SourceImage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller principale pour application
 */
public class MainController implements Initializable {

    @FXML
    public BorderPane MainPane;

    private Utilisateur utilisateur;


    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        if (!getUtilisateur().getNom_utilisateur().isBlank()) {
            affnav();
            affdashboard();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //pour bar de nav
    public void affnav()  {
        try {
            FXMLLoader fxmlLoader = SourceFxml.getsrcFxml("Layout/nav");
            Parent parent = fxmlLoader.load();
            NavController navController = fxmlLoader.getController();
            navController.getUser(getUtilisateur().getRole(), this);
            MainPane.setLeft(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //    affichage d'accueil
    public void affdashboard() {
        try {
            if (getUtilisateur() != null) {
                FXMLLoader fxmlLoader = SourceFxml.getsrcFxml("Dashboard");
                Parent parent = fxmlLoader.load();
                DashboardController controller = fxmlLoader.getController();
                controller.initApp(getUtilisateur(),this);
                MainPane.setCenter(parent);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    // Page vente
    public void affvente(){
        MainPane.setCenter(SourceFxml.getParentFxml("Vente"));
    }
    public void affcaisse(){
        MainPane.setCenter(SourceFxml.getParentFxml("Caisse"));
    }
    public void affmedicament(){
        MainPane.setCenter(SourceFxml.getParentFxml("Medicament"));
    }
    public void affFournisseur(){
        MainPane.setCenter(SourceFxml.getParentFxml("Fournisseur"));
    }

    public void affCommande(){
        MainPane.setCenter(SourceFxml.getParentFxml("Commande"));
    }

    public void affreceptionCommande(){
        MainPane.setCenter(SourceFxml.getParentFxml("ReceptionCommande"));
    }

    //deconnecter
    public void deconnecter() {
        Stage stage = (Stage) MainPane.getScene().getWindow();
        stage.close();

        // Relancer l'application
        Launch launch = new Launch();
        Stage login = new Stage();
        try {
            launch.start(login);
        } catch (Exception e) {
            // Gérer les erreurs de relance de l'application
            e.printStackTrace();
        }
    }

}
