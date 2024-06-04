package mu.pharmagest.pharmagestapp.Controller.Admin;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mu.pharmagest.pharmagestapp.LienBD.DAO.FournisseurDAO;
import mu.pharmagest.pharmagestapp.LienBD.Services.CommandeService;
import mu.pharmagest.pharmagestapp.Modele.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {
    @FXML
    public ListView<String> LMedicSuggestion;
    @FXML
    public ListView<String> LMedic;
    @FXML
    public Label I_NomMedic;
    @FXML
    public ComboBox<String> I_fournisseur;
    @FXML
    public Label I_prix_fournisseur;
    @FXML
    public TextField qt_commande;
    @FXML
    public TableView<LigneCommande> T_panier;
    @FXML
    public TableColumn<LigneCommande, String> T_medic;
    @FXML
    public TableColumn<LigneCommande, String> T_Fournisseur;
    @FXML
    public TableColumn<LigneCommande, Integer> T_qt;
    @FXML
    public TableColumn<LigneCommande, Double> T_payer;
    @FXML
    public TableColumn<LigneCommande, Integer> T_id;


    private static Integer id_last_commande;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            id_last_commande = CommandeService.getLastIdCommande() + 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        charge_data();
        clickLMedic();

    }

    //Charger data de la liste
    private void charge_data() {
        try {
            for (Medicament medicament : FXCollections.observableArrayList(CommandeService.suggestioncommande())) {
                LMedicSuggestion.getItems().add(medicament.getNom_medicament());
            }
            for (Medicament medicament : FXCollections.observableArrayList(CommandeService.getallmedicament())) {
                LMedic.getItems().add(medicament.getNom_medicament());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Pour click de data
    private void clickLMedic() {
        LMedicSuggestion.setOnMouseClicked(
                event -> {
                    if (LMedicSuggestion.getSelectionModel().getSelectedItem() != null) {
                        try {
                            details_commande(LMedicSuggestion.getSelectionModel().getSelectedItem());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
        LMedic.setOnMouseClicked(
                event -> {
                    if (LMedic.getSelectionModel().getSelectedItem() != null) {
                        try {
                            details_commande(LMedic.getSelectionModel().getSelectedItem());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );

    }

    //Pour detail de la commande
    private void details_commande(String medicament) throws SQLException {
        vider_detail_commande();
        I_NomMedic.setText(medicament);
        charge_combobox(medicament);
        Integer suggestion_qt = CommandeService.getallmedicmanetbyname(medicament).get(0).getQt_max() - CommandeService.getallmedicmanetbyname(medicament).get(0).getQt_stock();
        qt_commande.setText(suggestion_qt.toString());
    }

    //Vide details commande
    private void vider_detail_commande() {
        I_NomMedic.setText("");
        I_fournisseur.getItems().clear();
        I_prix_fournisseur.setText("");
        qt_commande.setText("");
    }

    //Combobox charge
    private void charge_combobox(String medicament) throws SQLException {
        Integer id_medicament = CommandeService.getallmedicmanetbyname(medicament).get(0).getId_medicament();
        String fournisseur_habituel = CommandeService.getallmedicmanetbyname(medicament).get(0).getFournisseur_habituel().getNom_fournisseur();
        I_fournisseur.getItems().add(fournisseur_habituel);
        for (ListePrix listePrix : FXCollections.observableArrayList(CommandeService.getallFournisseurbyid_medicament(id_medicament))) {
            if (!Objects.equals(fournisseur_habituel, listePrix.getFournisseur().getNom_fournisseur())) {
                I_fournisseur.getItems().add(listePrix.getFournisseur().getNom_fournisseur());
            }
        }
        //Lorsque j'ai choisi un fournisseur
        I_fournisseur.setOnAction(event -> {
            String nom_fournisseur = I_fournisseur.getSelectionModel().getSelectedItem();
            if (nom_fournisseur != null) {
                try {
                    Integer id_fournisseur = CommandeService.getallfournisseurbyname(nom_fournisseur).get(0).getId_fournisseur();
                    String prix = CommandeService.getListPrixbyid(id_medicament, id_fournisseur).getPrix_unitaire_achat().toString();
                    I_prix_fournisseur.setText(prix);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    //Pour afficher information etat
    private void AlertInfo(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);

        alert.showAndWait();
    }

    @FXML
    public void btn_ajout_commande(ActionEvent actionEvent) {
        try {
            //verification des champs
            String fournisseur = I_fournisseur.getSelectionModel().getSelectedItem();
            Integer commande = Integer.parseInt(qt_commande.getText());
            Double prix_to = Double.parseDouble(I_prix_fournisseur.getText());
            if (fournisseur != null && prix_to > 0) {
                LocalDate localDate = LocalDate.now();
                java.util.Date utilDate = java.sql.Date.valueOf(localDate);
                Double prix_total = commande * prix_to;
                LigneCommande ligneCommande = new LigneCommande(
                        CommandeService.getallmedicmanetbyname(I_NomMedic.getText()).get(0),
                        new Commande(
                                idDeCommande(),
                                utilDate,
                                prix_total,
                                CommandeService.getallfournisseurbyname(I_fournisseur.getSelectionModel().getSelectedItem()).get(0),
                                0.0,
                                "En cours"
                        ),
                        commande,
                        0
                );
                T_panier.getItems().add(ligneCommande);
                T_id.setCellValueFactory(call -> new SimpleIntegerProperty(call.getValue().getCommande().getId_commande()).asObject());
                T_medic.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getMedicament().getNom_medicament()));
                T_Fournisseur.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getCommande().getFournisseur().getNom_fournisseur()));
                T_qt.setCellValueFactory(call -> new SimpleIntegerProperty(call.getValue().getQt_medicament()).asObject());
                T_payer.setCellValueFactory(call -> new SimpleDoubleProperty(call.getValue().getCommande().getPrix_total()).asObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertInfo(Alert.AlertType.ERROR, "Veuillez bien remplir les champs");
        }


    }


    private int idDeCommande() throws SQLException {
        Fournisseur fournisseur = CommandeService.getallfournisseurbyname(I_fournisseur.getSelectionModel().getSelectedItem()).get(0);
        int id = id_last_commande;
        boolean isLook = false;

        List<LigneCommande> ligneCommandes = T_panier.getItems();
        for (LigneCommande ligneCommande : ligneCommandes) {

            if (ligneCommande.getCommande().getFournisseur().getNom_fournisseur().equals(fournisseur.getNom_fournisseur())) {
                id = ligneCommande.getCommande().getId_commande();
                isLook = true;
                break; // Sortir de la boucle car le fournisseur a été trouvé
            }
        }

        if (!isLook) {
            id_last_commande++; // Incrémenter id_last_commande seulement si le fournisseur n'est pas trouvé
        }

        return id;
    }


    @FXML
    public void btn_annule(ActionEvent actionEvent) {
        vider_detail_commande();
    }

    @FXML
    public void btn_annule_tout(ActionEvent actionEvent) throws SQLException {
        id_last_commande = CommandeService.getLastIdCommande() + 1;
        vider_detail_commande();
        T_panier.getItems().clear();
    }

    @FXML
    public void btn_envoyer_commande(ActionEvent actionEvent) throws SQLException {
        // Copier la liste pour éviter les modifications concurrentes
        List<LigneCommande> ligneCommandes = new ArrayList<>(T_panier.getItems());
        boolean reussi = false;
        for (LigneCommande ligneCommande : ligneCommandes) {
            if (CommandeService.addLCommande(ligneCommande)) {
                reussi = true;
            }
        }
        if (reussi){
            AlertInfo(Alert.AlertType.CONFIRMATION, "Voulez vous poursuivre la commande");
            vider_detail_commande();
            T_panier.getItems().clear();
        }
    }

}
