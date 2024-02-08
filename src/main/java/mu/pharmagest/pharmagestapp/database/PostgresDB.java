package mu.pharmagest.pharmagestapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connexion a Postgresql
 */
public class PostgresDB {

    //Obtenir la connexion
    public static Connection getConnexion() {
        Connection connection = null;
        // Essayer de se connecter à la base
        try {
            Class.forName("org.postgresql.Driver");

            //Configuration de la base
            String host = "localhost:5432";
            String bd_nom = "pharmagest";
            String utilisateur_bd = "postgres";
            String pass_bd = "root";


            connection = DriverManager.getConnection("jdbc:postgresql://" + host + "/" + bd_nom, utilisateur_bd, pass_bd);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }

}
