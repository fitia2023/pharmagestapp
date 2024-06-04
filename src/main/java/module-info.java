module mu.pharmagest.pharmagestapp {
    // Dépendances pour JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires com.jfoenix;
    requires org.controlsfx.controls;
    requires kernel;
    requires layout;
    requires io;
    requires java.desktop;


    // Exportation des packages
    exports mu.pharmagest.pharmagestapp;

    exports mu.pharmagest.pharmagestapp.Controller.Authentification;

    exports mu.pharmagest.pharmagestapp.Controller.Dashboard;

    exports mu.pharmagest.pharmagestapp.Controller.Vente;


    exports mu.pharmagest.pharmagestapp.Controller;

    exports mu.pharmagest.pharmagestapp.Modele;

    exports mu.pharmagest.pharmagestapp.Controller.Admin;


    // Ouverture des packages pour l'accès par les modules JavaFX
    opens mu.pharmagest.pharmagestapp to javafx.fxml,javafx.controls;

    opens mu.pharmagest.pharmagestapp.Controller to javafx.fxml,javafx.controls;
    opens mu.pharmagest.pharmagestapp.Controller.Authentification to javafx.controls,javafx.fxml;
    opens mu.pharmagest.pharmagestapp.Controller.Admin to javafx.controls,javafx.fxml;
    exports mu.pharmagest.pharmagestapp.util.layout;
    opens mu.pharmagest.pharmagestapp.util.layout to javafx.controls, javafx.fxml;

    opens mu.pharmagest.pharmagestapp.Controller.Vente to javafx.controls,javafx.fxml;

    opens mu.pharmagest.pharmagestapp.Views;
    opens mu.pharmagest.pharmagestapp.Views.Layout;

}
