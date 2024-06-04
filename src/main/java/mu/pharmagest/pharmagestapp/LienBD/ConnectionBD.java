package mu.pharmagest.pharmagestapp.LienBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connexion a Postgresql
 */
public class ConnectionBD {
    //Obtenir la connexion
    public static Connection getConnexion() {
        Connection connection = null;
        // Essayer de se connecter à la base
        try {
            Class.forName("org.postgresql.Driver");

            //Configuration de la base en production
//            String host = "postgresql-fitia2023.alwaysdata.net:5432";
//            String bd_nom = "fitia2023_pharmagest";
//            String utilisateur_bd = "fitia2023";
//            String pass_bd = "Mafoot2022";

            //Config base en local
            String host = "localhost:5432";
            String bd_nom = "PharmagestTest";
            String utilisateur_bd = "postgres";
            String pass_bd = "root";


            connection = DriverManager.getConnection("jdbc:postgresql://" + host + "/" + bd_nom, utilisateur_bd, pass_bd);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }

    /**
     * Ferme la connexion à la base de données.
     */
    public static void fermerConnexion(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la fermeture de la connexion", e);
        }
    }

}
