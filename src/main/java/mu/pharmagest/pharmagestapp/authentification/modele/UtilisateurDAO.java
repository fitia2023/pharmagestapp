package mu.pharmagest.pharmagestapp.authentification.modele;

import mu.pharmagest.pharmagestapp.database.PostgresDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsable de l'interaction avec la table Utilisateur pour l'authentification.
 */
public class UtilisateurDAO {

    private final Connection connection;

    public UtilisateurDAO() {
        this.connection = PostgresDB.getConnexion();
    }

    /**
     * Authentifie l'utilisateur en vérifiant les identifiants fournis.
     *
     * @param identifiant Identifiant de l'utilisateur.
     * @param motDePasse  Mot de passe de l'utilisateur.
     * @return true si l'authentification réussit, sinon false.
     */
    public boolean sAuthentifier(String identifiant, String motDePasse) {
        String requete = "SELECT COUNT(*) FROM Utilisateur WHERE identifiant = ? AND mot_de_passe = ? AND bloquer = false;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setString(1, identifiant);
            preparedStatement.setString(2, motDePasse);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'authentification", e);
        }
        return false;
    }
}
