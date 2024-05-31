package mu.pharmagest.pharmagestapp.Controller.Admin;

import javafx.beans.property.SimpleBooleanProperty;
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
import mu.pharmagest.pharmagestapp.LienBD.DAO.MedicamentDAO;
import mu.pharmagest.pharmagestapp.Modele.Fournisseur;
import mu.pharmagest.pharmagestapp.Modele.Medicament;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Pour gerer medicament du pharmacien
 */
public class MedicamentController implements Initializable {

    @FXML
    public TableView<Medicament> TableMedicament;
    @FXML
    public TableColumn<Medicament, String> TNomM;
    @FXML
    public TableColumn<Medicament, String> TFamilleM;
    @FXML
    public TableColumn<Medicament, String> TUniteM;
    @FXML
    public TableColumn<Medicament, Double> TPrixM;
    @FXML
    public TableColumn<Medicament, Integer> TQTStock;
    @FXML
    public TableColumn<Medicament, Integer> TQTMinStock;
    @FXML
    public TableColumn<Medicament, Integer> TQTMaxStock;
    @FXML
    public TableColumn<Medicament, Boolean> TOrdonnanceM;
    @FXML
    public TableColumn<Medicament, Integer> TSeuilComM;
    @FXML
    public TableColumn<Medicament, String> TFourniM;
    @FXML
    public TextField I_FamilleM;
    @FXML
    public TextField I_UniteM;
    @FXML
    public TextField I_nomM;
    @FXML
    public TextField I_PrixM;
    @FXML
    public TextField I_QtStockM;
    @FXML
    public TextField I_QtMinStockM;
    @FXML
    public TextField I_QtMaxStockM;
    @FXML
    public TextField I_SeuilM;
    @FXML
    public TextField Input_recherche;
    @FXML
    public ComboBox<String> I_FournHab;


    @FXML
    public ToggleGroup I_Ordonnance;

    private Integer id_medicament;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            charge_data(FXCollections.observableArrayList(MedicamentDAO.getallmedicament()));
            charge_fournisseur();
            TableMedicament.setOnMouseClicked(
                    event -> {
                        if (TableMedicament.getSelectionModel().getSelectedItem() != null) {
                            this.id_medicament = TableMedicament.getSelectionModel().getSelectedItem().getId_medicament();
                            detailMedicament(TableMedicament.getSelectionModel().getSelectedItem());
                        }
                    }
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Chargement de data
    private void charge_data(ObservableList<Medicament> medicaments) throws SQLException {
        TableMedicament.setItems(medicaments);
        TNomM.setCellValueFactory(call_nom -> new SimpleStringProperty(call_nom.getValue().getNom_medicament()));
        TFamilleM.setCellValueFactory(call_famille -> new SimpleStringProperty(call_famille.getValue().getFamille()));
        TUniteM.setCellValueFactory(call_unite -> new SimpleStringProperty(call_unite.getValue().getUnite()));
        TPrixM.setCellValueFactory(call_prix -> new SimpleDoubleProperty(call_prix.getValue().getPrix_vente()).asObject());
        TQTStock.setCellValueFactory(call_qts -> new SimpleIntegerProperty(call_qts.getValue().getQt_stock()).asObject());
        TQTMinStock.setCellValueFactory(call_qtmin -> new SimpleIntegerProperty(call_qtmin.getValue().getQt_min()).asObject());
        TQTMaxStock.setCellValueFactory(call_qtmax -> new SimpleIntegerProperty(call_qtmax.getValue().getQt_max()).asObject());
        TOrdonnanceM.setCellValueFactory(call_ordo -> new SimpleBooleanProperty(call_ordo.getValue().getOrdonnance()));
        TSeuilComM.setCellValueFactory(call_seuil -> new SimpleIntegerProperty(call_seuil.getValue().getSeuil_commande()).asObject());
        TFourniM.setCellValueFactory(call_nomfourni -> new SimpleStringProperty(call_nomfourni.getValue().getFournisseur_habituel().getNom_fournisseur()));
    }

    //Pour charger fournisseur
    private void charge_fournisseur() throws SQLException {
        for (Fournisseur fournisseur : FournisseurDAO.getallfournisseurs()) {
            I_FournHab.getItems().add(fournisseur.getNom_fournisseur());
        }
    }

    //Affichage fournisseur
    private void detailMedicament(Medicament medicament) {
        if (medicament != null) {
            I_nomM.setText(medicament.getNom_medicament());
            I_FamilleM.setText(medicament.getFamille());
            I_UniteM.setText(medicament.getUnite());
            I_FournHab.setValue(medicament.getFournisseur_habituel().getNom_fournisseur());
            I_PrixM.setText(medicament.getPrix_vente().toString());
            I_QtStockM.setText(medicament.getQt_stock().toString());
            I_QtMinStockM.setText(medicament.getQt_min().toString());
            I_QtMaxStockM.setText(medicament.getQt_max().toString());
            choiceOrdonnance(medicament.getOrdonnance() ? "Oui" : "Non");
            I_SeuilM.setText(medicament.getSeuil_commande().toString());
        } else {
            I_nomM.setText("");
            I_FamilleM.setText("");
            I_UniteM.setText("");
            I_FournHab.getSelectionModel().clearSelection();
            I_PrixM.setText("");
            I_QtStockM.setText("");
            I_QtMinStockM.setText("");
            I_QtMaxStockM.setText("");
            choiceOrdonnance(null);
            I_SeuilM.setText("");
        }

    }

    //Pour vider valeur d'ordonnance
    private void choiceOrdonnance(String value) {
        if (value == null) {
            if (I_Ordonnance.getSelectedToggle() != null) {
                I_Ordonnance.getSelectedToggle().setSelected(false);
            }
        } else {
            I_Ordonnance.getToggles().forEach(toggle -> {
                RadioButton radioButton = (RadioButton) toggle;
                if (radioButton.getText().equals(value)) {
                    I_Ordonnance.selectToggle(toggle);
                } else {
                    radioButton.setSelected(false);
                }
            });
        }
    }

    @FXML
    void btn_ajouter(ActionEvent event) {
        String nom = I_nomM.getText();
        String famille = I_FamilleM.getText();
        String unite = I_UniteM.getText();
        //verification des champs
        if (verificationchamps() && !nom.isEmpty() && !famille.isEmpty() && !unite.isEmpty() && id_medicament == null) {
            //System.out.println("Les champs sont correcte") ;
            // Vérifier si la valeur sélectionnée est "Oui"
            boolean isOrdonnanceOui = ((RadioButton) I_Ordonnance.getSelectedToggle()).getText().equals("Oui");
            try {
                Double prix = Double.parseDouble(I_PrixM.getText());
                Integer qt_stock = Integer.parseInt(I_QtStockM.getText());
                Integer qt_min = Integer.parseInt(I_QtMinStockM.getText());
                Integer qt_max = Integer.parseInt(I_QtMaxStockM.getText());
                Integer seuil = Integer.parseInt(I_SeuilM.getText());
                Fournisseur fournisseur = FournisseurDAO.getFournisseursByName(I_FournHab.getValue()).get(0);
                if (MedicamentDAO.addMedicament(new Medicament(null, nom, famille, isOrdonnanceOui, prix, qt_stock, qt_min, qt_max, seuil, unite, fournisseur))) {
                    AlertInfo(Alert.AlertType.CONFIRMATION, "Pour continuer, cliquer sur YES", ButtonType.YES);
                    detailMedicament(null);
                }
                // Faites quelque chose avec 'tel' ici
            } catch (NumberFormatException e) {
                // Gérez le cas où la conversion échoue (par exemple, si le texte n'est pas un entier valide)
                System.out.println("Erreur de conversion : " + e.getMessage());
                AlertInfo(Alert.AlertType.WARNING, "Veuillez bien remplir tous les champs correctement", ButtonType.OK);

            } catch (SQLException e) {
                e.printStackTrace(); // Log l'exception (vous pouvez utiliser un logger à la place)
                AlertInfo(Alert.AlertType.WARNING, "Erreur lors de l'insertion dans la base de données", ButtonType.OK);
            }

        } else {
            AlertInfo(Alert.AlertType.WARNING, "Veuillez bien remplir tous les champs ou rafraichir", ButtonType.OK);
        }

    }

    //Pour afficher information etat
    private void AlertInfo(Alert.AlertType alertType, String message, ButtonType buttonType) {
        Alert alert = new Alert(alertType, message, buttonType);
        // Récupérer le bouton "YES"
        alert.getButtonTypes().stream().filter(buttonType1 -> buttonType1 == ButtonType.YES).findFirst().ifPresent(buttonTypeYes -> alert.getDialogPane().lookupButton(buttonTypeYes).addEventFilter(ActionEvent.ACTION, event -> {
            // Fonction à exécuter si le bouton "YES" est pressé
            try {
                charge_data(FXCollections.observableArrayList(MedicamentDAO.getallmedicament()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));

        alert.showAndWait();
    }

    //Verification des champs dans le systeme
    private Boolean verificationchamps() {
        try {
            // Conversion des valeurs en nombres
            double price = Double.parseDouble(I_PrixM.getText());
            int qt_max = Integer.parseInt(I_QtMaxStockM.getText());
            int qt_min = Integer.parseInt(I_QtMinStockM.getText());
            int qt_stock = Integer.parseInt(I_QtStockM.getText());
            int seuil = Integer.parseInt(I_SeuilM.getText());

            // Vérification si la ComboBox a une valeur sélectionnée
            boolean comboBoxValueNotEmpty = I_FournHab.getValue() != null && !I_FournHab.getValue().isEmpty();
            // Vérification si au moins un bouton radio est sélectionné dans le ToggleGroup
            boolean ordonnanceSelected = I_Ordonnance.getSelectedToggle() != null;

            // Vérification de toutes les conditions
            return price > 0 && qt_max > 0 && qt_min > 0 && qt_stock > 0 && seuil > 0 && ordonnanceSelected && comboBoxValueNotEmpty;
        } catch (NumberFormatException e) {
            // Gestion de l'exception si la conversion échoue
            return false;
        }
    }

    @FXML
    void btn_rafraichir(ActionEvent event) throws SQLException {
        id_medicament = null;
        vide_recherche();
        detailMedicament(null);
        charge_data(FXCollections.observableArrayList(MedicamentDAO.getallmedicament()));
    }

    @FXML
    void btn_recherche(ActionEvent event) throws SQLException {
        String recherche = Input_recherche.getText();
        charge_data(FXCollections.observableArrayList(MedicamentDAO.getMedicamentsByName(recherche)));
    }

    @FXML
    void btn_supprimer(ActionEvent event) {
        try {
            if (id_medicament != null) {
                Integer id = id_medicament;
                if (MedicamentDAO.deleteMedicament(id)) {
                    AlertInfo(Alert.AlertType.CONFIRMATION, "Pour continuer suppression " + TableMedicament.getSelectionModel().getSelectedItem().getNom_medicament() + ", cliquer sur YES", ButtonType.YES);
                    detailMedicament(null);
                    id_medicament = null;
                }
            }else {
                AlertInfo(Alert.AlertType.WARNING, "Veuillez bien cliquer sur fournisseur à supprimer", ButtonType.OK);
            }
            // Faites quelque chose avec 'tel' ici
        } catch (NumberFormatException e) {
            // Gérez le cas où la conversion échoue (par exemple, si le texte n'est pas un entier valide)
            System.out.println("Erreur de conversion : " + e.getMessage());
            AlertInfo(Alert.AlertType.WARNING, "Veuillez bien cliquer sur fournisseur à supprimer", ButtonType.OK);

        } catch (SQLException e) {
            e.printStackTrace(); // Log l'exception (vous pouvez utiliser un logger à la place)
            AlertInfo(Alert.AlertType.WARNING, "Erreur lors de la suppression dans la base de données", ButtonType.OK);
        }
    }

    @FXML
    void btn_up(ActionEvent event) {
        String nom = I_nomM.getText();
        String famille = I_FamilleM.getText();
        String unite = I_UniteM.getText();
        //verification des champs
        if (verificationchamps() && !nom.isEmpty() && !famille.isEmpty() && !unite.isEmpty() && id_medicament != null) {
            //            System.out.println("Les champs ne sont pas vide")
            try {
                // Vérifier si la valeur sélectionnée est "Oui"
                boolean isOrdonnanceOui = ((RadioButton) I_Ordonnance.getSelectedToggle()).getText().equals("Oui");
                try {
                    Double prix = Double.parseDouble(I_PrixM.getText());
                    Integer qt_stock = Integer.parseInt(I_QtStockM.getText());
                    Integer qt_min = Integer.parseInt(I_QtMinStockM.getText());
                    Integer qt_max = Integer.parseInt(I_QtMaxStockM.getText());
                    Integer seuil = Integer.parseInt(I_SeuilM.getText());
                    Fournisseur fournisseur = FournisseurDAO.getFournisseursByName(I_FournHab.getValue()).get(0);
                    if (MedicamentDAO.updateMedicament(new Medicament(id_medicament,nom,famille,isOrdonnanceOui,prix,qt_stock,qt_min,qt_max,seuil,unite,fournisseur))) {
                        AlertInfo(Alert.AlertType.CONFIRMATION, "Pour continuer, cliquer sur YES", ButtonType.YES);
                        id_medicament = null;
                        detailMedicament(null);
                    }
                    // Faites quelque chose avec 'tel' ici
                } catch (NumberFormatException e) {
                    // Gérez le cas où la conversion échoue (par exemple, si le texte n'est pas un entier valide)
                    System.out.println("Erreur de conversion : " + e.getMessage());
                    AlertInfo(Alert.AlertType.WARNING, "Veuillez bien remplir tous les champs correctement", ButtonType.OK);
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Log l'exception (vous pouvez utiliser un logger à la place)
                AlertInfo(Alert.AlertType.WARNING, "Erreur lors de l'insertion dans la base de données", ButtonType.OK);
            }

        } else {
            AlertInfo(Alert.AlertType.WARNING, "Veuillez bien cliquer sur medicament à mettre à jour", ButtonType.OK);
        }

    }

    //vider recherche
    private void vide_recherche() {
        Input_recherche.setText("");
    }

}
