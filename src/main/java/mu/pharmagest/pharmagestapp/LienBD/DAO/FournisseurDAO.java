package mu.pharmagest.pharmagestapp.LienBD.DAO;

import mu.pharmagest.pharmagestapp.LienBD.ConnectionBD;
import mu.pharmagest.pharmagestapp.Modele.Fournisseur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Pour obtenir les donnees de la base
 */
public class FournisseurDAO {

    public FournisseurDAO() {
    }

    //Obtenir tous les fournisseurs
    public static List<Fournisseur> getallfournisseurs() throws SQLException {

        String requete = "SELECT * FROM fournisseur";

        List<Fournisseur> fournisseurs = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fournisseurs.add(mapfournisseur(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la liste fournisseurs. Cause : " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Gérer l'exception de fermeture de la connexion
                    e.printStackTrace();
                }
            }
        }

        return fournisseurs;
    }

    /**
     * Ajoute un fournisseur à la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static boolean addFournisseur(Fournisseur fournisseur) throws SQLException {
        String requete = "INSERT INTO fournisseur(nom_fournisseur, adresse_fournisseur, tel_fournisseur) VALUES (?, ?, ?)";

        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setString(1, fournisseur.getNom_fournisseur());
                preparedStatement.setString(2, fournisseur.getAdresse_fournisseur());
                preparedStatement.setInt(3, fournisseur.getTel_fournisseur());

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de l'ajout du fournisseur", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Gérer l'exception de fermeture de la connexion
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Supprime un fournisseur de la base de données.
     *
     * @param idFournisseur Identifiant du fournisseur à supprimer.
     * @return True si la suppression réussit, sinon False.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static boolean deleteFournisseur(Integer idFournisseur) throws SQLException {
        String requete = "DELETE FROM fournisseur WHERE id_fournisseur = ?";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, idFournisseur);

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Vous pouvez ajouter des informations supplémentaires si nécessaire
            throw new SQLException("Erreur lors de la suppression du fournisseur. Cause : " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Gérer l'exception de fermeture de la connexion
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Met à jour les informations d'un fournisseur dans la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static Boolean updateFournisseur(Fournisseur fournisseur) throws SQLException {
        // Requête SQL pour mettre à jour un fournisseur
        String requete = "UPDATE fournisseur SET nom_fournisseur = ?, adresse_fournisseur = ?, tel_fournisseur = ? WHERE id_fournisseur = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir les valeurs des paramètres dans la requête préparée
                preparedStatement.setString(1, fournisseur.getNom_fournisseur());
                preparedStatement.setString(2, fournisseur.getAdresse_fournisseur());
                preparedStatement.setInt(3, fournisseur.getTel_fournisseur());
                preparedStatement.setInt(4, fournisseur.getId_fournisseur());

                // Exécuter la requête de mise à jour
                int rowCount = preparedStatement.executeUpdate();

                // Vérifier si au moins une ligne a été mise à jour
                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la mise à jour du fournisseur. Cause : " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Gérer l'exception de fermeture de la connexion
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Récupère une liste de fournisseurs par nom.
     *
     * @param nomFournisseur Nom du fournisseur à rechercher.
     * @return Liste de fournisseurs correspondant au nom fourni.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static List<Fournisseur> getFournisseursByName(String nomFournisseur) throws SQLException {
        // Requête SQL pour récupérer les fournisseurs par nom (utilisation de LIKE pour une recherche partielle)
        String requete = "SELECT * FROM fournisseur WHERE nom_fournisseur LIKE ?;";

        List<Fournisseur> fournisseurs = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée avec le nom du fournisseur (utilisation de % pour une recherche partielle)
                preparedStatement.setString(1, "%" + nomFournisseur + "%");

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Parcourir les résultats et ajouter les fournisseurs à la liste
                    while (resultSet.next()) {
                        fournisseurs.add(mapfournisseur(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération des fournisseurs par nom. Cause : " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Gérer l'exception de fermeture de la connexion
                    e.printStackTrace();
                }
            }
        }

        return fournisseurs;
    }

    /**
     * Récupère fournisseurs par id.
     *
     * @param id dont id_fournisseur
     * @return fournisseur correspondant à l'id.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static Fournisseur getFournisseursById(Integer id) throws SQLException {
        // Requête SQL pour récupérer les fournisseurs par nom (utilisation de LIKE pour une recherche partielle)
        String requete = "SELECT * FROM fournisseur WHERE id_fournisseur = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée avec le nom du fournisseur (utilisation de % pour une recherche partielle)
                preparedStatement.setInt(1, id);

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapfournisseur(resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération des fournisseurs par nom. Cause : " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Gérer l'exception de fermeture de la connexion
                    e.printStackTrace();
                }
            }
        }

    }

    //Obtenir fournisseur
    private static Fournisseur mapfournisseur(ResultSet resultSet) throws SQLException {

        return new Fournisseur(
                resultSet.getInt("id_fournisseur"),
                resultSet.getString("nom_fournisseur"),
                resultSet.getString("adresse_fournisseur"),
                resultSet.getInt("tel_fournisseur")
        );

    }

}
