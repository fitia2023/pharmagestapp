package mu.pharmagest.pharmagestapp.Controller.Dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import mu.pharmagest.pharmagestapp.LienBD.DAO.MedicamentDAO;
import mu.pharmagest.pharmagestapp.Modele.Medicament;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ChartGraphique implements Initializable {
    @FXML
    public StackedBarChart<String, Number> chart_graph;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        affichagemedicament();
    }

    private void affichagemedicament(){
        XYChart.Series<String, Number> enstock = new XYChart.Series<>();
        enstock.setName("Médicament en stock");

        XYChart.Series<String, Number> dangerstock = new XYChart.Series<>();
        dangerstock.setName("Médicament en dessous du seuil");

        try {
            List<Medicament> medicaments = MedicamentDAO.getallmedicament();
            for (Medicament medicament : medicaments){
                int qt_stock= medicament.getQt_stock();
                int qt_seuil = medicament.getSeuil_commande();
                if (qt_stock <= qt_seuil){
                    enstock.getData().add(new XYChart.Data<>(medicament.getNom_medicament(), qt_stock));
                    dangerstock.getData().add(new XYChart.Data<>(medicament.getNom_medicament(),qt_seuil - qt_stock));

                }else{
                    enstock.getData().add(new XYChart.Data<>(medicament.getNom_medicament(), qt_stock));
                }
            }
            chart_graph.getData().addAll(enstock,dangerstock);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

