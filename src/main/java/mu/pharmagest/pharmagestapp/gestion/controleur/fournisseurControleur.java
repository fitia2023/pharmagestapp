package mu.pharmagest.pharmagestapp.gestion.controleur;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mu.pharmagest.pharmagestapp.gestion.modele.DAO.FournisseurDAO;
import mu.pharmagest.pharmagestapp.gestion.modele.enity.Fournisseur;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Control fournisseur
 */
public class fournisseurControleur implements Initializable {


    @FXML
    private TextField I_AdresseF;

    @FXML
    private TextField I_TelF;

    @FXML
    private TextField I_nomF;

    @FXML
    private Label Info_Id;

    @FXML
    private TextField Input_recherche;

    @FXML
    private TableColumn<Fournisseur, String> TAdresseF;

    @FXML
    private TableColumn<Fournisseur, String> TNomF;

    @FXML
    private TableColumn<Fournisseur, Integer> TTelF;

    @FXML
    private TableView<Fournisseur> TableFournisseur;

    private FournisseurDAO fournisseurDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Charger la liste
        fournisseurDAO = new FournisseurDAO();
        try {
            charge_data(FXCollections.observableArrayList(fournisseurDAO.getallfournisseurs()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        TableFournisseur.setOnMouseClicked(
                event -> {
                    if (TableFournisseur.getSelectionModel().getSelectedItem() != null) {
                        detailFournisseur(TableFournisseur.getSelectionModel().getSelectedItem());
                    }
                }
        );
    }

    //Chargement de data
    private void charge_data(ObservableList<Fournisseur> fournisseurs) throws SQLException {
        TableFournisseur.setItems(fournisseurs);
        TNomF.setCellValueFactory(call_n -> new SimpleStringProperty(call_n.getValue().getNom_fournisseur()));
        TAdresseF.setCellValueFactory(call_a -> new SimpleStringProperty(call_a.getValue().getAdresse_fournisseur()));
        TTelF.setCellValueFactory(call_t -> new SimpleIntegerProperty(call_t.getValue().getTel_fournisseur()).asObject());
    }

    //Affichage fournisseur
    private void detailFournisseur(Fournisseur fournisseur) {
        if (fournisseur != null) {
            Info_Id.setText(fournisseur.getId_fournisseur().toString());
            I_nomF.setText(fournisseur.getNom_fournisseur());
            I_AdresseF.setText(fournisseur.getAdresse_fournisseur());
            I_TelF.setText(fournisseur.getTel_fournisseur().toString());
        } else {
            Info_Id.setText("");
            I_nomF.setText("");
            I_AdresseF.setText("");
            I_TelF.setText("");
        }

    }

    @FXML
    void btn_ajouter(ActionEvent event) {
        //verification des champs
        if (!I_nomF.getText().isBlank() && !I_AdresseF.getText().isBlank() && !I_TelF.getText().isBlank()) {
            //            System.out.println("Les champs ne sont pas vide")
            String telText = I_TelF.getText();
            try {
                Integer tel = Integer.parseInt(telText);
                if (fournisseurDAO.addFournisseur(new Fournisseur(null, I_nomF.getText(), I_AdresseF.getText(), tel))) {
                    AlertInfo(Alert.AlertType.CONFIRMATION, "Pour continuer, cliquer sur YES", ButtonType.YES);
                    detailFournisseur(null);
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
                charge_data(FXCollections.observableArrayList(fournisseurDAO.getallfournisseurs()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));

        alert.showAndWait();
    }

    @FXML
    void btn_rafraichir(ActionEvent event) throws SQLException {
        detailFournisseur(null);
        vide_recherche();
        charge_data(FXCollections.observableArrayList(fournisseurDAO.getallfournisseurs()));
    }

    @FXML
    void btn_recherche(ActionEvent event) throws SQLException {
        //Recuper String de input
        String nom = Input_recherche.getText();
        charge_data(FXCollections.observableArrayList(fournisseurDAO.getFournisseursByName(nom)));
    }

    @FXML
    void btn_supprimer(ActionEvent event) {

        try {
            Integer id = Integer.parseInt(Info_Id.getText());
            if (fournisseurDAO.deleteFournisseur(id)) {
                AlertInfo(Alert.AlertType.CONFIRMATION, "Pour continuer suppression "+ TableFournisseur.getSelectionModel().getSelectedItem().getNom_fournisseur() +", cliquer sur YES", ButtonType.YES);
                detailFournisseur(null);
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
        //verification des champs
        if (!I_nomF.getText().isBlank() && !I_AdresseF.getText().isBlank() && !I_TelF.getText().isBlank() && !Info_Id.getText().isBlank()) {
            //            System.out.println("Les champs ne sont pas vide")
            String telText = I_TelF.getText();
            String id = Info_Id.getId();
            try {
                Integer tel = Integer.parseInt(telText);
                Integer idf = Integer.parseInt(id);
                if (fournisseurDAO.updateFournisseur(new Fournisseur(idf, I_nomF.getText(), I_AdresseF.getText(), tel))) {
                    AlertInfo(Alert.AlertType.CONFIRMATION, "Pour continuer, cliquer sur YES", ButtonType.YES);
                    detailFournisseur(null);
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
            AlertInfo(Alert.AlertType.WARNING, "Veuillez bien cliquer sur fournisseur à mettre à jour", ButtonType.OK);
        }

    }

    //vide champ nom fournisseur
    private void vide_recherche() {
        Input_recherche.setText("");
    }


}
