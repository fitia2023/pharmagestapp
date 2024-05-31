package mu.pharmagest.pharmagestapp.util.layout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import mu.pharmagest.pharmagestapp.util.SourceFxml;

import java.io.IOException;

public class NumPad extends GridPane {

    @FXML
    private Label qt_inserer;

    public NumPad() {
        // Charger le fichier FXML principal
        FXMLLoader fxmlLoader = SourceFxml.getsrcFxml("Layout/NumPadGrid");
        fxmlLoader.setRoot(this);

        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Label getQt_insert() {
        return qt_inserer;
    }

    public void setQt_insert(Label qt_insert) {
        this.qt_inserer = qt_insert;
    }


    @FXML
    public void btn0(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 0 && qt < 999) {
            String txt = qt_inserer.getText() + "0";
            qt_inserer.setText(txt);
        }
    }

    @FXML

    public void btn1(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 0 && qt < 999) {
            String txt = qt_inserer.getText() + "1";
            qt_inserer.setText(txt);
        } else if (qt < 999) {
            String txt = "1";
            qt_inserer.setText(txt);
        }
    }

    @FXML

    public void btn2(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 0 && qt < 999) {
            String txt = qt_inserer.getText() + "2";
            qt_inserer.setText(txt);
        } else if (qt < 999) {
            String txt = "2";
            qt_inserer.setText(txt);
        }
    }
    @FXML
    public void btnsup(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 9) {
            qt_inserer.setText(qt_inserer.getText().substring(0, qt_inserer.getText().length() - 1));
        } else {
            qt_inserer.setText("0");
        }
    }
    @FXML

    public void btn4(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 0 && qt < 999) {
            String txt = qt_inserer.getText() + "4";
            qt_inserer.setText(txt);
        } else if (qt < 999) {
            String txt = "4";
            qt_inserer.setText(txt);
        }
    }

    @FXML

    public void btn5(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 0 && qt < 999) {
            String txt = qt_inserer.getText() + "5";
            qt_inserer.setText(txt);
        } else if (qt < 999) {
            String txt = "5";
            qt_inserer.setText(txt);
        }
    }

    @FXML

    public void btn3(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 0 && qt < 999) {
            String txt = qt_inserer.getText() + "3";
            qt_inserer.setText(txt);
        } else if (qt < 999) {
            String txt = "3";
            qt_inserer.setText(txt);
        }
    }

    @FXML

    public void btn6(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 0 && qt < 999) {
            String txt = qt_inserer.getText() + "6";
            qt_inserer.setText(txt);
        } else if (qt < 999) {
            String txt = "6";
            qt_inserer.setText(txt);
        }
    }

    @FXML

    public void btn8(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 0 && qt < 999) {
            String txt = qt_inserer.getText() + "8";
            qt_inserer.setText(txt);
        } else if (qt < 999) {
            String txt = "8";
            qt_inserer.setText(txt);
        }
    }

    @FXML

    public void btn9(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 0 && qt < 999) {
            String txt = qt_inserer.getText() + "9";
            qt_inserer.setText(txt);
        } else if (qt < 999) {
            String txt = "9";
            qt_inserer.setText(txt);
        }
    }

    @FXML
    public void btn7(ActionEvent event) {
        int qt = Integer.parseInt(qt_inserer.getText());
        if (qt > 0 && qt < 999) {
            String txt = qt_inserer.getText() + "7";
            qt_inserer.setText(txt);
        } else if (qt < 999) {
            String txt = "7";
            qt_inserer.setText(txt);
        }
    }
}
