package mu.pharmagest.pharmagestapp.util.layout;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Keyboard {
    private final TextField textField;
    private Stage affichage;
    private double xOffset, yOffset;

    private static boolean aff = true;

    public Keyboard(TextField textField) {
        this.textField = textField;
    }

    private GridPane createKeyboard() {
        GridPane keyboardLayout = new GridPane();

        String[] keyValues = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
                "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                "A", "S", "D", "F", "G", "H", "J", "K", "L",
                "Z", "X", "C", "V", "B", "N", "M", ".", "<-",
                "espace", "reset"
        };

        int col = 0, row = 0;
        for (String keyValue : keyValues) {
            Button button = getButton(keyValue);

            // Vérifier si le bouton est "reset" ou "espace" et définir colspan à 2
            if (keyValue.equals("reset") || keyValue.equals("espace")) {
                GridPane.setColumnSpan(button, 2);
                keyboardLayout.add(button, col, row, 2, 1); // Utiliser add avec colspan = 2
                col += 2; // Incrémenter col de 2 pour sauter la prochaine colonne
            } else {
                keyboardLayout.add(button, col, row);
                col++;
            }

            // Vérifier si la colonne est égale à 10 pour passer à la ligne suivante
            if (col == 10) {
                col = 0;
                row++;
            }
        }


        return keyboardLayout;
    }

    @FXML
    private Button getButton(String keyValue) {
        Button button = new Button(keyValue);
        // Définir la taille du bouton
        if (keyValue.equals("reset")) {
            button.setPrefWidth(100);
            button.setPrefHeight(50);
        } else if (keyValue.equals("espace")) {
            button.setPrefWidth(100);
            button.setPrefHeight(50);
        } else {
            button.setPrefWidth(50);
            button.setPrefHeight(50);
        }
        button.setFocusTraversable(false);
        // Définir la taille de la police du texte à l'intérieur du bouton
        button.setStyle("-fx-font-size: 16px");
        button.setOnAction(event -> {
            if (keyValue.equals("<-")) {
                deleteLastCharacter();
            } else if (keyValue.equals("reset")) {
                textField.clear();
            } else if (keyValue.equals("espace")) {
                String text = textField.getText();
                textField.setText(text + " ");
            } else {
                textField.appendText(keyValue);
            }
        });
        return button;
    }

    private void deleteLastCharacter() {
        String text = textField.getText();
        if (!text.isEmpty()) {
            textField.setText(text.substring(0, text.length() - 1));
        }
    }

    private void affkeyboard() {
        if (affichage == null) {
            affichage = new Stage();
            Parent root = createKeyboard();

            // Gestionnaire d'événements de souris pour déplacer la fenêtre
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                affichage.setX(event.getScreenX() - xOffset);
                affichage.setY(event.getScreenY() - yOffset);
            });


            affichage.setResizable(false);

            // Obtenir les dimensions de l'écran
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();


            // Calculer les coordonnées X et Y pour centrer la fenêtre
            double centerX = (screenBounds.getWidth()-500.0) / 2;
            double centerY = screenBounds.getHeight()-250;

            // Définir les coordonnées pour centrer la fenêtre
            affichage.setX(centerX);
            affichage.setY(centerY);


            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            // Définir les propriétés du stage
            affichage.setScene(scene);
            affichage.initStyle(StageStyle.TRANSPARENT);
            affichage.show();

        }
    }


    public void show() {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (textField.isFocused()) {
                if (aff) {
                    affkeyboard();
                    aff = false;
                }
            } else {
                if (!aff) {
                    outaff();
                    aff = true;
                }
            }
        });
    }

    private void outaff() {
        if (affichage != null) {
            Platform.runLater(() -> {
                affichage.close();
                affichage = null;
            });
        }
    }
}
