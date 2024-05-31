package mu.pharmagest.pharmagestapp.LienBD.DAO;

import mu.pharmagest.pharmagestapp.LienBD.ConnectionBD;
import mu.pharmagest.pharmagestapp.Modele.Fournisseur;
import mu.pharmagest.pharmagestapp.Modele.Prescrition;
import mu.pharmagest.pharmagestapp.Modele.Vente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pour obtenir vente les donnees de la base
 */
public class VenteDAO {

    public VenteDAO() {
    }

    //Obtenir toutes les ventes
    public static List<Vente> getallvente() throws SQLException {

        String requete = "SELECT * FROM vente\n" +
                "ORDER BY id_vente ASC;\n";

        List<Vente> ventes = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ventes.add(mapvente(resultSet));
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération liste vente. Cause : " + e.getMessage(), e);
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

        return ventes;
    }

    /**
     * Ajoute une vente avec prescription à la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static boolean addVentePrescription(Vente vente) throws SQLException {
        String requete = "INSERT INTO vente(id_vente, date_vente, prix_total, payer,id_prescription) VALUES (?,?, ?, ?,?)";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, vente.getId_vente());
                preparedStatement.setDate(2, new Date(vente.getDate_vente().getTime()));
                preparedStatement.setDouble(3, vente.getPrix_total());
                preparedStatement.setBoolean(4, vente.getPayer());
                preparedStatement.setInt(5, vente.getPrescrition().getId_prescription());

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de l'ajout du prescription", e);
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
     * Ajoute une vente sans prescription à la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static boolean addVente(Vente vente) throws SQLException {
        String requete = "INSERT INTO vente(id_vente,date_vente, prix_total, payer) VALUES (?,?, ?, ?)";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, vente.getId_vente());
                preparedStatement.setDate(2, new Date(vente.getDate_vente().getTime()));
                preparedStatement.setDouble(3, vente.getPrix_total());
                preparedStatement.setBoolean(4, vente.getPayer());

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de l'ajout du prescription", e);
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
     * Supprime une vente de la base de données.
     *
     * @param idVente Identifiant de la vente à supprimer.
     * @return True si la suppression réussit, sinon False.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static boolean deleteVente(Integer idVente) throws SQLException {
        String requete = "DELETE FROM vente WHERE id_vente = ?";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, idVente);

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Vous pouvez ajouter des informations supplémentaires si nécessaire
            throw new SQLException("Erreur lors de la suppression du vente. Cause : " + e.getMessage(), e);
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
     * Met à jour les informations du vente dans la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static Boolean updateVente(Vente vente) throws SQLException {
        // Requête SQL pour mettre à jour un fournisseur
        String requete = "UPDATE vente SET date_vente = ?, prix_total = ?, payer = ?,id_prescription = ? WHERE id_vente = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                Integer id_prescription = null;
                if (vente.getPrescrition() != null) {
                    id_prescription = vente.getPrescrition().getId_prescription();
                }

                // Définir les valeurs des paramètres dans la requête préparée
                preparedStatement.setDate(1, new Date(vente.getDate_vente().getTime()));
                preparedStatement.setDouble(2, vente.getPrix_total());
                preparedStatement.setBoolean(3, vente.getPayer());
                preparedStatement.setObject(4, id_prescription);
                preparedStatement.setInt(5, vente.getId_vente());

                // Exécuter la requête de mise à jour
                int rowCount = preparedStatement.executeUpdate();

                // Vérifier si au moins une ligne a été mise à jour
                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la mise à jour du vente. Cause : " + e.getMessage(), e);
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
     * Met à jour les informations du vente dans la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static Boolean upventepayer(Vente vente) throws SQLException {
        // Requête SQL pour mettre à jour un fournisseur
        String requete = "UPDATE vente SET  payer = ? WHERE id_vente = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {

                // Définir les valeurs des paramètres dans la requête préparée
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, vente.getId_vente());

                // Exécuter la requête de mise à jour
                int rowCount = preparedStatement.executeUpdate();

                // Vérifier si au moins une ligne a été mise à jour
                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la mise à jour du vente. Cause : " + e.getMessage(), e);
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
     * Récupère une liste de vente par date.
     *
     * @param date date du vente à rechercher.
     * @return Liste de vente correspondant au nom fourni.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static List<Vente> getVentesByDate(Date date) throws SQLException {
        // Requête SQL pour récupérer les ventes par date
        String requete = "SELECT * FROM vente WHERE date_vente = ?;";

        List<Vente> ventes = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée avec la date du vente
                preparedStatement.setDate(1, date);

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Parcourir les résultats et ajouter les fournisseurs à la liste
                    while (resultSet.next()) {
                        ventes.add(mapvente(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération des vente par date. Cause : " + e.getMessage(), e);
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

        return ventes;
    }

    /**
     * Récupère vente par id.
     *
     * @param id dont id_vente
     * @return vente correspondant à l'id.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static Vente getVenteById(Integer id) throws SQLException {
        // Requête SQL pour récupérer les ventes par nom
        String requete = "SELECT * FROM vente WHERE id_vente = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée avec le id du vente
                preparedStatement.setInt(1, id);

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapvente(resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération des vente par id. Cause : " + e.getMessage(), e);
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
    private static Vente mapvente(ResultSet resultSet) throws SQLException {
        return new Vente(
                resultSet.getInt("id_vente"),
                resultSet.getDate("date_vente"),
                resultSet.getDouble("prix_total"),
                resultSet.getBoolean("payer"),
                PrescriptionDAO.getPrescriptionById(
                        resultSet.getInt("id_prescription")
                )
        );

    }

}
