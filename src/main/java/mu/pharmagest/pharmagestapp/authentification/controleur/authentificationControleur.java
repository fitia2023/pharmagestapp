package mu.pharmagest.pharmagestapp.authentification.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import mu.pharmagest.pharmagestapp.authentification.modele.UtilisateurDAO;

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


    //Utilisateurdao pour pouvoir recevoir vers base de donnee
    private UtilisateurDAO utilisateurDAO;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utilisateurDAO = new UtilisateurDAO();
    }


    @FXML
    void se_connecter(ActionEvent event){
        //Verifie si les champs sont bien correcte
        if (utilisateurDAO.sAuthentifier(InputIdentifiant.getText(),InputMdp.getText())){
            System.out.print("Correcte");
        }
    }
}
