package mu.pharmagest.pharmagestapp.Controller.Vente;

// Import des classes nécessaires

import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import mu.pharmagest.pharmagestapp.LienBD.DAO.VenteDAO;
import mu.pharmagest.pharmagestapp.LienBD.Services.CaisseService;
import mu.pharmagest.pharmagestapp.Modele.Vente;
import mu.pharmagest.pharmagestapp.util.SourceImage;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class CaisseAdvanceController implements Initializable {

    @FXML
    public TextField r_ticket;
    @FXML
    public TableView<Vente> t_venteticket;
    @FXML
    public TableColumn<Vente, Integer> t_numticket;
    @FXML
    public TableColumn<Vente, Date> t_datevente;
    @FXML
    public TableColumn<Vente, Double> t_prix_total;
    @FXML
    public TableColumn<Vente, Boolean> t_payer;
    @FXML
    public TableColumn<Vente, String> t_suppr;

    // Méthode d'initialisation appelée automatiquement lors du chargement du contrôleur
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initializeTableTicket(FXCollections.observableArrayList(VenteDAO.getallvente()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour initialiser le tableau des ventes
    private void initializeTableTicket(ObservableList<Vente> ventes) {
        t_venteticket.setItems(ventes);
        t_numticket.setCellValueFactory(call -> new SimpleIntegerProperty(call.getValue().getId_vente()).asObject());
        t_prix_total.setCellValueFactory(call -> new SimpleDoubleProperty(call.getValue().getPrix_total()).asObject());
        t_payer.setCellValueFactory(call -> new SimpleBooleanProperty(call.getValue().getPayer()));

        t_payer.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean payer, boolean empty) {
                super.updateItem(payer, empty);
                if (empty || payer == null) {
                    setText(null);
                } else {
                    setText(payer ? "Oui" : "Non");
                }
            }
        });
        t_datevente.setCellValueFactory(new PropertyValueFactory<>("date_vente"));
        // Configuration de la factory de cellule pour formater la date
        t_datevente.setCellFactory(column -> {
            return new TableCell<Vente, Date>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                protected void updateItem(Date date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty || date == null) {
                        setText(null);
                    } else {
                        // Convertir java.sql.Date en java.util.Date
                        java.util.Date utilDate = new java.util.Date(date.getTime());
                        // Formater la date selon le format souhaité
                        setText(formatter.format(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
                    }
                }
            };
        });
        // Configuration de la cellule de suppression
        t_suppr.setCellFactory(editBtn());
    }

    // Méthode pour configurer le bouton de suppression dans la cellule
    private Callback<TableColumn<Vente, String>, TableCell<Vente, String>> editBtn() {
        return param -> {
            // Création de la cellule contenant le bouton de suppression
            final TableCell<Vente, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    // Vérification si la cellule est vide
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        // Création d'un bouton avec une icône de corbeille
                        JFXButton deleteButton = new JFXButton();
                        ImageView imageView = new ImageView(SourceImage.getsrcImage("effacer.png")); // Chargement de l'icône de suppression
                        imageView.setFitHeight(20);
                        imageView.setFitWidth(20);
                        deleteButton.setGraphic(imageView);

                        // Ajout d'un gestionnaire d'événements pour la suppression
                        deleteButton.setOnAction(event -> {
                            Vente vente = getTableView().getItems().get(getIndex());
                            // Logique de suppression
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer ?", ButtonType.YES, ButtonType.NO);
                            alert.setHeaderText(null);
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.YES) {
                                    try {
                                        if (CaisseService.delventepharmacien(vente.getId_vente())) {
                                            initializeTableTicket(FXCollections.observableArrayList(VenteDAO.getallvente()));
                                        }
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        });

                        // Ajout du bouton à la cellule
                        setGraphic(deleteButton);
                        setText(null);
                    }
                }
            };

            return cell;
        };
    }

    // Méthode pour gérer la recherche d'une vente par son numéro de ticket
    @FXML
    public void rechercher(ActionEvent actionEvent) throws SQLException {
        String ticketText = r_ticket.getText();
        if (!ticketText.isEmpty()) { // Vérifie si le champ de texte n'est pas vide
            int ticketId = Integer.parseInt(ticketText);
            if (VenteDAO.getVenteById(ticketId) != null) {
                initializeTableTicket(FXCollections.observableArrayList(VenteDAO.getVenteById(ticketId)));
            } else {
                initializeTableTicket(FXCollections.observableArrayList());
            }
        }
    }

    // Méthode pour rafraîchir le tableau des ventes
    @FXML
    public void rafraichir(ActionEvent actionEvent) throws SQLException {
        r_ticket.setText("");
        initializeTableTicket(FXCollections.observableArrayList(VenteDAO.getallvente()));
    }
}
