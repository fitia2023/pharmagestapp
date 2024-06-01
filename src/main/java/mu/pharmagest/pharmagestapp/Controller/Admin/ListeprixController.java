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
import mu.pharmagest.pharmagestapp.LienBD.DAO.ListePrixDAO;
import mu.pharmagest.pharmagestapp.LienBD.DAO.MedicamentDAO;
import mu.pharmagest.pharmagestapp.Modele.Fournisseur;
import mu.pharmagest.pharmagestapp.Modele.ListePrix;
import mu.pharmagest.pharmagestapp.Modele.Medicament;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ListeprixController implements Initializable {
    @FXML
    public TableView<ListePrix> t_liste;
    @FXML
    public TableColumn<ListePrix, String> t_medicament;
    @FXML
    public TableColumn<ListePrix, Double> t_prixU;
    @FXML
    public TableColumn<ListePrix, Integer> t_qt;
    @FXML
    public TableColumn<ListePrix, Double> t_prix;
    @FXML
    public Label fourni;
    @FXML
    public TextField I_prixU;
    @FXML
    public TextField I_qt;
    @FXML
    public ComboBox<String> I_medicament;
    @FXML
    public TextField I_prix;

    //Pour detail du fournisseur
    private Integer id_fournisseur;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
         setId_fournisseur();

        //Evite le changement de prix de vente selon medicament
        I_prix.setDisable(true);
        try {
            change_nom_fournisseur();
            charger_combobox();

            charge_data(FXCollections.observableArrayList(ListePrixDAO.listemedicamentparidfournisseur(getId_fournisseur())));
            t_liste.setOnMouseClicked(
                    event -> {
                        if (t_liste.getSelectionModel().getSelectedItem() != null) {
                            detaillisteprix(t_liste.getSelectionModel().getSelectedItem());
                        }
                    }
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //detail liste prix
    private void detaillisteprix(ListePrix listePrix) {
        if (listePrix != null) {
            I_medicament.setValue(listePrix.getMedicament().getNom_medicament());
            I_medicament.setDisable(true);
            I_prixU.setText(listePrix.getPrix_unitaire_achat().toString());
            I_qt.setText(listePrix.getQt_min_commande().toString());
            I_prix.setText(listePrix.getPrix_vente().toString());

        } else {

            I_medicament.setValue("");
            I_medicament.setDisable(false);
            I_prixU.setText("");
            I_qt.setText("");
            I_prix.setText("");
        }

    }

    @FXML
    void btn_ajouter(ActionEvent event) {
        //verification des champs
        if (champs(I_prixU.getText(), I_qt.getText(), I_prix.getText()) && !Objects.equals(I_medicament.getValue(), "")) {
            //            System.out.println("Les champs sont correctes")
            double price = Double.parseDouble(I_prixU.getText());
            int quantity = Integer.parseInt(I_qt.getText());
            double price_vente = Double.parseDouble(I_prix.getText());
            try {
                Fournisseur fournisseur = FournisseurDAO.getFournisseursById(getId_fournisseur());
                Medicament medicament = MedicamentDAO.getMedicamentsByName(I_medicament.getValue()).get(0);
                if (ListePrixDAO.addListeprix(new ListePrix(fournisseur, medicament, price, quantity, price_vente))) {
                    AlertInfo(Alert.AlertType.CONFIRMATION, "Pour continuer, cliquer sur YES", ButtonType.YES);
                    detaillisteprix(null);
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
            AlertInfo(Alert.AlertType.WARNING, "Veuillez bien remplir tous les champs", ButtonType.OK);
        }

    }

    //Pour afficher information etat
    private void AlertInfo(Alert.AlertType alertType, String message, ButtonType buttonType) {
        Alert alert = new Alert(alertType, message, buttonType);
        // Récupérer le bouton "YES"
        alert.getButtonTypes().stream().filter(buttonType1 -> buttonType1 == ButtonType.YES).findFirst().ifPresent(buttonTypeYes -> alert.getDialogPane().lookupButton(buttonTypeYes).addEventFilter(ActionEvent.ACTION, event -> {
            // Fonction à exécuter si le bouton "YES" est pressé
            try {
                charge_data(FXCollections.observableArrayList(ListePrixDAO.listemedicamentparidfournisseur(getId_fournisseur())));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));

        alert.showAndWait();
    }


    //-----Getters et Setters du fournisseur
    public Integer getId_fournisseur() {
        return id_fournisseur;
    }

    public void setId_fournisseur() {
        this.id_fournisseur = FournisseurController.id_fo;
    }


    //Changer le nom du fournisseur
    private void change_nom_fournisseur() throws SQLException {
        Integer id = getId_fournisseur();
        if (id != null) {
            fourni.setText(FournisseurDAO.getFournisseursById(id).getNom_fournisseur());
        } else {
            // Fournir un comportement par défaut si l'ID du fournisseur est
            System.out.println("vide");
        }
    }

    //Pour charger le contenu de la liste
    private void charge_data(ObservableList<ListePrix> listePrixes) throws SQLException {
        t_liste.setItems(listePrixes);
//        t_medicament.setCellValueFactory(call_m -> new SimpleStringProperty(call_m.getValue().getMedicament().getNom_medicament()));
        t_medicament.setCellValueFactory(call_m -> {
            Medicament medicament = call_m.getValue().getMedicament();
            if (medicament != null) {
                return new SimpleStringProperty(medicament.getNom_medicament());
            } else {
                return new SimpleStringProperty("");
            }
        });

        t_prixU.setCellValueFactory(call_p -> new SimpleDoubleProperty(call_p.getValue().getPrix_unitaire_achat()).asObject());
        t_qt.setCellValueFactory(call_q -> new SimpleIntegerProperty(call_q.getValue().getQt_min_commande()).asObject());
        t_prix.setCellValueFactory(call_pr -> new SimpleDoubleProperty(call_pr.getValue().getPrix_vente()).asObject());
    }

    //Pour charger combobox
    private void charger_combobox() throws SQLException {
        for (Medicament medicament : MedicamentDAO.getallmedicament()) {
            I_medicament.getItems().add(medicament.getNom_medicament());
        }
        //ecoute le changement de valeur input
        I_medicament.setOnAction(event -> {
//            System.out.println(I_medicament.getValue());
            try {
                changer_prix(I_medicament.getValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //Pour changer le contenu du prix selon medicament
    private void changer_prix(String nom_medicament) throws SQLException {
        Medicament medicament = (Medicament) MedicamentDAO.getMedicamentsByName(nom_medicament).get(0);
        I_prix.setText(medicament.getPrix_vente().toString());
    }


    //Traitement des champs
    private boolean champs(String prix_unitaire, String qt, String prix_vente) {
        try {
            // Conversion des valeurs en nombres
            double price = Double.parseDouble(prix_unitaire);
            int quantity = Integer.parseInt(qt);
            double price_vente = Double.parseDouble(prix_vente);
            // Vérification si les valeurs sont correctes
            return price > 0 && quantity > 0 && price_vente > 0;
        } catch (NumberFormatException e) {
            // Gestion de l'exception si la conversion échoue
            return false;
        }
    }

    @FXML
    public void btn_rafraichir(ActionEvent actionEvent) throws SQLException {
        detaillisteprix(null);
        charge_data(FXCollections.observableArrayList(ListePrixDAO.listemedicamentparidfournisseur(getId_fournisseur())));
    }

    @FXML
    public void btn_supprimer(ActionEvent actionEvent) {
        try {
            Integer id_fournisseur = getId_fournisseur();
            Integer id_medicament = MedicamentDAO.getMedicamentsByName(I_medicament.getValue()).get(0).getId_medicament();
            if (ListePrixDAO.deletelisteprix(id_fournisseur, id_medicament)) {
                AlertInfo(Alert.AlertType.CONFIRMATION, "Pour continuer suppression , cliquer sur YES", ButtonType.YES);
                detaillisteprix(null);
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
    public void btn_up(ActionEvent actionEvent) {
        //verification des champs
        if (champs(I_prixU.getText(), I_qt.getText(), I_prix.getText()) && !Objects.equals(I_medicament.getValue(), "")) {
            // System.out.println("Les champs ne sont pas vide")
            double price = Double.parseDouble(I_prixU.getText());
            int quantity = Integer.parseInt(I_qt.getText());
            double price_vente = Double.parseDouble(I_prix.getText());
            try {
                Fournisseur fournisseur = FournisseurDAO.getFournisseursById(getId_fournisseur());
                Medicament medicament = MedicamentDAO.getMedicamentsByName(I_medicament.getValue()).get(0);
                if (ListePrixDAO.updateListeprix(new ListePrix(fournisseur, medicament, price, quantity, price_vente))) {
                    AlertInfo(Alert.AlertType.CONFIRMATION, "Pour continuer, cliquer sur YES", ButtonType.YES);
                    detaillisteprix(null);
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
            AlertInfo(Alert.AlertType.WARNING, "Veuillez bien cliquer sur la liste à mettre à jour", ButtonType.OK);
        }
    }

    public void initMedicament(String medicament) throws SQLException {
        Medicament medicament1 =MedicamentDAO.getMedicamentsByName(medicament).get(0);
        detaillisteprix(
                new ListePrix(FournisseurDAO.getFournisseursById(id_fournisseur),medicament1,0.0,0,medicament1.getPrix_vente())
        );
    }
}
