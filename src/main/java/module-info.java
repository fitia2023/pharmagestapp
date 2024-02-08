//Les modules utilisés pour le fonctionnement
module mu.pharmagest.pharmagestapp {

    //Javafx
    requires javafx.controls;
    requires javafx.fxml;

    //postgres
    requires java.sql;
    requires org.postgresql.jdbc;

    opens mu.pharmagest.pharmagestapp to javafx.fxml;

    exports mu.pharmagest.pharmagestapp;
}