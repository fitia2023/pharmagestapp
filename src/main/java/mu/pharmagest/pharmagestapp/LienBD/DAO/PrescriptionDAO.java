package mu.pharmagest.pharmagestapp.LienBD.DAO;

import mu.pharmagest.pharmagestapp.LienBD.ConnectionBD;
import mu.pharmagest.pharmagestapp.Modele.Prescrition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Prescription base
 */
public class PrescriptionDAO {

    public PrescriptionDAO() {
    }

    //Obtenir tous les fournisseurs
    public static List<Prescrition> getallprescription() throws SQLException {

        String requete = "SELECT * FROM prescription\n" +
                "ORDER BY id_prescription ASC;\n";

        List<Prescrition> prescritions = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                prescritions.add(mapprescription(resultSet));
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la recuperation liste prescription. Cause : " + e.getMessage(), e);
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
        return prescritions;
    }

    /**
     * Ajoute une prescription à la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static boolean addPrescription(Prescrition prescrition) throws SQLException {
        String requete = "INSERT INTO prescription(id_prescription,nom_medecin,date_prescription,nom_patient) VALUES (?,?, ?, ?)";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, prescrition.getId_prescription());

                preparedStatement.setString(2, prescrition.getNom_medecin());
                preparedStatement.setDate(3, new Date(prescrition.getDate_prescription().getTime()));
                preparedStatement.setString(4, prescrition.getNom_patient());

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de l'ajout du precription", e);
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
     * Supprime une precription de la base de données.
     *
     * @param idPrescription Identifiant de la prescription à supprimer.
     * @return True si la suppression réussit, sinon False.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static boolean deletePrescription(Integer idPrescription) throws SQLException {
        String requete = "DELETE FROM prescription WHERE id_prescription = ?";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, idPrescription);

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Vous pouvez ajouter des informations supplémentaires si nécessaire
            throw new SQLException("Erreur lors de la suppression du prescription. Cause : " + e.getMessage(), e);
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
     * Met à jour les informations d'une prescription dans la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static Boolean updatePrecription(Prescrition prescrition) throws SQLException {
        // Requête SQL pour mettre à jour un fournisseur
        String requete = "UPDATE prescription SET nom_medecin = ?, date_prescription = ?, nom_patient = ? WHERE id_prescription = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir les valeurs des paramètres dans la requête préparée
                preparedStatement.setString(1, prescrition.getNom_medecin());
                preparedStatement.setDate(2, new Date(prescrition.getDate_prescription().getTime()));
                preparedStatement.setString(3, prescrition.getNom_patient());
                preparedStatement.setInt(4, prescrition.getId_prescription());

                // Exécuter la requête de mise à jour
                int rowCount = preparedStatement.executeUpdate();

                // Vérifier si au moins une ligne a été mise à jour
                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la mise à jour du prescription. Cause : " + e.getMessage(), e);
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
     * Récupère prescription par id.
     *
     * @param id dont id_prescription
     * @return prescription correspondant à l'id.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static Prescrition getPrescriptionById(Integer id) throws SQLException {
        // Requête SQL pour récupérer les prescriptions par id
        String requete = "SELECT * FROM prescription WHERE id_prescription = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée
                preparedStatement.setInt(1, id);

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapprescription(resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération des prescriptions par nom. Cause : " + e.getMessage(), e);
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
    private static Prescrition mapprescription(ResultSet resultSet) throws SQLException {

        return new Prescrition(
                resultSet.getInt("id_prescription"),
                resultSet.getString("nom_medecin"),
                resultSet.getDate("date_prescription"),
                resultSet.getString("nom_patient")
        );

    }


}
