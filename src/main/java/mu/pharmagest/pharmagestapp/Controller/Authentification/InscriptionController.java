package mu.pharmagest.pharmagestapp.Controller.Authentification;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mu.pharmagest.pharmagestapp.LienBD.DAO.UtilisateurDAO;
import mu.pharmagest.pharmagestapp.Modele.Utilisateur;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Cette controller permet de l'inscription definitif d'utilisateur.
 */
public class InscriptionController implements Initializable {
    @FXML
    public TextField InputIdentifiant;
    @FXML
    public PasswordField InputMdp;
    @FXML
    public PasswordField InputNMdp;
    @FXML
    public PasswordField InputNMdp1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void s_inscrir(ActionEvent event) {

        if (!champvide()) {
            //verification que c'est correct
            if (UtilisateurDAO.inscription(InputIdentifiant.getText(), InputMdp.getText())) {
                // Verifie si les deux mots de passe correcte
                if (InputNMdp.getText().equals(InputNMdp1.getText())) {
                    Utilisateur utilisateur = UtilisateurDAO.getUtilisateurConnecter(InputIdentifiant.getText(), InputMdp.getText());
                    utilisateur.setMot_de_passe(InputNMdp.getText());
                    if (UtilisateurDAO.createUtilisateur(utilisateur)) {
                        showMessage(Alert.AlertType.INFORMATION, "Le compte " + utilisateur.getIdentifiant() + " est maintenant actif.\nVous pouvez vous connecter vers la connexion.");
                        cleanInput();
                    } else {
                        showMessage(Alert.AlertType.ERROR, "Une erreur lors de la création,réessaie ou revient plutard");
                    }
                } else {
                    showMessage(Alert.AlertType.WARNING, "Les deux mots de passe sont pas les mêmes");
                }

            } else {
                showMessage(Alert.AlertType.WARNING, "Le compte n'existe pas ou n'a pas été créer par pharmacien");
            }
        } else {
            showMessage(Alert.AlertType.ERROR, "Veuillez remplir tous les champs");
        }

    }

    //Efface contenu de input
    private void cleanInput(){
        InputIdentifiant.clear();
        InputMdp.clear();
        InputNMdp.clear();
        InputNMdp1.clear();
    }


    //Pour alert erreur :
    private void showMessage(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.show();
    }

    // Test si les champs input sont vides
    private Boolean champvide() {
        return InputIdentifiant.getText().isBlank() || InputMdp.getText().isBlank() || InputNMdp.getText().isBlank() || InputNMdp1.getText().isBlank();
    }
}
