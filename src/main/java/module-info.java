//Les modules utilisés pour le fonctionnement
module mu.pharmagest.pharmagestapp {

    //Javafx
    requires javafx.controls;
    requires javafx.fxml;

    //postgres
    requires java.sql;
    requires org.postgresql.jdbc;


    opens mu.pharmagest.pharmagestapp to javafx.fxml;
    opens mu.pharmagest.pharmagestapp.fxml to javafx.fxml,javafx.controls;
    opens mu.pharmagest.pharmagestapp.authentification.controleur to javafx.fxml,javafx.controls;
    opens mu.pharmagest.pharmagestapp.gestion.controleur;

    exports mu.pharmagest.pharmagestapp;
    exports mu.pharmagest.pharmagestapp.authentification.controleur;
    exports mu.pharmagest.pharmagestapp.gestion.controleur;
    exports mu.pharmagest.pharmagestapp.dashboard.controleur;

}