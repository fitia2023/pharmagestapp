package mu.pharmagest.pharmagestapp.Controller.Admin;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import mu.pharmagest.pharmagestapp.LienBD.DAO.FournisseurDAO;
import mu.pharmagest.pharmagestapp.LienBD.DAO.UtilisateurDAO;
import mu.pharmagest.pharmagestapp.LienBD.Services.ReceptionCommandeService;
import mu.pharmagest.pharmagestapp.Modele.Fournisseur;
import mu.pharmagest.pharmagestapp.Modele.Utilisateur;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class UtilisateursController implements Initializable {
    @FXML
    public TextField I_prenomU;
    @FXML
    public DatePicker I_annif;
    @FXML
    public TextField I_nomU;
    @FXML
    public TextField I_identifiant;
    @FXML
    public TextField I_tel;
    @FXML
    public TextField I_adress;
    @FXML
    public ComboBox<String> I_role;
    @FXML
    public PasswordField I_mdp;
    @FXML
    public Pane AdminUtilisateur;
    @FXML
    public TextField Input_recherche;
    @FXML
    public TableView<Utilisateur> TableUtilisateur;
    @FXML
    public TableColumn<Utilisateur, String> TNom;
    @FXML
    public TableColumn<Utilisateur, String> TPrenom;
    @FXML
    public TableColumn<Utilisateur, Date> TAnnif;
    @FXML
    public TableColumn<Utilisateur, String> Tadress;
    @FXML
    public TableColumn<Utilisateur, Integer> TTel;
    @FXML
    public TableColumn<Utilisateur, String> TIdentifiant;
    @FXML
    public TableColumn<Utilisateur, Utilisateur.Role> TRole;
    @FXML
    public TableColumn<Utilisateur, Boolean> TActif;
    @FXML
    public TableColumn<Utilisateur, Boolean> Tbloquer;
    @FXML
    public Button btn_up;
    @FXML
    public Button btn_bloquer;
    private Utilisateur I_id_utilisateur;

    private Utilisateur utilisateurConnecter;

    public void initApp(Utilisateur utilisateur) {
        try {
            this.utilisateurConnecter = utilisateur;
            charge_role();
            if (utilisateur.getRole().equals(Utilisateur.Role.pharmacien)) {
                AdminUtilisateur.setVisible(true);
                charge_data(FXCollections.observableArrayList(UtilisateurDAO.getallutilisateurs()));
                btn_up.setDisable(true);
            }else {
                detailUtilisateur(utilisateur);
                I_identifiant.setDisable(true);
                I_role.setDisable(true);
                btn_up.setDisable(false);
            }
            btn_bloquer.setDisable(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    //Chargement de data
    private void charge_data(ObservableList<Utilisateur> utilisateurs) throws SQLException {
        TableUtilisateur.setItems(utilisateurs);
        TNom.setCellValueFactory(call_n -> new SimpleStringProperty(call_n.getValue().getNom_utilisateur()));
        TPrenom.setCellValueFactory(call_n -> new SimpleStringProperty(call_n.getValue().getPrenom_utilisateur()));
        TAnnif.setCellValueFactory(call_n -> new SimpleObjectProperty<>(call_n.getValue().getAnnif_utilisateur()));
        Tadress.setCellValueFactory(call_n -> new SimpleStringProperty(call_n.getValue().getAdresse_utilisateur()));
        TTel.setCellValueFactory(call_n -> new SimpleIntegerProperty(call_n.getValue().getTel_utilisateur()).asObject());
        TIdentifiant.setCellValueFactory(call_n -> new SimpleStringProperty(call_n.getValue().getIdentifiant()));
        TRole.setCellValueFactory(call_n -> new SimpleObjectProperty<>(call_n.getValue().getRole()));
        TActif.setCellValueFactory(call_n -> new SimpleBooleanProperty(call_n.getValue().getActif()));
        Tbloquer.setCellValueFactory(call_n -> new SimpleBooleanProperty(call_n.getValue().getBloquer()));
        charge_table_click();
    }

    //Pour charger table click
    private void charge_table_click() {
        TableUtilisateur.setOnMouseClicked(
                event -> {
                    if (TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
                        Utilisateur utilisateur = TableUtilisateur.getSelectionModel().getSelectedItem();
                        detailUtilisateur(utilisateur);
                        this.I_id_utilisateur = utilisateur;
                        if (utilisateur.getBloquer()) {
                            btn_bloquer.setText("Débloquer");
                        } else {
                            btn_bloquer.setText("Bloquer");
                        }
                        I_identifiant.setDisable(true);
                        I_role.setDisable(true);
                    }
                }
        );
    }

    //Pour charger role
    private void charge_role() {
        for (Utilisateur.Role role : Utilisateur.Role.values()) {
            I_role.getItems().add(String.valueOf(role));
        }
    }

    //Affichage fournisseur
    private void detailUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            clearUserDetails();
            return;
        }

        Date dateAnniversaire = utilisateur.getAnnif_utilisateur();
// Convertir java.sql.Date en java.util.Date
        java.util.Date utilDate = new java.util.Date(dateAnniversaire.getTime());

// Convertir la Date en LocalDate
        LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        I_prenomU.setText(utilisateur.getPrenom_utilisateur());
        I_annif.setValue(localDate);
        I_nomU.setText(utilisateur.getNom_utilisateur());
        I_identifiant.setText(utilisateur.getIdentifiant());
        I_tel.setText(utilisateur.getTel_utilisateur().toString());
        I_adress.setText(utilisateur.getAdresse_utilisateur());
        I_role.setValue(utilisateur.getRole().name());
        I_mdp.setText(utilisateur.getMot_de_passe());

        boolean isCurrentUser = utilisateur.getIdentifiant().equals(this.utilisateurConnecter.getIdentifiant());

        if (isCurrentUser){
            btn_up.setDisable(false);

        }else {
            btn_up.setDisable(true);
        }
        setFieldsEnabled(!isCurrentUser);
    }

    private void clearUserDetails() {
        I_prenomU.setText("");
        I_annif.setValue(null);
        I_nomU.setText("");
        I_identifiant.setText("");
        I_tel.setText("");
        I_adress.setText("");
        I_role.getSelectionModel().clearSelection();
        I_mdp.setText("");
        setFieldsEnabled(false);
    }

    private void setFieldsEnabled(boolean enabled) {
        I_prenomU.setDisable(enabled);
        I_annif.setDisable(enabled);
        I_nomU.setDisable(enabled);
        I_identifiant.setDisable(enabled);
        I_tel.setDisable(enabled);
        I_adress.setDisable(enabled);
        I_role.setDisable(enabled);
        I_mdp.setDisable(enabled);
        btn_bloquer.setDisable(!enabled);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    public void btn_up(ActionEvent actionEvent) throws SQLException {

        Utilisateur utilisateur = creerUtilisateur();
        if (utilisateur != null) {
            if (upUtilisateur(utilisateur)) {
                AlertInfo(Alert.AlertType.INFORMATION, "Information bien mis à jours.");
                btn_rafraichir(new ActionEvent());

            } else {
                AlertInfo(Alert.AlertType.INFORMATION, "Problème lors de l'insertion dans la base.");
            }
        }

    }

    @FXML
    public void btn_recherche(ActionEvent actionEvent) throws SQLException {
        charge_data(FXCollections.observableArrayList(UtilisateurDAO.getallutilisateursbyname(Input_recherche.getText())));
    }

    @FXML
    public void btn_rafraichir(ActionEvent actionEvent) throws SQLException {
        Input_recherche.setText("");
        clearUserDetails();
        charge_data(FXCollections.observableArrayList(UtilisateurDAO.getallutilisateurs()));
        setFieldsEnabled(false);
        btn_up.setDisable(true);

    }

    @FXML
    public void btn_ajouter(ActionEvent actionEvent) throws SQLException {
        Utilisateur utilisateur = creerUtilisateur();
        if (utilisateur != null) {
            if (insererUtilisateur(utilisateur)) {
                AlertInfo(Alert.AlertType.INFORMATION, "Information bien insérée dans la base.");
                btn_rafraichir(new ActionEvent());

            } else {
                AlertInfo(Alert.AlertType.INFORMATION, "Problème lors de l'insertion dans la base.");
            }
        }
    }

    private Utilisateur creerUtilisateur() {
        String nom_utilisateur = I_nomU.getText();
        String prenom_utilisateur = I_prenomU.getText();
        String adresse_utilisateur = I_adress.getText();
        String identifiant = I_identifiant.getText();
        String mot_de_passe = I_mdp.getText();
        Utilisateur.Role role = Utilisateur.Role.valueOf(I_role.getValue());
        LocalDate localDate = I_annif.getValue();
        String tel = I_tel.getText();

        if (estChampVide(nom_utilisateur, prenom_utilisateur, adresse_utilisateur, identifiant, mot_de_passe, role, localDate, tel)) {
            AlertInfo(Alert.AlertType.INFORMATION, "Veuillez remplir tous les champs obligatoires.");

            return null;
        }

        java.util.Date annif_utilisateur = java.sql.Date.valueOf(localDate);
        Integer tel_utilisateur = Integer.parseInt(tel);
        return new Utilisateur(this.utilisateurConnecter.getId_utilisateur(), nom_utilisateur, prenom_utilisateur, annif_utilisateur,
                adresse_utilisateur, tel_utilisateur, identifiant,
                mot_de_passe, role, null, null);
    }



    private boolean estChampVide(String nom, String prenom, String adresse, String identifiant, String motDePasse, Utilisateur.Role role, LocalDate dateAnnif, String tel) {
        return nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || identifiant.isEmpty() || motDePasse.isEmpty() || role == null || dateAnnif == null || tel.isEmpty();
    }

    private boolean insererUtilisateur(Utilisateur utilisateur) {
        return UtilisateurDAO.createUtilisateurbypharmacien(utilisateur);
    }

    private boolean upUtilisateur(Utilisateur utilisateur){
        return UtilisateurDAO.upUtilisateur(utilisateur);
    }

    @FXML
    public void btn_bloquer(ActionEvent actionEvent) {
        String bloquer = btn_bloquer.getText();
        if (bloquer.equals("Bloquer")) {
            AlertInfo(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment bloquer l'utilisateur: " + I_id_utilisateur.getIdentifiant() + "?", this::bloquer_utilisateur, ButtonType.NO, ButtonType.YES);

        } else {
            AlertInfo(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment débloquer l'utilisateur: " + I_id_utilisateur.getIdentifiant() + "?", this::debloquer_utilisateur, ButtonType.NO, ButtonType.YES);
        }

    }

    private void bloquer_utilisateur() {
        if (UtilisateurDAO.descativeUtilisateur(this.I_id_utilisateur)) {
            try {
                btn_rafraichir(new ActionEvent());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void debloquer_utilisateur() {
        if (UtilisateurDAO.activeUtilisateur(this.I_id_utilisateur)) {
            try {
                btn_rafraichir(new ActionEvent());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void AlertInfo(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }

    //Pour afficher information etat
    private void AlertInfo(Alert.AlertType alertType, String message, Runnable runnable, ButtonType... buttonType) {
        Alert alert = new Alert(alertType, message, buttonType);
        // Exécuter l'action lorsque le bouton "YES" est pressé
        alert.getButtonTypes().stream().filter(bt -> bt == ButtonType.YES).findFirst().ifPresent(btYes ->
                alert.getDialogPane().lookupButton(btYes).addEventFilter(ActionEvent.ACTION, event -> runnable.run())
        );

        alert.showAndWait();
    }
}
