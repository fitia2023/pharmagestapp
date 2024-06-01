package mu.pharmagest.pharmagestapp.Controller.Dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mu.pharmagest.pharmagestapp.Controller.MainController;
import mu.pharmagest.pharmagestapp.Controller.UtilisateurAcces;
import mu.pharmagest.pharmagestapp.LienBD.DAO.MedicamentDAO;
import mu.pharmagest.pharmagestapp.LienBD.Services.CaisseService;
import mu.pharmagest.pharmagestapp.Modele.Utilisateur;

import java.net.URL;
import java.sql.SQLException;
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
    @FXML
    public Text profil_user;
    @FXML
    public Text users_details;
    @FXML
    public StackedBarChart admin_acces;
    @FXML
    public Label txt_vente;
    @FXML
    public Label txt_caisse;

    private MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    //Initialize app
    public void initApp(Utilisateur utilisateur, MainController mainControleur) {
        btn_vente.setManaged(UtilisateurAcces.getAutorisations(utilisateur.getRole()).contains("vente"));
        btn_caisse.setManaged(UtilisateurAcces.getAutorisations(utilisateur.getRole()).contains("caisse"));
        btn_appro.setManaged(UtilisateurAcces.getAutorisations(utilisateur.getRole()).contains("appro"));
        btn_maintenance.setManaged(UtilisateurAcces.getAutorisations(utilisateur.getRole()).contains("maintenance"));

        btn_vente.setVisible(UtilisateurAcces.getAutorisations(utilisateur.getRole()).contains("vente"));
        btn_caisse.setVisible(UtilisateurAcces.getAutorisations(utilisateur.getRole()).contains("caisse"));
        btn_appro.setVisible(UtilisateurAcces.getAutorisations(utilisateur.getRole()).contains("appro"));
        btn_maintenance.setVisible(UtilisateurAcces.getAutorisations(utilisateur.getRole()).contains("maintenance"));

        btn_vente.setOnMouseClicked(event -> mainControleur.affvente());
        btn_caisse.setOnMouseClicked(event -> mainControleur.affcaisse());
        if (!utilisateur.getRole().equals("pharmacien")){
            admin_acces.setVisible(false);
        }
        this.mainController  = mainControleur;
        profil_user.setText(utilisateur.getRole().toString());
        users_details.setText(utilisateur.getNom_utilisateur() + " "+
                utilisateur.getPrenom_utilisateur());
        try {

            txt_vente.setText("Nb dispo:" + MedicamentDAO.getNbMedicament());
            txt_caisse.setText("Nb non-payer:"+ CaisseService.getallventenonpayer().size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void btn_reception(ActionEvent actionEvent) {
        mainController.affreceptionCommande();
    }
    @FXML
    public void btn_commande(ActionEvent actionEvent) {
        this.mainController.affCommande();
    }
    @FXML
    public void btn_medicament(ActionEvent actionEvent) {
        this.mainController.affmedicament();
    }

    public void btn_fournisseur(ActionEvent actionEvent) {
        this.mainController.affFournisseur();
    }
}
