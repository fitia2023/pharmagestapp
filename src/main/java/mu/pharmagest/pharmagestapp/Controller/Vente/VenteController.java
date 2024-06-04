package mu.pharmagest.pharmagestapp.Controller.Vente;
// VenteController.java

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mu.pharmagest.pharmagestapp.LienBD.DAO.MedicamentDAO;
import mu.pharmagest.pharmagestapp.LienBD.Services.VenteService;
import mu.pharmagest.pharmagestapp.Modele.LigneVente;
import mu.pharmagest.pharmagestapp.Modele.Medicament;
import mu.pharmagest.pharmagestapp.Modele.Prescrition;
import mu.pharmagest.pharmagestapp.Modele.Vente;
import mu.pharmagest.pharmagestapp.util.AnimationPopup;
import mu.pharmagest.pharmagestapp.util.SourceFxml;
import mu.pharmagest.pharmagestapp.util.layout.Card;
import mu.pharmagest.pharmagestapp.util.layout.NumPad;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VenteController implements Initializable {
    @FXML
    public Text t_total;
    @FXML
    public TextField r_medic;
    @FXML
    public AnchorPane panevente;
    @FXML
    public Pane reussi;
    @FXML
    public NumPad numpad;

    @FXML
    private TableView<ModelVente> t_vente;

    @FXML
    private TableColumn<ModelVente, String> t_medicament;
    @FXML
    public TableColumn<ModelVente, Double> t_prix_vente;

    @FXML
    private TableColumn<ModelVente, Integer> t_qt;

    @FXML
    private TableColumn<ModelVente, Double> t_prix;


    @FXML
    private Label nb_ticket;

    @FXML
    private Label date_ticket;

    @FXML
    private Label nom_medic;

    @FXML
    private Label qt_inserer;

    @FXML
    private JFXButton changed_ticket;

    @FXML
    private GridPane grid;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateticket();
        initializeTableView();
        numpad.setQt_insert(qt_inserer);
        try {
            afficherMedicaments(FXCollections.observableList(MedicamentDAO.getallmedicament()));
            numbertiket();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherMedicaments(ObservableList<Medicament> medicaments) {
        int taille = medicaments.size();
        int column = 2;
        int rows = (taille + column - 1) / column;
        grid.getChildren().clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column; j++) {
                int index = i * column + j;
                if (index < taille) {
                    Medicament medicament = medicaments.get(index);
                    Card card = new Card();
                    card.setOnMouseClicked(
                            event -> {
                                nom_medic.setText(card.getMedicament().getNom_medicament());
                                qt_inserer.setText("0");
                            }
                    );
                    card.setMedicament(medicament);
                    GridPane.setColumnIndex(card, j);
                    GridPane.setRowIndex(card, i);
                    grid.getChildren().add(card);
                }
            }
        }
    }

    @FXML
    public void rechercher(ActionEvent event) throws SQLException {
        afficherMedicaments(FXCollections.observableList(MedicamentDAO.getMedicamentsByName(r_medic.getText())));
    }

    @FXML
    public void rafraichir(ActionEvent event) throws SQLException {
        r_medic.setText("");
        afficherMedicaments(FXCollections.observableList(MedicamentDAO.getallmedicament()));
        numbertiket();
    }

    private void initializeTableView() {
        t_medicament.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getMedicament().getNom_medicament()));
        t_prix_vente.setCellValueFactory(call -> new SimpleDoubleProperty(call.getValue().getPrice_vente()).asObject());
        t_qt.setCellValueFactory(call -> new SimpleIntegerProperty(call.getValue().getQt()).asObject());
        t_prix.setCellValueFactory(call -> new SimpleDoubleProperty(call.getValue().getPrice()).asObject());
        t_vente.setItems(FXCollections.observableArrayList());
        t_vente.setOnMouseClicked(
                event -> {
                    if (t_vente.getSelectionModel().getSelectedItem() != null) {
                        Medicament medicament = t_vente.getSelectionModel().getSelectedItem().getMedicament();
                        nom_medic.setText(medicament.getNom_medicament());
                        qt_inserer.setText(t_vente.getSelectionModel().getSelectedItem().getQt() + "");
                        changed_ticket.setText("modifier");
                        up = true;
                        index_of_up = t_vente.getSelectionModel().getSelectedIndex();
                    }
                }
        );
    }

    public void numbertiket() throws SQLException {
        nb_ticket.setText(
                VenteService.getNumVenteSuivant().toString()
        );
    }

    @FXML
    public void reset_ticket(ActionEvent event) throws SQLException {
        resetTicketTable();
        t_vente.setItems(FXCollections.observableArrayList());
        parcourirtable();
        up = false;
        index_of_up = -1;
        changed_ticket.setText("ajouter");
        numbertiket();
    }

    @FXML
    public void cancel_ticket(ActionEvent event) {
        resetTicketTable();
    }

    private void resetTicketTable() {
        nom_medic.setText("");
        qt_inserer.setText("0");
    }

    private Boolean up = false;
    private int index_of_up = -1;

    @FXML
    public void AddUp(ActionEvent event) throws SQLException {
        if (up) {
            if (index_of_up != -1) {
                int qt = Integer.parseInt(qt_inserer.getText());
                if (qt > 0) {
                    Medicament medicament = MedicamentDAO.getMedicamentsByName(nom_medic.getText()).get(0);
                    if (medicament.getQt_stock() < qt){
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Il n'y a pas assez de "+medicament.getNom_medicament()+" en stock");
                        alert.show();
                    }else {
                        double prix = medicament.getPrix_vente() * qt;
                        String prixFormate = String.format(Locale.US, "%.2f", prix);
                        t_vente.getItems().set(index_of_up, new ModelVente(
                                medicament,
                                medicament.getPrix_vente(),
                                qt,
                                Double.parseDouble(prixFormate)
                        ));
                        parcourirtable();
                        up = false;
                        index_of_up = -1;
                        changed_ticket.setText("ajouter");
                        resetTicketTable();
                    }

                }
            }
        } else {
            int qt = Integer.parseInt(qt_inserer.getText());
            if (qt > 0 && !nom_medic.getText().isEmpty()) {
                Medicament medicament = MedicamentDAO.getMedicamentsByName(nom_medic.getText()).get(0);
                if (medicament.getQt_stock() < qt){
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Il n'y a pas assez de "+medicament.getNom_medicament()+" en stock");
                    alert.show();
                }else {
                    double prix = medicament.getPrix_vente() * qt;
                    String prixFormate = String.format(Locale.US, "%.2f", prix);
                    t_vente.getItems().add(new ModelVente(
                            medicament,
                            medicament.getPrix_vente(),
                            qt,
                            Double.parseDouble(prixFormate)
                    ));
                    parcourirtable();
                    resetTicketTable();
                }
            }

        }
    }

    @FXML
    public void valider(ActionEvent event) throws SQLException {

        if (!t_vente.getItems().isEmpty()) {
            Boolean ordonnance = false;
            for (ModelVente m : t_vente.getItems()) {
                if (m.getMedicament().getOrdonnance()) {
                    ordonnance = true;
                }
            }
            if (ordonnance) {
                openPrescription();
            } else {


                LocalDate localDate = LocalDate.now();
                java.util.Date utilDate = java.sql.Date.valueOf(localDate);
                Vente vente = new Vente(
                        Integer.parseInt(nb_ticket.getText()),
                        utilDate,
                        prix_totale_vente(),
                        false,
                        null
                );

                if (VenteService.addvente(vente, ligneVenteList(vente))) {
                    affimprimer(null, null);
                }
            }
        }

    }

    private Double prix_totale_vente() {
        double prix_tot = 0.0;
        for (ModelVente vente : t_vente.getItems()) {
            String formattedPrice = String.format(Locale.US, "%.2f", vente.getPrice());
            prix_tot += Double.parseDouble(formattedPrice);
        }

        return prix_tot;
    }

    private List<LigneVente> ligneVenteList(Vente vente) {
        List<LigneVente> ligneVentes = new ArrayList<>();
        for (ModelVente modelVente : t_vente.getItems()) {
            ligneVentes.add(
                    new LigneVente(
                            vente,
                            modelVente.getMedicament(),
                            modelVente.getQt()
                    )
            );
        }
        return ligneVentes;
    }

    private void dateticket() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        event -> {
                            LocalDate date = LocalDate.now();
                            LocalTime time = LocalTime.now();

                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
                            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                            String formattedDate = date.format(dateFormatter);
                            String formattedTime = time.format(timeFormatter);

                            date_ticket.setText(formattedDate + "  " + formattedTime);
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void affimprimer(String med, String patient) throws SQLException {

        ButtonType imprimerButton = new ButtonType("Imprimer", ButtonBar.ButtonData.OK_DONE);
        ButtonType continuerButton = new ButtonType("Continuer", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous imprimer le PDF ou continuer?", imprimerButton, continuerButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == imprimerButton) {
            // Logique pour imprimer le PDF
            VentePDF.creatpdf(t_vente.getItems(), med, patient, nb_ticket.getText(), date_ticket.getText());
            VentePDF.ouvrirpdf();
            resetTicketTable();
            reset_ticket(new ActionEvent());
        } else {
            affreussi();
        }

    }

    public void affreussi() throws SQLException {
        resetTicketTable();
        reset_ticket(new ActionEvent());
        // Créer une transition de translation vers droit
        AnimationPopup.animation5sPane(reussi);
    }


    //    pane ordonnance
    private void openPrescription() {
        try {
            FXMLLoader fxmlLoader = SourceFxml.getsrcFxml("Prescription");
            Parent parent = fxmlLoader.load();
            PrescriptionController prescriptionController = fxmlLoader.getController();
            prescriptionController.initializeDonnee(this, t_vente.getItems());
            Pane pane = (Pane) parent;
            int x = 1050;
            int y = 700;
            pane.setLayoutX((x - pane.getPrefWidth()) / 2);
            pane.setLayoutY((y - pane.getPrefHeight()) / 2);

            panevente.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delprescription() {
        panevente.getChildren().remove(
                panevente.getChildren().size() - 1
        );
    }

    private void parcourirtable() {
        if (!t_vente.getItems().isEmpty()) {
            double prix_tot = 0.0;
            for (ModelVente vente : t_vente.getItems()) {
                prix_tot += vente.getPrice();
                String prixFormate = String.format(Locale.US, "%.2f", prix_tot);
                t_total.setText(prixFormate);
            }
        } else {
            t_total.setText("0.0");
        }
    }

}