package mu.pharmagest.pharmagestapp.Controller.Vente;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mu.pharmagest.pharmagestapp.LienBD.Services.CaisseService;
import mu.pharmagest.pharmagestapp.Modele.Vente;
import mu.pharmagest.pharmagestapp.util.AnimationPopup;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class CaisseController implements Initializable {
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
    public Text t_total;
    @FXML
    public Pane reussi;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            intializeTableTicket(FXCollections.observableArrayList(CaisseService.getallventenonpayer()));
            initializeTableVente();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void intializeTableTicket(ObservableList<Vente> ventes) {
        t_venteticket.setItems(ventes);
        t_numticket.setCellValueFactory(call -> new SimpleIntegerProperty(call.getValue().getId_vente()).asObject());
        t_prix_total.setCellValueFactory(call -> new SimpleDoubleProperty(call.getValue().getPrix_total()).asObject());

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

        t_venteticket.setOnMouseClicked(
                event -> {
                    if (t_venteticket.getSelectionModel().getSelectedItem() != null) {
                        try {
                            t_vente.setItems(
                                    FXCollections.observableArrayList(
                                            CaisseService.getallmodelvente(
                                                    t_venteticket.getSelectionModel().getSelectedItem()
                                            )
                                    )
                            );
                            t_total.setText(
                                    t_venteticket.getSelectionModel().getSelectedItem().getPrix_total() + ""
                            );
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    private void initializeTableVente() {
        t_medicament.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getMedicament().getNom_medicament()));
        t_prix_vente.setCellValueFactory(call -> new SimpleDoubleProperty(call.getValue().getPrice_vente()).asObject());
        t_qt.setCellValueFactory(call -> new SimpleIntegerProperty(call.getValue().getQt()).asObject());
        t_prix.setCellValueFactory(call -> new SimpleDoubleProperty(call.getValue().getPrice()).asObject());
    }

    @FXML
    public void rechercher(ActionEvent event) throws SQLException {
        String ticketText = r_ticket.getText();
        if (!ticketText.isEmpty()) { // Vérifie si le champ de texte n'est pas vide
            int ticketId = Integer.parseInt(ticketText);
            if (CaisseService.getallventenonpayerId(ticketId) != null) {
                intializeTableTicket(FXCollections.observableArrayList(CaisseService.getallventenonpayerId(ticketId)));
            } else {
                intializeTableTicket(FXCollections.observableArrayList());
            }
        }
    }


    @FXML
    public void rafraichir(ActionEvent event) throws SQLException {
        r_ticket.setText("");
        intializeTableTicket(FXCollections.observableArrayList(CaisseService.getallventenonpayer()));
    }

    @FXML
    public void cancel(ActionEvent event) {
        t_total.setText("");
        t_vente.setItems(FXCollections.observableArrayList());
    }

    @FXML
    public void payer(ActionEvent event) {
        if (t_venteticket.getSelectionModel().getSelectedItem() != null) {
            try {

                if (CaisseService.payervente(
                        t_venteticket.getSelectionModel().getSelectedItem()
                )) {
                    t_total.setText("");
                    AnimationPopup.animation5sPane(reussi);
                    cancel(new ActionEvent());
                    rafraichir(new ActionEvent());
                }
                ;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
