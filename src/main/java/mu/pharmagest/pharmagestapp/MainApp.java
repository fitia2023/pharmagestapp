package mu.pharmagest.pharmagestapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mu.pharmagest.pharmagestapp.utilis.chargerfxml;
import mu.pharmagest.pharmagestapp.utilis.chargerimages;

import java.sql.Connection;

/**
 * Classe pour lancer application
 */
public class MainApp extends Application {

    @Override
    public void start(Stage affichage) throws Exception {
        //Nom de application
        String nomApp = "Pharmagest";
        //Nom fichier pour premier vue
        String fichier = "authentification";
        //Logo de application
        String logo = "logo.png";

        Scene scene = new Scene(
                chargerfxml.getFxml(fichier)
                        .load()
                                );

        affichage.setScene(scene);
        affichage.setTitle(nomApp);
        affichage.getIcons()
                .add(
                        chargerimages.getimage(logo)
                );
        affichage.show();
    }

    //Lancement
    public static void main(String[] args) {
        launch(args);
    }
}
