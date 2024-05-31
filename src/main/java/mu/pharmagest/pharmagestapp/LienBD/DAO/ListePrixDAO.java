package mu.pharmagest.pharmagestapp.LienBD.DAO;

import mu.pharmagest.pharmagestapp.LienBD.ConnectionBD;
import mu.pharmagest.pharmagestapp.Modele.ListePrix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Pour obtenir les donnees de la base de la liste des prix
 */
public class ListePrixDAO {
    public ListePrixDAO() {
    }

    //Obtenir ListePrix selon fournisseur et medicament
    public static ListePrix getListPrixbyid(Integer id_medicament, Integer id_fournisseur) throws SQLException {
        String requete = "SELECT * FROM listeprix WHERE id_medicament = ? AND id_fournisseur = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée avec le nom du fournisseur (utilisation de % pour une recherche partielle)
                preparedStatement.setInt(1, id_medicament);
                preparedStatement.setInt(2, id_fournisseur);

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapListePrix(resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération listeprix par id. Cause : " + e.getMessage(), e);
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

    //Pour obtenir la liste fournisseur par un medicament
    public static List<ListePrix> listefournisseurparidmedicament(Integer id_medicament) throws SQLException {
        List<ListePrix> listePrixList = new ArrayList<>();


        String requete = "SELECT * FROM listeprix WHERE id_medicament = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée avec l'id_medicmanent
                preparedStatement.setInt(1, id_medicament);

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Parcourir les résultats et ajouter les fournisseurs à la liste
                    while (resultSet.next()) {
                        listePrixList.add(mapListePrix(resultSet));
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


        return listePrixList;
    }

    //Pour obtenir la liste des medicaments par un fournisseur
    public static List<ListePrix> listemedicamentparidfournisseur(Integer id_fournisseur) throws SQLException {
        List<ListePrix> listePrixList = new ArrayList<>();


        String requete = "SELECT * FROM listeprix WHERE id_fournisseur = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée avec l'id_medicmanent
                preparedStatement.setInt(1, id_fournisseur);

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Parcourir les résultats et ajouter les fournisseurs à la liste
                    while (resultSet.next()) {
                        listePrixList.add(mapListePrix(resultSet));
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


        return listePrixList;
    }

    //Ajouter liste prix par le fournisseur
    public static boolean addListeprix(ListePrix listePrix) throws SQLException {
        String requete = "INSERT INTO ListePrix (id_fournisseur, id_medicament, prix_unitaire, qt, prix_vente) VALUES (?, ?, ?, ?, ?);";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, listePrix.getFournisseur().getId_fournisseur());
                preparedStatement.setInt(2, listePrix.getMedicament().getId_medicament());
                preparedStatement.setDouble(3, listePrix.getPrix_unitaire_achat());
                preparedStatement.setInt(4, listePrix.getQt_min_commande());
                preparedStatement.setDouble(5, listePrix.getPrix_vente());
                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de l'ajout du liste prix", e);
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
    //Supprimer liste prix
    public static boolean deletelisteprix(Integer idFournisseur, Integer idMedicament) throws SQLException {
        String requete = "DELETE FROM Listeprix WHERE id_fournisseur = ? AND id_medicament = ?";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, idFournisseur);
                preparedStatement.setInt(2, idMedicament);
                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Vous pouvez ajouter des informations supplémentaires si nécessaire
            throw new SQLException("Erreur lors de la suppression du listeprix. Cause : " + e.getMessage(), e);
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

    //Pour mettre a jour la liste de prix
    public static boolean updateListeprix(ListePrix listePrix) throws SQLException {
        // Requête SQL pour mettre à jour un liste prix
        String requete = "UPDATE Listeprix SET prix_unitaire = ?, qt = ?, prix_vente = ? WHERE id_fournisseur = ? AND id_medicament = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir les valeurs des paramètres dans la requête préparée
                preparedStatement.setDouble(1, listePrix.getPrix_unitaire_achat());
                preparedStatement.setInt(2, listePrix.getQt_min_commande());
                preparedStatement.setDouble(3, listePrix.getPrix_vente());
                preparedStatement.setInt(4, listePrix.getFournisseur().getId_fournisseur());
                preparedStatement.setInt(5, listePrix.getMedicament().getId_medicament());
                // Exécuter la requête de mise à jour
                int rowCount = preparedStatement.executeUpdate();

                // Vérifier si au moins une ligne a été mise à jour
                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la mise à jour du liste prix. Cause : " + e.getMessage(), e);
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

    private static ListePrix mapListePrix(ResultSet resultSet) throws SQLException {
        return new ListePrix(
                FournisseurDAO.getFournisseursById(resultSet.getInt("id_fournisseur")),
                MedicamentDAO.getMedicamentById(resultSet.getInt("id_medicament")),
                resultSet.getDouble("prix_unitaire"),
                resultSet.getInt("qt"),
                resultSet.getDouble("prix_vente")

        );
    }


}
