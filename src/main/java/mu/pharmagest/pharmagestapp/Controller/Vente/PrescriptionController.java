package mu.pharmagest.pharmagestapp.Controller.Vente;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import mu.pharmagest.pharmagestapp.LienBD.DAO.PrescriptionDAO;
import mu.pharmagest.pharmagestapp.LienBD.Services.VenteService;
import mu.pharmagest.pharmagestapp.Modele.LigneVente;
import mu.pharmagest.pharmagestapp.Modele.Medicament;
import mu.pharmagest.pharmagestapp.Modele.Prescrition;
import mu.pharmagest.pharmagestapp.Modele.Vente;
import mu.pharmagest.pharmagestapp.util.layout.Keyboard;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Controleur de la prescription
 */

public class PrescriptionController implements Initializable {
    @FXML
    public TextField I_medecin;
    @FXML
    public DatePicker I_date;
    @FXML
    public TextField I_patient;

    private VenteController venteController;

    private ObservableList<ModelVente> modelVentes;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeKeyboard();
    }

    //affichage de keyboard
    private void initializeKeyboard() {
        Keyboard m = new Keyboard(I_medecin);
        m.show();
        Keyboard p = new Keyboard(I_patient);
        p.show();
    }

    public void initializeDonnee(VenteController venteController, ObservableList<ModelVente> modelVentes) {
        this.venteController = venteController;
        this.modelVentes = modelVentes;
    }

    @FXML
    public void ConfirmerV(ActionEvent event) throws SQLException {

        if (I_medecin.getText().isBlank() || I_patient.getText().isBlank() || I_date.getValue() == null) {
//            System.out.println("Champ vide");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs");
            alert.show();
        } else {
            addVentePrescri();
        }

    }

    private void addVentePrescri() throws SQLException {
        int id_prescription = VenteService.getNumPrescriSuivant();
        int id_vente = VenteService.getNumVenteSuivant();


        LocalDate localDate = LocalDate.now();
        java.util.Date utilDate = java.sql.Date.valueOf(localDate);

        Prescrition prescrition = new Prescrition(
                id_prescription,
                I_medecin.getText(),
                recuperationDate(),
                I_patient.getText()
        );

        if (VenteService.addventePrescription(
                prescrition,
                new Vente(
                        id_vente,
                        utilDate,
                        prix_totale(),
                        false,
                         prescrition
                ),
                ligneVenteList(
                        new Vente(
                                id_vente,
                                utilDate,
                                prix_totale(),
                                false,
                                prescrition
                        )
                )
        )) {
//            System.out.println("Bien ajoute");
            venteController.delprescription();
            venteController.affreussi();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vueillez recommencez, il y a une erreur");
            alert.show();
        }

    }

    private List<LigneVente> ligneVenteList(Vente vente) {
        List<LigneVente> ligneVentes = new ArrayList<>();
        for (ModelVente modelVente : modelVentes) {
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

    private Double prix_totale() {
        double prix_tot = 0.0;
        for (ModelVente vente : modelVentes) {
            String formattedPrice = String.format(Locale.US, "%.2f", vente.getPrice());
            prix_tot += Double.parseDouble(formattedPrice);
        }

        return prix_tot;
    }

    private Date recuperationDate() {
        // Récupérer la valeur sélectionnée dans le DatePicker
        LocalDate selectedDate = I_date.getValue();

        // Convertir LocalDate en LocalDateTime en ajoutant l'heure de début de la journée
        LocalDateTime localDateTime = selectedDate.atStartOfDay();

        // Convertir LocalDateTime en Instant
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        // Convertir Instant en Date
        return Date.from(instant);
    }

    @FXML
    public void AnnulerV(ActionEvent event) {
        venteController.delprescription();
    }
}
