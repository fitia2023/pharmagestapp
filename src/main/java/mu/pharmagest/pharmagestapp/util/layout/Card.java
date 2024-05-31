package mu.pharmagest.pharmagestapp.util.layout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mu.pharmagest.pharmagestapp.Modele.Medicament;
import mu.pharmagest.pharmagestapp.Modele.Utilisateur;
import mu.pharmagest.pharmagestapp.util.SourceFxml;

import java.io.IOException;

public class Card extends Pane {

    @FXML
    public Text I_nmedic;

    @FXML
    public Text I_pricemedic;

    private Medicament medicament;

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
        I_nmedic.setText(medicament.getNom_medicament());
        I_pricemedic.setText(medicament.getPrix_vente().toString());
    }


    public Card() {
        try {
            // Charger le fichier FXML principal
            FXMLLoader fxmlLoader = SourceFxml.getsrcFxml("Layout/Card");
            fxmlLoader.setRoot(this);

            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
