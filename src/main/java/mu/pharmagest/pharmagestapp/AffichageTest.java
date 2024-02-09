package mu.pharmagest.pharmagestapp;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mu.pharmagest.pharmagestapp.utilis.chargerfxml;

public class AffichageTest extends Application {
    @Override
    public void start(Stage affichage) throws Exception {
        Scene scene = new Scene( chargerfxml.getFxml("Fournisseur").load());
        // Obtenir les dimensions de l'écran
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Calculer les coordonnées pour centrer le stage
        double centerX = bounds.getMinX() + bounds.getWidth() / 2;
        double centerY = bounds.getMinY() + bounds.getHeight() / 2;

        // Définir les propriétés du stage
        affichage.setScene(scene);
        affichage.setX(centerX - (affichage.getWidth() / 2));
        affichage.setY(centerY - (affichage.getHeight() / 2));

        affichage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
