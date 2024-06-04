package mu.pharmagest.pharmagestapp.Controller.Admin;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mu.pharmagest.pharmagestapp.Modele.LigneCommande;
import mu.pharmagest.pharmagestapp.LienBD.Services.ReceptionCommandeService;

import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReceptionCommandeController implements Initializable {
    @FXML
    public TableView<LigneCommande> T_lignecommande;
    @FXML
    public TableColumn<LigneCommande, Double> T_payer;
    @FXML
    public TableColumn<LigneCommande, Date> T_date;
    @FXML
    public TableColumn<LigneCommande, Integer> T_id;
    @FXML
    public TableColumn<LigneCommande, String> T_medic;
    @FXML
    public TableColumn<LigneCommande, String> T_Fournisseur;
    @FXML
    public TableColumn<LigneCommande, Integer> T_qt;

    @FXML
    public TableColumn<LigneCommande, String> T_statut;
    @FXML
    public TextField I_recherche;
    @FXML
    public Label I_NomMedic;
    @FXML
    public Label I_id_commande;
    @FXML
    public TextField qt_recu;
    @FXML
    public Label I_Fournisseur;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            charge_data(FXCollections.observableArrayList(ReceptionCommandeService.getallcommande()));
            detail_livraison(null);
            T_lignecommande.setOnMouseClicked(event -> {
                if (T_lignecommande.getSelectionModel().getSelectedItem() != null) {
                    detail_livraison(T_lignecommande.getSelectionModel().getSelectedItem());
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Pour charger data dans la commande en cours
    private void charge_data(ObservableList<LigneCommande> ligneCommandes) throws SQLException {
        T_lignecommande.setItems(ligneCommandes);
        T_id.setCellValueFactory(call -> new SimpleIntegerProperty(call.getValue().getCommande().getId_commande()).asObject());
        T_date.setCellValueFactory(call -> new SimpleObjectProperty<>(call.getValue().getCommande().getDate_commande()));
        T_medic.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getMedicament().getNom_medicament()));
        T_Fournisseur.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getCommande().getFournisseur().getNom_fournisseur()));
        T_qt.setCellValueFactory(call -> new SimpleIntegerProperty(call.getValue().getQt_medicament()).asObject());
        T_payer.setCellValueFactory(call -> new SimpleDoubleProperty(call.getValue().getCommande().getPrix_total()).asObject());
        T_statut.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getCommande().getStatut()));


    }


    //Pour detail de la livraison
    private void detail_livraison(LigneCommande commande) {

        if (commande != null) {
            if (commande.getCommande().getStatut().equals("En cours")) {
                I_NomMedic.setText(commande.getMedicament().getNom_medicament());
                I_Fournisseur.setText(commande.getCommande().getFournisseur().getNom_fournisseur());
                I_id_commande.setText(commande.getCommande().getId_commande().toString());
                qt_recu.setText(commande.getQt_medicament().toString());
                qt_recu.setDisable(false);
            } else {
                I_NomMedic.setText(commande.getMedicament().getNom_medicament());
                I_Fournisseur.setText(commande.getCommande().getFournisseur().getNom_fournisseur());
                I_id_commande.setText(commande.getCommande().getId_commande().toString());
                qt_recu.setText(commande.getQt_recu().toString());
                qt_recu.setDisable(true);
            }


        } else {

            I_NomMedic.setText("");
            I_Fournisseur.setText("");
            I_id_commande.setText("");
            qt_recu.setText("");
        }

    }

    @FXML
    public void recherche(ActionEvent actionEvent) {
        try {
            Integer id = Integer.parseInt(I_recherche.getText());
            charge_data(FXCollections.observableArrayList(ReceptionCommandeService.getallcommandebyid(id)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btn_commande(ActionEvent actionEvent) {

        try {
            Integer qt_recue = Integer.parseInt(qt_recu.getText());

            LigneCommande ligneCommande = T_lignecommande.getSelectionModel().getSelectedItem();
            Double prix_payer = ReceptionCommandeService.getListPrixbyid(ligneCommande).getPrix_unitaire_achat();
            prix_payer *= qt_recue;
            ligneCommande.setQt_recu(qt_recue);
            ligneCommande.getCommande().setPrix_payer(prix_payer);
            if (ReceptionCommandeService.terminercommande(ligneCommande)) {
                AlertInfo(Alert.AlertType.CONFIRMATION, "Vous devez payer " + prix_payer + "RS, cliquer sur YES pour continuer", ButtonType.YES);
                detail_livraison(null);
            }

        } catch (NumberFormatException e) {
            // Gérez le cas où la conversion échoue (par exemple, si le texte n'est pas un entier valide)
            System.out.println("Erreur de conversion : " + e.getMessage());
            AlertInfo(Alert.AlertType.WARNING, "Veuillez bien remplir tous les champs correctement", ButtonType.OK);

        } catch (SQLException e) {
            e.printStackTrace(); // Log l'exception (vous pouvez utiliser un logger à la place)
            AlertInfo(Alert.AlertType.WARNING, "Erreur lors de l'insertion dans la base de données", ButtonType.OK);
        }


    }

    //Pour afficher information etat
    private void AlertInfo(Alert.AlertType alertType, String message, ButtonType buttonType) {
        Alert alert = new Alert(alertType, message, buttonType);
        // Récupérer le bouton "YES"
        alert.getButtonTypes().stream().filter(buttonType1 -> buttonType1 == ButtonType.YES).findFirst().ifPresent(buttonTypeYes -> alert.getDialogPane().lookupButton(buttonTypeYes).addEventFilter(ActionEvent.ACTION, event -> {
            // Fonction à exécuter si le bouton "YES" est pressé
            try {
                charge_data(FXCollections.observableArrayList(ReceptionCommandeService.getallcommande()));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));

        alert.showAndWait();
    }

    @FXML
    public void btn_annule(ActionEvent actionEvent) {
        detail_livraison(null);
    }

    @FXML
    public void refreche(ActionEvent actionEvent) throws SQLException {
        I_recherche.setText("");
        charge_data(FXCollections.observableArrayList(ReceptionCommandeService.getallcommande()));
    }
}
