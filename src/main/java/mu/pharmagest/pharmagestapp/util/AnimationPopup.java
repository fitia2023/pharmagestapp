package mu.pharmagest.pharmagestapp.util;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AnimationPopup {
    
    public static void animation5sPane(Pane pane){
        double arrive = pane.getLayoutX();
        pane.setLayoutX(0);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5), pane);
        translateTransition.setFromX(0);
        translateTransition.setToX(arrive);

        // Créer une transition d'opacité
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), pane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);

        // Afficher le popup avec animation
        pane.setVisible(true);
        translateTransition.play();
        fadeTransition.play();

        // Définir un gestionnaire pour masquer le popup après un délai
        fadeTransition.setOnFinished(event -> {
            fadeTransition.stop();
            translateTransition.stop();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            event1 -> pane.setVisible(true)
                    ),
                    new KeyFrame(Duration.seconds(5))
            );
            timeline.play();
            timeline.setOnFinished(
                    event1 ->
                            Platform.runLater(
                                    () -> pane.setVisible(false)
                            )
            );
        });
    }
}
