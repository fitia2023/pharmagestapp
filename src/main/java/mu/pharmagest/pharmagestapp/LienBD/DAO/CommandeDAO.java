package mu.pharmagest.pharmagestapp.LienBD.DAO;

import mu.pharmagest.pharmagestapp.LienBD.ConnectionBD;
import mu.pharmagest.pharmagestapp.Modele.Commande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Cette classe gère l'accès aux données commande dans la base de données.
 * Elle fournit des méthodes pour ajouter, récupérer, mettre à jour et supprimer des commandes.
 */
public class CommandeDAO {


    /****************
     *              *
     * Constructeur *
     *              *
     ****************/
    public CommandeDAO() {
    }


    /**
     * Récupère une commande à partir de son ID.
     *
     * @param id L'ID de la commande à récupérer.
     * @return La commande correspondante à l'ID, ou null s'il n'existe pas.
     * @throws SQLException Si une erreur survient lors de l'interaction avec la base de données.
     */
    public static Commande getcommandebyid(Integer id) throws SQLException {
        // Requête SQL pour récupérer la commande par id
        String requete = "SELECT * FROM commande WHERE id_commande = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée avec id commande
                preparedStatement.setInt(1, id);

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapCommande(resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération commande par id. Cause : " + e.getMessage(), e);
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

    // Obtenir le dernier ID de commande
    public static int getLastIdCommande() throws SQLException {
        String requete = "SELECT MAX(id_commande) AS last_id FROM commande;";

        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("last_id");
                    } else {
                        throw new SQLException("La table commande est vide.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération du dernier ID de commande. Cause : " + e.getMessage(), e);
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

    //Ajout du commande
    public static Boolean addCommande(Commande commande) throws SQLException {
        String requete = "INSERT INTO commande (id_commande, prix_total, id_fournisseur) VALUES (?, ?, ? );";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, commande.getId_commande());
                preparedStatement.setDouble(2, commande.getPrix_total());
                preparedStatement.setInt(3, commande.getFournisseur().getId_fournisseur());
                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de l'ajout du commande", e);
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

    //Pour changer statut en termine
    public static Boolean commandetermine(Integer id, Double prix) throws SQLException {
        String requete = "UPDATE commande SET prix_payer = ? , statut = ? WHERE id_commande = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setString(2, "Terminée");
                preparedStatement.setDouble(1, prix);
                preparedStatement.setInt(3, id);
                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de la mis a jours du commande", e);
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

    //Map de commande
    private static Commande mapCommande(ResultSet resultSet) throws SQLException {
        return new Commande(
                resultSet.getInt("id_commande"),
                resultSet.getDate("date_commande"),
                resultSet.getDouble("prix_total"),
                FournisseurDAO.getFournisseursById(resultSet.getInt("id_fournisseur")),
                resultSet.getDouble("prix_payer"),
                resultSet.getString("statut")
        );
    }
}
