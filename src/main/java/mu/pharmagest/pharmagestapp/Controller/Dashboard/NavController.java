package mu.pharmagest.pharmagestapp.Controller.Dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mu.pharmagest.pharmagestapp.Controller.MainController;
import mu.pharmagest.pharmagestapp.Controller.UtilisateurAcces;
import mu.pharmagest.pharmagestapp.Modele.Utilisateur;
import org.controlsfx.control.PopOver;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Pour nabar vertical
 */
public class NavController implements Initializable {
    @FXML
    public Button btnhome ;
    @FXML
    public Button btnvente ;
    @FXML
    public Button btncaisse ;
    @FXML
    public Button btnmedic ;
    @FXML
    public Button btnfourni ;
    @FXML
    public Button btnappro ;
    @FXML
    public Button btnlivraison ;
    @FXML
    public JFXButton btnutilisateur;

    private MainController controleur;

    private Utilisateur.Role role;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Créer une instance distincte de PopOver pour chaque bouton
        PopOver popOverHome = getPopOver("Dashboard");
        PopOver popOverVente = getPopOver("Vente");
        PopOver popOverCaisse = getPopOver("Caisse");
        PopOver popOverMedic = getPopOver("Medicament");
        PopOver popOverFourni = getPopOver("Fournisseur");
        PopOver popOverAppro = getPopOver("Approvisionnement");
        PopOver popOverLivraison = getPopOver("Inventaire");

        // Afficher les popovers respectifs lors du survol des boutons correspondants
        btnhome.setOnMouseEntered(event -> popOverHome.show(btnhome));
        btnvente.setOnMouseEntered(event -> popOverVente.show(btnvente));
        btncaisse.setOnMouseEntered(event -> popOverCaisse.show(btncaisse));
        btnmedic.setOnMouseEntered(event -> popOverMedic.show(btnmedic));
        btnfourni.setOnMouseEntered(event -> popOverFourni.show(btnfourni));
        btnappro.setOnMouseEntered(event -> popOverAppro.show(btnappro));
        btnlivraison.setOnMouseEntered(event -> popOverLivraison.show(btnlivraison));

        // Fermer les popovers respectifs lorsqu'ils perdent le focus
        btnhome.setOnMouseExited(event -> popOverHome.hide());
        btnvente.setOnMouseExited(event -> popOverVente.hide());
        btncaisse.setOnMouseExited(event -> popOverCaisse.hide());
        btnmedic.setOnMouseExited(event -> popOverMedic.hide());
        btnfourni.setOnMouseExited(event -> popOverFourni.hide());
        btnappro.setOnMouseExited(event -> popOverAppro.hide());
        btnlivraison.setOnMouseExited(event -> popOverLivraison.hide());
    }
    //init pour acces btn
    public void getUser(Utilisateur.Role role, MainController mainControleur){
        this.role = role;
        this.controleur = mainControleur;
        btnvente.setManaged(UtilisateurAcces.getAutorisations(role).contains("vente"));
        btncaisse.setManaged(UtilisateurAcces.getAutorisations(role).contains("caisse"));
        btnmedic.setManaged(UtilisateurAcces.getAutorisations(role).contains("medicament"));
        btnfourni.setManaged(UtilisateurAcces.getAutorisations(role).contains("fournisseur"));
        btnappro.setManaged(UtilisateurAcces.getAutorisations(role).contains("approvisionnement"));
        btnlivraison.setManaged(UtilisateurAcces.getAutorisations(role).contains("inventaire"));

        btnvente.setVisible(UtilisateurAcces.getAutorisations(role).contains("vente"));
        btncaisse.setVisible(UtilisateurAcces.getAutorisations(role).contains("caisse"));
        btnmedic.setVisible(UtilisateurAcces.getAutorisations(role).contains("medicament"));
        btnfourni.setVisible(UtilisateurAcces.getAutorisations(role).contains("fournisseur"));
        btnappro.setVisible(UtilisateurAcces.getAutorisations(role).contains("approvisionnement"));
        btnlivraison.setVisible(UtilisateurAcces.getAutorisations(role).contains("inventaire"));
    }

    // Pour affichage popover avec nom sélectionné
    private PopOver getPopOver(String nom) {
        Label label = new Label(nom);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #01ACBF; -fx-background-color: #FEFEFE;");

        // Créer et configurer le PopOver de ControlsFX
        PopOver popOver = new PopOver(label);

        // Configurer le PopOver
        popOver.setDetachable(false);
        popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);

        return popOver;
    }

    @FXML
    public void home(ActionEvent actionEvent) {
        this.controleur.affdashboard();
    }
    @FXML
    public void Vente(ActionEvent actionEvent) {
        this.controleur.affvente();
    }
    @FXML
    public void Caisse(ActionEvent actionEvent) {
        this.controleur.affcaisse();
    }

    public void Medic(ActionEvent actionEvent)  {
        this.controleur.affmedicament();
    }
    @FXML

    public void Fourni(ActionEvent actionEvent) {
        this.controleur.affFournisseur();

    }
    @FXML

    public void Appro(ActionEvent actionEvent) {
        this.controleur.affCommande();

    }
    @FXML

    public void Livraison(ActionEvent actionEvent) {
        this.controleur.affreceptionCommande();

    }
    @FXML

    public void Utilisateur(ActionEvent event) {
    }
    @FXML
    public void Logout(ActionEvent event) {
        this.controleur.deconnecter();
    }
}