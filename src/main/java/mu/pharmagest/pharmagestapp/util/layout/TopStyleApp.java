package mu.pharmagest.pharmagestapp.util.layout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import mu.pharmagest.pharmagestapp.util.SourceFxml;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Class pour le haut d'application
 */
public class TopStyleApp extends HBox {

    @FXML
    public Label dth;

    // Afficher une fenêtre d'alerte en cas d'erreur
    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public TopStyleApp() {

        try {
            // Charger le fichier FXML principal
            FXMLLoader fxmlLoader = SourceFxml.getsrcFxml("Layout/topstyleapp");
            fxmlLoader.setRoot(this);

            fxmlLoader.setController(this);
            heurelive();
            fxmlLoader.load();

        } catch (IOException e) {
            // Gérer les erreurs de chargement du fichier FXML
            e.printStackTrace();
            displayErrorAlert("Erreur de chargement ici de l'application");
        } catch (Exception e) {
            // Gérer toute autre exception
            e.printStackTrace();
            displayErrorAlert("Une erreur inattendue ici s'est produite");
        }


    }

    public void heurelive() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        event -> {
                            LocalDate date = LocalDate.now();
                            LocalTime time = LocalTime.now();

                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
                            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                            String formattedDate = date.format(dateFormatter);
                            String formattedTime = time.format(timeFormatter);

                            dth.setText("📆 " + formattedDate + " 🕙 " + formattedTime);
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    @FXML
    void closebtn(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.close();
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setIconified(true);
    }


}
