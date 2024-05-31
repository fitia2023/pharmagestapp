package mu.pharmagest.pharmagestapp.Controller.Dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mu.pharmagest.pharmagestapp.Controller.MainController;
import mu.pharmagest.pharmagestapp.Controller.UtilisateurAcces;
import mu.pharmagest.pharmagestapp.Modele.Utilisateur;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Pour afficher ce qui est contenu de dashboard
 */
public class DashboardController implements Initializable {
    @FXML
    public Pane btn_vente;
    @FXML
    public Pane btn_caisse;
    @FXML
    public Pane btn_appro;
    @FXML
    public Pane btn_maintenance;
    @FXML
    public HBox hb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    //Initialize app
    public void initApp(Utilisateur.Role role, MainController mainControleur){
        btn_vente.setManaged(UtilisateurAcces.getAutorisations(role).contains("vente"));
        btn_caisse.setManaged(UtilisateurAcces.getAutorisations(role).contains("caisse"));
        btn_appro.setManaged(UtilisateurAcces.getAutorisations(role).contains("appro"));
        btn_maintenance.setManaged(UtilisateurAcces.getAutorisations(role).contains("maintenance"));

        btn_vente.setVisible(UtilisateurAcces.getAutorisations(role).contains("vente"));
        btn_caisse.setVisible(UtilisateurAcces.getAutorisations(role).contains("caisse"));
        btn_appro.setVisible(UtilisateurAcces.getAutorisations(role).contains("appro"));
        btn_maintenance.setVisible(UtilisateurAcces.getAutorisations(role).contains("maintenance"));

        btn_vente.setOnMouseClicked(event -> mainControleur.affvente());
        btn_caisse.setOnMouseClicked(event -> mainControleur.affcaisse());
        btn_appro.setOnMouseClicked(event -> mainControleur.affCommande());
    }
}
